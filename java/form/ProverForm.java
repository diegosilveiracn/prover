package form;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;

import proposition.Proposition;
import resolution.KnowlegdeBase;
import resolution.ProverController;
import data.DataPrintStream;
import data.DataPrinter;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class ProverForm extends javax.swing.JFrame implements DataPrinter {

	private JMenuItem jMenuItemProve;

	private JMenu jMenuTheorem;

	private JMenuItem deleteMenuItem;

	private JSeparator jSeparator1;

	private JMenuItem pasteMenuItem;

	private JTextArea jTextAreaStatus;

	private JTextArea jTextAreaProposition;

	private JScrollPane jScrollPane2;

	private JScrollPane jScrollPane1;

	private JPanel jPanel2;

	private JPanel jPanel1;

	private JCheckBoxMenuItem jCheckBoxMenuInfo;

	private JMenuItem jMenuItemLoadBase;

	private JMenuItem copyMenuItem;

	private JMenuItem exitMenuItem;

	private JSeparator jSeparator2;

	private JMenuItem closeFileMenuItem;

	private JMenuItem saveAsMenuItem;

	private JMenuItem saveMenuItem;

	private JMenuItem openFileMenuItem;

	private JMenu jMenuBase;

	private JMenuBar jMenuBar1;

	private ProverController controller = new ProverController();
	
	private boolean detailedInfo = false;

	public ProverForm() {
		super();
		initGUI();
		DataPrintStream.setDataPrinter(this);
	}

	private void initGUI() {
		try {
			{
				this.setTitle("Theorem Prover");
				this.setFocusTraversalKeysEnabled(false);
				BoxLayout thisLayout = new BoxLayout(getContentPane(),
						javax.swing.BoxLayout.Y_AXIS);
				getContentPane().setLayout(thisLayout);
				this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				{
					jPanel1 = new JPanel();
					BoxLayout jPanel1Layout = new BoxLayout(jPanel1,
							javax.swing.BoxLayout.Y_AXIS);
					getContentPane().add(jPanel1);
					jPanel1.setBounds(14, 14, 567, 147);
					jPanel1.setBorder(BorderFactory
							.createTitledBorder("Proposition"));
					jPanel1.setLayout(jPanel1Layout);
					{
						jScrollPane1 = new JScrollPane();
						jPanel1.add(jScrollPane1);
						jScrollPane1.setBounds(7, 21, 553, 119);
						{
							jTextAreaProposition = new JTextArea();
							jScrollPane1.setViewportView(jTextAreaProposition);
							jTextAreaProposition.setFont(new java.awt.Font("Tahoma",0,16));
						}
					}
				}
				{
					jPanel2 = new JPanel();
					BoxLayout jPanel2Layout = new BoxLayout(jPanel2,
							javax.swing.BoxLayout.Y_AXIS);
					getContentPane().add(jPanel2);
					jPanel2.setBounds(14, 175, 567, 112);
					jPanel2.setBorder(BorderFactory
							.createTitledBorder("Status"));
					jPanel2.setLayout(jPanel2Layout);
					{
						jScrollPane2 = new JScrollPane();
						jPanel2.add(jScrollPane2);
						jScrollPane2.setBounds(7, 21, 553, 84);
						jScrollPane2.setAutoscrolls(true);
						jScrollPane2.getVerticalScrollBar().setAutoscrolls(true);
						{
							jTextAreaStatus = new JTextArea();
							jScrollPane2.setViewportView(jTextAreaStatus);
							jTextAreaStatus.setFont(new java.awt.Font("Tahoma",0,16));
						}
					}
				}
			}
			this.setSize(603, 353);
			{
				jMenuBar1 = new JMenuBar();
				setJMenuBar(jMenuBar1);
				{
					jMenuBase = new JMenu();
					jMenuBar1.add(jMenuBase);
					jMenuBase.setText("Base");
					{
						openFileMenuItem = new JMenuItem();
						jMenuBase.add(openFileMenuItem);
						openFileMenuItem.setText("Open Base");
						openFileMenuItem
								.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent evt) {
										openFileMenuItemActionPerformed(evt);
									}
								});
					}
					{
						jMenuItemLoadBase = new JMenuItem();
						jMenuBase.add(jMenuItemLoadBase);
						jMenuItemLoadBase.setText("Load Base");
						jMenuItemLoadBase
								.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent evt) {
										jMenuItemLoadBaseActionPerformed(evt);
									}
								});
					}
					{
						saveMenuItem = new JMenuItem();
						jMenuBase.add(saveMenuItem);
						saveMenuItem.setText("Save Base");
						saveMenuItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								saveMenuItemActionPerformed(evt);
							}
						});
					}
					{
						jSeparator2 = new JSeparator();
						jMenuBase.add(jSeparator2);
					}
					{
						exitMenuItem = new JMenuItem();
						jMenuBase.add(exitMenuItem);
						exitMenuItem.setText("Exit");
					}
				}
				{
					jMenuTheorem = new JMenu();
					jMenuBar1.add(jMenuTheorem);
					jMenuTheorem.setText("Theorem");
					{
						jMenuItemProve = new JMenuItem();
						jMenuTheorem.add(jMenuItemProve);
						jMenuItemProve.setText("Prove");
						jMenuItemProve.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								jMenuItemProveActionPerformed(evt);
							}
						});
					}
					{
						jSeparator1 = new JSeparator();
						jMenuTheorem.add(jSeparator1);
					}
					{
						jCheckBoxMenuInfo = new JCheckBoxMenuItem();
						jMenuTheorem.add(jCheckBoxMenuInfo);
						jCheckBoxMenuInfo.setText("Detailed Info");
						jCheckBoxMenuInfo
							.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								jCheckBoxMenuInfoActionPerformed(evt);
							}
							});
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void write(int b) throws IOException {

		this.jTextAreaStatus.append(new Character((char) b).toString());
	}

	private void cutMenuItemActionPerformed(ActionEvent evt) {

	}

	private void jMenuItemLoadBaseActionPerformed(ActionEvent evt) {
		if (jTextAreaProposition.getText().length() == 0) {
			JOptionPane.showMessageDialog(this, "There is nothing to load",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		byte[] array = this.jTextAreaProposition.getText().getBytes();
		final InputStream is = new ByteArrayInputStream(array);
		new Thread() {
			public void run() {
				try {
					jTextAreaStatus.setText("");
					DataPrintStream
							.println("Compiling the base propositions...");
					if (!controller.compileBase(is))
						return;
					DataPrintStream.println("Compilation process done!");
					DataPrintStream
							.println("The following propositions were loaded:");
					Iterator<Proposition> iterator = controller
							.getLastPropositions().iterator();
					DataPrintStream.println();
					while (iterator.hasNext()) {
						Proposition proposition = iterator.next();
						DataPrintStream.println("\tOriginal propositional: "
								+ proposition.getOriginalProposition());
						DataPrintStream.println("\tProposition in CNF: "
								+ proposition);
						DataPrintStream.println();
					}
				} catch (Exception e) {
					e.printStackTrace(DataPrintStream.out);
				}
			}
		}.start();
	}

	private void saveMenuItemActionPerformed(ActionEvent evt) {
		JFileChooser fileChooser = new JFileChooser();

		fileChooser.setFileFilter(new FileFilter() {
			public boolean accept(File file) {
				return file.getName().toLowerCase().endsWith(".peb");
			}

			public String getDescription() {
				return "Peba file";
			}
		});

		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int result = fileChooser.showSaveDialog(this);

		if (result == JFileChooser.CANCEL_OPTION)
			return;

		File file = fileChooser.getSelectedFile();
		if (file == null || file.getName().equals(""))
			JOptionPane.showMessageDialog(this, "Inavalid File Name",
					"Inavalid File Name", JOptionPane.ERROR_MESSAGE);
		else {
			try {
				ObjectOutputStream os = new ObjectOutputStream(
						new FileOutputStream(file));
				KnowlegdeBase base = controller.getBase();
				os.writeObject(base);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Error Saving File",
						"Error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace(DataPrintStream.out);
			}
		}
	}

	private void jMenuItemProveActionPerformed(ActionEvent evt) {
		if (jTextAreaProposition.getText().length() == 0) {
			JOptionPane.showMessageDialog(this, "There is nothing to prove",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		byte[] array = this.jTextAreaProposition.getText().getBytes();
		final InputStream is = new ByteArrayInputStream(array);
		new Thread() {
			public void run() {
				try {
					jTextAreaStatus.setText("");
					DataPrintStream.println("Compiling the theorems...");
					controller.compileTheorem(is);
					DataPrintStream.println("Compilation process done!");
					DataPrintStream
							.println("The following propositions were loaded:");
					Iterator<Proposition> iterator = controller
							.getLastPropositions().iterator();
					DataPrintStream.println();
					while (iterator.hasNext()) {
						Proposition proposition = iterator.next();
						DataPrintStream
								.println("\tProposition: " + proposition);
						DataPrintStream.println();
					}
					controller.proveTheorems();
				} catch (Exception e) {
					e.printStackTrace(DataPrintStream.out);
				}
			}
		}.start();
	}

	private void openFileMenuItemActionPerformed(ActionEvent evt) {
		JFileChooser fileChooser = new JFileChooser();

		fileChooser.setFileFilter(new FileFilter() {
			public boolean accept(File file) {
				return file.getName().toLowerCase().endsWith(".peb");
			}

			public String getDescription() {
				return "Peba file";
			}
		});

		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int result = fileChooser.showOpenDialog(this);

		if (result == JFileChooser.CANCEL_OPTION)
			return;

		File file = fileChooser.getSelectedFile();
		if (file == null || file.getName().equals(""))
			JOptionPane.showMessageDialog(this, "Inavalid file name",
					"Inavalid file name", JOptionPane.ERROR_MESSAGE);
		else {
			try {
				ObjectInputStream is = new ObjectInputStream(
						new FileInputStream(file));
				KnowlegdeBase base = (KnowlegdeBase) is.readObject();
				controller.setCompiledBase(base);
				DataPrintStream.println("Compiled base loaded");
				DataPrintStream.println("Base propositions:");
				DataPrintStream.println();
				Iterator<Proposition> iterator = base.getPropositions()
						.iterator();
				while (iterator.hasNext()) {
					Proposition proposition = iterator.next();
					DataPrintStream.println("\tOriginal propositional: "
							+ proposition.getOriginalProposition());
					DataPrintStream.println("\tProposition in CNF: "
							+ proposition);
					DataPrintStream.println();
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Error opening file",
						"Error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace(DataPrintStream.out);
			} catch (ClassNotFoundException e) {
				JOptionPane.showMessageDialog(this,
						"This file is not a valid base", "Error",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace(DataPrintStream.out);
			}
		}

	}
	
	private void jCheckBoxMenuInfoActionPerformed(ActionEvent evt) {
		detailedInfo = !detailedInfo;
		controller.printDetailedInfo(detailedInfo);
	}
}
