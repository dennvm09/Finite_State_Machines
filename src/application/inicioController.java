package application;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class inicioController {
	
	private int option;
	private int amountSets;
	private String inputsSets;
	private String inputsSets1;
	
	@FXML
	private Pane pane;
	@FXML
	private RadioButton rbtMealy;
	@FXML
	private RadioButton rbtMoore;
	@FXML
	private Pane paneMealy;
	@FXML
	private Pane paneMoore;
	@FXML
	private TextField txtAmountSetsQ;
	@FXML
	private TextField txtInputsSetsS;
	@FXML
	private Button btStart;
	@FXML
	private TextField txtBegin;
	@FXML
	private TextField txtEnd;
	@FXML
	private ComboBox<String> cbxInput;
	@FXML
	private TextField txtOutput;
	@FXML
	private Button btCreateRelation;
	@FXML
	private Button btDeleteColumns;
	@FXML
	private TextArea tableMealy;
	@FXML
	private TextField txtInputsSetsS1;
	@FXML
	private TextField txtOutputs1;
	@FXML
	private TextField txtBegin1;
	@FXML
	private TextField txtEnd1;
	@FXML
	private TextArea tableMoore;
	@FXML
	private ComboBox<String> cbxInput1;
	@FXML
	private Button btAddInputs;
	@FXML
	private Button btAddOutputs;
	@FXML
	private Button btCreateRelation1;
	@FXML
	private TextArea txtReaction;
	@FXML
	private TextArea txtPartitions;
	@FXML
	private Button btReady;
	@FXML
	private Button btReady1;
	@FXML
	private Pane paneReaction;
	
	
	public void initialize() {
		paneMealy.setVisible(false);
		paneMoore.setVisible(false);
		rbtMealy.setOnAction(e-> checkers(1));
		rbtMoore.setOnAction(e-> checkers(-1));
		paneReaction.setVisible(false);
	}
	
	/**
	 * Metodo encargado de devolver un arreglo con las entradas
	 * @param inputs - cadena de texto la cual contiene las entradas del automata separadas por coma
	 * @return fInputs - arreglo que contiene las entradas que puede recibir el automata
	 */
	public String[] inputSetsM(String inputs) {	
		//la entrada me la manda el boton y son los inputs separados por comas
		String[] fInputs = inputs.split(",");
		return fInputs;	
	}
	

	/**
	 * Metodo utilizado para inicializar la tabla de estados en un automata de mealy
	 * @param e - evento generado luego de presionar el boton "start"
	 */
	public void startP(ActionEvent e) {
		//metodo para iniciar el programa, eenvia valor a las variables globales
		amountSets = Integer.parseInt(txtAmountSetsQ.getText());
		inputsSets = txtInputsSetsS.getText();
		updateTable();
		fillInputs();

	}
	

	/**
	 * Metodo utilizado para inicializar la tabla de estados en un automata de moore
	 * @param e - evento generado luego de presionar el boton "add inputs"
	 */
	public void addInputs(ActionEvent e) {	
		inputsSets1 = txtInputsSetsS1.getText();
		updateTable1();
		fillInputs1();
	}
	
	
	
	//metodo que actualiza la tabla, utiliza el metodo que separa las entradas mealy
	/**
	 * Metodo que actualiza la tabla de estados del automata de mealy
	 * utiliza el metodo que devuelve un arreglo con las entradas del automata
	 */
	public void updateTable() {
		String[] inputs = inputSetsM(inputsSets);
		
		String title = "";
		
		for(int i = 0; i < inputs.length+1; i++) {
			
			if(title.isEmpty()) {
				title += "---";
				
			}else {
				title += "               "+inputs[i-1];
			}
		}
		
		tableMealy.setText(title);	
	}
	
	/**
	 * Metodo que actualiza la tabla de estados del automata de moore
	 * utiliza el metodo que devuelve un arreglo con las entradas del automata
	 */
	public void updateTable1() {
		String[] inputs = inputSetsM(inputsSets1);
		
		String title = "";
		
		for(int i = 0; i < inputs.length+1; i++) {
			
			if(title.isEmpty()) {
				title += "---";
				
			}else {
				title += "               "+inputs[i-1];
			}
		}
		tableMoore.setText(title);
	}
	
	
	//rellenar el cbx
	/**
	 * Metodo utilizado para rellenar el combobox de entradas
	 * del automata de mealy
	 */
	public void fillInputs() {
		String[] inputs = inputSetsM(inputsSets);
		ObservableList<String> items = FXCollections.observableArrayList();
		for(int i = 0; i < inputs.length; i++) {
			items.addAll(inputs[i]);
		}
		cbxInput.setItems(items);
	}
	
	/**
	 * Metodo utilizado para rellenar el combobox de entradas
	 * del automata de moore
	 */
	public void fillInputs1() {
		String[] inputs = inputSetsM(inputsSets1);
		ObservableList<String> items = FXCollections.observableArrayList();
		for(int i = 0; i < inputs.length; i++) {
			items.addAll(inputs[i]);
		}
		cbxInput1.setItems(items);
	}
	
	/**
	 * Metodo que elimina los datos y permite registrar un nuevo automata de mealy
	 * @param e - evento generado al presionar el boton "new"
	 */
	public void refreshMealy(ActionEvent e) {
		tableMealy.clear();
		cbxInput.getItems().clear();
		paneReaction.setVisible(false);
	}
	
	/**
	 * Metodo que elimina los datos y permite registrar un nuevo automata de moore
	 * @param e - evento generado al presionar el boton "new"
	 */
	public void refreshMoore(ActionEvent e) {
		tableMoore.clear();
		cbxInput1.getItems().clear();
		paneReaction.setVisible(false);
	}
	
	public void checkers(int i) {
		
		if(i > 0) {
			if(rbtMealy.isSelected()) {
				rbtMoore.setSelected(false);
				paneMealy.setVisible(true);
				paneMoore.setVisible(false);
				option = 1;
			}
		}else {
			if(rbtMoore.isSelected()) {
				rbtMealy.setSelected(false);
				paneMealy.setVisible(false);
				paneMoore.setVisible(true);
				option = 2;
			}
		}
	}

	public void resultado(ActionEvent e) {
		
		
		if(option == 1) {
			paneReaction.setVisible(true);
			txtPartitions.setText("Hola, aquí deberían ir las particiones!!");
			txtReaction.setText("Hola, aquí debería ir la tabla de estados!!");
			
		}else {
			paneReaction.setVisible(true);
			txtPartitions.setText("Hola, aquí deberían las particiones!!");
			txtReaction.setText("Hola, aquí debería ir la tabla de estados!!");
		}
		
		
		
	}
	
	
	
	
	

	//crear un metodo que cada que le presione crear relacion, me vaya creando la matriz
	
//	public String[][] crearMatriz(ActionEvent e){
//		
//		
//		String strBegin = txtBegin.getText();
//		String strInputSet = cbxInput.getSelectionModel().toString();
//		String strEnd = txtEnd.getText();
//		String strOutputSet = txtOutput.getText();
//		
//		
//		
//	}
//	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//NO ESTA LISTOOOOOOOOOOOOOOOO!! ARREGLAR
	public void crearMaquina(ActionEvent e) {
		
		
		String strBegin = txtBegin.getText();
		String strInputSet = cbxInput.getSelectionModel().toString();
		String strEnd = txtEnd.getText();
		String strOutputSet = txtOutput.getText();
		
		String strFinal = tableMealy.getText();
		
		
		//final
		//strFinal += "\n" + strBegin + "               " + salida
	
	}
	
	
	
	
	
	
	
	

}
