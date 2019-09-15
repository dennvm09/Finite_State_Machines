package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import graph.*;


public class Automaton {
	
	/**
	 * Grafo dirigido que modela un automata
	 */
	private AdjacencyListGraph graph;
	/**
	 * Variable que establece si el automata es de Mealy o de Moore
	 */
	private boolean isMealy;
	
	/**
	 * Entradas del automata
	 */
	private String[] inputs;
	
	/**
	 * Tabla de estado del automata
	 */
	private String[][] stateTable;
	
	/**
	 * Estado inicial del automata
	 */
	private String initialState;
	

	/**
	 * 
	 * @param isMealy: Indica si el automata es de Mealy
	 * @param inputs : Entradas del automata
	 * @param stateTable : tabla de estado del automata
	 * @param initialState : Estaado inicial del automata
	 */
	public Automaton(boolean isMealy, String[] inputs, String[][] stateTable, String initialState) {
		super();
		graph = new AdjacencyListGraph<>(true);
		this.isMealy = isMealy;
		this.inputs = inputs;
		this.stateTable = stateTable;
		this.initialState = initialState;
	}

	/**
	 * Metodo para renombrar estados y generar tabla de estado final de un automata de Moore
	 * @param partitioning Arreglo que contiene el particionamiento 
	 * @return Tabla de estado del automata conexo minimo equivalente.
	 */
	private String[][] combineStatesMoore(ArrayList<ArrayList<String>> partitioning) {
		
		String[][] statesTable = new String[partitioning.size()+1][inputs.length+2];
		String basedName = "|Q";
		
		for(int i = 1; i < statesTable[0].length-1; i++ ) {
			statesTable[0][i] = inputs[i-1];
		}
		
		for(int i = 1; i < statesTable.length; i++ ) {
			statesTable[i][0] = basedName + i + "|";
		}
		
		int k = 2;
		for(ArrayList partition : partitioning) {
			
			if(partition.contains(initialState)) {			
				statesTable[1][statesTable[0].length-1] = (String) graph.getVertice(initialState).getValor();			
				partition.add("|Q1|");
			}
			else {
				String rename ="|Q"+ k + "|"; 
				partition.add(rename);
				k++;		
			}		
		}
		
		int l = 1;
		int r = 1;
		for(ArrayList<String> partition : partitioning) {
			
			statesTable[r][0] = partition.get(partition.size()-1);
			r++;
			ArrayList<Pair> adjacents =  graph.getVertice(partition.get(0)).getAdy();
			for(int i = 0; i < adjacents.size(); i++) {
				for(int j = 1; j< statesTable[0].length-1; j++)
				 if(statesTable[0][j].equals(adjacents.get(i).getID())){
					 
					 boolean ended = false;
					 for(int p = 0; p<partitioning.size() && !ended; p++) {
						 if(partitioning.get(p).contains(adjacents.get(i).getVertice().getKey())) {
							 ended = true;
							 statesTable[l][j] = (String) partitioning.get(p).get(partitioning.get(p).size()-1);
							 statesTable[l][statesTable[0].length-1] = (String) graph.getVertice(partition.get(0)).getValor();
						 }
					 }					 
				 }
			}
			l++;
		}
		
		return statesTable;
	}
	
	/**
	 * Metodo para renombrar estados y generar tabla de estado final de un automata de Mealy
	 * @param partitioning Arreglo que contiene el particionamiento 
	 * @return Tabla de estado del automata conexo minimo equivalente.
	 */
	private String[][] combineStatesMealy(ArrayList<ArrayList<String>> partitioning) {
		String[][] statesTable = new String[partitioning.size() + 1 ][inputs.length+1];
		
		String basedName = "|Q";
		
		for(int i = 1; i < statesTable[0].length; i++ ) {
			statesTable[0][i] = inputs[i-1];
		}
		
		for(int i = 1; i < statesTable.length; i++ ) {
			statesTable[i][0] = basedName + i + "|";
		}
		
		int k = 2;
		for(ArrayList partition : partitioning) {
			
			if(partition.contains(initialState)) {						
				partition.add("|Q1|");
			}
			else {
				String rename ="|Q"+ k + "|"; 
				partition.add(rename);
				k++;		
			}	
			
		}
			
			for(int i = 0; i< partitioning.size(); i++) {
				ArrayList<String> partition = partitioning.get(i);
				
				//La i indica la particion y la i+1 la fila en la nueva tabla de estados
				statesTable[i+1][0] = partition.get(partition.size()-1);
				
				String statePartition = partition.get(0);
				
				// S indica la posicion del estado en la tabla de estado original
				int s = 0;
				for(int j = 1; j< stateTable.length; j++) {
					if(stateTable[j][0].equals(statePartition)) {
						s = j;
					}
				}
				
				for(int j = 1; j < stateTable[0].length; j++ ) {
					
					String adjacentState = stateTable[s][j].split(",")[0];
					String response = stateTable[s][j].split(",")[1];
					
					for(ArrayList<String> partition2 : partitioning) {
						if(partition2.contains(adjacentState)) {
							statesTable[i+1][j] = partition2.get(partition2.size()-1) + " , " + response;
						}
					}
				}
			}
		
		
		
		
		
		return statesTable;
	}

	/**
	 * Da el definitivo automata equivalente
	 * @return Tabla de estado del automata conexo minimo equivalente
	 */
	public String[][] getMinimumConnectedAutomaton(){
		
		String[][] response = null;
		
		fillGraph(stateTable, isMealy);	
		try {
		connectedStateMachine();
		}catch(Exception e) {
			
		}
		
		ArrayList<ArrayList<String>> partitioning = partition();

		if(isMealy) {
			response = combineStatesMealy(partitioning);
			}else {
			response = combineStatesMoore(partitioning);
		}
		return response;
		
	}
	
	
	/**
	 * Modifica el automata asegurando que este se a conexo
	 */
	public void connectedStateMachine() {
		
		ArrayList<String> connectedVertices =  (ArrayList<String>) graph.BFS2(initialState);

		HashMap vertices = graph.getVerticesMap();
		Set keys1 = vertices.keySet();
		ArrayList keys = new ArrayList<>();
		keys.addAll(keys1);
					
		for(int i = 0; i < vertices.size(); i++) {		
			
			Vertex v = graph.getVertice(keys.get(i));
			if(!connectedVertices.contains(v.getKey())) {
				
				graph.removeVertex(v.getKey());
			}
		}
		
	}
	

	public void fillGraph(String[][] matrixInf, boolean isMealy) {
		if (isMealy) {
			fillMealy(matrixInf);
		}else {
			fillMoore(matrixInf);
		}
	}


	public void fillMealy(String[][] matrixInf) {

		ArrayList transitions = new ArrayList<>();
		ArrayList state = new ArrayList<>();
		for (int i = 0; i < matrixInf[0].length; i++) {

			transitions.add(matrixInf[0][i]);
		}

		for (int i = 0; i < matrixInf.length; i++) {

			state.add(matrixInf[i][0]);
		}
		ArrayList<String> answers = new ArrayList<String>();

		for (int i = 1; i < matrixInf.length; i++) {
			for (int j = 1; j < matrixInf[0].length; j++) {
				String aswer = matrixInf[i][j].split(",")[1];
				if (!answers.contains(aswer)) {
					answers.add(aswer);
				}

			}
		}

		boolean flat = true;
		for (int i = 1; i < matrixInf.length; i++) {
			for (int j = 0; j < answers.size(); j++) {

				String nameState = (String) state.get(i) + "," + answers.get(j);
				Vertex<String, String> vtoAdd = new Vertex<String, String>(nameState);
				vtoAdd.setValor(answers.get(j) + "");
				if (flat) {
					vtoAdd.setInitial(true);
					flat = false;
				}
				graph.addVertex(vtoAdd);

			}
		}

		for (int i = 1; i < matrixInf.length; i++) {
			for (int j = 0; j < answers.size(); j++) {
				String nameState = (String) state.get(i) + "," + answers.get(j);
				for (int k = 1; k < matrixInf[0].length; k++) {
					graph.addEdge1(nameState, matrixInf[i][k], 0, matrixInf[0][k]);
				}

			}
		}
	}

	public void fillMoore(String[][] matrixInf) {
		ArrayList transitions = new ArrayList<>();
		ArrayList state = new ArrayList<>();
		ArrayList answers = new ArrayList<>();

		for (int i = 0; i < matrixInf[0].length - 1; i++) {

			transitions.add(matrixInf[0][i]);
		}

		for (int i = 0; i < matrixInf.length; i++) {

			state.add(matrixInf[i][0]);
			answers.add(matrixInf[i][matrixInf[0].length - 1]);
		}

		boolean flat = true;
		for (int i = 1; i < matrixInf.length; i++) {

			String nameState = (String) state.get(i);
			Vertex<String, String> vtoAdd = new Vertex<String, String>(nameState);
			vtoAdd.setValor(answers.get(i) + "");
			if (flat) {
				vtoAdd.setInitial(true);
				flat = false;
			}
			graph.addVertex(vtoAdd);

		}

		for (int i = 1; i < matrixInf.length; i++) {
			for (int j = 1; j < matrixInf[0].length - 1; j++) {
				String nameState = (String) state.get(i);

				graph.addEdge1(nameState, matrixInf[i][j], 0, matrixInf[0][j]);
			}

		}
	}

	public ArrayList partition() {
		// Sera el arreglo general que contiene los arreglos tipo particion.
		ArrayList<ArrayList> generalArray = new ArrayList<ArrayList>();

		// Obtengo la lista de llaves de los vertices en el grafo es decir
		// obtengo los estados
		HashMap vertices = graph.getVerticesMap();
		Set keys1 = vertices.keySet();
		ArrayList keys = new ArrayList<>();
		keys.addAll(keys1);
		int numberTransition = (int) graph.getVertice(keys.get(0)).getAdy().size();
		ArrayList<String> transitions = new ArrayList<String>();
		for (int i = 0; i < numberTransition; i++) {
			transitions.add((String) ((Pair) graph.getVertice(keys.get(0)).getAdy().get(i)).getID());
		}
		// Lista de respuestas de los estados
		ArrayList<String> listAswers = new ArrayList<String>();
		// guarda la cantidad de respuestas de los estados
		for (int i = 0; i < keys.size(); i++) {
			// Respuesta actual
			String aswer = (String) graph.getVertice(keys.get(i)).getValor();

			if (!listAswers.contains(aswer)) {
				listAswers.add(aswer);
			}

		}
		listAswers.sort((e1, e2) -> e1.compareTo(e2));
		// Particion
		boolean partEqual = true;

		ArrayList<ArrayList> partition = new ArrayList<ArrayList>();
		// Define la primera particion
		for (int i = 0; i < listAswers.size(); i++) {
			ArrayList<String> listEquivalent = new ArrayList<String>();
			// Designa los bloques segun su respuesta
			for (int j = 0; j < keys.size(); j++) {

				String aswer = (String) graph.getVertice(keys.get(j)).getValor();
				if (aswer.equals(listAswers.get(i))) {
					listEquivalent.add((String) graph.getVertice(keys.get(j)).getKey());
				}
			}
			partition.add(listEquivalent);

		}
		generalArray.add(partition);
		// Particiona para p2 en adelante
		while (partEqual) {
			// Nueva particion
			ArrayList<ArrayList> partition1 = new ArrayList<ArrayList>();
			// particion anterior
			ArrayList<ArrayList> beforePart = generalArray.get(generalArray.size() - 1);
			generalArray.add(partition1);
			// nivel bloques de una particion
			for (int i = 0; i < beforePart.size(); i++) {
				// Sirve para guardar temporalmente los valores que se van a
				// retirar de este bloque
				ArrayList<String> aux = new ArrayList<String>();
				ArrayList listEquivalent = beforePart.get(i);
				if (listEquivalent.size() > 1) {
					// Elementos de un bloque
					for (int j = 1; j < listEquivalent.size(); j++) {
						// La idea es compartar el primer elemento con lo demas
						// para definir quien permananece y quien se va
						Vertex first = graph.getVertice(listEquivalent.get(0));
						Vertex anyOther = graph.getVertice(listEquivalent.get(j));
						// Se compararan segun sus transiciones
						// nivel adyacentes a los vertices
						for (int k = 0; k < transitions.size(); k++) {
							String keyPair = (String) ((Pair) first.getAdy().get(k)).getVertice().getKey();
							String keyPair1 = (String) ((Pair) anyOther.getAdy().get(k)).getVertice().getKey();
							// Necesito verificar si estas dos pertenecen al
							// mismo bloque si es asi no pasa nada, de lo
							// contrario lo debo cambiar de bloque
							int numReject = 0;
							// Para decidir si sale o no
							for (int u = 0; u < beforePart.size(); u++) {
								ArrayList listStates = beforePart.get(u);

								if (!(listStates.contains(keyPair) && listStates.contains(keyPair1))) {
									numReject++;
								}

							}
							if (numReject == beforePart.size() && !(aux.contains((String) anyOther.getKey())))
								aux.add((String) anyOther.getKey());
						}

					}
					ArrayList notExit = new ArrayList<>();
					for (int j1 = 0; j1 < listEquivalent.size(); j1++) {

						if (!(aux.contains(listEquivalent.get(j1)))) {
							notExit.add(listEquivalent.get(j1));
						}

					}
					partition1.add(notExit);
					if (aux.size() > 0)
						partition1.add(aux);
				} else {
					partition1.add(listEquivalent);
				}
			}
			// Realizar comparacion entre Pn y Pn-1
			if (!(beforePart.size() != partition1.size()) && beforePart.size() > 0 && partition1.size() > 0) {
				for (int k = 0; k < beforePart.size(); k++) {
					beforePart.get(k).sort((e1, e2) -> ((String) e1).compareTo((String) e2));
				}
				for (int k = 0; k < partition1.size(); k++) {
					partition1.get(k).sort((e1, e2) -> ((String) e1).compareTo((String) e2));
				}
				partEqual = false;
				for (int k = 0; k < partition1.size() && !partEqual; k++) {
					for (int k2 = 0; k2 < partition1.size(); k2++) {
						if (partition1.get(k).get(0).equals(beforePart.get(k2).get(0))) {
							for (int l = 0; l < partition1.get(k).size(); l++) {

								if ((partition1.get(k).get(l).equals(beforePart.get(k).get(l)))) {
									partEqual = false;
								} else {
									partEqual = true;

								}
							}
						}
					}
				}
			}
		}
		if (isMealy) {
			ArrayList<ArrayList> mealy = generalArray.get(generalArray.size() - 1);
			ArrayList<ArrayList> pn = new ArrayList<ArrayList>();
			for (int i = 0; i < mealy.size() / 2; i++) {
				mealy.get(i);
				ArrayList<String> block = new ArrayList<String>();
				for (int j = 0; j < mealy.get(i).size(); j++) {
					String state = ((String) mealy.get(i).get(j)).split(",")[0];
					block.add(state);
				}
				pn.add(block);
			}
			return pn;
		} else {
			return generalArray.get(generalArray.size() - 1);
		}

	}

	/**
	 * Metodos Set y Get de los atributos
	 */	
	
	public boolean isMealy() {
		return isMealy;
	}

	public void setMealy(boolean isMealy) {
		this.isMealy = isMealy;
	}

	public String[] getInputs() {
		return inputs;
	}

	public void setInputs(String[] inputs) {
		this.inputs = inputs;
	}

	public String[][] getStateTable() {
		return stateTable;
	}

	public void setStateTable(String[][] stateTable) {
		this.stateTable = stateTable;
	}

	public String getInitialState() {
		return initialState;
	}

	public void setInitialState(String initialState) {
		this.initialState = initialState;
	}

	public void setGraph(AdjacencyListGraph graph) {
		this.graph = graph;
	}
	
	public AdjacencyListGraph<String,String> getGraph() {
		return graph;
	}

}
