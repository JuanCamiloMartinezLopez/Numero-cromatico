package logica;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Logica {

	int numNodos;
	int numEnlaces;
	int numeroCromatico;
	ArrayList<Nodo> Grafo = new ArrayList<Nodo>();
	ArrayList<String[]> Permutaciones= new ArrayList<String[]>();
	String[] permutacionGanadora;
	ArrayList<ArrayList<String>> conjuntosGanadores;
	String InformacionTotal="";
	int [][]MA;
	

	public Logica() {
		this.numNodos = 0;
		this.numEnlaces = 0;
		this.numeroCromatico=50;
	}
	
	public void AñadirNodo(int x, int y, int w, int h) {
		this.numNodos++;
		Grafo.add(new Nodo(this.numNodos,x,y,w,h));
	}

	public Point[] lineaOptima(Nodo a, Nodo b) {
		Point[] points = new Point[2];
		Point A = new Point();
		Point B = new Point();
		double dMenor = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				double d = distancia(a.puntos[i], b.puntos[j]);
				//System.out.println((i + 1) + "," + (j + 1) + ": " + d);
				if (dMenor == 0 || dMenor > d) {
					dMenor = d;
					A = a.puntos[i];
					B = b.puntos[j];
				}
			}
		}
		//System.out.println("dMenor: " + dMenor);
		points[0] = A;
		points[1] = B;
		//System.out.println("Ax:" + A.getX() + ",Ay:" + A.getY());
		//System.out.println("Bx:" + B.getX() + ",By:" + B.getY());
		return points;
	}

	public double distancia(Point A, Point B) {
		double d;
		double x = A.x - B.x;
		double y = A.y - B.y;
		d = (int) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		return d;
	}

	public void agregarEnlaces() {
		String infoEnlaces[];
		String infoEnlace[];
		String info;
		Enlace enlace;
		for (int i = 0; i < MA.length; i++) {
			Nodo nodoInicial = Grafo.get(i);
			for(int j=0; j< MA.length;j++) {
			//System.out.println(data[i][1]);
				if (MA[i][j]==1) {
					Nodo nodoFinal=this.Grafo.get(j);
					enlace=nodoInicial.agregarEnlace(nodoFinal);
					Point puntos[] = this.lineaOptima(nodoInicial, nodoFinal);
					System.out.println("Enlace entre el nodo : "+nodoInicial.num+" y el nodo "+nodoFinal.num);
					System.out.println("Nodo inicial: ");
					nodoInicial.info();
					System.out.println("-------------------------");
					System.out.println("Nodo final: ");
					nodoFinal.info();
					System.out.println("-------------------------");
					enlace.setPuntosConexion(puntos);
				} else {
				  continue;
				}
			}
		}
	}
	
	public void setMA(int[][] MA) {
		this.MA=MA;
		this.agregarEnlaces();
	}
	
	public void MostrarMA() {
		for (int i = 0; i < this.numNodos; i++) {
			for (int j = 0; j < this.numNodos; j++) {
				System.out.print(MA[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	public void NumeroCromatico() {
		ArrayList<ArrayList<String>> conjuntos= new ArrayList<ArrayList<String>>();
		this.CalcularPermutacion();
		int j=1;
		for(String[] permutacion: Permutaciones) {
			conjuntos=numeroCromaticoPermutacion(permutacion);
			if(this.numeroCromatico>conjuntos.size()) {
				this.numeroCromatico=conjuntos.size();
				this.permutacionGanadora=permutacion;
				this.conjuntosGanadores=conjuntos;
				if(this.numeroCromatico==2)break;
			}
			this.llenarInformacionTotal(j,this.infoPermutacionFinal(permutacion), this.infoConjuntos(conjuntos), this.infoNumeroCromatico(conjuntos.size()));
			j++;
		}
		System.out.println("Numero Cromatico: "+this.numeroCromatico);
		System.out.println("con la permutacion: ");
		for(String nodo:this.permutacionGanadora) {
			System.out.print(nodo+"-");
		}
		System.out.println("\ncon los conjuntos: ");
		int i=1;
		for(ArrayList<String> conjunto: this.conjuntosGanadores) {
			System.out.print("V"+i+"-> ");
			for(String nodo:conjunto) {
				System.out.print(nodo+",");
			}
			System.out.println();
			i++;
		}
		
	}
	
	public ArrayList<ArrayList<String>> numeroCromaticoPermutacion(String[] permutacion){
		System.out.println("se evalua la permutacion:");
		for(String nodo:permutacion) {
			System.out.print(nodo+"-");
		}
		System.out.println();
		ArrayList<ArrayList<String>> conjuntos= new ArrayList<ArrayList<String>>();
		for(int i=0;i<permutacion.length;i++) {
			if(conjuntos.isEmpty()) {
				ArrayList<String> conjunto=new ArrayList<String>();
				conjunto.add(permutacion[i]);
				System.out.println("se agrega el nodo "+permutacion[i] + " al conjunto 1"+" linea 1");
				conjuntos.add(conjunto);
				continue;
			}else {
				System.out.println("tamaño conjuntos:"+ conjuntos.size());
				boolean seAgrego=false;
				for(ArrayList<String> conjunto: conjuntos) {
					Boolean flag=true;
					System.out.println("tamaño conjunto:"+ conjunto.size());
					for(String nodo:conjunto) {
						System.out.println("son adyacentes?"+nodo+"-"+permutacion[i]+" respuesta: "+this.Adyacente(Integer.valueOf(nodo), Integer.valueOf(permutacion[i])));
						if(this.Adyacente(Integer.valueOf(nodo), Integer.valueOf(permutacion[i]))) {
							flag=false;
							break;
						}
					}
					if(flag) {
						conjunto.add(permutacion[i]);
						System.out.println("se agrega el nodo "+permutacion[i] + " al conjunto "+(conjuntos.indexOf(conjunto)+1)+" linea 2");
						 seAgrego=true;
						break;
					}else {
						continue;
					}
				}
				if(!seAgrego) {
					ArrayList<String> conjuntoNuevo=new ArrayList<String>();
					conjuntoNuevo.add(permutacion[i]);
					System.out.println("se agrega el nodo "+permutacion[i] + " al conjunto "+(conjuntos.size()+1)+" linea 3");
					conjuntos.add(conjuntoNuevo);
				}
			}
		}
		System.out.println("-------------------------------------------");
		return conjuntos;
	}
	
	public void CalcularPermutacion() {
		String[] elementos= new String[this.numNodos];
		for( int i=1;i<=this.numNodos;i++) {
			elementos[i-1]=String.valueOf(i);
		}
		Permutaciones(elementos,"",this.numNodos,elementos.length);
	}
	
	private void Permutaciones(String[] elem, String act, int n, int r) {
        if (n == 0) {
            System.out.println(act);
            //String info=act.substring(0, act.length()-2);
            String[] permutacion=act.split(",");
            this.Permutaciones.add(permutacion);
            System.out.println("--------"+permutacion.length+"----------");
            for(int j=0;j<permutacion.length;j++) {
            	System.out.print(permutacion[j]+"-");
            }
            System.out.println("\n------------------");
        } else {
            for (int i = 0; i < r; i++) {
                if (!act.contains(elem[i])) { // Controla que no haya repeticiones
                	Permutaciones(elem, act + elem[i] + ",", n - 1, r);
                }
            }
        }
    }
	
	public String infoPermutacionFinal() {
		String info="Permutacion: ";
		for(String nodo:this.permutacionGanadora) {
			info+=nodo+" ";
		}
		return info;
	}
	
	public String infoConjuntos() {
		String info="Conjuntos:\n";
		int i=1;
		for(ArrayList<String> conjunto: this.conjuntosGanadores) {
			info+="V"+i+"-> ";
			for(String nodo:conjunto) {
				info+=nodo+",";
			}
			info+="\n";
			i++;
		}
		return info;
	}
	
	public String infoNumeroCromatico() {
		return "Numero Cromatico: "+this.numeroCromatico;
	}
	
	public String infoPermutacionFinal(String[] permutacion) {
		String info="Permutacion: ";
		for(String nodo:permutacion) {
			info+=nodo+" ";
		}
		return info;
	}
	
	public String infoConjuntos(ArrayList<ArrayList<String>> conjuntos) {
		String info="Conjuntos:\n";
		int i=1;
		for(ArrayList<String> conjunto: conjuntos) {
			info+="V"+i+"-> ";
			for(String nodo:conjunto) {
				info+=nodo+",";
			}
			info+="\n";
			i++;
		}
		return info;
	}
	
	public String infoNumeroCromatico(int numCrom) {
		return "Numero Cromatico: "+numCrom;
	}
	
	public void llenarInformacionTotal(int numPer,String permutacion, String conjuntos, String numCrom) {
		this.InformacionTotal+="----------- "+numPer+" -----------";
		this.InformacionTotal+="\n";
		this.InformacionTotal+=permutacion;
		this.InformacionTotal+="\n";
		this.InformacionTotal+=conjuntos;
		this.InformacionTotal+=numCrom;
		this.InformacionTotal+="\n";
		this.InformacionTotal+="----------------------";
	}

	public int getNumNodos() {
		return numNodos;
	}
	
	public Boolean Adyacente(int A, int B) {
		if(MA[A-1][B-1]==1 ||MA[B-1][A-1]==1) {
			return true;
		}
		return false;
	}

	public ArrayList<Nodo> getGrafo() {
		return Grafo;
	}

	public ArrayList<ArrayList<String>> getConjuntosGanadores() {
		return conjuntosGanadores;
	}

	public String getInformacionTotal() {
		return InformacionTotal;
	}
	
}
