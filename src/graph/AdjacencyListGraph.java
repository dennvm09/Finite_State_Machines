package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import javax.naming.ldap.HasControls;

public class AdjacencyListGraph<K, V> implements IGraph<K, V> {
	public static final double INFINITO = 10000000.0;
	// Sirve para el DFS
	int tiempo;
	
	private HashMap<K, V> verticesMap;
	private boolean dirigido;
	private static ArrayList a;
	/**
	 * Crea un nuevo grafa
	 * @param dirigido-saber si es o no dirigido
	 */

	public AdjacencyListGraph(boolean dirigido) {
		verticesMap = new HashMap<K, V>();
		this.dirigido = dirigido;
		tiempo = 0;
	}

	/**
	 * Metodo que retorna un vertice del grafo.
	 * @param k - llave que identifica el vertice.
	 * @return Vertice
	 */
	public Vertex getVertice(K k) {
		return (Vertex) verticesMap.get(k);
	}

	/**
	 * Metodo que realiza el recorrido por amplitud.
	 * @param k -llave que identifica el vertice de inicio.
	 */
	public void BFS(K k) {
		@SuppressWarnings("unchecked")
		K[] listaClaves = (K[]) verticesMap.keySet().toArray();
		for (int i = 0; i < listaClaves.length; i++) {
			((Vertex) verticesMap.get(listaClaves[i])).setColor(Vertex.WHITE);
			((Vertex) verticesMap.get(listaClaves[i])).setDistancia(Vertex.INFINITO);
			((Vertex) verticesMap.get(listaClaves[i])).setPadre(null);
		}
		Vertex vertInicial = (Vertex) verticesMap.get(k);
		vertInicial.setColor(Vertex.GRAY);
		vertInicial.setDistancia(0);

		Queue<Vertex> cola = new LinkedList<>();
		cola.offer(vertInicial);

		while (!cola.isEmpty()) {
			Vertex verActual = cola.poll();
			ArrayList<Pair> listaAdya = verActual.getAdy();
			for (int i = 0; i < listaAdya.size(); i++) {
				Vertex verticeAdy = listaAdya.get(i).getVertice();
				if (verticeAdy.getColor() == Vertex.WHITE) {
					verticeAdy.setColor(Vertex.GRAY);
					verticeAdy.setDistancia(verActual.getDistancia() + 1);
					verticeAdy.setPadre(verActual);
					cola.offer(verticeAdy);
				}
			}
			verActual.setColor(Vertex.BLACK);
		}
	}
	
	/**
	 * Metodo que realiza el recorrido por amplitud
	 * @param k - llave que identifica el vertice de inicio
	 */
	public ArrayList<K> BFS2(K k) {
		@SuppressWarnings("unchecked")
		K[] listaClaves = (K[]) verticesMap.keySet().toArray();
		for (int i = 0; i < listaClaves.length; i++) {
			((Vertex) verticesMap.get(listaClaves[i])).setColor(Vertex.WHITE);
			((Vertex) verticesMap.get(listaClaves[i])).setDistancia(Vertex.INFINITO);
			((Vertex) verticesMap.get(listaClaves[i])).setPadre(null);
		}
		Vertex vertInicial = (Vertex) verticesMap.get(k);
		vertInicial.setColor(Vertex.GRAY);
		vertInicial.setDistancia(0);

		Queue<Vertex> cola = new LinkedList<>();
		cola.offer(vertInicial);

		ArrayList response = new ArrayList();
		response.add(vertInicial.getKey());
		
		while (!cola.isEmpty()) {
			Vertex verActual = cola.poll();
			ArrayList<Pair> listaAdya = verActual.getAdy();
			for (int i = 0; i < listaAdya.size(); i++) {
				Vertex verticeAdy = listaAdya.get(i).getVertice();
				if (verticeAdy.getColor() == Vertex.WHITE) {
					verticeAdy.setColor(Vertex.GRAY);
					response.add(verticeAdy.getKey());
					verticeAdy.setDistancia(verActual.getDistancia() + 1);
					verticeAdy.setPadre(verActual);
					cola.offer(verticeAdy);
				}
			}
			verActual.setColor(Vertex.BLACK);
		}
		
		return response;
	}
	
	
    /**
     * Metodo que calcula el camino mas corto entre dos vertices
     * @param inicio - llave que identifica el vertice de inicio
     * @param llegada - llave que identifica el vertice de llegada
     */
	
	public void shortestPath(K k1, K k2) {
		a = new ArrayList();
		if (k2 == k1) {
			
		} else if (((Vertex) verticesMap.get(k2)).getPadre() == null) {

		} else
			shortestPath(k1, (K) ((Vertex) verticesMap.get(k2)).getPadre().getKey());
		a.add(k2);
		
	}
	
	/**
	 * Metodo que realiza el recorrido por profundidad
	 */
	public void DFS() {
		K[] keyArray = (K[]) verticesMap.keySet().toArray();
		for (int i = 0; i < keyArray.length; i++) {
			((Vertex) verticesMap.get(keyArray[i])).setColor(Vertex.WHITE);
			((Vertex) verticesMap.get(keyArray[i])).setPadre(null);
		}
		tiempo = 0;
		for (int i = 0; i < keyArray.length; i++) {
			Vertex vertInicial = ((Vertex) verticesMap.get(keyArray[i]));
			if (vertInicial.getColor() == Vertex.WHITE) {
				DFSVisit((V) vertInicial);
			}
		}
	}

	/**
	 * Metodo que realiza el recorrido auxiliar del metodo DFS
	 * @param vertice-Vertice inicial
	 */
	public void DFSVisit(V v) {
		tiempo = tiempo + 1;
		((Vertex) v).setDistancia(tiempo);
		((Vertex) v).setColor(Vertex.GRAY);

		ArrayList<Pair> listaAdya = ((Vertex) v).getAdy();
		for (int i = 0; i < listaAdya.size(); i++) {
			Vertex verticeAdy = listaAdya.get(i).getVertice();
			if (verticeAdy.getColor() == Vertex.WHITE) {
				verticeAdy.setPadre((Vertex) v);
				DFSVisit((V) verticeAdy);
			}
		}
		((Vertex) v).setColor(Vertex.BLACK);
		tiempo = tiempo + 1;
		((Vertex) v).setDistanciaFinal(tiempo);
	}

	/**
	 * Metodo que calcula el camino mas corto y de longitud minima entre un vertice y los demas 
	 * @param keyInicial - llave que identifica el vertice al que se le debe encontrar longitud minima
	 * Ingresa el key del vertice desde donde quiero empezar. Uso un arrayList de
	   ArrayList en donde hay dos cosas 1) el peso mas pequeno de un vertice al
	   vertice inicial, 2) la clave del vertice al que corresponde ese peso.
	 * @return Devuelve Camino mas corto y longitud minima entre un nodo y los demas
	 */
	public ArrayList Dijkstra(K keyInicial) {
		K[] listaClaves = (K[]) verticesMap.keySet().toArray();
		ArrayList<ArrayList> L = new ArrayList<ArrayList>();

		for (int i = 0; i < listaClaves.length; i++) {
			ArrayList<String> interno = new ArrayList<String>();
			K llaveActual = listaClaves[i];
			if (llaveActual.equals(keyInicial)) {
				interno.add(0 + "");
			} else
				interno.add(Integer.MAX_VALUE + "");
			interno.add(llaveActual + "");
			interno.add(llaveActual + "");
			interno.add("ruta");
			interno.add("pesoRuta");
			L.add(interno);
		}
		ArrayList S = new ArrayList<>();
		int contador = 0;

		while (contador != listaClaves.length) {
			int posicL = -1;
			K u = null;
			int menor = Integer.MAX_VALUE;
			for (int i = 0; i < listaClaves.length; i++) {
				boolean estaEnS = false;
				K claveActualEnL = ((K) L.get(i).get(1));
				// Verifico que la llave que voy a comparar no exista en el conjunto S
				for (int j = 0; j < S.size(); j++) {
					K claveActualEnS = ((K) S.get(j));
					if (claveActualEnL.equals(claveActualEnS)) {
						estaEnS = true;
					}
				}
				// Sí no estaba en el conjunto S entonces lo comparo para saber si es menor
				if (!estaEnS) {
					int actual = Integer.parseInt((String) L.get(i).get(0));
					if (actual < menor) {
						u = (K) L.get(i).get(1);
						posicL = i;
						menor = actual;
					}
				}
			}
			S.add(u);
			contador++;
			for (int i = 0; i < listaClaves.length; i++) {
				boolean estaEnS = false;
				K claveActualEnL = ((K) L.get(i).get(1));
				// Verifico que la llave que voy a comparar no exista en el conjunto S
				for (int j = 0; j < S.size(); j++) {
					K claveActualEnS = (K) S.get(j);
					if (claveActualEnL.equals(claveActualEnS)) {
						estaEnS = true;
					}
				}
				if (!estaEnS) {
					int numeroVecinos = 0;
					ArrayList adyacentes = null;
					Vertex verticeU = (Vertex) verticesMap.get(u);
					if (verticeU.getAdy() != null) {
						adyacentes = verticeU.getAdy();
						numeroVecinos = adyacentes.size();

					}

					for (int j = 0; j < numeroVecinos; j++) {
						// Peso de esa ruta
						int menorPesoAV = Integer.MAX_VALUE;
						// Para el problema
						String ruta = null;
						K vecino = null;
						vecino = (K) ((Pair) adyacentes.get(j)).getVertice().getKey();
						
						for (int j2 = 0; j2 < numeroVecinos ; j2++) {
							K actual = (K) ((Pair) adyacentes.get(j2)).getVertice().getKey();
							if (actual.equals(vecino)) {
								
								int pesoActual = (int) ((Pair) adyacentes.get(j2)).getPeso();
								if (pesoActual < menorPesoAV) {
									menorPesoAV = pesoActual;
									ruta = (String) ((Pair) adyacentes.get(j2)).getID();
									
								}
							}
						}
						int posicionV = -1;
						boolean encontrado1 = false;
						for (int k = 0; k < L.size() && !encontrado1; k++) {
							K claveActual = (K) L.get(k).get(1);
							if (claveActual.equals(vecino)) {
								posicionV = k;
								encontrado1 = true;
							}
						}
						double valorUEnL = ((Integer.parseInt((String) L.get(posicL).get(0))));
						double valorVEnL = Integer.parseInt((String) L.get(posicionV).get(0));

						if (posicL != -1 && (valorUEnL + menorPesoAV) < valorVEnL) {
							int nuevo = (int) (valorUEnL + menorPesoAV);
							ArrayList arregloV = L.get(posicionV);
							String textoAConcatenar = (String) L.get(posicL).get(2) + ","
									+ (String) L.get(posicionV).get(1);

							arregloV.set(0, (((nuevo + ""))));
							arregloV.set(2, textoAConcatenar);
							arregloV.set(3, ruta);
							arregloV.set(4, menorPesoAV + "");
						}
					}
				}
			}
		}
		return L;
	}
   
	/**
	 * Metodo que calcula el camino mas corto y de longitud minima entre un vertice y los demas 
	 * @param keyInicial - llave que identifica el vertice al que se le debe encontrar longitud minima
	 * Ingresa el key del vertice desde donde quiero empezar. Uso un arrayList de
	   ArrayList en donde hay dos cosas 1) el peso mas pequeno de un vertice al
	   vertice inicial, 2) la clave del vertice al que corresponde ese peso.
	 * @return Devuelve Camino mas corto y longitud minima entre un nodo y los demas
	 */
	public ArrayList Dijkstra1(K keyInicial) {
		K[] listaClaves = (K[]) verticesMap.keySet().toArray();
		ArrayList<ArrayList> L = new ArrayList<ArrayList>();
		for (int i = 0; i < listaClaves.length; i++) {
			ArrayList interno = new ArrayList<>();
			if (listaClaves[i].equals(keyInicial)) {
				interno.add(0);
			} else
				interno.add(Integer.MAX_VALUE);
			interno.add(listaClaves[i]);
			L.add(interno);
		}
		ArrayList S = new ArrayList<>();
		int contador = 0;

		while (contador != listaClaves.length) {
			int posicL = -1;
			K u = null;
			int menor = Integer.MAX_VALUE;
			for (int i = 0; i < listaClaves.length; i++) {
				boolean estaEnS = false;
				// Verifico que la llave que voy a comparar no exista en el conjunto S
				for (int j = 0; j < S.size(); j++) {
					if (((K) L.get(i).get(1)).equals((K) S.get(j))) {
						estaEnS = true;
					}
				}
				// Sí no estaba en el conjunto S entonces lo comparo para saber si es menor
				if (!estaEnS) {
					int actual = (int) L.get(i).get(0);
					if (actual < menor) {
						u = (K) L.get(i).get(1);
						posicL = i;
						menor = actual;
					}
				}
			}
			S.add(u);
			contador++;
			for (int i = 0; i < listaClaves.length; i++) {
				boolean estaEnS = false;
				// Verifico que la llave que voy a comparar no exista en el conjunto S
				for (int j = 0; j < S.size(); j++) {
					if (((K) L.get(i).get(1)).equals((K) S.get(j))) {
						estaEnS = true;
					}
				}
				if (!estaEnS) {
					ArrayList adyacentes = ((Vertex) verticesMap.get(u)).getAdy();
					int numeroVecinos = 0;
					if (adyacentes != null)
						numeroVecinos = adyacentes.size();

					for (int j = 0; j < numeroVecinos; j++) {
						int menorCaminoAV = Integer.MAX_VALUE;
						K vecino = null;
						vecino = (K) ((Pair) adyacentes.get(j)).getVertice().getKey();

						for (int j2 = 0; j2 < numeroVecinos; j2++) {
							K actual = (K) ((Pair) adyacentes.get(j2)).getVertice().getKey();
							if (actual.equals(vecino)) {
								int pesoActual = (int) ((Pair) adyacentes.get(j2)).getPeso();
								if (pesoActual < menorCaminoAV) {
									menorCaminoAV = pesoActual;
								}
							}
						}
						int posicionV = -1;
						for (int k = 0; k < L.size(); k++) {
							if (L.get(k).get(1) == vecino) {
								posicionV = k;
							}
						}
						if (posicL != -1
								&& ((((int) L.get(posicL).get(0))) + menorCaminoAV) < (int) L.get(posicionV).get(0)) {
							L.get(posicionV).set(0, ((((int) L.get(posicL).get(0))) + menorCaminoAV));
						}
					}
				}
			}
		}
		return L;
	}

	/**
	 * Metodo que genera una matriz con los pesos de cada nodo
	 * @return weights - Matriz con los pesos
	 */
	public double[][] weightsMatrix() {
		K[] keyArray = (K[]) verticesMap.keySet().toArray();
		double[][] weights = new double[keyArray.length][keyArray.length];
		for (int i = 0; i < keyArray.length; i++) {
			Vertex vertice = (Vertex) verticesMap.get(keyArray[i]);
			ArrayList<Pair> ady = vertice.getAdy();
			for (int j = 0; j < keyArray.length; j++) {
				weights[i][j] = INFINITO;
				if (i == j)
					weights[i][j] = 0;
				for (int j2 = 0; j2 < ady.size(); j2++) {
					K verticeAComp = (K) ((Vertex) verticesMap.get(keyArray[j])).getKey();
					K verticeAdy = (K) ((Pair) ady.get(j2)).getVertice().getKey();
					if (verticeAdy.equals(verticeAComp)) {
						weights[i][j] = ((Pair) ady.get(j2)).getPeso();
					}
				}
			}
		}
		return weights;
	}

	/**
	 * Metodo que calcula el camino  minimo entre todos los nodos, con los demas
	 * @return paths - matriz de arreglos que contiene el camino minimo entre todos los nodos con los demas
	 */
	public ArrayList<Vertex<K, V>>[][] floydWarshall() {
		K[] keyArray = (K[]) verticesMap.keySet().toArray();
		double[][] matriz = weightsMatrix().clone();

		ArrayList<Vertex<K, V>>[][] paths = new ArrayList[verticesMap.size()][verticesMap.size()];

		for (int i = 0; i < paths.length; i++) {
			for (int j = 0; j < paths[0].length; j++) {
				paths[i][j] = new ArrayList();
				if (matriz[i][j] != INFINITO) {
					paths[i][j].add((Vertex) verticesMap.get(keyArray[i]));
					if (i != j && !dirigido) {
						paths[i][j].add((Vertex) verticesMap.get(keyArray[j]));
					}
				}
			}
		}
		for (int i = 0; i < verticesMap.size(); i++) {

			for (int j = 0; j < verticesMap.size(); j++) {
				for (int k = 0; k < verticesMap.size(); k++) {

					if (j != k && j != i && k != i) {

						if (matriz[j][i] + matriz[i][k] < matriz[j][k]) {

							matriz[j][k] = matriz[j][i] + matriz[i][k];

							paths[j][k] = new ArrayList<Vertex<K, V>>();
							paths[j][k].addAll(paths[j][i]);
							ArrayList<Vertex<K, V>> aux = (ArrayList<Vertex<K, V>>) paths[i][k].clone();
							if (aux.size() > 0) {
								aux.remove(0);
							}
							paths[j][k].addAll(aux);
						}
					}
				}
			}
		}
		return paths;
	}
	

	public static ArrayList getA() {
		return a;
	}
	public HashMap<K, V> getVerticesMap() {
		return verticesMap;
	}

	public void setVerticesMap(HashMap<K, V> verticesMap) {
		this.verticesMap = verticesMap;
	}

	@Override
	public void addVertex(V v) {
		// TODO Auto-generated method stub
		verticesMap.put((K) ((Vertex) v).getKey(), v);
		
	}

	@Override
	public void removeVertex(K key) {
		// TODO Auto-generated method stub
		
		if (dirigido) {
			verticesMap.remove(key);
		} else {
			Vertex vAEliminar = (Vertex) verticesMap.get(key);
			ArrayList<Pair> parejasVAEliminar = vAEliminar.getAdy();
			for (int i = 0; i < parejasVAEliminar.size(); i++) {
				Vertex vecino = parejasVAEliminar.get(i).getVertice();
				ArrayList<Pair> parejasVecino = vecino.getAdy();
				for (int j = 0; j < parejasVecino.size(); j++) {
					if (parejasVecino.get(j).getVertice().equals(vAEliminar)) {
						parejasVecino.remove(j);
					}
				}
			}
			verticesMap.remove(key);
		}
	}

	
	/**
	 * Metodo que agrega una arista al grafo.
	 * @param k1 - llave del vertice de origen
	 * @param k2 - llave del vertice de destino
	 * @param w - peso de la arista
	 */
	@Override
	public void addEdge(K k1, K k2, double w) {
		// TODO Auto-generated method stub
		
		if (dirigido) {
			((Vertex) verticesMap.get(k1)).addPareja((Vertex) verticesMap.get(k2), w);
		} else {
			((Vertex) verticesMap.get(k1)).addPareja((Vertex) verticesMap.get(k2), w);
			((Vertex) verticesMap.get(k2)).addPareja((Vertex) verticesMap.get(k1), w);
		}
	}
	
	/**
	 * Metodo que agrega una nueva arista al grafo.
	 * @param k1 - llave del vertice de origen
	 * @param k2 - llave del vertice de destino
	 * @param w - peso de la arista
	 * @param ruta - nombre arista
	 */
	public void addEdge1(K k1, K k2, double w, String ruta) {
		if (dirigido) {
			((Vertex) verticesMap.get(k1)).addPareja1((Vertex) verticesMap.get(k2), w, ruta);
		} else {
			((Vertex) verticesMap.get(k1)).addPareja1((Vertex) verticesMap.get(k2), w, ruta);
			((Vertex) verticesMap.get(k2)).addPareja1((Vertex) verticesMap.get(k1), w, ruta);
		}
	}

	@Override
	public void removeEdge(K k1, K k2) {
		// TODO Auto-generated method stub
		ArrayList<Pair> parejas = ((Vertex) verticesMap.get(k1)).getAdy();
		for (int i = 0; i < parejas.size(); i++) {
			if (parejas.get(i).getVertice().equals(verticesMap.get(k2))) {
				parejas.remove(i);
			}
		}
		if (!dirigido) {
			ArrayList<Pair> parejasW = ((Vertex) verticesMap.get(k2)).getAdy();
			for (int i = 0; i < parejasW.size(); i++) {
				if (parejasW.get(i).getVertice().equals(verticesMap.get(k1))) {
					parejasW.remove(i);
				}
			}
		}
		
	}

}
