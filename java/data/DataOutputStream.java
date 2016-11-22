package data;

import java.io.IOException;
import java.io.OutputStream;

public class DataOutputStream extends OutputStream {

	private DataPrinter dataPrinter;
	
	public DataOutputStream(DataPrinter dataPrinter) {
		setDataPrinter(dataPrinter);
	}

	public void write(int b) throws IOException {
		dataPrinter.write(b);
	}

	public DataPrinter getDataPrinter() {
		return dataPrinter;
	}

	public void setDataPrinter(DataPrinter dataPrinter) {
		this.dataPrinter = dataPrinter;
	}
	
}
