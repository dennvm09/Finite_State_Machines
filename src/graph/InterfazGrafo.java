package graph;

public interface InterfazGrafo<K, V> {

	
	
	void addVertex(V v);
	void removeVertex(K key);
	void addEdge(K k1, K k2, double w);
	void removeEdge(K k1, K k2);


}
