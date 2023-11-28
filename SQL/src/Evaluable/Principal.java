package Evaluable;

/**
 * Aquesta classe conté el mètode principal que inicia l'aplicació, creant les
 * instàncies de la vista, el model i el controlador.
 */
public class Principal {

	public static void main(String[] args) {

		Vista vista = new Vista();
		Modelo model = new Modelo();
		Controlador control = new Controlador(vista, model);

	}

}
