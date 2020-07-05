package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<Integer> boxLUN;

    @FXML
    private Button btnCalcolaComponenteConnessa;

    @FXML
    private Button btnCercaOggetti;

    @FXML
    private Button btnAnalizzaOggetti;

    @FXML
    private TextField txtObjectId;

    @FXML
    private TextArea txtResult;

    @FXML
    void doAnalizzaOggetti(ActionEvent event) {
    	this.model.creaGrafo();
    	this.txtResult.appendText("Vertex :"+ this.model.setV().size()+"\n");
    	this.txtResult.appendText("Edge :"+ this.model.setE().size()+"\n");

    }

    @FXML
    void doCalcolaComponenteConnessa(ActionEvent event) {
    	String id = this.txtObjectId.getText();
    	if(id==null)
    	{
    		this.txtResult.appendText("Inserire!\n");
    		return;
    	}
    	int n;
    	try {
    		n= Integer.parseInt(id);
    	} catch(NumberFormatException e)
    	{
    		this.txtResult.appendText("Formato non valido!\n");
    		return;
    	}
    	if(!this.model.isInGraph(n))
    	{
    		this.txtResult.appendText("Non presente nel grafo riporvare!\n");
    		return;	
    	}
    	int nc=this.model.componenteConn(n);
    	if(nc==1)
    	{
this.txtResult.appendText("Selezionare un altra componenete connessa questa ha lunghezza =1 quindi il cammino coincide con questa stessa"+"\n ");
    		return;	
    		}
    	
    	this.txtResult.appendText("Selezionare un numero compreso tra 2 e "+ nc+"\n");
    	List<Integer> list = new ArrayList<>();
    	for(int i=2; i<=nc;i++)
    	{
    		list.add(i);
    	}
    	
    	
    	this.boxLUN.getItems().addAll(list);

    }

    @FXML
    void doCercaOggetti(ActionEvent event) {
    if(this.boxLUN.getValue()==null)
    {
    	this.txtResult.appendText("Selezionare un numero "+"\n");
    			return;
    }
    String id = this.txtObjectId.getText();
	if(id==null)
	{
		this.txtResult.appendText("Inserire!\n");
		return;
	}
	int n;
	try {
		n= Integer.parseInt(id);
	} catch(NumberFormatException e)
	{
		this.txtResult.appendText("Formato non valido!\n");
		return;
	}
    this.model.cammino(this.boxLUN.getValue(),n);
    this.txtResult.appendText(Integer.toString(this.model.getBestPeso()));
    	
    	
    	

    }

    @FXML
    void initialize() {
        assert boxLUN != null : "fx:id=\"boxLUN\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnCalcolaComponenteConnessa != null : "fx:id=\"btnCalcolaComponenteConnessa\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnCercaOggetti != null : "fx:id=\"btnCercaOggetti\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnAnalizzaOggetti != null : "fx:id=\"btnAnalizzaOggetti\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtObjectId != null : "fx:id=\"txtObjectId\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}
