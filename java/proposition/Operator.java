package proposition;

import java.io.Serializable;

public class Operator implements Serializable {

	private static final long serialVersionUID = -2054579603920019449L;

	public enum OperatorType {
		AND, OR, IMPLICATION, BICONDICIONAL, ERROR
	};
	
	private OperatorType operatorType = OperatorType.ERROR;

	public Operator() {
	}

	public Operator(Operator operator) {
		this(operator.operatorType);
	}
	
	public Operator(OperatorType operatorType) {
		this.setType(operatorType);
	}

	public OperatorType getType() {
		return operatorType;
	}

	public void setType(OperatorType operatorType) {
		this.operatorType = operatorType;
	}
	
	public Operator clone() {
		return new Operator(this);
	}
	
	public String toString() {
		switch (operatorType) {
		case AND: return "&&";
		case BICONDICIONAL: return "<->";
		case IMPLICATION: return "->";
		case OR: return "||";
		default: return "ERROR";
		}
	}
	
}