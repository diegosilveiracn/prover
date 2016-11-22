package analyzer;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import proposition.CompositeProposition;
import proposition.Literal;
import proposition.Operator;
import proposition.Proposition;
import proposition.Operator.OperatorType;
import analyzer.SyntaxTreeNode.NodeType;

public class SyntaxTree {

	private SyntaxTreeNode root;

	List<Proposition> propositionList = new LinkedList<Proposition>();

	public SyntaxTree(SyntaxTreeNode root) {
		this.root = root;
	}

	public List<Proposition> getPropositionList() {
		return propositionList;
	}

	public void buildPropositionList() {
		List<SyntaxTreeNode> children = root.getChildren();
		Iterator<SyntaxTreeNode> iterator = children.iterator();
		boolean prop = true;
		while (iterator.hasNext()) {
			SyntaxTreeNode node = iterator.next();
			if (prop) {
				propositionList.add(getProposition(node));
				prop = false;
			} else
				prop = true;
		}
	}

	private Proposition getProposition(SyntaxTreeNode node) {
		Proposition proposition = null;

		List<SyntaxTreeNode> children = node.getChildren();
		Iterator<SyntaxTreeNode> iterator = children.iterator();
		if (node.numChildren() > 3) {
			proposition = getProposition(iterator);
		} else if (node.numChildren() == 3) {
			SyntaxTreeNode node1 = iterator.next();
			SyntaxTreeNode node2 = iterator.next();
			SyntaxTreeNode node3 = iterator.next();
			if (node1.getNodeType() == NodeType.TOKEN
					&& node3.getNodeType() == NodeType.TOKEN) {
				proposition = getProposition(node2);
			} else if (node2.getNodeType() == NodeType.TOKEN) {
				Proposition leftProposition = getProposition(node1);
				Proposition rightProposition = getProposition(node3);
				Operator operator = new Operator();
				switch (node2.getToken().getTokenType()) {
				case AND:
					operator.setType(OperatorType.AND);
					break;
				case OR:
					operator.setType(OperatorType.OR);
					break;
				case IMPLICATION:
					operator.setType(OperatorType.IMPLICATION);
					break;
				case BICONDITIONAL:
					operator.setType(OperatorType.BICONDICIONAL);
					break;
				}
				proposition = new CompositeProposition(leftProposition,
						operator, rightProposition);
			}
		} else if (node.numChildren() == 2) {
			SyntaxTreeNode node1 = iterator.next();
			SyntaxTreeNode node2 = iterator.next();
			if (node1.getNodeType() == NodeType.TOKEN) {
				proposition = getProposition(node2);
				//proposition.setNegated(Boolean.TRUE);
				proposition.setNegated(!proposition.isNegated());
			}
		} else if (node.numChildren() == 1) {
			SyntaxTreeNode node1 = iterator.next();
			if (node1.getNodeType() == NodeType.TOKEN)
				proposition = new Literal(node1.getToken().getTokenString());
			else
				proposition = getProposition(node1);
		}

		return proposition;
	}

	private Proposition getProposition(Iterator<SyntaxTreeNode> iterator) {
		Proposition proposition = null;

		SyntaxTreeNode node1 = iterator.next();
		if (iterator.hasNext()) {
			SyntaxTreeNode node2 = iterator.next();
			Proposition leftProposition = getProposition(node1);
			Proposition rightProposition = getProposition(iterator);
			Operator operator = new Operator();
			switch (node2.getToken().getTokenType()) {
			case AND:
				operator.setType(OperatorType.AND);
				break;
			case OR:
				operator.setType(OperatorType.OR);
				break;
			case IMPLICATION:
				operator.setType(OperatorType.IMPLICATION);
				break;
			case BICONDITIONAL:
				operator.setType(OperatorType.BICONDICIONAL);
				break;
			}
			proposition = new CompositeProposition(leftProposition, operator,
					rightProposition);
		} else {
			proposition = getProposition(node1);
		}

		return proposition;
	}

}
