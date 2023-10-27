package datos;

public class Libros implements Dato {
	private static final long serialVersionUID = 5584343327170641830L;
	private String Nombre, Autor;
	private long Fecha;
	private long ISBN;

	/**
	 * Clase elemento para los libros
	 * 
	 * @param Nombre
	 * @param Autor
	 * @param ISBN
	 * @param Fecha
	 */
	public Libros(String Nombre, String Autor, long ISBN, long Fecha) {
		this.Nombre = Nombre;
		this.Autor = Autor;
		this.ISBN = ISBN;
		this.Fecha = Fecha;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public String getAutor() {
		return Autor;
	}

	public void setAutor(String autor) {
		Autor = autor;
	}

	public long getISBN() {
		return ISBN;
	}

	public void setISBN(long iSBN) {
		ISBN = iSBN;
	}

	public long getFecha() {
		return Fecha;
	}

	public void setFecha(long fecha) {
		this.Fecha = fecha;
	}

	public String toString() {
		return "Libros [Nombre=" + Nombre + ", Autor=" + Autor + ", ISBN=" + ISBN + ", Fecha=" + Fecha + "]";
	}

}
