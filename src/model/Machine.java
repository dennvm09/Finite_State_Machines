package model;

import java.util.ArrayList;

public class Machine {

	public final static int MEALY = 1;
	public final static int MOORE = 2;
	
	private int typeMachine;
	private String[] inputsSets;
	private String[][] stateTable;
	private String initial;
	
	public Machine(int typeMachine, String[] inputsSets, String[][] stateTable, String initial) {
		
		this.typeMachine = typeMachine;
		this.inputsSets = inputsSets;
		this.stateTable = stateTable;
		this.initial = initial;	
	}

	
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
				String newName = "|q" + c + "|";
				part.add(newName);
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
	
	
	
	
	
	
	public int getTypeMachine() {
		return typeMachine;
	}

	public void setTypeMachine(int typeMachine) {
		this.typeMachine = typeMachine;
	}

	public String[] getInputsSets() {
		return inputsSets;
	}

	public void setInputsSets(String[] inputsSets) {
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

	public void setInitial(String initial) {
		this.initial = initial;
	}
	
	
	
	
	
}
