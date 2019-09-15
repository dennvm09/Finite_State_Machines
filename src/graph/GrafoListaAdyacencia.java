package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import javax.naming.ldap.HasControls;

public class GrafoListaAdyacencia<K, V> implements InterfazGrafo<K, V> {
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

	public GrafoListaAdyacencia(boolean dirigido) {
		verticesMap = new HashMap<K, V>();
		this.dirigido = dirigido;
		tiempo = 0;
	}

	/**
	 * Metodo que retorna un vertice del grafo.
	 * @param k - llave que identifica el vertice.
	 * @return Vertice
	 */
	public Vertice getVertice(K k) {
		return (Vertice) verticesMap.get(k);
	}

	/**
	 * Metodo que realiza el recorrido por amplitud.
	 * @param k -llave que identifica el vertice de inicio.
	 */
	public void BFS(K k) {
		@SuppressWarnings("unchecked")
		K[] listaClaves = (K[]) verticesMap.keySet().toArray();
		for (int i = 0; i < listaClaves.length; i++) {
			((Vertice) verticesMap.get(listaClaves[i])).setColor(Vertice.WHITE);
			((Vertice) verticesMap.get(listaClaves[i])).setDistancia(Vertice.INFINITO);
			((Vertice) verticesMap.get(listaClaves[i])).setPadre(null);
		}
		Vertice vertInicial = (Vertice) verticesMap.get(k);
		vertInicial.setColor(Vertice.GRAY);
		vertInicial.setDistancia(0);

		Queue<Vertice> cola = new LinkedList<>();
		cola.offer(vertInicial);

		while (!cola.isEmpty()) {
			Vertice verActual = cola.poll();
			ArrayList<Pareja> listaAdya = verActual.getAdy();
			for (int i = 0; i < listaAdya.size(); i++) {
				Vertice verticeAdy = listaAdya.get(i).getVertice();
				if (verticeAdy.getColor() == Vertice.WHITE) {
					verticeAdy.setColor(Vertice.GRAY);
					verticeAdy.setDistancia(verActual.getDistancia() + 1);
					verticeAdy.setPadre(verActual);
					cola.offer(verticeAdy);
				}
			}
			verActual.setColor(Vertice.BLACK);
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
			((Vertice) verticesMap.get(listaClaves[i])).setColor(Vertice.WHITE);
			((Vertice) verticesMap.get(listaClaves[i])).setDistancia(Vertice.INFINITO);
			((Vertice) verticesMap.get(listaClaves[i])).setPadre(null);
		}
		Vertice vertInicial = (Vertice) verticesMap.get(k);
		vertInicial.setColor(Vertice.GRAY);
		vertInicial.setDistancia(0);

		Queue<Vertice> cola = new LinkedList<>();
		cola.offer(vertInicial);

		ArrayList response = new ArrayList();
		response.add(vertInicial.getKey());
		
		while (!cola.isEmpty()) {
			Vertice verActual = cola.poll();
			ArrayList<Pareja> listaAdya = verActual.getAdy();
			for (int i = 0; i < listaAdya.size(); i++) {
				Vertice verticeAdy = listaAdya.get(i).getVertice();
				if (verticeAdy.getColor() == Vertice.WHITE) {
					verticeAdy.setColor(Vertice.GRAY);
					response.add(verticeAdy.getKey());
					verticeAdy.setDistancia(verActual.getDistancia() + 1);
					verticeAdy.setPadre(verActual);
					cola.offer(verticeAdy);
				}
			}
			verActual.setColor(Vertice.BLACK);
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
			
		} else if (((Vertice) verticesMap.get(k2)).getPadre() == null) {

		} else
			shortestPath(k1, (K) ((Vertice) verticesMap.get(k2)).getPadre().getKey());
		a.add(k2);
		
	}
	
	/**
	 * Metodo que realiza el recorrido por profundidad
	 */
	public void DFS() {
		K[] keyArray = (K[]) verticesMap.keySet().toArray();
		for (int i = 0; i < keyArray.length; i++) {
			((Vertice) verticesMap.get(keyArray[i])).setColor(Vertice.WHITE);
			((Vertice) verticesMap.get(keyArray[i])).setPadre(null);
		}
		tiempo = 0;
		for (int i = 0; i < keyArray.length; i++) {
			Vertice vertInicial = ((Vertice) verticesMap.get(keyArray[i]));
			if (vertInicial.getColor() == Vertice.WHITE) {
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
		((Vertice) v).setDistancia(tiempo);
		((Vertice) v).setColor(Vertice.GRAY);

		ArrayList<Pareja> listaAdya = ((Vertice) v).getAdy();
		for (int i = 0; i < listaAdya.size(); i++) {
			Vertice verticeAdy = listaAdya.get(i).getVertice();
			if (verticeAdy.getColor() == Vertice.WHITE) {
				verticeAdy.setPadre((Vertice) v);
				DFSVisit((V) verticeAdy);
			}
		}
		((Vertice) v).setColor(Vertice.BLACK);
		tiempo = tiempo + 1;
		((Vertice) v).setDistanciaFinal(tiempo);
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
				// S� no estaba en el conjunto S entonces lo comparo para saber si es menor
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
					Vertice verticeU = (Vertice) verticesMap.get(u);
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
						vecino = (K) ((Pareja) adyacentes.get(j)).getVertice().getKey();
						
						for (int j2 = 0; j2 < numeroVecinos ; j2++) {
							K actual = (K) ((Pareja) adyacentes.get(j2)).getVertice().getKey();
							if (actual.equals(vecino)) {
								
								int pesoActual = (int) ((Pareja) adyacentes.get(j2)).getPeso();
								if (pesoActual < menorPesoAV) {
									menorPesoAV = pesoActual;
									ruta = (String) ((Pareja) adyacentes.get(j2)).getID();
									
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
				// S� no estaba en el conjunto S entonces lo comparo para saber si es menor
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
					ArrayList adyacentes = ((Vertice) verticesMap.get(u)).getAdy();
					int numeroVecinos = 0;
					if (adyacentes != null)
						numeroVecinos = adyacentes.size();

					for (int j = 0; j < numeroVecinos; j++) {
						int menorCaminoAV = Integer.MAX_VALUE;
						K vecino = null;
						vecino = (K) ((Pareja) adyacentes.get(j)).getVertice().getKey();

						for (int j2 = 0; j2 < numeroVecinos; j2++) {
							K actual = (K) ((Pareja) adyacentes.get(j2)).getVertice().getKey();
							if (actual.equals(vecino)) {
								int pesoActual = (int) ((Pareja) adyacentes.get(j2)).getPeso();
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
			Vertice vertice = (Vertice) verticesMap.get(keyArray[i]);
			ArrayList<Pareja> ady = vertice.getAdy();
			for (int j = 0; j < keyArray.length; j++) {
				weights[i][j] = INFINITO;
				if (i == j)
					weights[i][j] = 0;
				for (int j2 = 0; j2 < ady.size(); j2++) {
					K verticeAComp = (K) ((Vertice) verticesMap.get(keyArray[j])).getKey();
					K verticeAdy = (K) ((Pareja) ady.get(j2)).getVertice().getKey();
					if (verticeAdy.equals(verticeAComp)) {
						weights[i][j] = ((Pareja) ady.get(j2)).getPeso();
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
	public ArrayList<Vertice<K, V>>[][] floydWarshall() {
		K[] keyArray = (K[]) verticesMap.keySet().toArray();
		double[][] matriz = weightsMatrix().clone();

		ArrayList<Vertice<K, V>>[][] paths = new ArrayList[verticesMap.size()][verticesMap.size()];

		for (int i = 0; i < paths.length; i++) {
			for (int j = 0; j < paths[0].length; j++) {
				paths[i][j] = new ArrayList();
				if (matriz[i][j] != INFINITO) {
					paths[i][j].add((Vertice) verticesMap.get(keyArray[i]));
					if (i != j && !dirigido) {
						paths[i][j].add((Vertice) verticesMap.get(keyArray[j]));
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

							paths[j][k] = new ArrayList<Vertice<K, V>>();
							paths[j][k].addAll(paths[j][i]);
							ArrayList<Vertice<K, V>> aux = (ArrayList<Vertice<K, V>>) paths[i][k].clone();
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
		verticesMap.put((K) ((Vertice) v).getKey(), v);
		
	}

	@Override
	public void removeVertex(K key) {
		// TODO Auto-generated method stub
		
		if (dirigido) {
			verticesMap.remove(key);
		} else {
			Vertice vAEliminar = (Vertice) verticesMap.get(key);
			ArrayList<Pareja> parejasVAEliminar = vAEliminar.getAdy();
			for (int i = 0; i < parejasVAEliminar.size(); i++) {
				Vertice vecino = parejasVAEliminar.get(i).getVertice();
				ArrayList<Pareja> parejasVecino = vecino.getAdy();
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
			((Vertice) verticesMap.get(k1)).addPareja((Vertice) verticesMap.get(k2), w);
		} else {
			((Vertice) verticesMap.get(k1)).addPareja((Vertice) verticesMap.get(k2), w);
			((Vertice) verticesMap.get(k2)).addPareja((Vertice) verticesMap.get(k1), w);
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
			((Vertice) verticesMap.get(k1)).addPareja1((Vertice) verticesMap.get(k2), w, ruta);
		} else {
			((Vertice) verticesMap.get(k1)).addPareja1((Vertice) verticesMap.get(k2), w, ruta);
			((Vertice) verticesMap.get(k2)).addPareja1((Vertice) verticesMap.get(k1), w, ruta);
		}
	}

	@Override
	public void removeEdge(K k1, K k2) {
		// TODO Auto-generated method stub
		ArrayList<Pareja> parejas = ((Vertice) verticesMap.get(k1)).getAdy();
		for (int i = 0; i < parejas.size(); i++) {
			if (parejas.get(i).getVertice().equals(verticesMap.get(k2))) {
				parejas.remove(i);
			}
		}
		if (!dirigido) {
			ArrayList<Pareja> parejasW = ((Vertice) verticesMap.get(k2)).getAdy();
			for (int i = 0; i < parejasW.size(); i++) {
				if (parejasW.get(i).getVertice().equals(verticesMap.get(k1))) {
					parejasW.remove(i);
				}
			}
		}
		
	}

}
