package pantallas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import datos.Dato;
import datos.Libros;
import principal.Principal;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Inicio extends JFrame {
	private static final long serialVersionUID = 9101326053860304398L;
	private final String[] Orders = { "Titulo", "Autor", "Fecha", "ISBN" };
	private static int punteroOrders = 0;
	private JTable table;
	private List<Dato> books;
	private JPanel contentPane;
	private boolean consejo = true;

	/**
	 * Pantalla que representa la tabla de libros.
	 * 
	 * @param books
	 */
	public Inicio(List<Dato> books) {
		this.books = books;
		setTitle("Inicio");
		cambiarOrden();

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		int ancho_pantalla = screenSize.width;
		int alto_pantalla = screenSize.height;
		setSize(ancho_pantalla, alto_pantalla);
		setExtendedState(MAXIMIZED_BOTH);

		contentPane = new JPanel(new BorderLayout());
		JPanel topPanel = new JPanel(new GridLayout(1, 5, 20, 0));
		JButton button1 = createStyledButton("Agregar Libros", "No selected");
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Principal.makeVisible(2);
			}
		});
		JButton button2 = createStyledButton("Inicio", "Selected");
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Principal.makeVisible(1);
			}
		});
		JButton button3 = createStyledButton("Historial de prestamos", "No selected");
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Principal.makeVisible(3);
				Principal.makeInvisible(1);
			}
		});
		JButton button4 = createStyledButton("Registrar prestamo", "No selected");
		button4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Principal.makeVisible(4);
			}
		});
		JButton button5 = createStyledButton("Ordenar: Titulo", "No selected");
		button5.setBackground(new Color(255, 165, 0));
		button5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				punteroOrders++;
				if (punteroOrders >= 4) {
					punteroOrders = 0;
				}
				button5.setText("Ordenar: " + Orders[punteroOrders]);
				cambiarOrden();

			}
		});
		JComboBox<String> comboss = new JComboBox<>();
		comboss.setModel(new DefaultComboBoxModel(new String[] { "Titulo", "Autor", "ISBN" }));
		comboss.setSelectedIndex(0);

		JTextField buscador = new JTextField("Buscador");
		buscador.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				String aux = buscador.getText();
				buscador.setText(aux);
				if (consejo) {
					buscador.setText("");
					JOptionPane.showMessageDialog(topPanel, "Para buscar, presione Enter", "Consejo",
							JOptionPane.INFORMATION_MESSAGE);
					consejo = false;
				}
			}
		});
		buscador.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getExtendedKeyCode() == 10) {
					Inicio.this.books = Principal.getBooks();
					String busqueda = buscador.getText().toLowerCase();
					List<Dato> aux = new LinkedList<>();
					switch (comboss.getSelectedIndex()) {
					case 0:
						for (int i = 0; i < Inicio.this.books.size(); i++) {
							if (((Libros) Inicio.this.books.get(i)).getNombre().toLowerCase().contains(busqueda)) {
								aux.add(Inicio.this.books.get(i));
							}
						}
						break;
					case 1:
						for (int i = 0; i < Inicio.this.books.size(); i++) {
							if (((Libros) Inicio.this.books.get(i)).getAutor().toLowerCase().contains(busqueda)) {
								aux.add(Inicio.this.books.get(i));
							}
						}
						break;
					case 2:
						for (int i = 0; i < Inicio.this.books.size(); i++) {
							if (Long.valueOf(((Libros) Inicio.this.books.get(i)).getISBN())
									.compareTo(Long.parseLong(busqueda)) == 0) {
								aux.add(Inicio.this.books.get(i));
							}
						}
					default:
					}
					Inicio.this.books = aux;
					table.setModel(plantilla());
				}

			}
		});
		topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		topPanel.add(button1);
		topPanel.add(button2);
		topPanel.add(button3);
		topPanel.add(button4);
		topPanel.add(button5);
		topPanel.add(buscador);
		topPanel.add(comboss);

		contentPane.add(topPanel, BorderLayout.NORTH);
		table = creacionDetable();
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
					return ((Libros) elemento1).getNombre().compareTo(((Libros) elemento2).getNombre());
				case 1:
					return ((Libros) elemento1).getAutor().compareTo(((Libros) elemento2).getAutor());
				case 2:
					return Long.compare(((Libros) elemento1).getFecha(), ((Libros) elemento2).getFecha());
				case 3:
					return Long.compare(((Libros) elemento1).getISBN(), ((Libros) elemento2).getISBN());
				}
				return ((Libros) elemento1).getNombre().compareTo(((Libros) elemento2).getNombre());
			}
		};
		books = Principal.getBooks();
		Collections.sort(books, comparador);
		if (table != null) {
			table.setModel(plantilla());
		}

	}

	/**
	 * Metodo que genera la tabla. Con ciertos cambios de diseño
	 * 
	 * @return
	 */
	private JTable creacionDetable() {
		DefaultTableModel tableModel = plantilla();
		JTable table = new JTable(tableModel);
		table.setGridColor(Color.WHITE);
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
		for (int i = 0; i < books.size(); i++) {
			Vector<String> row = new Vector<>();
			row.add(((Libros) books.get(i)).getNombre());
			row.add(((Libros) books.get(i)).getAutor());
			row.add(Long.toString(((Libros) books.get(i)).getFecha()));
			row.add(Long.toString(((Libros) books.get(i)).getISBN()));
			data.add(row);
		}

		Vector<String> columns = new Vector<>();
		columns.add("Titulo");
		columns.add("Autor");
		columns.add("Fecha de Lanzamiento");
		columns.add("ISBN");

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
		} else if (modo.equalsIgnoreCase("Salir")) {
			JButton button = new JButton(text);
			button.setFont(new Font("Arial", Font.BOLD, 20));
			button.setBorderPainted(false);
			button.setBackground(Color.red);
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
		table.setModel(plantilla());
	}

}
