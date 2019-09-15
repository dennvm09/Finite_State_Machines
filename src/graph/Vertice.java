package graph;

import java.util.ArrayList;

public class Vertice<K, V> {

	// Atributos de color. Se usa en "BFS".
	public final static int INFINITO = Integer.MAX_VALUE;
	public final static char WHITE = 'W';
	public final static char GRAY = 'G';
	public final static char BLACK = 'B';

	// Identificador del vertice.
	public K key;
	// Objeto o atributo que se guarda en el vertice.
	public V valor;

	public boolean isInitial=false;


	// Lista de adyacencia (lista de parejas).
	public ArrayList<Pareja> ady;

	// Color actual del vertice. Se usa en "BFS" . Se usa en "DFS".
	private char Color;
	// Padre o predecesor del vertice. Se usa en "BFS". Se usa en "DFS".
	private Vertice<K, V> padre;
	// Distancia de este vertece a la raiz definida, Sirve tambien como timestamp
	// guardando la distancia la primera vez. Se usa en "BFS". Se usa en
	// "DFS".
	private int distancia;
	// Sirve como timestamp, guarda la distancia ultima vez que pasa. Se usa en
	// "DFS".
	private int distanciaFinal;

	public Vertice(K key) {
		this.key = key;
		ady = new ArrayList<Pareja>();
		valor = null;
	}

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public ArrayList<Pareja> getAdy() {
		return ady;
	}

	public void setParejas(ArrayList<Pareja> ady) {
		this.ady = ady;
	}

	public V getValor() {
		return valor;
	}

	public void setValor(V valor) {
		this.valor = valor;
	}

	public void addPareja(Vertice vertice, double peso) {
		ady.add(new Pareja(vertice, peso));

	}
	public void addPareja1(Vertice vertice, double peso, String ruta) {
		ady.add(new Pareja(vertice, peso, ruta));

	}
	public char getColor() {
		return Color;
	}

	public void setColor(char color) {
		Color = color;
	}

	public Vertice<K, V> getPadre() {
		return padre;
	}

	public void setPadre(Vertice<K, V> padre) {
		this.padre = padre;
	}

	public int getDistancia() {
		return distancia;
	}

	public void setDistancia(int distancia) {
		this.distancia = distancia;
	}

	public void setAdy(ArrayList<Pareja> ady) {
		this.ady = ady;
	}

	public int getDistanciaFinal() {
		return distanciaFinal;
	}

	public void setDistanciaFinal(int distanciaFinal) {
		this.distanciaFinal = distanciaFinal;
	}
	
	public boolean isInitial() {
		return isInitial;
	}

	public void setInitial(boolean isInitial) {
		this.isInitial = isInitial;
	}
}
