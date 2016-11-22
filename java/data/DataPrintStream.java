package data;

import java.io.PrintStream;
import java.util.Locale;

public class DataPrintStream {

	public static PrintStream out = null;
	
	private DataPrintStream() {
	}
	
	public static void setDataPrinter(DataPrinter dataPrinter) {
		out = new PrintStream(new DataOutputStream(dataPrinter));
	}

	public static PrintStream append(char c) {
		// TODO Auto-generated method stub
		return out.append(c);
	}

	
	public static PrintStream append(CharSequence csq, int start, int end) {
		// TODO Auto-generated method stub
		return out.append(csq, start, end);
	}

	
	public static PrintStream append(CharSequence csq) {
		// TODO Auto-generated method stub
		return out.append(csq);
	}

	
	public static boolean checkError() {
		// TODO Auto-generated method stub
		return out.checkError();
	}

	
	public static void close() {
		// TODO Auto-generated method stub
		out.close();
	}

	
	public static void flush() {
		// TODO Auto-generated method stub
		out.flush();
	}

	
	public static PrintStream format(Locale l, String format, Object... args) {
		// TODO Auto-generated method stub
		return out.format(l, format, args);
	}

	
	public static PrintStream format(String format, Object... args) {
		// TODO Auto-generated method stub
		return out.format(format, args);
	}

	
	public static void print(boolean b) {
		// TODO Auto-generated method stub
		out.print(b);
	}

	
	public static void print(char c) {
		// TODO Auto-generated method stub
		out.print(c);
	}

	
	public static void print(char[] s) {
		// TODO Auto-generated method stub
		out.print(s);
	}

	
	public static void print(double d) {
		// TODO Auto-generated method stub
		out.print(d);
	}

	
	public static void print(float f) {
		// TODO Auto-generated method stub
		out.print(f);
	}

	
	public static void print(int i) {
		// TODO Auto-generated method stub
		out.print(i);
	}

	
	public static void print(long l) {
		// TODO Auto-generated method stub
		out.print(l);
	}

	
	public static void print(Object obj) {
		// TODO Auto-generated method stub
		out.print(obj);
	}

	
	public static void print(String s) {
		// TODO Auto-generated method stub
		out.print(s);
	}

	
	public static PrintStream printf(Locale l, String format, Object... args) {
		// TODO Auto-generated method stub
		return out.printf(l, format, args);
	}

	
	public static PrintStream printf(String format, Object... args) {
		// TODO Auto-generated method stub
		return out.printf(format, args);
	}

	
	public static void println() {
		// TODO Auto-generated method stub
		out.println();
	}

	
	public static void println(boolean x) {
		// TODO Auto-generated method stub
		out.println(x);
	}

	
	public static void println(char x) {
		// TODO Auto-generated method stub
		out.println(x);
	}

	
	public static void println(char[] x) {
		// TODO Auto-generated method stub
		out.println(x);
	}

	
	public static void println(double x) {
		// TODO Auto-generated method stub
		out.println(x);
	}

	
	public static void println(float x) {
		// TODO Auto-generated method stub
		out.println(x);
	}

	
	public static void println(int x) {
		// TODO Auto-generated method stub
		out.println(x);
	}

	
	public static void println(long x) {
		// TODO Auto-generated method stub
		out.println(x);
	}

	
	public static void println(Object x) {
		// TODO Auto-generated method stub
		out.println(x);
	}

	
	public static void println(String x) {
		// TODO Auto-generated method stub
		out.println(x);
	}
	
	public static void write(byte[] buf, int off, int len) {
		// TODO Auto-generated method stub
		out.write(buf, off, len);
	}

	
	public static void write(int b) {
		// TODO Auto-generated method stub
		out.write(b);
	}
	
}
