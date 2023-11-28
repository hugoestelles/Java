package Evaluable;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import java.awt.Toolkit;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

/**
 * Aquesta classe representa la interfície gràfica de l'aplicació, amb els
 * diferents components com botons, etiquetes i taules.
 */
public class Vista extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsuari;
	private JTextField txtContrasenya;
	private JTextField txtTipoConsulta;
	private JTextField txtConsulta;
	private JTable tableInfo;
	private JPanel pLogIn;
	private JLabel lblUsuari;
	private JLabel lblContrasenya;
	private JButton btnIniciarSesio;
	private JButton btnTancarSesio;
	private JPanel panelTipoUsuari;
	private JLabel lblTitolTipoUsuari;
	private JLabel lblTipoUsuari;
	private JPanel panelConsultes;
	private JLabel lblTipoConsulta;
	private JButton btnSelect;
	private JButton btnInsert;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JLabel lblConsulta;
	private JButton btnAceptarConsulta;
	private JButton btnTancarConexio;
	private JButton btnBorrar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Vista frame = new Vista();
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
	public Vista() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Hugo\\AD\\SQL\\fotos\\logo.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1058, 738);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		pLogIn = new JPanel();
		pLogIn.setToolTipText("");
		pLogIn.setBackground(new Color(154, 237, 169));
		pLogIn.setBounds(21, 24, 991, 130);
		contentPane.add(pLogIn);
		pLogIn.setLayout(null);

		lblUsuari = new JLabel("Usuari:");
		lblUsuari.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblUsuari.setBackground(new Color(255, 255, 255));
		lblUsuari.setBounds(71, 26, 65, 33);
		pLogIn.add(lblUsuari);

		lblContrasenya = new JLabel("Contrasenya:");
		lblContrasenya.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblContrasenya.setBackground(Color.WHITE);
		lblContrasenya.setBounds(35, 69, 101, 33);
		pLogIn.add(lblContrasenya);

		txtUsuari = new JTextField();
		txtUsuari.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtUsuari.setBounds(160, 30, 136, 24);
		pLogIn.add(txtUsuari);
		txtUsuari.setColumns(10);

		txtContrasenya = new JTextField();
		txtContrasenya.setToolTipText("");
		txtContrasenya.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtContrasenya.setColumns(10);
		txtContrasenya.setBounds(160, 73, 136, 24);
		pLogIn.add(txtContrasenya);

		btnIniciarSesio = new JButton("Iniciar Sesió");
		btnIniciarSesio.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnIniciarSesio.setBounds(317, 35, 137, 67);
		pLogIn.add(btnIniciarSesio);

		btnTancarSesio = new JButton("Tancar Sesió");
		btnTancarSesio.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnTancarSesio.setBounds(474, 35, 136, 67);
		pLogIn.add(btnTancarSesio);

		panelTipoUsuari = new JPanel();
		panelTipoUsuari
				.setToolTipText("Es possible que algunes funcions estiguen limitades depenguent del tipo de usuari");
		panelTipoUsuari.setBounds(805, 24, 176, 78);
		pLogIn.add(panelTipoUsuari);
		panelTipoUsuari.setLayout(null);

		lblTitolTipoUsuari = new JLabel("Tipus de Usuari:");
		lblTitolTipoUsuari.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTitolTipoUsuari.setBounds(28, 10, 124, 29);
		panelTipoUsuari.add(lblTitolTipoUsuari);

		lblTipoUsuari = new JLabel("Administrador");
		lblTipoUsuari.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTipoUsuari.setBounds(47, 38, 93, 19);
		panelTipoUsuari.add(lblTipoUsuari);

		btnTancarConexio = new JButton("Tancar Conexió");
		btnTancarConexio.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnTancarConexio.setBounds(631, 34, 156, 68);
		pLogIn.add(btnTancarConexio);

		panelConsultes = new JPanel();
		panelConsultes.setBackground(new Color(146, 215, 237));
		panelConsultes.setBounds(21, 164, 991, 527);
		contentPane.add(panelConsultes);
		panelConsultes.setLayout(null);

		lblTipoConsulta = new JLabel("Tipus consulta SQL:");
		lblTipoConsulta.setBackground(new Color(255, 255, 255));
		lblTipoConsulta.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTipoConsulta.setBounds(20, 25, 159, 26);
		panelConsultes.add(lblTipoConsulta);

		btnSelect = new JButton("SELECT");
		btnSelect.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnSelect.setBounds(201, 22, 159, 32);
		panelConsultes.add(btnSelect);

		btnInsert = new JButton("INSERT");
		btnInsert.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnInsert.setBounds(406, 22, 159, 32);
		panelConsultes.add(btnInsert);

		btnUpdate = new JButton("UPDATE");
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnUpdate.setBounds(608, 22, 159, 32);
		panelConsultes.add(btnUpdate);

		btnDelete = new JButton("DELETE");
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnDelete.setBounds(809, 22, 159, 32);
		panelConsultes.add(btnDelete);

		lblConsulta = new JLabel("Consulta:");
		lblConsulta.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblConsulta.setBounds(20, 71, 82, 26);
		panelConsultes.add(lblConsulta);

		txtTipoConsulta = new JTextField();
		txtTipoConsulta.setHorizontalAlignment(SwingConstants.CENTER);
		txtTipoConsulta.setText("SELECT");
		txtTipoConsulta.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtTipoConsulta.setEditable(false);
		txtTipoConsulta.setBounds(99, 69, 80, 32);
		panelConsultes.add(txtTipoConsulta);
		txtTipoConsulta.setColumns(10);

		txtConsulta = new JTextField();
		txtConsulta.setHorizontalAlignment(SwingConstants.LEFT);
		txtConsulta.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtConsulta.setColumns(10);
		txtConsulta.setBounds(189, 69, 716, 32);
		panelConsultes.add(txtConsulta);

		btnAceptarConsulta = new JButton("");
		btnAceptarConsulta.setBackground(new Color(146, 215, 237));
		ImageIcon imageIcon = new ImageIcon("C:\\Users\\Hugo\\AD\\SQL\\Fotos\\aceptarBD.png");
		Image image = imageIcon.getImage();
		Image newimg = image.getScaledInstance(-1, 37, java.awt.Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(newimg);
		btnAceptarConsulta.setIcon(imageIcon);
		btnAceptarConsulta.setBounds(915, 64, 53, 37);
		panelConsultes.add(btnAceptarConsulta);

		JScrollPane scrollPaneTabla = new JScrollPane();
		scrollPaneTabla.setBounds(20, 127, 948, 346);
		panelConsultes.add(scrollPaneTabla);

		tableInfo = new JTable();
		scrollPaneTabla.setViewportView(tableInfo);
		tableInfo.setEnabled(false);

		btnBorrar = new JButton("Borrar");
		btnBorrar.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnBorrar.setBounds(794, 485, 174, 32);
		panelConsultes.add(btnBorrar);
		setVisible(true);
	}

	public JButton getBtnBorrar() {
		return btnBorrar;
	}

	public JButton getBtnTancarConexio() {
		return btnTancarConexio;
	}

	public JTable getTableInfo() {
		return tableInfo;
	}

	public JButton getBtnIniciarSesio() {
		return btnIniciarSesio;
	}

	public JButton getBtnTancarSesio() {
		return btnTancarSesio;
	}

	public JLabel getLblTipoUsuari() {
		return lblTipoUsuari;
	}

	public JPanel getPanelConsultes() {
		return panelConsultes;
	}

	public JLabel getLblTipoConsulta() {
		return lblTipoConsulta;
	}

	public JButton getBtnSelect() {
		return btnSelect;
	}

	public JButton getBtnInsert() {
		return btnInsert;
	}

	public JButton getBtnUpdate() {
		return btnUpdate;
	}

	public JTextField getTxtUsuari() {
		return txtUsuari;
	}

	public JTextField getTxtContrasenya() {
		return txtContrasenya;
	}

	public JTextField getTxtConsulta() {
		return txtConsulta;
	}

	public JTextField getTxtTipoConsulta() {
		return txtTipoConsulta;
	}

	public JButton getBtnDelete() {
		return btnDelete;
	}

	public JButton getBtnAceptarConsulta() {
		return btnAceptarConsulta;
	}
}
