package pantallas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import datos.Dato;
import datos.Libros;
import datos.Prestamos;
import principal.Principal;
import javax.swing.JComboBox;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;

public class Registrar extends JFrame {
	private static final long serialVersionUID = -3811800021910275121L;
	private JLabel titulo;
	private JTextField textField_1;
	private JLabel lblNewLabel;
	private JLabel lblAutor;
	private JButton button;
	private JPanel contentPane;
	private JComboBox<String> comboBox;
	private List<Dato> books;
	private List<Dato> history;

	/**
	 * Pantalla para crear registros. En base a los registros de libros
	 * 
	 * @param books
	 * @param history
	 */
	public Registrar(List<Dato> books, List<Dato> history) {
		this.books = books;
		this.history = history;
		setResizable(false);
		setAlwaysOnTop(true);
		setTitle("Registrar Prestamo");
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		setSize(408, 487);
		int ancho_pantalla = screenSize.width - getWidth();
		int alto_pantalla = screenSize.height - getHeight();
		setBounds(ancho_pantalla / 2, alto_pantalla / 2, 408, 487);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		titulo = new JLabel("Registrar ");
		titulo.setHorizontalAlignment(SwingConstants.CENTER);
		titulo.setFont(new Font("Arial Black", Font.BOLD, 25));
		titulo.setBounds(39, 11, 313, 81);
		contentPane.add(titulo);

		JLabel lblPrestamoODevolucion = new JLabel("Prestamo o Devolucion");
		lblPrestamoODevolucion.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrestamoODevolucion.setFont(new Font("Arial Black", Font.BOLD, 25));
		lblPrestamoODevolucion.setBounds(24, 47, 343, 81);
		contentPane.add(lblPrestamoODevolucion);

		comboBox = new JComboBox<String>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				recargarBtn();
			}
		});
		comboBox.setModel(crearModelo());
		comboBox.setBounds(92, 176, 242, 33);
		contentPane.add(comboBox);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(92, 220, 242, 33);
		contentPane.add(textField_1);

		lblNewLabel = new JLabel("Titulo:");
		lblNewLabel.setFont(new Font("Arial Black", Font.BOLD, 14));
		lblNewLabel.setBounds(26, 179, 81, 23);
		contentPane.add(lblNewLabel);

		lblAutor = new JLabel("Usuario:");
		lblAutor.setFont(new Font("Arial Black", Font.BOLD, 14));
		lblAutor.setBounds(10, 223, 81, 23);
		contentPane.add(lblAutor);

		button = new JButton("Prestar");
		button.setBounds(155, 377, 121, 33);
		button.setBackground(new Color(75, 175, 255));
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Arial", Font.BOLD, 16));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (button.getText().equalsIgnoreCase("Prestar")) {
					String titulo = (String) comboBox.getSelectedItem();
					String user = textField_1.getText();
					textField_1.setText("");
					Prestamos aux = new Prestamos(buscaLibro(titulo), new Date(), null, user);
					Principal.agregarPrestamo(aux);
					Principal.makeInvisible(4);
				} else {
					String titulo = (String) comboBox.getSelectedItem();
					for (int i = 0; i < history.size(); i++) {
						if (((Prestamos) history.get(i)).getLibro().getNombre().equalsIgnoreCase(titulo)) {
							Prestamos pres = (Prestamos) history.get(i);
							pres.setFechaDevolucion(new Date());
							Principal.setPrestamo(i, pres);
							Principal.makeInvisible(4);
						}
					}
				}
			}
		});
		contentPane.add(button);

	}

	/**
	 * Metodo que busca entre los registros de libros en base a su nombre.
	 * 
	 * @param titulo2
	 * @return
	 */
	private Libros buscaLibro(String titulo2) {
		Libros salida = null;
		for (Dato libros : books) {
			if (((Libros) libros).getNombre().equalsIgnoreCase(titulo2)) {
				salida = (Libros) libros;
			}
		}
		return salida;
	}

	/**
	 * Metodo que en base a los datos de registro de Libros crea un modelo para
	 * elegir.
	 * 
	 * @return
	 */
	private DefaultComboBoxModel<String> crearModelo() {
		String[] LibrosaTituloaArray = new String[books.size()];
		for (int i = 0; i < books.size(); i++) {
			LibrosaTituloaArray[i] = ((Libros) books.get(i)).getNombre();
		}
		DefaultComboBoxModel<String> aux = new DefaultComboBoxModel<>(LibrosaTituloaArray);
		return aux;
	}

	public List<Dato> getBooks() {
		return books;
	}

	public void setBooks(List<Dato> books) {
		this.books = books;
		comboBox.setModel(crearModelo());
	}

	public List<Dato> getHistory() {
		return history;
	}

	public void setHistory(List<Dato> history) {
		this.history = history;
	}

	/**
	 * Cambia el texto de el boton y otros campos en funcion de si se esta
	 * registrando un prestamo o una devolucon
	 */
	public void recargarBtn() {
		for (Dato pres : Registrar.this.history) {
			if (((Prestamos) pres).getLibro().getNombre().equalsIgnoreCase((String) comboBox.getSelectedItem())
					&& (((Prestamos) pres).getFechaDevolucion() == null)) {
				button.setText("Devolver");
				textField_1.setVisible(false);
				lblAutor.setVisible(false);
				break;
			} else {
				button.setText("Prestar");
				lblAutor.setVisible(true);
				textField_1.setVisible(true);
			}
		}
	}

}
