package proposition;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import proposition.Operator.OperatorType;
import resolution.Clause;

public class CompositeProposition implements Proposition {

	private static final long serialVersionUID = 5325416263728674765L;

	private Proposition leftProposition;

	private Proposition rightProposition;

	private Operator operator;

	private Boolean negated = Boolean.FALSE;

	private List<Clause> clauses = null;

	private CompositeProposition originalProposition;

	public CompositeProposition() {
	}

	public CompositeProposition(Proposition leftProposition, Operator operator,
			Proposition rightProposition) {
		setProposition(leftProposition, operator, rightProposition);
	}

	public Proposition getOriginalProposition() {
		return originalProposition == null ? this : originalProposition;
	}

	public void setProposition(CompositeProposition proposition) {
		setProposition(proposition.leftProposition, proposition.operator,
				proposition.rightProposition);
	}

	public void setProposition(Proposition leftProposition, Operator operator,
			Proposition rightProposition) {
		setLeftProposition(leftProposition);
		setOperator(operator);
		setRightProposition(rightProposition);
	}

	public Proposition getLeftProposition() {
		return leftProposition;
	}

	public void setLeftProposition(Proposition leftProposition) {
		this.leftProposition = leftProposition;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public Proposition getRightProposition() {
		return rightProposition;
	}

	public void setRightProposition(Proposition rightProposition) {
		this.rightProposition = rightProposition;
	}

	public List<Clause> getClauses() {
		if (this.clauses != null)
			return this.clauses;
		originalProposition = (CompositeProposition) this.clone();
		toConjunctiveNormalForm();
		return getClausesRecursive();
	}

	private List<Clause> getClausesRecursive() {
		if (this.clauses != null)
			return this.clauses;

		clauses = new ArrayList<Clause>();

		if (this.operator.getType() == OperatorType.AND) {
			if (this.leftProposition instanceof CompositeProposition)
				clauses.addAll(((CompositeProposition) this.leftProposition)
						.getClausesRecursive());
			else {
				Clause clause = new Clause();
				clause.addLiteral((Literal) this.leftProposition);
				clauses.add(clause);
			}
			if (this.rightProposition instanceof CompositeProposition)
				clauses.addAll(((CompositeProposition) this.rightProposition)
						.getClausesRecursive());
			else {
				Clause clause = new Clause();
				clause.addLiteral((Literal) this.rightProposition);
				clauses.add(clause);
			}
		} else {
			Set<Literal> literals = new TreeSet<Literal>();
			if (this.leftProposition instanceof CompositeProposition)
				literals.addAll(((CompositeProposition) this.leftProposition)
						.getLiterals());
			else
				literals.add((Literal) this.leftProposition);
			if (this.rightProposition instanceof CompositeProposition)
				literals.addAll(((CompositeProposition) this.rightProposition)
						.getLiterals());
			else
				literals.add((Literal) this.rightProposition);
			Clause clause = new Clause(literals);
			clauses.add(clause);
		}

		return clauses;
	}

	public void negate() {
		if (negated) {
			negated = Boolean.FALSE;
			return;
		}
		switch (operator.getType()) {
		case AND:
			leftProposition.negate();
			operator.setType(OperatorType.OR);
			rightProposition.negate();
			break;
		case OR:
			leftProposition.negate();
			operator.setType(OperatorType.AND);
			rightProposition.negate();
			break;
		case IMPLICATION:
			operator.setType(OperatorType.AND);
			rightProposition.negate();
			break;
		case BICONDICIONAL:
			CompositeProposition left = new CompositeProposition();
			left.setLeftProposition(leftProposition.clone());
			left.setOperator(new Operator(OperatorType.OR));
			left.setRightProposition(rightProposition.clone());
			CompositeProposition right = new CompositeProposition();
			right.setLeftProposition(rightProposition.clone());
			right.getLeftProposition().negate();
			right.setOperator(new Operator(OperatorType.OR));
			right.setRightProposition(leftProposition.clone());
			right.getRightProposition().negate();
			this.setLeftProposition(left);
			this.operator.setType(OperatorType.AND);
			this.setRightProposition(right);
			break;
		}
	}

	private CompositeProposition DistributeOR() {
		if (operator.getType() != OperatorType.OR)
			return null;
		CompositeProposition proposition = null;
		if (leftProposition instanceof CompositeProposition) {
			CompositeProposition left = (CompositeProposition) leftProposition
					.clone();
			if (left.getOperator().getType() == OperatorType.AND) {
				left.getOperator().setType(OperatorType.OR);
				Proposition tempRight = left.getRightProposition().clone();
				left.setRightProposition(rightProposition);
				CompositeProposition right = new CompositeProposition();
				right.setLeftProposition(tempRight);
				right.setOperator(new Operator(OperatorType.OR));
				right.setRightProposition(rightProposition);
				proposition = new CompositeProposition(left, new Operator(
						OperatorType.AND), right);
			}
		} else if (rightProposition instanceof CompositeProposition) {
			CompositeProposition right = (CompositeProposition) rightProposition
					.clone();
			if (right.getOperator().getType() == OperatorType.AND) {
				CompositeProposition left = new CompositeProposition();
				left.setLeftProposition(leftProposition);
				left.setOperator(new Operator(OperatorType.OR));
				left.setRightProposition(right.getLeftProposition());
				right.setLeftProposition(leftProposition);
				right.getOperator().setType(OperatorType.OR);
				proposition = new CompositeProposition(left, new Operator(
						OperatorType.AND), right);
			}
		}
		return proposition;
	}

	private void toConjunctiveNormalForm() {
		if (this.negated) {
			this.negated = Boolean.FALSE;
			this.negate();
		}

		if (leftProposition instanceof CompositeProposition)
			((CompositeProposition) (this.leftProposition))
					.toConjunctiveNormalForm();
		if (rightProposition instanceof CompositeProposition)
			((CompositeProposition) (this.rightProposition))
					.toConjunctiveNormalForm();

		switch (this.operator.getType()) {
		case AND:
			break;
		case OR:
			CompositeProposition proposition = DistributeOR();
			if (proposition != null) {
				this.setProposition(proposition);
				this.toConjunctiveNormalForm();
			}
			break;
		case IMPLICATION:
			this.leftProposition.negate();
			this.operator.setType(OperatorType.OR);
			this.toConjunctiveNormalForm();
			break;
		case BICONDICIONAL:
			Proposition tempLeft = this.leftProposition.clone();
			this.leftProposition = new CompositeProposition(tempLeft.clone(),
					new Operator(OperatorType.IMPLICATION),
					this.rightProposition.clone());
			this.operator.setType(OperatorType.AND);
			this.rightProposition = new CompositeProposition(
					this.rightProposition.clone(), new Operator(
							OperatorType.IMPLICATION), tempLeft.clone());
			this.toConjunctiveNormalForm();
			break;
		}
	}

	private Set<Literal> getLiterals() {
		Set<Literal> literals = new TreeSet<Literal>();
		if (leftProposition instanceof Literal)
			literals.add((Literal) leftProposition);
		else
			literals.addAll(((CompositeProposition) leftProposition)
					.getLiterals());
		if (rightProposition instanceof Literal)
			literals.add((Literal) rightProposition);
		else
			literals.addAll(((CompositeProposition) rightProposition)
					.getLiterals());
		return literals;
	}

	public Boolean isNegated() {
		return negated;
	}

	public void setNegated(Boolean negated) {
		this.negated = negated;
	}

	public Proposition clone() {
		CompositeProposition cloneProposition = new CompositeProposition();
		cloneProposition.setLeftProposition(leftProposition.clone());
		cloneProposition.setOperator(operator.clone());
		cloneProposition.setRightProposition(rightProposition.clone());
		cloneProposition.negated = this.negated;
		return cloneProposition;
	}

	public String toString() {
		String ret = "(" + leftProposition.toString() + " " + operator.toString()
				+ " " + rightProposition.toString() + ")";
		return (!negated ? ret : "~" + ret);
	}

}
