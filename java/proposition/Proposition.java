package proposition;

import java.io.Serializable;
import java.util.List;

import resolution.Clause;

public interface Proposition extends Serializable {

	public Boolean isNegated();
	
	public void setNegated(Boolean negated);
	
	public void negate();

	public List<Clause> getClauses();
	
	public Proposition clone();
	
	public Proposition getOriginalProposition();

}