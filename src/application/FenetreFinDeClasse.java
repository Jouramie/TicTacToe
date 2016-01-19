package application;

import java.io.IOException;

import tictactoe.TicTacToe;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * Cette classe gère la fenètre de fin de match !
 *
 * @author Jbolduc Jsamson 
 *     @Version 1.0.0       
 */
public class FenetreFinDeClasse{

	Stage stage;
	@FXML
	Button rejouer;
	@FXML
	Label label2;
	@FXML
	AnchorPane ap ;
	
	/**
	 * Enclenche l'apparition d'une fenètre.
	 *
	 *  CONSTRUCTEUR     
	 */
	public FenetreFinDeClasse(TicTacToe ttt){
		stage = new Stage();
		try {
			ap = FXMLLoader.load(FenetreFinDeClasse.class.getResource("win.fxml"));
			Scene scene = new Scene(ap);
			stage.setScene(scene);
			label2 = (Label) ap.lookup("#label2");
			if(ttt.gagnantProperty().get() != 0){
				label2.setText("La partie est nul !");
			} else {
				label2.setText("Le gagnant est le joueur fuu" + ttt.gagnantProperty().get() + " !");
			}
			label2 = (Label) ap.lookup("#label2");
			label2.setTextAlignment(TextAlignment.CENTER);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Stage getStage(){
		return stage;
	}


}
