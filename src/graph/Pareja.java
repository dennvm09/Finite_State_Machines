package graph;


public class Pareja<T> {

	private Vertice vertice;
	private double peso;
	private T ID;
	
	public Pareja(Vertice vertice, double peso) {
		this.vertice = vertice;
		this.peso = peso;
		ID=null;
	}
	public Pareja(Vertice vertice, double peso, T ID) {
		this.vertice = vertice;
		this.peso = peso;
		this.ID = ID;
	}

	public Vertice getVertice() {
		return vertice;
	}

	public void setVertice(Vertice vertice) {
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
