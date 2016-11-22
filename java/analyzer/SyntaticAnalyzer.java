package analyzer;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import proposition.Proposition;

import analyzer.ScannerToken.TokenType;
import analyzer.SyntaxTreeNode.NodeType;
import data.DataPrintStream;

public class SyntaticAnalyzer {

	private LexicalAnalyzer lexicalAnalyzer;

	private ScannerToken currentToken;

	private SyntaxTree syntaxTree;

	public void compileTest(InputStream inputStream) throws IOException {
		this.lexicalAnalyzer = new LexicalAnalyzer(inputStream);

		ScannerToken token = null;
		do {
			token = lexicalAnalyzer.nextToken();
			DataPrintStream.println(token);
		} while (token.getTokenType() != TokenType.EOF);
	}

	public void compile(InputStream inputStream) throws IOException,
			CompilationException {
		this.lexicalAnalyzer = new LexicalAnalyzer(inputStream);
		currentToken = lexicalAnalyzer.nextToken();
		SyntaxTreeNode root = PROP_LIST();
		if (currentToken.getTokenType() != TokenType.EOF)
			throw new CompilationException("Syntatic error at line "
					+ lexicalAnalyzer.getLine() + " near "
					+ currentToken.getTokenString());
		syntaxTree = new SyntaxTree(root);
		syntaxTree.buildPropositionList();
	}

	private void match(ScannerToken expectedToken) throws IOException,
			CompilationException {
		if (currentToken.equals(expectedToken)) {
			currentToken = lexicalAnalyzer.nextToken();
			if (currentToken.isError())
				throw new CompilationException(currentToken.getMessage());
		} else
			throw new CompilationException("Syntatic error at line "
					+ lexicalAnalyzer.getLine() + ": found "
					+ currentToken.getTokenString() + ", expected "
					+ expectedToken.getTokenString());
	}

	private SyntaxTreeNode PROP_LIST() throws IOException, CompilationException {
		SyntaxTreeNode node = new SyntaxTreeNode(NodeType.PROP_LIST);
		node.addChild(PROP5());
		ScannerToken commaToken = new ScannerToken(TokenType.COMMA, "Comma");
		while (currentToken.getTokenType() == TokenType.COMMA) {
			match(commaToken);
			node.addChild(new SyntaxTreeNode(commaToken));
			node.addChild(PROP5());
		}
		return node;
	}

	private SyntaxTreeNode PROP5() throws IOException, CompilationException {
		SyntaxTreeNode node = new SyntaxTreeNode(NodeType.PROP5);
		node.addChild(PROP4());
		ScannerToken implicationToken = new ScannerToken(TokenType.IMPLICATION,
				"Implication");
		while (currentToken.getTokenType() == TokenType.IMPLICATION) {
			match(implicationToken);
			node.addChild(new SyntaxTreeNode(implicationToken));
			node.addChild(PROP4());
		}
		return node;
	}

	private SyntaxTreeNode PROP4() throws IOException, CompilationException {
		SyntaxTreeNode node = new SyntaxTreeNode(NodeType.PROP4);
		node.addChild(PROP3());
		ScannerToken biconditionalToken = new ScannerToken(
				TokenType.BICONDITIONAL, "Biconditional");
		while (currentToken.getTokenType() == TokenType.BICONDITIONAL) {
			match(biconditionalToken);
			node.addChild(new SyntaxTreeNode(biconditionalToken));
			node.addChild(PROP3());
		}
		return node;
	}

	private SyntaxTreeNode PROP3() throws IOException, CompilationException {
		SyntaxTreeNode node = new SyntaxTreeNode(NodeType.PROP3);
		node.addChild(PROP2());
		ScannerToken andToken = new ScannerToken(TokenType.AND, "And");
		while (currentToken.getTokenType() == TokenType.AND) {
			match(andToken);
			node.addChild(new SyntaxTreeNode(andToken));
			node.addChild(PROP2());
		}
		return node;
	}

	private SyntaxTreeNode PROP2() throws IOException, CompilationException {
		SyntaxTreeNode node = new SyntaxTreeNode(NodeType.PROP2);
		node.addChild(PROP1());
		ScannerToken orToken = new ScannerToken(TokenType.OR, "or");
		while (currentToken.getTokenType() == TokenType.OR) {
			match(orToken);
			node.addChild(new SyntaxTreeNode(orToken));
			node.addChild(PROP1());
		}
		return node;
	}

	private SyntaxTreeNode PROP1() throws IOException, CompilationException {
		SyntaxTreeNode node = new SyntaxTreeNode(NodeType.PROP1);
		ScannerToken leftParenToken = new ScannerToken(TokenType.LEFT_PAREN,
				"Left parentheses");
		ScannerToken rightParenToken = new ScannerToken(TokenType.RIGHT_PAREN,
				"Right parentheses");
		ScannerToken literalToken = new ScannerToken(TokenType.LITERAL,
				"Literal");
		ScannerToken negationToken = new ScannerToken(TokenType.NEGATION,
				"Negation");

		if (currentToken.getTokenType() == TokenType.LITERAL) {
			node.addChild(new SyntaxTreeNode(currentToken));
			match(literalToken);
		} else if (currentToken.getTokenType() == TokenType.LEFT_PAREN) {
			match(leftParenToken);
			node.addChild(new SyntaxTreeNode(leftParenToken));
			node.addChild(PROP5());
			match(rightParenToken);
			node.addChild(new SyntaxTreeNode(rightParenToken));
		} else if (currentToken.getTokenType() == TokenType.NEGATION) {
			match(negationToken);
			node.addChild(new SyntaxTreeNode(negationToken));
			node.addChild(PROP1());
		} else if (currentToken.getTokenType() == TokenType.EOF)
			throw new CompilationException(
					"Syntax error: unexpected end of file at line"
							+ lexicalAnalyzer.getLine());
		else
			throw new CompilationException(
					"Syntax error: unknown error at line"
							+ lexicalAnalyzer.getLine());
		return node;
	}

	public List<Proposition> getPropositions() {
		return syntaxTree.getPropositionList();
	}

}