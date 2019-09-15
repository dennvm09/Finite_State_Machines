package graph;

import java.util.ArrayList;

public class Vertex<K, V> {

	public final static int INFINITO = Integer.MAX_VALUE;
	public final static char WHITE = 'W';
	public final static char GRAY = 'G';
	public final static char BLACK = 'B';

	public K key;
	public V valor;

	public boolean isInitial=false;


	public ArrayList<Pair> ady;

	private char Color;
	private Vertex<K, V> padre;
	private int distancia;
	private int distanciaFinal;

	public Vertex(K key) {
		this.key = key;
		ady = new ArrayList<Pair>();
		valor = null;
	}

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public ArrayList<Pair> getAdy() {
		return ady;
	}

	public void setParejas(ArrayList<Pair> ady) {
		this.ady = ady;
	}

	public V getValor() {
		return valor;
	}

	public void setValor(V valor) {
		this.valor = valor;
	}

	public void addPareja(Vertex vertice, double peso) {
		ady.add(new Pair(vertice, peso));

	}
	public void addPareja1(Vertex vertice, double peso, String ruta) {
		ady.add(new Pair(vertice, peso, ruta));

	}
	public char getColor() {
		return Color;
	}

	public void setColor(char color) {
		Color = color;
	}

	public Vertex<K, V> getPadre() {
		return padre;
	}

	public void setPadre(Vertex<K, V> padre) {
		this.padre = padre;
	}

	public int getDistancia() {
		return distancia;
	}

	public void setDistancia(int distancia) {
		this.distancia = distancia;
	}

	public void setAdy(ArrayList<Pair> ady) {
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
