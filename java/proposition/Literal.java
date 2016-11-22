package proposition;

import java.util.ArrayList;
import java.util.List;

import resolution.Clause;

public class Literal implements Proposition, Comparable<Literal> {

	private static final long serialVersionUID = 6391800694205025347L;

	private Boolean negated;

	private String literalString;

	public Literal() {
		negated = new Boolean(Boolean.FALSE);
	}

	public Literal(Literal literal) {
		this(literal.negated, literal.getLiteralString());
	}
	
	public Literal(String literal) {
		negated = new Boolean(Boolean.FALSE);
		this.setLiteralString(literal);
	}

	public Literal(Boolean negated, String literal) {
		this.negated = negated;
		this.setLiteralString(literal);
	}

	public boolean complementaryPair(Literal literal) {
		if (this.literalString.equals(literal.literalString) && (this.negated ^ literal.negated)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public String getLiteralString() {
		return literalString;
	}

	public void setLiteralString(String literal) {
		this.literalString = literal;
	}

	public Boolean isNegated() {
		return negated;
	}

	public void setNegated(Boolean negated) {
		this.negated = negated;
	}
	
	public void negate() {
		this.negated = !this.negated;
	}

	public boolean equals(Object obj) {
		boolean ret = false;
		if (obj instanceof Literal) {
			Literal literal = (Literal) obj;
			ret = this.literalString.equals(literal.literalString) && (this.negated == literal.negated);
		}
		return ret;
	}

	public String toString() {
		return negated ? "~" + literalString : literalString;
	}

	public int compareTo(Literal literal) {
		boolean compPair = this.complementaryPair(literal);// && this.isNegated();
		if (!compPair)
			return this.getLiteralString().compareTo(literal.getLiteralString());
		else if (compPair && this.isNegated())
			return 1;
		else
			return -1;
	}

	public List<Clause> getClauses() {
		Clause clause = new Clause();
		clause.addLiteral(this);
		List<Clause> clauses = new ArrayList<Clause>();
		clauses.add(clause);
		return clauses;
	}
	
	public Proposition clone() {
		return new Literal(this);
	}

	public Proposition getOriginalProposition() {
		return this;
	}

}