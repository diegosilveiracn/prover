package resolution;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

import proposition.Proposition;
import data.DataPrintStream;

public class ResolutionAlgorithm {

	private KnowlegdeBase base;

	private Proposition theorem;

	private boolean printDetailed = false;

	private long timeOut = 30000;

	private long initTime;

	public ResolutionAlgorithm() {
	}

	public KnowlegdeBase getBase() {
		return base;
	}

	public long getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(long timeOut) {
		this.timeOut = timeOut;
	}

	private boolean timeOut() {
		return System.currentTimeMillis() - initTime >= timeOut;
	}

	public boolean setBase(KnowlegdeBase base) {
		boolean baseIntegrity = true;
		DataPrintStream.println("Checking the knowlegde base integrity...");
		this.base = base;
		boolean flag = printDetailed;
		printDetailed = false;
		baseIntegrity = checkBaseIntegrity();

		if (baseIntegrity)
			DataPrintStream
					.println("Knowlegde base does not contain contradictions!");
		else {
			DataPrintStream.println("Knowlegde base contains contradictions!");
			DataPrintStream
					.println("The current knowledge base was discarded!");
			this.base = null;
		}

		printDetailed = flag;
		return baseIntegrity;
	}

	public Proposition getTheorem() {
		return theorem;
	}

	public void setTheorem(Proposition theorem) {
		this.theorem = theorem;
	}

	public void printDetailedInfo(boolean flag) {
		printDetailed = flag;
	}

	private boolean checkBaseIntegrity() {
		// This comparator orders the clauses by the number of literals
		Comparator<Clause> comparator = new Comparator<Clause>() {
			public int compare(Clause clause1, Clause clause2) {
				int num1 = clause1.numLiterals();
				int num2 = clause2.numLiterals();
				if (num1 == num2) {
					return clause1.compareTo(clause2);
				} else if (num1 > num2)
					return 1;
				else
					return -1;
			}
		};

		// Set of already processed clauses
		Set<Clause> processedClauses = new TreeSet<Clause>(comparator);
		// Priority queue of new derived clauses
		// The priority is given by the number of literals the clause has
		// The less, the higher is its priority
		PriorityQueue<Clause> newClausesQueue = new PriorityQueue<Clause>(11,
				comparator);

		newClausesQueue.addAll(base.getClauses());
		return !executeResolution(processedClauses, newClausesQueue);
	}

	private Clause testPair(Clause clause1, Clause clause2) {
		Clause clause3 = clause1.removeComplementaryPair(clause2);
		if (printDetailed)
			DataPrintStream.println("\t" + clause1 + "\tand\t" + clause2
					+ "\t=\t"
					+ (clause3 == null ? "no complemenatary pairs" : clause3));
		return clause3;
	}

	private boolean removeSuperSets(Set<Clause> clauses, Clause clause) {
		if (clauses.contains(clause)) {
			if (printDetailed)
				DataPrintStream.println("\tThe clause " + clause
						+ " already exists");
			return true;
		}

		Iterator<Clause> iter = clauses.iterator();
		Clause nextClause = null;
		while (iter.hasNext()) {
			nextClause = iter.next();
			if (clause.subSet(nextClause)) {
				if (printDetailed)
					DataPrintStream.println("\tExisting clause " + nextClause
							+ " is a subclause of new clause " + clause
							+ " (discarded)");
				return true;
			} else if (nextClause.subSet(clause)) {
				if (printDetailed)
					DataPrintStream.println("\tNew clause " + clause
							+ " is a subclause of existing clause "
							+ nextClause + " (removed)");
				iter.remove();
			}
		}
		return false;
	}

	private boolean testAllPairs(Set<Clause> clauses, Clause clause,
			Queue<Clause> clausesQueue) {
		Clause clause1 = null;
		Clause clause2 = clause;
		Clause clause3 = null;
		Iterator<Clause> iter = clauses.iterator();
		while (iter.hasNext()) {
			clause1 = iter.next();
			clause3 = testPair(clause1, clause2);
			if (clause3 != null && !clause3.isTautology()) {
				if (clause3.isEmpty())
					return true;
				clausesQueue.offer(clause3);
			}
		}
		return false;
	}

	private boolean executeResolution(Set<Clause> processedClauses,
			Queue<Clause> newClausesQueue) {
		// Save initial execution time to check for timeout
		initTime = System.currentTimeMillis();

		Clause clause = null;
		// Process all pairs of clauses
		while (!newClausesQueue.isEmpty()) {
			// If the time is out then stop the resolution algorithm

			if (timeOut()) {
				DataPrintStream.println("\n>>>>> TIME OUT <<<<<");
				return false;
			}

			clause = newClausesQueue.poll();
			if (!removeSuperSets(processedClauses, clause)) {
				if (testAllPairs(processedClauses, clause, newClausesQueue))
					return true;
				processedClauses.add(clause);
			}
		}

		return false;
	}

	public boolean execute() {
		// Print some information
		DataPrintStream.println("Trying to prove theorem: " + theorem);
		DataPrintStream.println();
		Proposition theoremNegation = theorem.clone();
		theoremNegation.negate();
		DataPrintStream.println("\tTheorem negation: " + theoremNegation);
		List<Clause> theoremClauses = theoremNegation.getClauses();
		DataPrintStream
				.println("\tTheorem negation in CNF: " + theoremNegation);
		DataPrintStream.println();
		if (base != null)
			DataPrintStream.println("\tBase clauses: " + base.getClauses());
		else
			DataPrintStream.println("\tBase clauses: empty");
		DataPrintStream.println("\tTheorem clauses: " + theoremClauses);
		DataPrintStream.println();

		// This comparator orders the clauses by the number of literals
		Comparator<Clause> comparator = new Comparator<Clause>() {
			public int compare(Clause clause1, Clause clause2) {
				int num1 = clause1.numLiterals();
				int num2 = clause2.numLiterals();
				if (num1 == num2) {
					return clause1.compareTo(clause2);
				} else if (num1 > num2)
					return 1;
				else
					return -1;
			}
		};

		// Set of already processed clauses
		Set<Clause> processedClauses = new TreeSet<Clause>(comparator);
		// Priority queue of new derived clauses
		// The priority is given by the number of literals the clause has
		// The less, the higher is its priority
		PriorityQueue<Clause> newClausesQueue = new PriorityQueue<Clause>(11,
				comparator);

		if (base != null)
			processedClauses.addAll(base.getClauses());
		newClausesQueue.addAll(theoremClauses);

		return executeResolution(processedClauses, newClausesQueue);
	}
}