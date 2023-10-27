package pantallas;

import javax.swing.*;

import principal.Principal;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PantallaCarga extends JFrame {

	private Timer temporizador;
	private int progreso;

	public PantallaCarga() {
		setTitle("Simulación de Carga");
		setBackground(new Color(255, 0, 0));
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		int ancho_pantalla = screenSize.width;
		int alto_pantalla = screenSize.height;
		setSize(ancho_pantalla, alto_pantalla);
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		progreso = 0;
		temporizador = new Timer(100, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				progreso += 1;
				if (progreso >= 15) {
					temporizador.stop();
					Principal.makeInvisible(0);
					Principal.makeVisible(1);
				}
			}
		});
		getContentPane().setLayout(null);
		setUndecorated(true);
		JLabel ImgCarga = new JLabel("");
		ImgCarga.setIcon(new ImageIcon("loading.gif"));
		ImgCarga.setBounds(715, 316, 474, 408);
		getContentPane().add(ImgCarga);
	}

	public void iniciarCarga() {
		progreso = 0;
		temporizador.start();
	}
}
