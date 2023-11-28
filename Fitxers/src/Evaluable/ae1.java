package Evaluable;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.xml.crypto.dsig.spec.XPathType.Filter;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class ae1 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtDirectori;
	private final ButtonGroup bgOrdre = new ButtonGroup();
	private JTextField txtBusqueda;
	private JTextArea txtaInfo;
	private JComboBox cmbFiltro;
	private JRadioButton rdbtnAscendent;

	/**
	 * Funció per a obtindre la extensio de un fitcher a partir del seu nom.
	 * 
	 * @param nombre Nom del fitcher.
	 * @return String amb la extensio del fitcher.
	 */
	public static String Extensio(String nombre) {
		int posicio = nombre.lastIndexOf(".");
		if (posicio == -1) {
			return "ERROR: No es troba la extensio del archiu.";
		} else {
			return nombre.substring(posicio + 1);
		}
	}

	/**
	 * Funcio per a llistar tots els archius de un directori pasat per paramatre.
	 * 
	 * @param dir Directori del que volem llistar els archius.
	 */
	public void Llistar(File dir) {
		FiltroExtension filtro = new FiltroExtension(".txt");
		String text = "NOM \t\t EXTENSIÓ \t\t GRANDARIA \t\t ULTIMA DATA DE MODIFICACIÓ\n";
		if (dir.exists() && dir.isDirectory()) {
			File[] archivos = dir.listFiles(filtro);
			OrdenarArchius(archivos, cmbFiltro.getSelectedItem().toString(), rdbtnAscendent.isSelected());
			for (File file : archivos) {
				text += file.getName() + "\t\t" + Extensio(file.getName()) + "\t\t" + file.length() + " bytes \t\t"
						+ LocalDate.ofEpochDay(file.lastModified() / 86400000L) + "\n";
			}
			txtaInfo.setText(text);
		} else {
			txtaInfo.setText("ERROR: No se ha trobat el directori.");
		}
	}

	/**
	 * Funció per a ordenar un Array de archius.
	 * 
	 * @param archius Array de archius que volem ordenar.
	 * @param criteri String en el criteri de ordenacio (Nom/Grandaria/Data)
	 * @param ordre   Boolean per a definir ordre ascendent o descendent (true ->
	 *                ascendent // false -> descendent).
	 */
	public void OrdenarArchius(File[] archius, String criteri, boolean ordre) {

		Comparator<File> comparador;
		if (criteri.equalsIgnoreCase("Nom")) {
			comparador = Comparator.comparing(File::getName);
		} else if (criteri.equalsIgnoreCase("Grandaria")) {
			comparador = Comparator.comparingLong(File::length);
		} else if (criteri.equalsIgnoreCase("Data")) {
			comparador = Comparator.comparingLong(File::lastModified);
		} else {
			txtaInfo.setText("Error. Se ordenara per nom ascendent per defecte.");
			comparador = Comparator.comparing(File::getName);
		}

		if (!ordre) {
			comparador = comparador.reversed();
		}

		Arrays.sort(archius, comparador);
	}

	/**
	 * Funcio per a trobar les coincidences en un grup de archius a partir de la
	 * ruta i la paraula que volem trobar.
	 * 
	 * @param ruta    Ruta on estan els archius que volem revisar.
	 * @param paraula Paraula que volem trobar en els archius.
	 */
	public void Coincidencies(String ruta, String paraula) {
		File dir = new File(ruta);
		FiltroExtension filtre = new FiltroExtension(".txt");
		File[] fitchers;
		int numeroCoincidencies = 0;
		String coincidencies = "Paraula : " + paraula + "\n\n";
		if (dir.exists() && dir.isDirectory()) {
			fitchers = dir.listFiles(filtre);
			if (fitchers.length > 0) {
				for (File file : fitchers) {
					try {
						FileReader fr = new FileReader(file);
						BufferedReader br = new BufferedReader(fr);
						String text = br.readLine();
						do {
							if (text.contains(paraula))
								numeroCoincidencies++;
							text = br.readLine();
						} while (text != null);
						fr.close();
						br.close();
						coincidencies += numeroCoincidencies + " coincidencies en el archiu: " + file.getName() + "\n";
						System.out.println(coincidencies);
						numeroCoincidencies = 0;

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				txtaInfo.setText(coincidencies);
			} else {
				txtaInfo.setText("No ni ha ningun archiu .txt en el directori.");
			}
		} else {
			txtaInfo.setText("Error: No se troba el directori.");
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ae1 frame = new ae1();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ae1() {
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage("C:\\Users\\Hugo\\AD\\Fitxers\\Fotos\\hand_document_list_paper_file_icon_219540.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 700);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(144, 238, 144));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtDirectori = new JTextField();
		txtDirectori.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtDirectori.setBounds(39, 117, 268, 34);
		contentPane.add(txtDirectori);
		txtDirectori.setColumns(10);

		JScrollPane spFitxeros = new JScrollPane();
		spFitxeros.setBounds(404, 117, 448, 455);
		contentPane.add(spFitxeros);

		txtaInfo = new JTextArea();
		txtaInfo.setEditable(false);
		spFitxeros.setViewportView(txtaInfo);

		JLabel lblDirectori = new JLabel("Introdueix el directori:");
		lblDirectori.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblDirectori.setBounds(39, 69, 286, 38);
		contentPane.add(lblDirectori);

		JButton btnDirectorio = new JButton("");
		btnDirectorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int respuesta = fc.showOpenDialog(null);
				if (respuesta == JFileChooser.APPROVE_OPTION) {
					File archivoElegido = fc.getSelectedFile();
					txtDirectori.setText(archivoElegido.getAbsolutePath());
					Llistar(archivoElegido);
				}
			}
		});
		btnDirectorio.setBackground(new Color(144, 238, 144));
		ImageIcon imageIcon = new ImageIcon(
				"C:\\Users\\Hugo\\AD\\Fitxers\\Fotos\\foldersearchfilelocatefinddocument-115838_115791.png");
		Image image = imageIcon.getImage();
		Image newimg = image.getScaledInstance(-1, 34, java.awt.Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(newimg);
		btnDirectorio.setIcon(imageIcon);
		btnDirectorio.setBounds(317, 117, 59, 34);
		contentPane.add(btnDirectorio);

		JLabel lblFiltro = new JLabel("Filtrar per:");
		lblFiltro.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblFiltro.setBounds(39, 178, 235, 25);
		contentPane.add(lblFiltro);

		cmbFiltro = new JComboBox();
		cmbFiltro.setFont(new Font("Tahoma", Font.BOLD, 14));
		cmbFiltro.setModel(new DefaultComboBoxModel(new String[] { "Nom", "Grandaria", "Data" }));
		cmbFiltro.setSelectedIndex(0);
		cmbFiltro.setToolTipText("");
		cmbFiltro.setBounds(39, 223, 162, 34);
		contentPane.add(cmbFiltro);

		rdbtnAscendent = new JRadioButton("Ascendent");
		rdbtnAscendent.setSelected(true);
		bgOrdre.add(rdbtnAscendent);
		rdbtnAscendent.setBackground(new Color(144, 238, 144));
		rdbtnAscendent.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rdbtnAscendent.setBounds(39, 276, 104, 34);
		contentPane.add(rdbtnAscendent);

		JRadioButton rdbtnDescendent = new JRadioButton("Descendent");
		bgOrdre.add(rdbtnDescendent);
		rdbtnDescendent.setBackground(new Color(144, 238, 144));
		rdbtnDescendent.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rdbtnDescendent.setBounds(190, 276, 104, 34);
		contentPane.add(rdbtnDescendent);

		JButton btnLlistar = new JButton("Llistar");
		btnLlistar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File dir = new File(txtDirectori.getText());
				Llistar(dir);
			}
		});
		btnLlistar.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnLlistar.setBounds(39, 325, 168, 34);
		contentPane.add(btnLlistar);

		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtaInfo.setText("");
			}
		});
		btnBorrar.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnBorrar.setBounds(559, 599, 168, 34);
		contentPane.add(btnBorrar);

		JLabel lblTitol = new JLabel("Fitxers");
		lblTitol.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTitol.setBounds(572, 69, 115, 38);
		contentPane.add(lblTitol);

		JLabel lblBusqueda = new JLabel("Busqueda per text:");
		lblBusqueda.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblBusqueda.setBounds(39, 391, 255, 25);
		contentPane.add(lblBusqueda);

		txtBusqueda = new JTextField();
		txtBusqueda.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtBusqueda.setColumns(10);
		txtBusqueda.setBounds(39, 432, 268, 34);
		contentPane.add(txtBusqueda);

		JButton btnBusqueda = new JButton("");
		btnBusqueda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtBusqueda.getText().equals(""))
					txtaInfo.setText("Error: Deus introduir una paraula");
				else
					Coincidencies(txtDirectori.getText(), txtBusqueda.getText());
			}
		});
		btnBusqueda.setBackground(new Color(144, 238, 144));
		ImageIcon imageLupa = new ImageIcon(
				"C:\\Users\\Hugo\\AD\\Fitxers\\Fotos\\iconfinder-documents07-1622836_121949.png");
		Image img = imageLupa.getImage();
		Image newimagen = img.getScaledInstance(-1, 34, java.awt.Image.SCALE_SMOOTH);
		imageLupa = new ImageIcon(newimagen);
		btnBusqueda.setIcon(imageLupa);
		btnBusqueda.setBounds(317, 432, 59, 34);
		contentPane.add(btnBusqueda);

		JLabel lblFusion = new JLabel("Selecciona els archius per a fusionar");
		lblFusion.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblFusion.setBounds(39, 502, 337, 44);
		contentPane.add(lblFusion);

		JLabel lblInfoFusion = new JLabel("[Mantenir pulsada la tecla CTRL per a seleccionar mes de un archiu]");
		lblInfoFusion.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblInfoFusion.setBounds(39, 628, 420, 25);
		contentPane.add(lblInfoFusion);

		JButton btnFusion = new JButton("");
		btnFusion.setBackground(new Color(144, 238, 144));
		ImageIcon imageFile = new ImageIcon("C:\\Users\\Hugo\\AD\\Fitxers\\Fotos\\search-file_40436.png");
		Image imgFile = imageFile.getImage();
		Image novaimg = imgFile.getScaledInstance(-1, 67, java.awt.Image.SCALE_SMOOTH);
		imageFile = new ImageIcon(novaimg);
		btnFusion.setIcon(imageFile);
		btnFusion.setBounds(143, 549, 85, 67);
		contentPane.add(btnFusion);
		btnFusion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fc.setMultiSelectionEnabled(true);
				int respuesta = fc.showOpenDialog(null);
				if (respuesta == JFileChooser.APPROVE_OPTION) {
					File[] archivosElegidos = fc.getSelectedFiles();
					if (archivosElegidos.length < 2) {
						txtaInfo.setText("ERROR: Se han de seleccionar 2 archius o mes.");
					} else {
						String nom = JOptionPane
								.showInputDialog("Introdueix el nom del nou ficher (Amb la extensió incluida): ");
						String directoriActual = System.getProperty("user.dir");
						String separador = System.getProperty("file.separator");
						File nouArchiu = new File(directoriActual + separador + nom);
						if (!nouArchiu.exists()) {
							try {
								FileWriter fw = new FileWriter(nouArchiu);
								BufferedWriter bw = new BufferedWriter(fw);
								for (File file : archivosElegidos) {
									FileReader fr = new FileReader(file);
									BufferedReader br = new BufferedReader(fr);
									String text;
									while ((text = br.readLine()) != null) {
										bw.write(text);
										bw.newLine();
									}
									br.close();
									fr.close();
									txtaInfo.setText("Archius fusionats correctament!");
								}
								bw.close();
								fw.close();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						} else {
							txtaInfo.setText("ERROR: No se han fusionat el archius correctament.");
						}

					}
				}
			}
		});
	}
}
