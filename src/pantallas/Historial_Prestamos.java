package pantallas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import datos.Dato;
import datos.Libros;
import datos.Prestamos;
import principal.Principal;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * Esta clase representa una ventana que muestra el historial de préstamos.
 */
public class Historial_Prestamos extends JFrame {
	private static final long serialVersionUID = 9101326053860304398L;
	private final String[] Orders = { "Libro", "Usuario" };
	private static int punteroOrders = 0;
	private JTable table;
	private List<Dato> books;
	private List<Dato> History;

	/**
	 * Constructor de la clase Historial_Prestamos.
	 *
	 * @param books   La lista de libros existentes.
	 * @param History La lista de historial de préstamos.
	 */
	public Historial_Prestamos(List<Dato> books, List<Dato> History) {
		this.books = books;
		this.History = History;
		setTitle("Historial de prestamos");
		cambiarOrden();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		int ancho_pantalla = screenSize.width;
		int alto_pantalla = screenSize.height;
		setSize(ancho_pantalla, alto_pantalla);
		setExtendedState(MAXIMIZED_BOTH);

		JPanel contentPane = new JPanel(new BorderLayout());
		JPanel topPanel = new JPanel(new GridLayout(1, 5, 20, 0));
		JButton button1 = createStyledButton("Agregar Libros", "No selected");
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Principal.makeVisible(2);
			}
		});
		JButton button2 = createStyledButton("Inicio", "No selected");
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Principal.makeVisible(1);
				Principal.makeInvisible(3);
			}
		});
		JButton button3 = createStyledButton("Historial de prestamos", "selected");
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Principal.makeVisible(3);
			}
		});
		JButton button4 = createStyledButton("Registrar prestamo", "No selected");
		button4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Principal.makeVisible(4);
			}
		});
		JButton button5 = createStyledButton("Ordenar: Libro", "No selected");
		button5.setBackground(new Color(255, 165, 0));
		button5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				punteroOrders++;
				if (punteroOrders > 1) {
					punteroOrders = 0;
				}
				button5.setText("Ordenar: " + Orders[punteroOrders]);
				cambiarOrden();
			}
		});
		topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		topPanel.add(button1);
		topPanel.add(button2);
		topPanel.add(button3);
		topPanel.add(button4);
		topPanel.add(button5);

		contentPane.add(topPanel, BorderLayout.NORTH);

		table = creacionDeTabla();
		JScrollPane scrollPane = new JScrollPane(table);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		setContentPane(contentPane);
		setLocationRelativeTo(null);
	}

	/**
	 * Metodo que cambia el orden de los datos en en el DefaultTablemodel de la
	 * tabla en funcion de la seleccion
	 */
	private void cambiarOrden() {
		Comparator<Dato> comparador = new Comparator<Dato>() {
			public int compare(Dato elemento1, Dato elemento2) {
				switch (punteroOrders) {
				case 0:
					return ((Prestamos) elemento1).getLibro().getNombre()
							.compareTo(((Prestamos) elemento2).getLibro().getNombre());
				case 1:
					return ((Prestamos) elemento1).getUser().compareTo(((Prestamos) elemento2).getUser());
				}
				return ((Prestamos) elemento1).getLibro().getNombre()
						.compareTo(((Prestamos) elemento2).getLibro().getNombre());
			}
		};
		Collections.sort(History, comparador);
		if (table != null) {
			table.setModel(plantilla());
		}
	}

	/**
	 * Metodo que genera la tabla. Con ciertos cambios de diseño
	 * 
	 * @return
	 */
	private JTable creacionDeTabla() {
		DefaultTableModel tableModel = plantilla();
		JTable table = new JTable(tableModel);
		table.setGridColor(Color.WHITE);
		table.setSelectionBackground(new Color(75, 175, 255));
		table.setSelectionForeground(Color.WHITE);
		table.setFont(new Font("Arial", Font.PLAIN, 18));
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 25));
		table.setRowHeight(40);
		table.getTableHeader().setReorderingAllowed(false);
		table.setEnabled(false);
		return table;
	}

	/**
	 * Genera el Defaulttablemodel para la tabla con los datos a mostrar
	 * 
	 * @return
	 */
	private DefaultTableModel plantilla() {
		Vector<Vector<String>> data = new Vector<>();
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		System.out.println();
		for (int i = 0; i < History.size(); i++) {
			Vector<String> row = new Vector<>();
			row.add(((Prestamos) History.get(i)).getLibro().getNombre());
			row.add(((Prestamos) History.get(i)).getUser());
			if (((Prestamos) History.get(i)).getFechaPrestamo() == null) {
				row.add(null);
			} else {
				row.add(formato.format(((Prestamos) History.get(i)).getFechaPrestamo()));
			}
			if (((Prestamos) History.get(i)).getFechaDevolucion() == null) {
				row.add(null);
			} else {
				row.add(formato.format(((Prestamos) History.get(i)).getFechaDevolucion()));
			}
			data.add(row);
		}

		Vector<String> columns = new Vector<>();
		columns.add("Libro");
		columns.add("Usuario");
		columns.add("Fecha de Prestamo");
		columns.add("Fecha de Devolucion");

		DefaultTableModel tableModel = new DefaultTableModel(data, columns);
		return tableModel;
	}

	/**
	 * Metodo que crea botones con ciertos cambios de diseño
	 * 
	 * @param text
	 * @param modo
	 * @return
	 */
	private JButton createStyledButton(String text, String modo) {
		if (modo.equalsIgnoreCase("Selected")) {
			JButton button = new JButton(text);
			button.setPreferredSize(new Dimension(getWidth() / 3, 50));
			button.setBackground(new Color(0, 100, 255));
			button.setForeground(Color.WHITE);
			button.setFont(new Font("Arial", Font.BOLD, 16));
			return button;
		} else {
			JButton button = new JButton(text);
			button.setPreferredSize(new Dimension(getWidth() / 3, 50));
			button.setBackground(new Color(75, 175, 255));
			button.setForeground(Color.WHITE);
			button.setFont(new Font("Arial", Font.BOLD, 16));
			return button;
		}
	}

	public List<Dato> getBooks() {
		return books;
	}

	public void setBooks(List<Dato> books) {
		this.books = books;
	}

	public List<Dato> getHistory() {
		return History;
	}

	public void setHistory(List<Dato> history) {
		History = history;
		table.setModel(plantilla());
	}
}
