package datos;

import java.util.Date;

public class Prestamos implements Dato {
	private static final long serialVersionUID = -1999705336895685825L;
	private Libros libro;
	private Date fechaPrestamo, fechaDevolucion;
	private String user;

	/**
	 * Clase para el almacenaje de los datos de los prestamos.
	 * 
	 * @param libro
	 * @param fechaPrestamo
	 * @param fechaDevolucion
	 * @param user
	 */
	public Prestamos(Libros libro, Date fechaPrestamo, Date fechaDevolucion, String user) {
		this.libro = libro;
		this.fechaDevolucion = fechaDevolucion;
		this.fechaPrestamo = fechaPrestamo;
		this.user = user;
	}

	public Libros getLibro() {
		return libro;
	}

	public void setLibro(Libros libro) {
		this.libro = libro;
	}

	public Date getFechaDevolucion() {
		return fechaDevolucion;
	}

	public Date getFechaPrestamo() {
		return fechaPrestamo;
	}

	public void setFechaPrestamo(Date fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}

	public void setFechaDevolucion(Date fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}

	public String getUser() {
		return user;
	}

	@Override
	public String toString() {
		return "Prestamos [libro=" + libro + ", fechaDevolucion=" + fechaDevolucion + ", user=" + user + "]";
	}

	public void setUser(String user) {
		this.user = user;
	}

}
