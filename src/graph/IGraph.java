package graph;

public interface IGraph<K, V> {

	
	
	void addVertex(V v);
	void removeVertex(K key);
	void addEdge(K k1, K k2, double w);
	void removeEdge(K k1, K k2);


}
