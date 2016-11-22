package form;

import proposition.CompositeProposition;
import proposition.Literal;
import proposition.Operator;
import proposition.Proposition;
import proposition.Operator.OperatorType;

public class Main {

	public static void main(String[] args) {
		new ProverForm().setVisible(Boolean.TRUE);
		
		/*List<Literal> list = new LinkedList<Literal>();
		list.add(new Literal("a"));
		//list.add(new Literal("b"));
		//list.add(new Literal("c"));
		Clause clause1 = new Clause(list);
		list.clear();
		list.add(new Literal(true, "a"));
		//list.add(new Literal("f"));
		//list.add(new Literal("g"));
		Clause clause2 = new Clause(list);
		System.out.println(clause1.removeComplementaryPair(clause2));*/
		
		/*Proposition prop = new CompositeProposition(new Literal("a"),
				new Operator(OperatorType.IMPLICATION), new Literal("b"));
		System.out.println(prop);
		prop.setNegated(true);
		System.out.println(prop);
		prop.negate();
		System.out.println(prop);*/
	}
}
