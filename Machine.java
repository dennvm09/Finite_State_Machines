package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import graph.*;


public class Machine {

	
	private AdjacencyListGraph graph;

	//true: mealy false: moore
	private boolean typeMachine;
	private String[] inputsSets;
	private String[][] stateTable;
	private String initial;
	

	/**
	 * Constructor
	 * @param isMealy: Indica si el automata es de Mealy
	 * @param inputs : Entradas del automata
	 * @param stateTable : tabla de estado del automata
	 * @param initialState : Estaado inicial del automata
	 */
	public Machine(boolean typeMachine, String[] inputsSets, String[][] stateTable, String initial) {
		super();
		graph = new AdjacencyListGraph<>(true);
		this.typeMachine = typeMachine;
		this.inputsSets = inputsSets;
		this.stateTable = stateTable;
		this.initial = initial;	
	}

	/**
	 * Metodo para renombrar estados y generar tabla de estado final de un automata de Moore
	 * @param partitioning Arreglo que contiene el particionamiento 
	 * @return Tabla de estado del automata conexo minimo equivalente.
	 */
	private String[][] finalStateTableMoore(ArrayList<ArrayList<String>> partitioning) {
		
		String[][] sTable = new String[partitioning.size()+1][inputsSets.length+2];
		String baseName = "|q";
		
		for(int i = 1; i < sTable[0].length-1; i++ ) {
			sTable[0][i] = inputsSets[i-1];
		}
		
		for(int i = 1; i < sTable.length; i++ ) {
			sTable[i][0] = baseName + i + "|";
		}
		
		int k = 2;
		for(ArrayList partition : partitioning) {
			
			if(partition.contains(initial)) {			
				sTable[1][sTable[0].length-1] = (String) graph.getVertice(initial).getValor();			
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
			
			sTable[r][0] = partition.get(partition.size()-1);
			r++;
			ArrayList<Pair> adjacents =  graph.getVertice(partition.get(0)).getAdy();
			for(int i = 0; i < adjacents.size(); i++) {
				for(int j = 1; j< sTable[0].length-1; j++)
				 if(sTable[0][j].equals(adjacents.get(i).getID())){
					 
					 boolean ended = false;
					 for(int p = 0; p<partitioning.size() && !ended; p++) {
						 if(partitioning.get(p).contains(adjacents.get(i).getVertice().getKey())) {
							 ended = true;
							 sTable[l][j] = (String) partitioning.get(p).get(partitioning.get(p).size()-1);
							 sTable[l][sTable[0].length-1] = (String) graph.getVertice(partition.get(0)).getValor();
						 }
					 }					 
				 }
			}
			l++;
		}
		
		return sTable;
	}
	
	/**
	 * Metodo para renombrar estados y generar tabla de estado final de un automata de Mealy
	 * @param partitioning Arreglo que contiene el particionamiento 
	 * @return Tabla de estado del automata conexo minimo equivalente.
	 */
	public String[][] finalStateTableMealy(ArrayList<ArrayList<String>> partition){
		
		String[][] stable = new String[partition.size()+1][inputsSets.length+1];
		
		String baseName = "|q";
		
		//fila
		for (int i = 0; i < stable[0].length; i++) {
			stable[0][i] = inputsSets[i-1];
		}
		//columna
		for (int i = 0; i < stable.length; i++) {
			stable[i][0] = baseName + i + "|";
		}
		
		int c = 2;
		for(ArrayList<String> part : partition) {
			
			if(part.contains(initial)) {
				part.add("|q1|");
			}else {
				String rename = "|q" + c + "|";
				part.add(rename);
				c++;
			}
		}
		
		for (int i = 0; i < partition.size(); i++) {
			
			ArrayList<String> part = partition.get(i);
			stable[i+1][0] = part.get(part.size()-1);
			
			String sPartition = part.get(0);
			
			int pos = 0;
			for (int j = 1; j < stable.length; j++) {
				if(stable[j][0].equals(sPartition)) {
					pos = j;
				}
			}
			
			for (int j = 0; j < stable[0].length; j++) {
				String adjState = stable[pos][j].split(",")[0];
				String reaction = stable[pos][j].split(",")[1];
				
				for(ArrayList<String> parti : partition) {
					if(parti.contains(adjState)) {
						stable[i+1][j] = parti.get(parti.size()-1) + "," + reaction;
					}
				}
			}
		}
		
		return stable;	
	}
	
	
	/**
	 * Da el definitivo automata equivalente
	 * @return Tabla de estado del automata conexo minimo equivalente
	 */
	public String[][] minimalRelatedMachine(){
		
		String[][] reaction = null;
		
		fillGraph(stateTable, typeMachine);	
		try {
		relatedStateMachine();
		}catch(Exception e) {
			
		}
		
		ArrayList<ArrayList<String>> partition = partition();

		if(typeMachine) {
			reaction = finalStateTableMealy(partition);
			}else {
			reaction = finalStateTableMoore(partition);
		}
		return reaction;
		
	}
	
	
	/**
	 * Metodo que se encarga de asegurar que el automata sea conexo
	 */
	public void relatedStateMachine() {
		
		ArrayList<String> connectedVertices =  (ArrayList<String>) graph.BFS2(initial);

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

	/**
	 * 
	 * @return generalArray - arreglo contiene los arreglos tipo particion
	 */
	public ArrayList partition() {
		ArrayList<ArrayList> generalArray = new ArrayList<ArrayList>();

		
		HashMap vertex = graph.getVerticesMap();
		Set keys1 = vertex.keySet();
		ArrayList keys = new ArrayList<>();
		keys.addAll(keys1);
		int numberTransition = (int) graph.getVertice(keys.get(0)).getAdy().size();
		ArrayList<String> transitions = new ArrayList<String>();
		for (int i = 0; i < numberTransition; i++) {
			transitions.add((String) ((Pair) graph.getVertice(keys.get(0)).getAdy().get(i)).getID());
		}
		ArrayList<String> listStatesReactions = new ArrayList<String>();
		for (int i = 0; i < keys.size(); i++) {
			String reaction = (String) graph.getVertice(keys.get(i)).getValor();

			if (!listStatesReactions.contains(reaction)) {
				listStatesReactions.add(reaction);
			}

		}
		listStatesReactions.sort((e1, e2) -> e1.compareTo(e2));
		boolean partEqual = true;

		ArrayList<ArrayList> partition = new ArrayList<ArrayList>();
		for (int i = 0; i < listStatesReactions.size(); i++) {
			ArrayList<String> listEquivalent = new ArrayList<String>();
			for (int j = 0; j < keys.size(); j++) {

				String reaction = (String) graph.getVertice(keys.get(j)).getValor();
				if (reaction.equals(listStatesReactions.get(i))) {
					listEquivalent.add((String) graph.getVertice(keys.get(j)).getKey());
				}
			}
			partition.add(listEquivalent);

		}
		generalArray.add(partition);
		while (partEqual) {
			ArrayList<ArrayList> partition1 = new ArrayList<ArrayList>();
			ArrayList<ArrayList> beforePart = generalArray.get(generalArray.size() - 1);
			generalArray.add(partition1);
			for (int i = 0; i < beforePart.size(); i++) {
				
				ArrayList<String> aux = new ArrayList<String>();
				ArrayList listEquivalent = beforePart.get(i);
				if (listEquivalent.size() > 1) {
					for (int j = 1; j < listEquivalent.size(); j++) {
						
						Vertex first = graph.getVertice(listEquivalent.get(0));
						Vertex anyOther = graph.getVertice(listEquivalent.get(j));
						
						for (int k = 0; k < transitions.size(); k++) {
							String keyPair = (String) ((Pair) first.getAdy().get(k)).getVertice().getKey();
							String keyPair1 = (String) ((Pair) anyOther.getAdy().get(k)).getVertice().getKey();
							
							int numReject = 0;
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
		if (typeMachine) {
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


	public boolean getTypeMachine() {
		return typeMachine;
	}

	public void setTypeMachine(boolean typeMachine) {
		this.typeMachine = typeMachine;
	}

	public String[] getinputsSets() {
		return inputsSets;
	}

	public void setinputsSets(String[] inputsSets) {
		this.inputsSets = inputsSets;
	}

	public String[][] getStateTable() {
		return stateTable;
	}

	public void setStateTable(String[][] stateTable) {
		this.stateTable = stateTable;
	}

	public String getInitial() {
		return initial;
	}

	public void setinitial(String initial) {
		this.initial = initial;
	}

	public void setGraph(AdjacencyListGraph graph) {
		this.graph = graph;
	}
	
	public AdjacencyListGraph<String,String> getGraph() {
		return graph;
	}

}
