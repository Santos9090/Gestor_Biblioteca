package principal;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import datos.Dato;

/**
 * Clase que maneja la conexion con el fichero, con una variable para almacenar
 * el nombre del fichero, y un metodo para leer y otro para guardar.
 * 
 * @author sdltm
 *
 */
public class ConexionFichero {
	private String nombreArchivo;

	public ConexionFichero(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public void guardarArraysEnArchivo(List<Dato> arrays) {
		try (FileOutputStream archivoSalida = new FileOutputStream(nombreArchivo);
				ObjectOutputStream objetoSalida = new ObjectOutputStream(archivoSalida)) {
			objetoSalida.writeObject(arrays);
			archivoSalida.flush();
			objetoSalida.flush();
			objetoSalida.close();
			archivoSalida.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Dato> leerArraysDesdeArchivo() {
		List<Dato> listaDeArrays = new LinkedList<>();
		try (FileInputStream archivoEntrada = new FileInputStream(nombreArchivo);
				ObjectInputStream objetoEntrada = new ObjectInputStream(archivoEntrada)) {
			listaDeArrays = (List<Dato>) objetoEntrada.readObject();
			archivoEntrada.close();
			objetoEntrada.close();
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("No existe el fichero");
		}
		return listaDeArrays;
	}
}
