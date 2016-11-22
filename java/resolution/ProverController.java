package resolution;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import proposition.Proposition;
import analyzer.CompilationException;
import analyzer.SyntaticAnalyzer;
import data.DataPrintStream;

public class ProverController {

	private SyntaticAnalyzer parser = new SyntaticAnalyzer();

	private ResolutionAlgorithm resolution = new ResolutionAlgorithm();

	private List<Proposition> propositions;

	public ProverController() {
	}

	public List<Proposition> getLastPropositions() {
		return parser.getPropositions();
	}

	public KnowlegdeBase getBase() {
		return resolution.getBase();
	}

	public boolean compileBase(InputStream is) throws IOException,
			CompilationException {
		parser.compile(is);
		KnowlegdeBase base = new KnowlegdeBase();
		base.setPropositions(parser.getPropositions());
		return resolution.setBase(base);
	}

	public void compileTheorem(InputStream is) throws IOException,
			CompilationException {
		parser.compile(is);
		propositions = parser.getPropositions();
	}

	public void proveTheorems() {
		Iterator<Proposition> iterator = propositions.iterator();
		while (iterator.hasNext()) {
			Proposition theorem = iterator.next();
			resolution.setTheorem(theorem);
			if (resolution.execute())
				DataPrintStream.println("\nTheorem "
						+ theorem.getOriginalProposition()
						+ " was proved sucessfully!");
			else
				DataPrintStream.println("\nTheorem "
						+ theorem.getOriginalProposition()
						+ " can not be proved!");
			DataPrintStream.println();
		}
	}
	
	public void setCompiledBase(KnowlegdeBase base) {
		resolution.setBase(base);
	}
	
	public void printDetailedInfo(boolean flag) {
		resolution.printDetailedInfo(flag);
	}

}
