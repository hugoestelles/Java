package Evaluable;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Classe que representa el model de l'aplicació.
 */
public class Modelo {

	/**
	 * Converteix un hash en una cadena de caràcters.
	 *
	 * @param hash Hash que volem convertir.
	 * @return Cadena de caràcters convertida.
	 */
	public String hashToString(String hash) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < hash.length(); i += 2) {
			String str = hash.substring(i, i + 2);
			sb.append((char) Integer.parseInt(str, 16));
		}
		return sb.toString();
	}

	/**
	 * Converteix una cadena de caràcters en un hash.
	 *
	 * @param input Cadena de caràcters a convertir.
	 * @return Hash de la cadena.
	 */
	public String stringToHash(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hashBytes = md.digest(input.getBytes());
			StringBuilder hexString = new StringBuilder();
			for (byte b : hashBytes) {
				String hex = Integer.toHexString(0xFF & b);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Valida un usuari a partir de la connexió, el nom d'usuari i la contrasenya.
	 *
	 * @param conexio Connexió a la base de dades.
	 * @param usuari  Nom d'usuari.
	 * @param passw   Contrasenya.
	 * @return true si l'usuari és vàlid; false sino.
	 */
	public boolean validacioUsuari(Connection conexio, String usuari, String passw) {
		try {
			PreparedStatement psInsertar = conexio.prepareStatement("SELECT user, pass FROM users WHERE user = ?");
			psInsertar.setString(1, usuari);
			ResultSet rs = psInsertar.executeQuery();
			String userBD;
			String hashBD;
			String hash = stringToHash(passw); // Convertir passw a hash.
			if (rs.next()) {
				userBD = rs.getString(1);
				hashBD = rs.getString(2);
				if (usuari.equals(userBD) && hash.equals(hashBD))
					return true; // El usuario y contraseña coinciden
				else
					return false; // El usuario y contraseña no coinciden
			} else
				return false; // No se encuentra el usuario.

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Comprova si un usuari té permisos d'administrador.
	 *
	 * @param conexio Connexió a la base de dades.
	 * @param usuari  Nom d'usuari.
	 * @return true si l'usuari té permisos d'administrador; false sino.
	 */
	public boolean comprobarAdmin(Connection conexio, String usuari) {
		try {
			PreparedStatement psInsertar = conexio.prepareStatement("SELECT type FROM users WHERE user = ?");
			psInsertar.setString(1, usuari);
			ResultSet rs = psInsertar.executeQuery();
			if (rs.next()) {
				String tipo = rs.getString(1);
				if (tipo.equals("admin"))
					return true; // El usuario y contraseña coinciden
				else
					return false; // El usuario y contraseña no coinciden
			} else
				return false; // No se encuentra el usuario.

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Estableix la connexió del client llegint la configuració des d'un fitxer XML.
	 *
	 * @param conexio Connexió a establir.
	 * @return true si la connexió s'estableix amb èxit; false sino.
	 */
	public boolean conexioClient(ConexioSQL conexio) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(new File("client.xml"));
			NodeList nodeList = document.getElementsByTagName("usuarios");
			Node node = nodeList.item(0);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) node;
				conexio.setUrl(eElement.getElementsByTagName("url").item(0).getTextContent());
				conexio.setUsuari(eElement.getElementsByTagName("user").item(0).getTextContent());
				conexio.setContrasenya(eElement.getElementsByTagName("password").item(0).getTextContent());
				return true;
			} else
				return false;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Estableix la connexió de l'administrador llegint la configuració des d'un
	 * fitxer XML.
	 *
	 * @param conexio Connexió a establir.
	 * @return true si la connexió s'estableix amb èxit; false sino.
	 */
	public boolean conexioAdmin(ConexioSQL conexio) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(new File("admin.xml"));
			NodeList nodeList = document.getElementsByTagName("usuarios");
			Node node = nodeList.item(0);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) node;
				conexio.setUrl(eElement.getElementsByTagName("url").item(0).getTextContent());
				conexio.setUsuari(eElement.getElementsByTagName("user").item(0).getTextContent());
				conexio.setContrasenya(eElement.getElementsByTagName("password").item(0).getTextContent());
				return true;
			} else
				return false;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Executa una consulta SQL d'actualització, eliminació o insercició.
	 *
	 * @param con      Connexió a la base de dades.
	 * @param consulta Consulta SQL a executar.
	 * @return Nombre de files afectades per la consulta.
	 */
	public int executarConsulta(Connection con, String consulta) {
		try {
			PreparedStatement ps = con.prepareStatement(consulta);
			int resultat = ps.executeUpdate();
			return resultat;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * Executa una consulta SQL de selecció.
	 *
	 * @param con      Connexió a la base de dades.
	 * @param consulta Consulta SQL a executar.
	 * @return Llista d'arrays de String[] que conté les dades resultants.
	 */
	public ArrayList<String[]> executarSelect(Connection con, String consulta) {
		ArrayList<String[]> resultat = new ArrayList<String[]>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(consulta);

			String[] noms = new String[rs.getMetaData().getColumnCount()];
			for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
				noms[i] = rs.getMetaData().getColumnName(i + 1);
			}
			resultat.add(noms);
			while (rs.next()) {
				String[] dades = new String[rs.getMetaData().getColumnCount()];
				for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
					dades[i] = rs.getString(i + 1);
				}
				resultat.add(dades);
			}
			return resultat;
		} catch (Exception e) {
			e.printStackTrace();
			return resultat;
		}
	}
}
