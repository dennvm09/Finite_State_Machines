package graph;


public class Pair<T> {

	private Vertex vertice;
	private double peso;
	private T ID;
	
	public Pair(Vertex vertice, double peso) {
		this.vertice = vertice;
		this.peso = peso;
		ID=null;
	}
	public Pair(Vertex vertice, double peso, T ID) {
		this.vertice = vertice;
		this.peso = peso;
		this.ID = ID;
	}

	public Vertex getVertice() {
		return vertice;
	}

	public void setVertice(Vertex vertice) {
		this.vertice = vertice;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}
	public T getID() {
		return ID;
	}

	public void setID(T ID) {
		this.ID = ID;
	}
}
