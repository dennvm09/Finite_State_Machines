package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class inicioController {
	
	private int option;
	private int amountSets;
	private String inputsSets;
	
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
	private TableView<String> tableMealy;
	@FXML
	private Button btDeleteColumns;
	
	
	
	
	public void initialize() {
		paneMealy.setVisible(false);
		paneMoore.setVisible(false);
		rbtMealy.setOnAction(e-> checkers(1));
		rbtMoore.setOnAction(e-> checkers(-1));
	}
	
	public String[] inputSetsM(String inputs) {	
		//la entrada me la manda el boton y son los inputs separados por comas
		String[] fInputs = inputs.split(",");
		return fInputs;	
	}
	//metodo para iniciar el programa, eenvia valor a las variables globales
	public void startP(ActionEvent e) {
		amountSets = Integer.parseInt(txtAmountSetsQ.getText());
		inputsSets = txtInputsSetsS.getText();
		updateTable();
		fillInputs();
		tableMealy.setVisible(true);
	}
	//metodo que actualiza la tabla, utiliza el metodo que separa las entradas
	public void updateTable() {
		String[] inputs = inputSetsM(inputsSets);
		tableMealy.getColumns().add(new TableColumn<>());
		if(amountSets == inputs.length) {
			for(int i = 0; i < inputs.length; i++) {
				tableMealy.getColumns().add(new TableColumn<String, String>(inputs[i]));
			}
		}
	}
	//rellenar el cbx
	public void fillInputs() {
		String[] inputs = inputSetsM(inputsSets);
		ObservableList<String> items = FXCollections.observableArrayList();
		for(int i = 0; i < inputs.length; i++) {
			items.addAll(inputs[i]);
		}
		cbxInput.setItems(items);
	}
	
	public void refreshMealy(ActionEvent e) {
		tableMealy.getColumns().clear();
		cbxInput.getItems().clear();
	}
	
	public void checkers(int i) {
		
		if(i > 0) {
			if(rbtMealy.isSelected()) {
				rbtMoore.setSelected(false);
				paneMealy.setVisible(true);
				paneMoore.setVisible(false);
			}
		}else {
			if(rbtMoore.isSelected()) {
				rbtMealy.setSelected(false);
				paneMealy.setVisible(false);
				paneMoore.setVisible(true);
			}
		}
	}

	public void crearMaquina(ActionEvent e) {
		
		
		//utilizo la clase machine
			
	}
	
	
	
	
	public static class Machine1{
		private final StringProperty begin;
		private final StringProperty inputSet;
		private final StringProperty end;
		private final StringProperty outputSet;
		
		private Machine1(String strBegin, String strInputSet, String strEnd, String strOutputSet) {
			
			this.begin = new SimpleStringProperty(strBegin);
			this.inputSet = new SimpleStringProperty(strInputSet);
			this.end = new SimpleStringProperty(strEnd);
			this.outputSet = new SimpleStringProperty(strOutputSet);
		}

		public StringProperty getBegin() {
			return begin;
		}

		public StringProperty getInputSet() {
			return inputSet;
		}

		public StringProperty getEnd() {
			return end;
		}

		public StringProperty getOutputSet() {
			return outputSet;
		}
		
		public void setBegin(String strBegin) {
			begin.set(strBegin);
		}
		
		public void setInputSet(String strInputSet) {
			inputSet.set(strInputSet);
		}
	
		public void setEnd(String strEnd) {
			end.set(strEnd);
		}
		
		public void setOutputSet(String strOutputSet) {
			outputSet.set(strOutputSet);
		}
		
		
		
	}
	
	

	

}
