package Evaluable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Aquesta classe actua com a controlador en el model MVC per a la gestió de la
 * interfície gràfica i les interaccions amb el model de dades.
 */
public class Controlador {

	private Vista vista;
	private Modelo model;
	private Connection conexio;
	private ActionListener alBtnIniciarSesio;
	private ActionListener alBtnTancarSesio;
	private ActionListener alBtnSelect;
	private ActionListener alBtnInsert;
	private ActionListener alBtnUpdate;
	private ActionListener alBtnDelete;
	private ActionListener alBtnAceptarConsulta;
	private ActionListener alBtnTancarConexio;
	private ActionListener alBtnBorrar;

	/**
	 * Constructor que rep la vista i el model com a paràmetres i estableix la
	 * lògica del controlador.
	 *
	 * @param vista La vista de l'aplicació.
	 * @param model El model de dades de l'aplicació.
	 */
	public Controlador(Vista vista, Modelo model) {
		this.vista = vista;
		this.model = model;
		control();
	}

	public void control() {

		ConexioSQL conClient = new ConexioSQL();
		ConexioSQL conAdmin = new ConexioSQL();
		vista.getPanelConsultes().setVisible(false);
		vista.getBtnTancarSesio().setEnabled(false);
		vista.getBtnTancarConexio().setEnabled(false);
		vista.getBtnBorrar().setEnabled(false);
		vista.getLblTipoUsuari().setText("");
		if (model.conexioClient(conClient)) {
			conexio = conClient.obrirConexio();
			this.alBtnIniciarSesio = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					String user = vista.getTxtUsuari().getText();
					String passw = vista.getTxtContrasenya().getText();
					if (model.validacioUsuari(conexio, user, passw)) {
						vista.getPanelConsultes().setVisible(true);
						vista.getBtnTancarSesio().setEnabled(true);
						vista.getBtnTancarConexio().setEnabled(true);
						vista.getBtnIniciarSesio().setEnabled(false);
						vista.getTxtUsuari().setText("");
						vista.getTxtContrasenya().setText("");
						if (model.comprobarAdmin(conexio, user)) {

							conClient.tancarConexio(conexio);
							if (model.conexioAdmin(conAdmin)) {
								conexio = conAdmin.obrirConexio();
								vista.getLblTipoUsuari().setText("Administrador");
							} else {
								JOptionPane.showMessageDialog(null, "Error de conexió a la base de dades.");
							}
						} else {
							vista.getLblTipoUsuari().setText("Client");
							vista.getBtnInsert().setEnabled(false);
							vista.getBtnDelete().setEnabled(false);
							vista.getBtnUpdate().setEnabled(false);
						}
					} else {
						JOptionPane.showMessageDialog(null,
								"Error de autentificació, les dades del usuari no son correctes.");
					}

				}

			};
			vista.getBtnIniciarSesio().addActionListener(this.alBtnIniciarSesio);
			alBtnTancarSesio = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					conAdmin.tancarConexio(conexio);
					conexio = conClient.obrirConexio();
					vista.getPanelConsultes().setVisible(false);
					vista.getBtnTancarSesio().setEnabled(false);
					vista.getBtnTancarConexio().setEnabled(false);
					vista.getBtnIniciarSesio().setEnabled(true);
					vista.getBtnInsert().setEnabled(true);
					vista.getBtnDelete().setEnabled(true);
					vista.getBtnUpdate().setEnabled(true);
					resetearTabla();

				}
			};
			vista.getBtnTancarSesio().addActionListener(alBtnTancarSesio);
			alBtnTancarConexio = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					conAdmin.tancarConexio(conexio);
					JOptionPane.showMessageDialog(null,
							"Conexio amb la base de dades tancada.\nSe va a tancar el programa.");
					System.exit(0);

				}
			};
			vista.getBtnTancarConexio().addActionListener(alBtnTancarConexio);

			alBtnSelect = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					vista.getTxtTipoConsulta().setText("SELECT");

				}
			};
			vista.getBtnSelect().addActionListener(alBtnSelect);
			alBtnInsert = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					vista.getTxtTipoConsulta().setText("INSERT");

				}
			};
			vista.getBtnInsert().addActionListener(alBtnInsert);
			alBtnUpdate = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					vista.getTxtTipoConsulta().setText("UPDATE");

				}
			};
			vista.getBtnUpdate().addActionListener(alBtnUpdate);
			alBtnDelete = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					vista.getTxtTipoConsulta().setText("DELETE");

				}
			};
			vista.getBtnDelete().addActionListener(alBtnDelete);
			alBtnAceptarConsulta = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					String tipoConsulta = vista.getTxtTipoConsulta().getText();
					if (tipoConsulta.equals("SELECT")) {
						String consulta = tipoConsulta + " " + vista.getTxtConsulta().getText();
						int confirmacio = JOptionPane.showConfirmDialog(null,
								"Se va a executar la seguent consulta: \n" + consulta + "\nEstas segur de executarla?",
								"Confirmacio de consulta", JOptionPane.YES_NO_OPTION);
						if (confirmacio == JOptionPane.YES_OPTION) {
							ArrayList<String[]> dades = model.executarSelect(conexio, consulta);
							if (dades.isEmpty()) {
								JOptionPane.showMessageDialog(null,
										"La consulta no se ha pogut realitzar, hi ha algun error en la consulta");
								vista.getTxtConsulta().setText("");
							} else {

								String[] noms = dades.get(0);
								dades.remove(0);
								String[][] informacio = new String[dades.size()][];
								for (int i = 0; i < dades.size(); i++) {
									String[] fila = dades.get(i);
									informacio[i] = fila;
									System.out.println(Arrays.toString(fila));

								}
								DefaultTableModel tableModel = new DefaultTableModel(informacio, noms);
								JOptionPane.showMessageDialog(null, "Consulta realitzada amb exit");
								vista.getTableInfo().setModel(tableModel);
								vista.getTxtConsulta().setText("");
								vista.getBtnBorrar().setEnabled(true);

							}
						}

					} else {
						String consulta = tipoConsulta + " " + vista.getTxtConsulta().getText();
						int confirmacio = JOptionPane.showConfirmDialog(null,
								"Se va a executar la seguent consulta: \n" + consulta + "\nEstas segur de executarla?",
								"Confirmacio de consulta", JOptionPane.YES_NO_OPTION);
						if (confirmacio == JOptionPane.YES_OPTION) {
							int numAfectats = model.executarConsulta(conexio, consulta);
							if (numAfectats != 0) {
								JOptionPane.showMessageDialog(null, "Consulta realitzada correctament. \n "
										+ numAfectats + " files afectades per la consulta.");
								vista.getTxtConsulta().setText("");
							} else {
								JOptionPane.showMessageDialog(null,
										"La consulta no se ha pogut realitzar, hi ha algun error en la consulta");
								vista.getTxtConsulta().setText("");
							}
						}

					}

				}
			};
			vista.getBtnAceptarConsulta().addActionListener(alBtnAceptarConsulta);
			alBtnBorrar = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					resetearTabla();
					vista.getBtnBorrar().setEnabled(false);

				}
			};
			vista.getBtnBorrar().addActionListener(alBtnBorrar);
		} else {
			JOptionPane.showMessageDialog(null, "Error de conexió a la base de dades.");
		}

	}

	/**
	 * Mètode per reiniciar la taula de dades.
	 */
	public void resetearTabla() {
		DefaultTableModel model = (DefaultTableModel) vista.getTableInfo().getModel();
		model.setRowCount(0);
		model.setColumnCount(0);
		vista.getTableInfo().setModel(model);
	}

}
