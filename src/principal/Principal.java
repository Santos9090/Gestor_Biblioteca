package principal;

import java.util.LinkedList;
import java.util.List;
import javax.swing.JFrame;

import datos.Dato;
import datos.Libros;
import datos.Prestamos;
import pantallas.*;

/**
 * Esta clase Principal es la clase principal de la aplicación que gestiona
 * préstamos de libros.
 */
public class Principal {
	// Conexiones a archivos y listas de libros y préstamos
	private static ConexionFichero conexL;
	private static ConexionFichero conexP;
	private static List<Dato> books;
	private static List<Dato> historial;

	// Arrays de pantallas
	private static JFrame[] pantallas = new JFrame[5];

	/**
	 * El método principal que se ejecuta al iniciar la aplicación. Inicializa las
	 * conexiones, listas y pantallas.
	 *
	 * @param args Argumentos de línea de comandos (no se utilizan aquí).
	 */
	public static void main(String[] args) {
		conexP = new ConexionFichero("Prestamos.txt");
		conexL = new ConexionFichero("Libros.txt");
		books = conexL.leerArraysDesdeArchivo();
		historial = conexP.leerArraysDesdeArchivo();
		pantallas[0] = new PantallaCarga();
		pantallas[1] = new Inicio(books);
		pantallas[2] = new Agregar(books);
		pantallas[3] = new Historial_Prestamos(books, historial);
		pantallas[4] = new Registrar(books, historial);
		makeVisible(0);
		((PantallaCarga) pantallas[0]).iniciarCarga();
	}

	/**
	 * Guarda los datos de libros y préstamos en archivos.
	 */
	public static void guardadoFinal() {
		conexL.guardarArraysEnArchivo(books);

		// Limpia la lista y guarda los datos de historial de préstamos

		conexP.guardarArraysEnArchivo(historial);
	}

	/**
	 * Muestra una pantalla específica.
	 *
	 * @param num El índice de la pantalla a mostrar.
	 */
	public static void makeVisible(int num) {
		pantallas[num].setVisible(true);
		if (num == 4) {
			((Registrar) pantallas[num]).recargarBtn();
		}
	}

	/**
	 * Oculta una pantalla específica.
	 *
	 * @param num El índice de la pantalla a ocultar.
	 */
	public static void makeInvisible(int num) {
		pantallas[num].setVisible(false);
	}

	/**
	 * Agrega un libro a la lista de libros.
	 *
	 * @param libro El libro a agregar.
	 */
	public static void agregarLibro(Libros libro) {
		books.add(libro);
		((Inicio) pantallas[1]).setBooks(books);
		((Agregar) pantallas[2]).setBooks(books);
		((Historial_Prestamos) pantallas[3]).setBooks(books);
		((Registrar) pantallas[4]).setBooks(books);
		guardadoFinal();
	}

	/**
	 * Agrega un préstamo a la lista de historial de préstamos.
	 *
	 * @param prestamo El préstamo a agregar.
	 */
	public static void agregarPrestamo(Prestamos prestamo) {
		historial.add(prestamo);
		((Historial_Prestamos) pantallas[3]).setHistory(historial);
		((Registrar) pantallas[4]).setHistory(historial);
		guardadoFinal();
	}

	/**
	 * Actualiza un préstamo en la lista de historial de préstamos.
	 *
	 * @param i    El índice del préstamo a actualizar.
	 * @param pres El préstamo actualizado.
	 */
	public static void setPrestamo(int i, Prestamos pres) {
		historial.set(i, pres);
		((Historial_Prestamos) pantallas[3]).setHistory(historial);
		((Registrar) pantallas[4]).setHistory(historial);
		guardadoFinal();
	}

	public static List<Dato> getBooks() {

		return books;
	}
}
