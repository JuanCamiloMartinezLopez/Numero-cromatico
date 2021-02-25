package interfaz;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import logica.Logica;

public class Vista extends JFrame{
	
	JLabel titulo;
	JTextArea resultado;
	JButton boton;
	JButton botonMA;
	JScrollPane resultadoScroll;
	
	Tablero tablero;
	MatrizAdyacencia MA;
	Logica log;
	
	public Vista() {
		this.setLayout(null);
		this.setBounds(200, 50, 500,600);
		this.setTitle("Numero Cromatico");
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		log= new Logica();
		
		titulo= new JLabel("NUMERO CROMATICO");
		titulo.setFont(new Font("Arial", Font.BOLD, 25));
		titulo.setBounds(100, 5, 300, 40);
		//titulo.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
		this.getContentPane().add(titulo);
		
		tablero= new Tablero();
		tablero.setLog(log);
		this.getContentPane().add(tablero);
		
		resultado= new JTextArea();
		resultado.setEditable(false);
		resultado.setLineWrap(true);
		resultadoScroll=new JScrollPane(resultado);
		resultadoScroll.setBounds(240, 270, 252, 220);
		resultadoScroll.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		resultadoScroll.setVisible(false);
		this.getContentPane().add(resultadoScroll);
		
		botonMA= new JButton("Matriz Adyacencia");
		botonMA.setBounds(230, 525, 180, 20);
		botonMA.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MA= new MatrizAdyacencia(log.getNumNodos());
				MA.setLog(log);
				//LA.setBounds(190, 270, 120, 100);
				MA.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				MA.repaint();
				getContentPane().add(MA);
				resultadoScroll.setVisible(true);
			}
		});
		this.getContentPane().add(botonMA);
		
		
		//tablero.setLa(LA);*/
		
		boton= new JButton("Calcular");
		boton.setBounds(90, 525, 120, 20);
		boton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("entro al boton");
				log.setMA(MA.getInfo());
				log.MostrarMA();
				tablero.paintEnlaces();
				log.NumeroCromatico();
				tablero.paintResultado();
				resultado.setText(log.getInformacionTotal());
			}
		});
		this.getContentPane().add(boton);
		
		this.repaint();
	}
}
