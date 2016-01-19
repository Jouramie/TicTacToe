/**
 * Cette classe permet de jouer au TicTacToe
 * 
 * 0 = vide
 * 1 = X
 * 2 = O
 * 
 * @author Jbolduc Jsamson 
 *     @Version 1.0.0       
 */

package tictactoe;

import java.util.ArrayList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class TicTacToe {

	private IntegerProperty[][] grille;
	/* 1 = X 2 = O */
	private IntegerProperty tour;
	/* -1 = Pas terminé 0 = Nul 1 = X gagnant 2 = O gagnant */
	private IntegerProperty gagnant;

	private int[] dernierCoup;

	/**
	 * Constructeur de TicTacToe
	 */
	public TicTacToe() {
		grille = new SimpleIntegerProperty[3][3];
		tour = new SimpleIntegerProperty();
		tour.set(1);
		gagnant = new SimpleIntegerProperty();
		gagnant.set(-1);
		initGrille();
		tour.set(1);
		dernierCoup = new int[2];

	}

	/**
	 * Permet de réinitialiser la grille
	 */
	public void rejouer() {
		initGrilleZero();
		tour.set(1);
		gagnant.set(-1);
		System.out.println(this);
		System.out.println(tour.get());
		System.out.println(gagnant.get());

	}

	/**
	 * change le tour
	 */
	public void prochainTour() {
		if (tour.get() == 1)
			tour.set(2);
		else
			tour.set(1);
	}

	/**
	 * Initialise toute les cases de la grille
	 */
	private void initGrille() {
		for (int i = 0; i < grille.length; i++) {
			for (int j = 0; j < grille[i].length; j++) {
				grille[i][j] = new SimpleIntegerProperty();
			}
		}
	}

	/**
	 * Place 0 dans toute les cases de la grille
	 */
	private void initGrilleZero() {
		for (int i = 0; i < grille.length; i++) {
			for (int j = 0; j < grille[i].length; j++) {
				grille[i][j].set(0);
			}
		}
	}

	/**
	 * Permet de mettre une valeur 1 ou 2 dans la matrice de jeu, si les
	 * coordonnées jouées sont libres.
	 * 
	 * @param pLigne
	 *            le numéro de la ligne.
	 * @param pCol
	 *            le numéro de la colonne.
	 * 
	 * @return boolean, vrai si l'affectation a été faite.
	 */
	public boolean setCoup(int pL, int pC) {

		if (grille[pL][pC].get() == 0) {
			grille[pL][pC].set(tour.get());
			dernierCoup[0] = pL;
			dernierCoup[1] = pC;
			System.out.println(this.toString());
			if (!validerGrille()) {
				prochainTour();
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Valide si la grille est gagnante
	 * 
	 * @return true si gagnant
	 */
	public boolean validerGrille() {
		if (validerHorizontale(dernierCoup[0]) != 0) {
			gagnant.set(validerHorizontale(dernierCoup[0]));
			return true;

		}
		if (validerVerticale(dernierCoup[1]) != 0) {
			gagnant.set(validerVerticale(dernierCoup[1]));
			return true;

		}
		if (validerDiagonaleGD() != 0) {
			System.out.println("La partie est terminée.");
			System.out.println("Le joueur " + validerDiagonaleGD()
					+ " a gagné!");
			gagnant.set(validerDiagonaleGD());
			return true;

		}
		if (validerDiagonaleDG() != 0) {
			System.out.println("La partie est terminée.");
			System.out.println("Le joueur " + validerDiagonaleDG()
					+ " a gagné!");
			gagnant.set(validerDiagonaleDG());
			return true;

		}
		if (estPleine()) {
			gagnant.set(0);
			System.out.println("La partie est terminé.");
			System.out.println("Match nul!");
			return true;
		}
		return false;
	}

	/**
	 * Permet de valider s'il y a une réussite sur une ligne.
	 * 
	 * @param pLigne
	 *            indice de la ligne.
	 * 
	 * @return le numéro gagnant.
	 */
	private int validerHorizontale(int pL) {

		if (grille[pL][0].get() == grille[pL][1].get()
				&& grille[pL][0].get() == grille[pL][2].get()) {
			return grille[pL][0].get();
		}
		return 0;

	}

	/**
	 * Permet de valider s'il y a une réussite sur une colonne.
	 * 
	 * @param pC
	 *            indice de la colonne.
	 * 
	 * @return le numéro gagnant
	 */
	private int validerVerticale(int pC) {

		if (grille[0][pC].get() == grille[1][pC].get()
				&& grille[0][pC].get() == grille[2][pC].get()) {
			return grille[0][pC].get();
		}
		return 0;
	}

	/**
	 * Permet de valider s'il y a une réussite sur la diagonale Gauche-Droite.
	 * 
	 * @return le numéro du gagnant
	 */
	private int validerDiagonaleGD() {

		if (grille[0][2].get() == grille[1][1].get()
				&& grille[0][2].get() == grille[2][0].get()) {
			return grille[1][1].get();
		}
		return 0;
	}

	/**
	 * Permet de valider si on a une réussite sur la diagonale Droite-Gauche.
	 * 
	 * @return le numméro du gagnant
	 */
	private int validerDiagonaleDG() {
		if (grille[0][0].get() == grille[1][1].get()
				&& grille[1][1].get() == grille[2][2].get()) {
			return grille[0][0].get();
		}
		return 0;
	}

	/**
	 * Permet de valider s'il n'y a plus de place dans la matrice.
	 * 
	 * 
	 * @return boolean, vrai s'il n'a plus de place
	 */
	private boolean estPleine() {
		int c = 0;
		for (int i = 0; i < grille.length; i++) {
			for (int j = 0; j < grille[i].length; j++) {
				if (grille[i][j].get() == 0) {
					c++;
				}
			}
		}
		if (c == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Trouve les positions des numéros gagnant
	 * 
	 * @return une arraylist de position
	 */
	public ArrayList<int[]> getGagnant() {
		ArrayList<int[]> r = new ArrayList<int[]>();
		if (validerHorizontale(dernierCoup[0]) != 0) {
			for (int i = 0; i < grille.length; i++) {
				int[] pos = { dernierCoup[0], i };
				r.add(pos);
			}

		}
		if (validerVerticale(dernierCoup[1]) != 0) {
			for (int i = 0; i < grille.length; i++) {
				int[] pos = { i, dernierCoup[1] };
				r.add(pos);
			}

		}
		if (validerDiagonaleDG() != 0) {
			for (int i = 0; i < grille.length; i++) {
				int[] pos = { i, i };
				r.add(pos);
			}

		}
		if (validerDiagonaleGD() != 0) {
			for (int i = 0; i < grille.length; i++) {
				int[] pos = { grille.length - (i + 1), i };
				r.add(pos);
			}

		}
		if (estPleine() && r.isEmpty()) {
			for (int i = 0; i < grille.length; i++) {
				for (int j = 0; j < grille[i].length; j++) {
					int[] pos = { i, j };
					r.add(pos);
				}
			}

		}
		return r;

	}

	/**
	 * @return La propriété tour
	 */
	public IntegerProperty tourProperty() {
		return tour;
	}

	/**
	 * @return La propriété gagnant
	 */
	public IntegerProperty gagnantProperty() {
		return gagnant;
	}

	/**
	 * @return La grille
	 */
	public IntegerProperty[][] grilleProperty() {
		return grille;
	}

	/**
	 * Met la grille en string
	 * 
	 * @return le string
	 */
	public String toString() {

		String str = "    ";
		for (int j = 0; j < grille.length; j++) {
			str += j + "  ";
		}
		str += "\n";
		for (int i = 0; i < grille.length; i++) {
			str += i + "  ";
			for (int j = 0; j < grille[i].length; j++) {
				if (grille[i][j].get() == 0) {
					str += "[ ]";
				} else {
					if (grille[i][j].get() == 1) {
						str += "[X]";
					} else {
						str += "[O]";
					}
				}
			}
			str += "\n";
		}
		return (str);
	}
}
