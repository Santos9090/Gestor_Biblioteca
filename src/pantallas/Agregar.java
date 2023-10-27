package pantallas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import datos.Dato;
import datos.Libros;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import principal.Principal;

/**
 * Esta clase representa una ventana para agregar libros en la aplicación.
 */
public class Agregar extends JFrame {
	private static final long serialVersionUID = -412080129862886356L;
	private JPanel contentPane;
	private JLabel titulo;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JLabel lblNewLabel;
	private JLabel lblAutor;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JButton button;
	private List<Dato> books;
	private JLabel ErrorLabel;

	/**
	 * Constructor de la clase Agregar.
	 *
	 * @param books La lista de libros existentes.
	 */
	public Agregar(List<Dato> books) {
		this.books = books;
		setTitle("Agregar Libro");
		setAlwaysOnTop(true);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		setSize(408, 487);
		int ancho_pantalla = screenSize.width - getWidth();
		int alto_pantalla = screenSize.height - getHeight();
		setBounds(ancho_pantalla / 2, alto_pantalla / 2, 408, 487);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setResizable(false);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		titulo = new JLabel("AGREGAR LIBRO");
		titulo.setFont(new Font("Arial Black", Font.BOLD, 25));
		titulo.setBounds(72, 11, 247, 84);
		contentPane.add(titulo);

		textField = new JTextField();
		textField.setBounds(92, 176, 242, 33);
		contentPane.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(92, 220, 242, 33);
		contentPane.add(textField_1);

		textField_2 = new JTextField();
		textField_2.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				String texto = textField_2.getText();
				if (!texto.matches("\\d*")) {
					textField_2.setText(texto.replaceAll("[^\\d]", ""));
				}
			}
		});
		textField_2.setColumns(10);
		textField_2.setBounds(92, 263, 242, 33);
		contentPane.add(textField_2);

		textField_3 = new JTextField();
		textField_3.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				String texto = textField_3.getText();
				if (!texto.matches("\\d*")) {
					textField_3.setText(texto.replaceAll("[^\\d]", ""));
				}
			}
		});
		textField_3.setColumns(10);
		textField_3.setBounds(92, 307, 242, 33);
		contentPane.add(textField_3);

		lblNewLabel = new JLabel("Titulo:");
		lblNewLabel.setFont(new Font("Arial Black", Font.BOLD, 14));
		lblNewLabel.setBounds(26, 179, 81, 23);
		contentPane.add(lblNewLabel);

		lblAutor = new JLabel("Autor:");
		lblAutor.setFont(new Font("Arial Black", Font.BOLD, 14));
		lblAutor.setBounds(26, 223, 81, 23);
		contentPane.add(lblAutor);

		lblNewLabel_2 = new JLabel("Año:");
		lblNewLabel_2.setFont(new Font("Arial Black", Font.BOLD, 14));
		lblNewLabel_2.setBounds(26, 266, 81, 23);
		contentPane.add(lblNewLabel_2);

		lblNewLabel_3 = new JLabel("ISBN");
		lblNewLabel_3.setFont(new Font("Arial Black", Font.BOLD, 14));
		lblNewLabel_3.setBounds(26, 310, 81, 23);
		contentPane.add(lblNewLabel_3);

		button = new JButton("Agregar");
		button.setBounds(155, 377, 121, 33);
		button.setBackground(new Color(75, 175, 255));
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Arial", Font.BOLD, 16));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String titulo = textField.getText();
				if (!comprobarExistencia(titulo)) {
					if (comprobarFields()) {
						String autor = textField_1.getText();
						long fecha = Long.parseLong(textField_2.getText());
						long ISBN = Long.parseLong(textField_3.getText());
						Libros libro = new Libros(titulo, autor, ISBN, fecha);
						Principal.agregarLibro(libro);
						Principal.makeInvisible(2);
						textField.setText("");
						textField_1.setText("");
						textField_2.setText("");
						textField_3.setText("");
						ErrorLabel.setText("");
					} else {
						ErrorLabel.setText("Rellene todos los campos");
					}
				} else {
					ErrorLabel.setText("Libro Ya Existente");
				}

			}

			private boolean comprobarFields() {
				if (textField.getText().equals("") || textField_1.getText().equals("")
						|| textField_2.getText().equals("") || textField_3.getText().equals("")) {
					return false;
				} else {
					return true;
				}
			}
		});
		contentPane.add(button);

		ErrorLabel = new JLabel("");
		ErrorLabel.setForeground(new Color(255, 0, 0));
		ErrorLabel.setFont(new Font("Arial Black", Font.BOLD, 15));
		ErrorLabel.setBounds(118, 99, 175, 14);
		contentPane.add(ErrorLabel);
	}

	public List<Dato> getBooks() {
		return books;
	}

	public void setBooks(List<Dato> books) {
		this.books = books;
	}

	/**
	 * Comprueba si un libro con el mismo título ya existe en la lista.
	 *
	 * @param titulo2 El título del libro a verificar.
	 * @return true si el libro ya existe, false en caso contrario.
	 */
	private boolean comprobarExistencia(String titulo2) {
		for (int i = 0; i < books.size(); i++) {
			if (((Libros) books.get(i)).getNombre().equalsIgnoreCase(titulo2)) {
				return true;
			}
		}
		return false;
	}
}
