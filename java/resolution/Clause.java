package resolution;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import proposition.Literal;

public class Clause implements Comparable<Clause>, Serializable {

	private static final long serialVersionUID = 7702889199103167299L;

	private Set<Literal> literals;

	private boolean tautology = false;

	public Clause() {
		this.literals = new TreeSet<Literal>(new Comparator<Literal>() {
			public int compare(Literal l1, Literal l2) {
				return l1.compareTo(l2);
			}
		});
	}

	public Clause(Clause clause) {
		this.literals = new TreeSet<Literal>(clause.literals);
	}

	public Clause(Collection<Literal> literals) {
		this.literals = new TreeSet<Literal>(literals);
	}

	public Clause(boolean tautology) {
		this.literals = new TreeSet<Literal>(new Comparator<Literal>() {
			public int compare(Literal l1, Literal l2) {
				return l1.compareTo(l2);
			}
		});
		this.tautology = tautology;
	}

	public int numLiterals() {
		return literals.size();
	}

	public boolean isTautology() {
		return tautology;
	}

	public Iterator<Literal> iterator() {
		return this.literals.iterator();
	}

	public Clause removeComplementaryPair(Clause clause) {
		Clause resultClause = null;
		Clause clause1 = this.clone();
		Clause clause2 = clause.clone();
		ComplementaryPair complementaryPair = null;

		Iterator<Literal> iter1 = clause1.iterator();
		Iterator<Literal> iter2 = clause2.iterator();
		if (!iter1.hasNext() && !iter2.hasNext())
			return null;

		Literal literal1 = iter1.next();
		Literal literal2 = iter2.next();

		int compare;
		do {
			compare = literal1.getLiteralString().compareTo(
					literal2.getLiteralString());
			while (compare > 0 && iter2.hasNext()) {
				literal2 = iter2.next();
				compare = literal1.getLiteralString().compareTo(
						literal2.getLiteralString());
			}
			if (compare == 0 && literal1.complementaryPair(literal2)) {
				if (complementaryPair != null)
					return new Clause(true);
				complementaryPair = new ComplementaryPair(literal1, clause1,
						literal2, clause2);
				if (iter1.hasNext())
					literal1 = iter1.next();
				else if (iter2.hasNext())
					literal2 = iter2.next();
			} else if (compare < 0) {
				Literal literalT = literal1;
				literal1 = literal2;
				literal2 = literalT;
				Iterator<Literal> iterT = iter1;
				iter1 = iter2;
				iter2 = iterT;
				Clause clauseT = clause1;
				clause1 = clause2;
				clause2 = clauseT;
			} else if (iter1.hasNext())
				literal1 = iter1.next();
			else if (iter2.hasNext())
				literal2 = iter2.next();
		} while (iter1.hasNext() || iter2.hasNext());

		if (complementaryPair != null) {
			complementaryPair.remove();
			resultClause = new Clause(clause1);
			resultClause.literals.addAll(clause2.literals);
		}
		return resultClause;
	}

	public boolean isEmpty() {
		if (tautology)
			return false;
		return literals.isEmpty();
	}

	public boolean isUnitary() {
		return literals.size() == 1;
	}

	public String toString() {
		if (tautology)
			return "tautology";
		if (literals.isEmpty())
			return "empty clause";
		return this.literals.toString();
	}

	public void addLiteral(Literal literal) {
		this.literals.add(literal);
	}

	public void addAll(Collection<Literal> literals) {
		this.literals.addAll(literals);
	}

	public boolean removeLiteral(Literal literal) {
		return this.literals.remove(literal);
	}

	public boolean equals2(Object obj) {
		if (obj instanceof Clause) {
			Clause clause = (Clause) obj;
			return this.literals.equals(clause.literals);
		}
		return false;
	}

	public boolean equals(Object obj) {
		if (obj instanceof Clause) {
			Clause clause = (Clause) obj;
			return this.compareTo(clause) == 0;
		}
		return false;
	}

	public boolean subSet(Clause clause) {
		return this.literals.containsAll(clause.literals);
	}

	public int compareTo(Clause clause) {
		return this.toString().compareTo(clause.toString());
	}

	public Clause clone() {
		return new Clause(this);
	}

}

class ComplementaryPair {

	Literal literal1;

	Literal literal2;

	Clause clause1;

	Clause clause2;

	public ComplementaryPair(Literal literal1, Clause clause1,
			Literal literal2, Clause clause2) {
		this.literal1 = literal1;
		this.literal2 = literal2;
		this.clause1 = clause1;
		this.clause2 = clause2;
	}

	public void remove() {
		clause1.removeLiteral(literal1);
		clause2.removeLiteral(literal2);
	}

}