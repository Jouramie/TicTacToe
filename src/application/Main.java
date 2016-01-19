package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import joueur.Humain;
import joueur.IntelligenceArtificielle;
import joueur.Joueur;
import tictactoe.TicTacToe;
import timer.FXTimer;

public class Main extends Application implements Initializable {

	public static final int DIFFICULTE = 2;
	public static final int PAUSE = 10000;

	/*
	 * 0 = Facile 1 = Moyen 2 = Difficile 3 = Impossible
	 */

	private Thread th;
	private TicTacToe ttt;
	private Joueur joueur1;
	private Joueur joueur2;
	private static boolean onOff;
	private Thread clign;
	private Rectangle l;

	@FXML
	protected Pane root;
	@FXML
	private Label label;
	@FXML
	private Label label2;
	@FXML
	private Label temps;
	@FXML
	private CheckBox joueuri;
	@FXML
	private CheckBox joueurii;
	@FXML
	private Button reco;
	@FXML
	private Button quit;
	@FXML
	private Button rejouer;
	@FXML
	private ImageView x00;
	@FXML
	private ImageView x01;
	@FXML
	private ImageView x02;
	@FXML
	private ImageView x10;
	@FXML
	private ImageView x11;
	@FXML
	private ImageView x12;
	@FXML
	private ImageView x20;
	@FXML
	private ImageView x21;
	@FXML
	private ImageView x22;
	@FXML
	private ImageView o00;
	@FXML
	private ImageView o01;
	@FXML
	private ImageView o02;
	@FXML
	private ImageView o10;
	@FXML
	private ImageView o11;
	@FXML
	private ImageView o12;
	@FXML
	private ImageView o20;
	@FXML
	private ImageView o21;
	@FXML
	private ImageView o22;

	@FXML
	private Pane p00;
	@FXML
	private Pane p01;
	@FXML
	private Pane p02;
	@FXML
	private Pane p10;
	@FXML
	private Pane p11;
	@FXML
	private Pane p12;
	@FXML
	private Pane p20;
	@FXML
	private Pane p21;
	@FXML
	private Pane p22;
	@FXML
	private Rectangle rectangle;

	/**
	 * Le start fait naître les éléments graphiques du Pane.
	 *
	 * @param stage
	 *            return void
	 */
	public void start(Stage primaryStage) {
		try {
			Pane pane = FXMLLoader.load(Main.class.getResource("tictac.fxml"));
			Scene scene = new Scene(pane);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Tic Tac Toe");

			primaryStage.setMaxHeight(652);
			primaryStage.setMaxWidth(446);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Main de l'application
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Sert à initialiser les variables, le jeux TicTacToe et les différents joueurs.
	 */
	public void initialize(URL location, ResourceBundle resources) {
		ttt = new TicTacToe();
		joueur1 = new Humain(1, ttt);
		if (joueuri.isSelected()) {
			joueur2 = new IntelligenceArtificielle(DIFFICULTE, 2, ttt);
		} else {
			joueur2 = new Humain(2, ttt);
		}
		ecouteur();
		label.textProperty().bind(ttt.tourProperty().asString());
		FXTimer task = new FXTimer();
		th = new Thread(task);
		th.setDaemon(true);
		th.start();
		temps.textProperty().bind((task.timeSeconds.asString()));
		l = new Rectangle();
		l.setVisible(false);
	}

	/**
	 * Grande sections de différents écouteurs pour la gestion du jeu.
	 *
	 * @param stage
	 *            return void
	 */
	private void ecouteur() {
		ttt.gagnantProperty().addListener(new ChangeListener<Object>() {
			public void changed(ObservableValue<?> o, Object oldVal,
					Object newVal) {
				if (!(ttt.gagnantProperty().get() == -1)) {
					fenetreFinDePartie().show();
					clign = new Thread(new Clignoter());
					clign.setDaemon(true);
					clign.start();
					Thread anim = new Thread(new Anim());
					anim.setDaemon(true);
					anim.start();
					for (int i = 0; i < 3; i++) {
						for (int j = 0; j < 3; j++) {
							selectionnerP(i, j).setMouseTransparent(true);
						}
					}
				}
			}
		});

		for (int i = 0; i < ttt.grilleProperty().length; i++) {
			for (int j = 0; j < ttt.grilleProperty()[i].length; j++) {
				final int x = i;
				final int y = j;
				ttt.grilleProperty()[i][j]
						.addListener(new ChangeListener<Object>() {
							public void changed(ObservableValue<?> o,
									Object oldVal, Object newVal) {
								if ((int) newVal == 1) {
									selectionnerX(x, y).setVisible(true);
								} else if ((int) newVal == 2) {
									attendre(PAUSE);
									selectionnerO(x, y).setVisible(true);
								} else {
									selectionnerX(x, y).setVisible(false);
									selectionnerO(x, y).setVisible(false);
								}
							}
						});
			}
		}
	}

	/**
	 * Crée une fenètre qui annoncera le gagnant.
	 *
	 * @param null
	 *            return Stage
	 */
	private Stage fenetreFinDePartie() {
		final URL resource = getClass().getResource("../application/kultur0413.wav");
		final Media media = new Media(resource.toString());
		final MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
		return (new FenetreFinDeClasse(ttt)).getStage();

	}

	private void attendre(int temps) {
	}

	/**
	 * Permet de basculer entre le monde 1 et 2 joueurs.
	 *
	 * @param ActionEvent e
	 *            return void
	 */
	@FXML
	private void Checkbox(ActionEvent e) {
		
		if (joueuri.equals(e.getSource())) {
			if (!joueuri.isSelected()) {
				Platform.runLater(() -> {
					joueurii.setSelected(true);
				});
			}
			joueurii.setSelected(false);
		} else {
			if (!joueurii.isSelected()) {
				Platform.runLater(() -> {
					joueuri.setSelected(true);
				});
			}
			joueuri.setSelected(false);
		}
		rejouer();
	}

	/**
	 * Calcule les coups au jeu.
	 *
	 * @param MouseEvent a
	 *            return void
	 */
	@FXML
	private void clic(MouseEvent a) {
		int L = ((Pane) a.getSource()).getId().charAt(1) - 48;
		int C = ((Pane) a.getSource()).getId().charAt(2) - 48;
		if (ttt.tourProperty().get() == 1) {
			joueur1.jouerCoup(L, C);
		} else {
			joueur2.jouerCoup(L, C);
		}

	}

	/**
	 * Quitter l'application.
	 *
	 * @param MouseEvent a
	 *            return void
	 */
	@FXML
	private void quit(MouseEvent a) {
		Platform.exit();
	}

	/**
	 * Restarter le pane dans le but de rejouer.
	 *
	 * @param null
	 *            return void
	 */
	@FXML
	private void rejouer() {
		ttt.tourProperty().unbind();
		joueur1 = new Humain(1, ttt);
		if (joueuri.isSelected()) {
			joueur2 = new IntelligenceArtificielle(DIFFICULTE, 2, ttt);
		} else {
			joueur2.setID(3);
			joueur2 = new Humain(2, ttt);
		}
		onOff = false;
		ttt.rejouer();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				selectionnerP(i, j).setMouseTransparent(false);
			}
		}
		l.setVisible(false);
	}

	/**
	 * Sélectionne uncarré dans la grille de TicTacToe.
	 *
	 * @param Int x, int y
	 *            return Pane
	 */
	private Pane selectionnerP(int x, int y) {
		Pane r = null;
		if (x == 0 && y == 0) {
			r = p00;
		} else if (x == 0 && y == 1) {
			r = p01;
		} else if (x == 0 && y == 2) {
			r = p02;
		} else if (x == 1 && y == 0) {
			r = p10;
		} else if (x == 1 && y == 1) {
			r = p11;
		} else if (x == 1 && y == 2) {
			r = p12;
		} else if (x == 2 && y == 0) {
			r = p20;
		} else if (x == 2 && y == 1) {
			r = p21;
		} else if (x == 2 && y == 2) {
			r = p22;
		}
		return r;
	}

	/**
	 * Sélectionne le carré ou jouer, mais en ImageView, car nos x et o sont des images.
	 *
	 * @param int x, int y
	 *            return ImageView
	 */
	private ImageView selectionnerX(int x, int y) {
		ImageView r = null;
		if (x == 0 && y == 0) {
			r = x00;
		} else if (x == 0 && y == 1) {
			r = x01;
		} else if (x == 0 && y == 2) {
			r = x02;
		} else if (x == 1 && y == 0) {
			r = x10;
		} else if (x == 1 && y == 1) {
			r = x11;
		} else if (x == 1 && y == 2) {
			r = x12;
		} else if (x == 2 && y == 0) {
			r = x20;
		} else if (x == 2 && y == 1) {
			r = x21;
		} else if (x == 2 && y == 2) {
			r = x22;
		}
		return r;
	}

	/**
	 * Sélectionne un Image.
	 *
	 * @param int x, int y
	 *            return ImageView
	 */
	private ImageView selectionnerO(int x, int y) {
		ImageView r = null;
		if (x == 0 && y == 0) {
			r = o00;
		} else if (x == 0 && y == 1) {
			r = o01;
		} else if (x == 0 && y == 2) {
			r = o02;
		} else if (x == 1 && y == 0) {
			r = o10;
		} else if (x == 1 && y == 1) {
			r = o11;
		} else if (x == 1 && y == 2) {
			r = o12;
		} else if (x == 2 && y == 0) {
			r = o20;
		} else if (x == 2 && y == 1) {
			r = o21;
		} else if (x == 2 && y == 2) {
			r = o22;
		}
		return r;
	}

	/**
	 * Classe interne qui gère la fin de partie, par une petite fenêtre.
	 *
	 * @class
	 * @author jbolduc jsamson
	 * @version 1.0.1
	 *          
	 */
	private class FenetreFinDeClasse {

		Stage stage;
		@FXML
		Button rejouer;
		@FXML
		Label label2;
		@FXML
		AnchorPane ap;

		public FenetreFinDeClasse(TicTacToe ttt) {
			stage = new Stage();
			try {
				ap = FXMLLoader.load(FenetreFinDeClasse.class
						.getResource("win.fxml"));
				Scene scene = new Scene(ap);
				stage.setScene(scene);
				label2 = (Label) ap.lookup("#label2");
				label2.setText("Le gagnant est le joueur "
						+ ttt.gagnantProperty().get() + " !");
				label2 = (Label) ap.lookup("#label2");
				label2.setTextAlignment(TextAlignment.CENTER);
				rejouer = (Button) ap.lookup("#rejouer");
				rejouer.setOnMouseClicked(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent me) {
						rejouer();
						stage.close();
					}
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public Stage getStage() {
			return stage;
		}

	}

	/**
	 * Thread pour l'animation de clignotement.
	 *
	 * @Thread
	 *           return Object
	 */	
	protected class Clignoter extends Task<Object> {

		@Override
		protected Object call() {
			ArrayList<int[]> list = ttt.getGagnant();
			onOff = true;
			try {
				while (onOff) {
					for (int[] i : list) {
						Platform.runLater(() -> {
							selectionnerP(i[0], i[1]).setVisible(false);
						});
					}
					Thread.sleep(500);
					for (int[] i : list) {
						Platform.runLater(() -> {
							selectionnerP(i[0], i[1]).setVisible(true);
						});
					}
					Thread.sleep(500);
				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}

	/**
	 * Classe interne qui gère les animations.
	 *
	 * @param int x, int y
	 *            return Task
	 */
	protected class Anim extends Task<Object> {

		@Override
		protected Object call() {
			ArrayList<int[]> list = ttt.getGagnant();
			if (list.size() == 3) {
				try {
					
					l = new Rectangle(10, 20, Color.RED);
					l.setStrokeWidth(1);
					l.setStroke(Color.DARKRED);
					
					if (list.get(0)[0] == list.get(1)[0]) {
						
						if(list.get(0)[0] == 0){
							l.setLayoutX(75);
							l.setLayoutY(196 + 68);
						}else if(list.get(0)[0] == 1){
							l.setLayoutX(75);
							l.setLayoutY(334 + 68);
						}else{
							l.setLayoutX(75);
							l.setLayoutY(610 - 68);
							
						}
						Platform.runLater(() -> {
							root.getChildren().add(l);
						});
						for (int i = 0; i < 280; i++) {
							Platform.runLater(() -> {
								l.setWidth(l.getWidth() + 1);
							});
							Thread.sleep(10);
						}
						
					} else if (list.get(0)[1] == list.get(1)[1]) {
						
						if(list.get(0)[1] == 0){
							l.setLayoutX(9 + 66);
							l.setLayoutY(196 + 68);
						}else if(list.get(0)[1] == 1){
							l.setLayoutX(150 + 66);
							l.setLayoutY(196 + 68);
						}else{
							l.setLayoutX(289 + 66);
							l.setLayoutY(196 + 68);
						}
						Platform.runLater(() -> {
							root.getChildren().add(l);
						});
						for (int i = 0; i < 278; i++) {
							Platform.runLater(() -> {
								l.setHeight(l.getHeight() + 1);
							});
							Thread.sleep(10);
						}
						
					} else if (list.get(0)[0] == list.get(0)[1]
							&& list.get(1)[0] == list.get(1)[1]) {
						
						l.setLayoutX(9 + 66);
						l.setLayoutY(196 + 68);
						l.setRotate(45);
						Platform.runLater(() -> {
							root.getChildren().add(l);
						});

						for (int i = 0; i < 395; i++) {
							Platform.runLater(() -> {
								l.setWidth(l.getWidth() + 1);
								l.setLayoutY(l.getLayoutY() + 0.35);
								l.setLayoutX(l.getLayoutX() - 0.15);
							});
							Thread.sleep(10);
						}
						
					} else {
						l.setLayoutX(9 + 66);
						l.setLayoutY(610 - 68);
						l.setRotate(-45);
						Platform.runLater(() -> {
							root.getChildren().add(l);
						});

						for (int i = 0; i < 395; i++) {
							Platform.runLater(() -> {
								l.setWidth(l.getWidth() + 1);
								l.setLayoutY(l.getLayoutY() - 0.35);
								l.setLayoutX(l.getLayoutX() - 0.15);
							});
							Thread.sleep(10);
						}
					}
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return null;
		}
	}
}
