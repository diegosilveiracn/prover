package resolution;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import proposition.Proposition;

public class KnowlegdeBase implements Serializable {

	private static final long serialVersionUID = 2014029823765216664L;
	
	List<Proposition> propositions = new LinkedList<Proposition>();
	
	List<Clause> clauses = new LinkedList<Clause>();
	
	public KnowlegdeBase() {
	}

	public List<Proposition> getPropositions() {
		return propositions;
	}

	public void setPropositions(List<Proposition> propositions) {
		this.propositions = propositions;
		Iterator<Proposition> iterator = propositions.iterator();
		while (iterator.hasNext()) {
			List<Clause> propClauses = iterator.next().getClauses();
			clauses.addAll(propClauses);
		}
	}

	public List<Clause> getClauses() {
		return clauses;
	}
	
}
