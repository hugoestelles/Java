package Evaluable;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Clase que implementa FilenameFilter per a filtrar archius a partir de una extensio donada.
 */
public class FiltroExtension implements FilenameFilter{
	String extension;
	/**
	 * Constructor de clase.
	 * @param extension Extensio que utilizarem per a filtrar.
	 */
	FiltroExtension(String extension){
		this.extension = extension;
	}
	/**
	 * Metode que determina si un archiu cumplix amb el filtre.
	 */
	public boolean accept(File dir, String name) {
		return name.endsWith(extension);
	}
}