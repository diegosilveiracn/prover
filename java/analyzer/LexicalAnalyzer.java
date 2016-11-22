package analyzer;

import java.io.IOException;
import java.io.InputStream;

import analyzer.ScannerToken.TokenType;

public class LexicalAnalyzer {

	private enum CharacterType {
		ALPHA_NUM, SYMBOL, SPACE, ERROR, EOF
	};

	// private InputStream input;

	private ScannerToken nextToken;

	private StringBuffer buffer = new StringBuffer();

	private int bufferIndex = 0;

	private StringBuffer tokenString;

	private int character;

	private CharacterType charType;

	private int line = 1;

	public LexicalAnalyzer(InputStream input) throws IOException {
		int available = input.available();
		byte[] data = new byte[available];
		input.read(data);
		buffer.append(new String(data));
	}

	private void nextCharacter() throws IOException {
		if (bufferIndex < buffer.length()) {
			character = (int) buffer.charAt(bufferIndex++);
			tokenString.append((char) character);
		} else {
			character = -1;
			charType = CharacterType.EOF;
			return;
		}

		if (isAlphaNumeric(character))
			charType = CharacterType.ALPHA_NUM;
		else if (isSymbol(character))
			charType = CharacterType.SYMBOL;
		else if (isSpace(character))
			charType = CharacterType.SPACE;
		else
			charType = CharacterType.ERROR;
	}

	private void rowBackCharacter() {
		bufferIndex--;
		tokenString.deleteCharAt(tokenString.length() - 1);
	}

	private boolean isAlphaNumeric(int character) {
		return (character >= 'a' && character <= 'z' || character >= 'A'
				&& character <= 'Z' || character >= '0' && character <= '9');
	}

	private boolean isSymbol(int character) {
		return (character >= '(' && character <= '-' || character >= '<'
				&& character <= '>' || character == '/' || character == '&'
				|| character == '|' || character == '~');
	}

	private boolean isSpace(int character) {
		return character == ' ' || character == '\r' || character == '\n';
	}

	private ScannerToken isKeyword() throws IOException {
		ScannerToken token = new ScannerToken();
		switch (character) {
		case '&':
			nextCharacter();
			if (character == '&') {
				token.setTokenType(TokenType.AND);
				token.setTokenString("&&");
				token.setMessage("And");
				if (nextToken.getTokenType() == TokenType.LITERAL)
					rowBackCharacter();
			} else {
				rowBackCharacter();
			}
			break;
		case '|':
			nextCharacter();
			if (character == '|') {
				token.setTokenType(TokenType.OR);
				token.setTokenString("||");
				token.setMessage("Or");
				if (nextToken.getTokenType() == TokenType.LITERAL)
					rowBackCharacter();
			} else {
				rowBackCharacter();
			}
			break;
		case '-':
			nextCharacter();
			if (character == '>') {
				token.setTokenType(TokenType.IMPLICATION);
				token.setTokenString("->");
				token.setMessage("Implication");
				if (nextToken.getTokenType() == TokenType.LITERAL)
					rowBackCharacter();
			} else {
				rowBackCharacter();
			}
			break;
		case '<':
			nextCharacter();
			if (character == '-') {
				nextCharacter();
				if (character == '>') {
					token.setTokenType(TokenType.BICONDITIONAL);
					token.setTokenString("<->");
					token.setMessage("Biconditional");
					if (nextToken.getTokenType() == TokenType.LITERAL) {
						rowBackCharacter();
						rowBackCharacter();
					}
				} else {
					rowBackCharacter();
					rowBackCharacter();
				}
			} else {
				rowBackCharacter();
			}
			break;
		case '~':
			token.setTokenType(TokenType.NEGATION);
			token.setTokenString("~");
			token.setMessage("Negation");
			break;
		case '(':
			token.setTokenType(TokenType.LEFT_PAREN);
			token.setTokenString("(");
			token.setMessage("Left parentheses");
			break;
		case ')':
			token.setTokenType(TokenType.RIGHT_PAREN);
			token.setTokenString(")");
			token.setMessage("Right parentheses");
			break;
		case ',':
			token.setTokenType(TokenType.COMMA);
			token.setTokenString(",");
			token.setMessage("Comma");
			break;
		}
		return token;
	}

	private void stateAlphaNum() throws IOException {
		nextToken.setTokenType(TokenType.LITERAL);
		do {
			nextCharacter();
		} while (charType == CharacterType.ALPHA_NUM);
		switch (charType) {
		case SYMBOL:
			stateSymbol();
			break;
		case SPACE:
			stateSpace();
			break;
		case EOF:
			stateEof();
			break;
		default:
			stateError();
		}
	}

	private void stateSymbol() throws IOException {
		boolean done = false;
		while (charType == CharacterType.SYMBOL) {
			ScannerToken token = isKeyword();
			if (token.getTokenType() != TokenType.ERROR) {
				if (nextToken.getTokenType() == TokenType.LITERAL) {
					rowBackCharacter();
					nextToken.setTokenString(tokenString.toString().trim());
					nextToken.setMessage("Literal");
				} else {
					nextToken.set(token);
				}
				done = true;
				break;
			} else
				nextCharacter();
		}
		if (!done) {
			switch (charType) {
			case ALPHA_NUM:
				stateAlphaNum();
				break;
			case SPACE:
				stateSpace();
				break;
			case EOF:
				stateEof();
				break;
			default:
				stateError();
			}
		}

	}

	private void stateSpace() throws IOException {
		if (nextToken.getTokenType() == TokenType.LITERAL) {
			if (character == '\n') {
				rowBackCharacter();
				tokenString.append(" ");
				bufferIndex++;
				line++;
			} else if (character == '\r')
				rowBackCharacter();
			stateAlphaNum();
		} else {
			while (charType == CharacterType.SPACE) {
				if (character == '\n')
					line++;
				nextCharacter();
			}
			if (charType == CharacterType.ALPHA_NUM)
				stateAlphaNum();
			else if (charType == CharacterType.SYMBOL)
				stateSymbol();
		}
	}

	private void stateEof() {
		if (nextToken.getTokenType() == TokenType.LITERAL) {
			nextToken.setTokenString(tokenString.toString().trim());
			nextToken.setMessage("Literal");
		} else {
			nextToken.setTokenString("EOF");
			nextToken.setMessage("End of file");
			nextToken.setTokenType(TokenType.EOF);
		}
	}

	private void stateError() {
		if (nextToken.getTokenType() != TokenType.LITERAL) {
			nextToken.setTokenType(TokenType.ERROR);
			nextToken.setTokenString(tokenString.toString().trim());
			nextToken.setMessage("Lexical Error at line " + line
					+ ": character " + nextToken.getTokenString()
					+ " out of grammar");
		} else {
			rowBackCharacter();
			nextToken.setTokenString(tokenString.toString().trim());
			nextToken.setMessage("Literal");
		}
	}

	public ScannerToken nextToken() throws IOException {
		nextToken = new ScannerToken();
		tokenString = new StringBuffer();

		nextCharacter();
		switch (charType) {
		case ALPHA_NUM:
			stateAlphaNum();
			break;
		case SYMBOL:
			stateSymbol();
			break;
		case SPACE:
			stateSpace();
			break;
		case ERROR:
			stateError();
			break;
		case EOF:
			stateEof();
			break;
		}

		return nextToken;
	}

	public int getLine() {
		return line;
	}

}
