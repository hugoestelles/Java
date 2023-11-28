package Evaluable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Aquesta classe gestiona la connexió amb la base de dades MySQL.
 */
public class ConexioSQL {
	private String url;
	private String usuari;
	private String contrasenya;

	/**
	 * Constructor per inicialitzar la connexió amb la base de dades.
	 *
	 * @param url         URL de la base de dades.
	 * @param usuari      Nom d'usuari per la connexió.
	 * @param contrasenya Contrasenya per la connexió.
	 */
	public ConexioSQL(String url, String usuari, String contrasenya) {
		this.url = url;
		this.usuari = usuari;
		this.contrasenya = contrasenya;
	}

	/**
	 * Constructor sense paràmetres.
	 */
	public ConexioSQL() {

	}

	/**
	 * Obre la connexió amb la base de dades MySQL.
	 *
	 * @return La connexió amb la base de dades.
	 */
	public Connection obrirConexio() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, usuari, contrasenya);
			return con;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Tanca la connexió amb la base de dades.
	 *
	 * @param con Connexió a tancar.
	 */
	public void tancarConexio(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUsuari(String usuari) {
		this.usuari = usuari;
	}

	public void setContrasenya(String contrasenya) {
		this.contrasenya = contrasenya;
	}

	public String getUrl() {
		return url;
	}

	public String getUsuari() {
		return usuari;
	}

	public String getContrasenya() {
		return contrasenya;
	}

}
