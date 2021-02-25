package interfaz;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import logica.Logica;

public class MatrizAdyacencia extends JPanel {

	JTable Matriz;
	JScrollPane js;
	Logica log;
	DefaultTableModel model;

	int numNodos;

	public MatrizAdyacencia(int numNodos) {
		this.numNodos = numNodos;
		this.inicializar();
	}

	public void inicializar() {
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		String[] columnNames = this.colNames();
		Object data[][] = this.dataInicial();

		Matriz = new JTable(data, columnNames);
		
		this.darTamaño();
		
		JScrollPane scrollPane = new JScrollPane(Matriz);
		scrollPane.setBounds(1, 1, (this.numNodos + 1) * 20,(this.numNodos + 1) * 20);
		JTable rowTable = new RowNumberTable(Matriz);
		scrollPane.setRowHeaderView(rowTable);
		add(scrollPane);
		setBounds(10, 270,(this.numNodos + 1) * 20,(this.numNodos + 1) * 20);
	}

	public void darTamaño() {
		TableColumnModel columnModel = this.Matriz.getColumnModel();
		for (int i = 0; i < columnModel.getColumnCount(); i++) {
			columnModel.getColumn(i).setWidth(20);
		}
		this.Matriz.setRowHeight(20);
	}
	

	public String[] colNames() {
		String[] colNames = new String[this.numNodos];
		for (int i = 0; i < this.numNodos; i++) {
			colNames[i] = String.valueOf((i+1));
		}

		return colNames;
	}

	public Object[][] dataInicial() {
		Object data[][] = new Object[this.numNodos][this.numNodos];
		for (int i = 0; i < this.numNodos; i++) {
			for (int j = 0; j < this.numNodos; j++) {
				if (j >= i) {
					data[i][j] = "0";
				} else {
					data[i][j] = "X";
				}
			}
		}
		return data;
	}

	public void setLog(Logica log) {
		this.log = log;
	}
	
	public int[][]  getInfo() {
		int data[][] = new int[this.numNodos][this.numNodos];
		for (int i = 0; i < this.numNodos; i++) {
			for (int j = 0; j < this.numNodos; j++) {
				if (j >= i) {
					data[i][j] = Integer.parseInt((String) this.Matriz.getModel().getValueAt(i, j));
				} else {
					data[i][j] = data[j][i];
				}
			}
		}
		return data;
	}

}
