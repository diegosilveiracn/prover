package analyzer;

public class ScannerToken {

	public enum TokenType {
		LITERAL, NEGATION, AND, OR, IMPLICATION, BICONDITIONAL,
		LEFT_PAREN, RIGHT_PAREN, COMMA, ERROR, EOF
	};

	private TokenType tokenType = TokenType.ERROR;

	private String tokenString;

	private String message;

	public ScannerToken() {
	}
	
	public ScannerToken(TokenType tokenType, String tokenString) {
		setTokenType(tokenType);
		setTokenString(tokenString);
	}

	public void set(ScannerToken token) {
		this.tokenType = token.tokenType;
		this.tokenString = token.tokenString;
		this.message = token.message;
	}
	
	public String getTokenString() {
		return tokenString;
	}

	public void setTokenString(String tokenString) {
		this.tokenString = tokenString;
	}

	public TokenType getTokenType() {
		return tokenType;
	}

	public void setTokenType(TokenType tokenType) {
		this.tokenType = tokenType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public boolean isError() {
		return tokenType == TokenType.ERROR;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof ScannerToken))
			return false;
		return this.getTokenType() == ((ScannerToken) obj).getTokenType();
	}
	
	public String toString() {
		return getTokenString() + ": " + getMessage();
	}
}
