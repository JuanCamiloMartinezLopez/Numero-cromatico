package interfaz;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logica.Enlace;
import logica.Logica;
import logica.Nodo;
import logica.Point;

@SuppressWarnings("serial")
public class Tablero extends JPanel{
	
	
	Logica log;
	MatrizAdyacencia la;
	
	Color colores[]= new Color[10];

	public Tablero() {
		//this.graphics= this.getGraphics();
		setBounds(10, 50, 480, 200);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setBackground(Color.WHITE);
		addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
            	if(log.getNumNodos()<10) {
            		addNodo(e.getX(),e.getY());
            	}else {
            		JOptionPane.showMessageDialog(null, "Maximo 10 Nodos","Alerta", JOptionPane.INFORMATION_MESSAGE, null);
            	}
            }
        });
		
		colores[0]=Color.blue;
		colores[1]=Color.cyan;
		colores[2]=Color.gray;
		colores[3]=Color.green;
		colores[4]=Color.magenta;
		colores[5]=Color.orange;
		colores[6]=Color.pink;
		colores[7]=Color.red;
		colores[8]=Color.yellow;
		colores[9]=Color.lightGray;
	}
	
	protected void addNodo(int x, int y) {
		Rectangle2D bounds=this.getGraphics().getFontMetrics().getStringBounds(String.valueOf(log.getNumNodos()), this.getGraphics());
    	int w=(int)bounds.getWidth();
    	int h= (int)bounds.getHeight();
		log.AñadirNodo(x,y,h,w);
    	paintNodo(x,y);
	}
	
	public void paintNodo(int x, int y) {
		this.getGraphics().drawString(String.valueOf(log.getNumNodos()), x,y);
		//g.drawRect(x, y-13, 8, 13);
    }
	
	public void paintEnlaces() {
		ArrayList<Nodo> nodos=this.log.getGrafo();
		ArrayList<Enlace> enlaces;
		Enlace enlace;
		for(int i=0;i<nodos.size();i++) {
			enlaces=nodos.get(i).getEnlaces();
			if(enlaces.size()>0) {
				for(int j=0;j<enlaces.size();j++) {
					enlace=enlaces.get(j);
					paintEnlace(enlace.getPinicial(),enlace.getPFinal());
				}
			}
		}
		this.paintOvalos(nodos);
	}
	
	public void paintEnlace( Point A,Point B) {
		//this.getGraphics().drawLine(2,2,10,10);
		System.out.println("Ax:"+A.getX()+",Ay:"+A.getY());
		System.out.println("Bx:"+B.getX()+",By:"+B.getY());
    	this.getGraphics().drawLine(A.getX(), A.getY(), B.getX(), B.getY());
    }
	
	public void paintOvalos(ArrayList<Nodo> nodos) {
		
		System.out.println("Se entra a dibujar los ovalos");
		for(Nodo nod: nodos) {
			System.out.println("diametro "+nod.getDiametro());
			this.getGraphics().drawOval(nod.getInicioCirculo().getX(), nod.getInicioCirculo().getY(), nod.getDiametro(), nod.getDiametro());
			//this.getGraphics().drawRect(nod.getX(), nod.getY(), nod.getDiametro(), nod.getDiametro());
			//this.getGraphics().drawString(String.valueOf(nod.num), nod.getX(), nod.getY());
		}
		//this.getGraphics().drawLine(2,2,10,10);
    }
	
	public void paintResultado() {
		this.paintPermutacion(this.getGraphics());
		this.paintNumeroCromatico(this.getGraphics());
		this.paintConjuntos(this.getGraphics());
		this.colorearNodos(this.getGraphics());		
	}
	
	public void paintPermutacion(Graphics g) {
		g.setColor(Color.RED);
		g.drawString(log.infoPermutacionFinal(), 5, 15);
	}
	
	public void paintNumeroCromatico(Graphics g) {
		g.setColor(Color.RED);
		g.drawString(log.infoNumeroCromatico(),340,15);
	}
	
	public void paintConjuntos(Graphics g) {
		g.setColor(Color.RED);
		g.drawString(log.infoConjuntos(), 5,180);
	}
	
	public void colorearNodos(Graphics g) {
		ArrayList<ArrayList<String>> conjuntos=this.log.getConjuntosGanadores();
		ArrayList<Nodo> grafo= this.log.getGrafo();
		int i=0;
		for(ArrayList<String> conjunto: conjuntos) {
			Color color=colores[i];
			for(String nodo:conjunto) {
				Nodo n=grafo.get(Integer.valueOf(nodo)-1);
				g.setColor(color);
				g.fillOval(n.getInicioCirculo().getX(), n.getInicioCirculo().getY(), n.getDiametro(), n.getDiametro());
				g.setColor(Color.WHITE);
				g.drawString(nodo, n.getX(), n.getYDraw());
			}
			i++;
		}
	}
	
	public void setLog(Logica log) {
		this.log = log;
	}


}
