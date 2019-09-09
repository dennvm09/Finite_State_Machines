package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class inicioController {
	
	private int option;
	
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
	
	public void initialize() {
		paneMealy.setVisible(false);
		paneMoore.setVisible(false);
		rbtMealy.setOnAction(e-> checkers(1));
		rbtMoore.setOnAction(e-> checkers(-1));
	}
	
	public void mostrarTabla(ActionEvent e) {
		tableMealy.setVisible(true);
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
}
