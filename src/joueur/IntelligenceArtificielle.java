/**
 * Cette classe gère la fenètre de fin de match !
 *
 * @author Jbolduc Jsamson 
 *     @Version 1.0.0       
 */

package joueur;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import tictactoe.TicTacToe;

public class IntelligenceArtificielle extends Joueur {

	private int difficulte;
	private int nTour;

	/**
	 * Constructeur d'intelligence artificiel
	 * 
	 * @param pDifficulte
	 *            la difficulté de l'ia
	 * @param pID
	 *            le numéro de l'ia
	 * @param pttt
	 *            le tictactoe dans lequel il joue
	 */
	public IntelligenceArtificielle(int pDifficulte, int pID, TicTacToe pttt) {
		super(pID, pttt);
		difficulte = pDifficulte;
		nTour = 0;
		ecouterTour();

	}

	/**
	 * Constructeur de base de l'ia
	 * 
	 * @param pID
	 *            le numéro de l'ia
	 * @param pttt
	 *            le tictactoe dans lequel il joue
	 */
	public IntelligenceArtificielle(int pID, TicTacToe pttt) {
		this(1, pID, pttt);
	}

	/**
	 * place un écouteur sur la propriété tour de la classe tictactoe pour que
	 * l'ia sache quand c'est son tour
	 */
	private void ecouterTour() {
		ttt.tourProperty().addListener(new ChangeListener<Object>() {
			public void changed(ObservableValue<?> o, Object oldVal,
					Object newVal) {
				if (ttt.tourProperty().get() == ID
						&& ttt.gagnantProperty().get() == -1) {
					nTour++;
					jouerCoup();
				}
			}
		});
	}

	/**
	 * Permet à l'ia de joueur
	 * 
	 * @return vrai si ça a marcher
	 */
	public boolean jouerCoup() {
		if (difficulte == 0) {
			while (!super.jouerCoup((int) (Math.random() * 3),
					(int) (Math.random() * 3)))
				;
		} else if (difficulte == 1) {
			if (peutGagner()[0] != -1) {
				super.jouerCoup(peutGagner()[0], peutGagner()[1]);
			} else if (peutPasPerdre()[0] != -1) {
				super.jouerCoup(peutPasPerdre()[0], peutPasPerdre()[1]);
			} else {
				while (!super.jouerCoup((int) (Math.random() * 3),
						(int) (Math.random() * 3)))
					;
			}
		} else if (difficulte == 2) {
			if (peutGagner()[0] != -1) {
				super.jouerCoup(peutGagner()[0], peutGagner()[1]);
			} else if (peutPasPerdre()[0] != -1) {
				super.jouerCoup(peutPasPerdre()[0], peutPasPerdre()[1]);
			} else if (ttt.grilleProperty()[1][1].get() == 0) {
				super.jouerCoup(1, 1);

			} else if (ttt.grilleProperty()[0][0].get() == 0
					|| ttt.grilleProperty()[2][2].get() == 0
					|| ttt.grilleProperty()[2][0].get() == 0
					|| ttt.grilleProperty()[0][2].get() == 0) {
				while (!super.jouerCoup(((int) (Math.random() * 2)) * 2,
						((int) (Math.random() * 2)) * 2))
					;

			} else {
				int i = 0;
				while (!super.jouerCoup((int) (Math.random() * 3),
						(int) (Math.random() * 3))) {
					i++;
					if (i == 5)
						break;
				}
			}
		} else if (difficulte == 3) {
			if (peutGagner()[0] != -1) {
				super.jouerCoup(peutGagner()[0], peutGagner()[1]);
			} else if (peutPasPerdre()[0] != -1) {
				super.jouerCoup(peutPasPerdre()[0], peutPasPerdre()[1]);
			} else if (ttt.grilleProperty()[1][1].get() == 0) {
				super.jouerCoup(1, 1);

			} else if (ttt.grilleProperty()[0][0].get() == 0
					|| ttt.grilleProperty()[2][2].get() == 0
					|| ttt.grilleProperty()[2][0].get() == 0
					|| ttt.grilleProperty()[0][2].get() == 0) {

				while (!super.jouerCoup(((int) (Math.random() * 2)) * 2,
						((int) (Math.random() * 2)) * 2))
					;

			} else {
				while (!super.jouerCoup((int) (Math.random() * 3),
						(int) (Math.random() * 3)))
					;
			}

		}
		return true;
	}

	/**
	 * vérifi si l'ia peut gagner
	 * 
	 * @return la position
	 */
	private int[] peutGagner() {
		int[] r = compterGrille(ID);
		return r;
	}

	/**
	 * vérifie si l'ia peut bloquer
	 * 
	 * @return la position
	 */
	private int[] peutPasPerdre() {
		int[] r;
		if (ID == 2) {
			r = compterGrille(1);
		} else {
			r = compterGrille(2);
		}
		return r;
	}

	/**
	 * Trouve la position où le joueur doit jouer pour gagner
	 * 
	 * @param pJoueur
	 *            le numéro de joueur
	 * @return la position
	 */
	private int[] compterGrille(int pJoueur) {
		int[] r = { -1, -1 };
		for (int i = 0; i <= 2; i++)
			if (validerHorizontale(i, pJoueur)[0] != -1) {
				r = validerHorizontale(i, pJoueur);

			}
		for (int i = 0; i <= 2; i++)
			if (validerVerticale(i, pJoueur)[0] != -1) {
				r = validerVerticale(i, pJoueur);

			}
		if (validerDiagonaleGD(pJoueur)[0] != -1) {
			r = validerDiagonaleGD(pJoueur);

		}
		if (validerDiagonaleDG(pJoueur)[0] != -1) {
			r = validerDiagonaleDG(pJoueur);

		}

		return r;
	}

	/**
	 * valide si un joueur peut gagner dans cette ligne
	 * 
	 * @param pL
	 *            la ligne
	 * @param pJoueur
	 *            le joueur
	 * @return la position
	 */
	private int[] validerHorizontale(int pL, int pJoueur) {
		int[] r = { -1, -1 };
		int n = 0;
		for (int i = 0; i < 3; i++) {
			if (ttt.grilleProperty()[pL][i].get() == pJoueur) {
				n++;
			} else if (ttt.grilleProperty()[pL][i].get() == 0) {
				r[0] = pL;
				r[1] = i;
			}
		}
		if (n != 2) {
			r[0] = -1;
			r[1] = -1;
		}
		return r;

	}

	/**
	 * valide si un joueur peut gagner dans cette colonne
	 * 
	 * @param pC
	 *            la colonne
	 * @param pJoueur
	 *            le joueur
	 * @return la position
	 */
	private int[] validerVerticale(int pC, int pJoueur) {
		int[] r = { -1, -1 };
		int n = 0;
		for (int i = 0; i < 3; i++) {
			if (ttt.grilleProperty()[i][pC].get() == pJoueur) {
				n++;
			} else if (ttt.grilleProperty()[i][pC].get() == 0) {
				r[0] = i;
				r[1] = pC;
			}
		}
		if (n != 2) {
			r[0] = -1;
			r[1] = -1;
		}
		return r;
	}

	/**
	 * valide si un joueur peut gagner dans cette diagonale
	 * 
	 * @param pJoueur
	 *            le joueur
	 * @return la position
	 */
	private int[] validerDiagonaleGD(int pJoueur) {
		int[] r = { -1, -1 };
		int n = 0;
		for (int i = 0; i < 3; i++) {
			if (ttt.grilleProperty()[i][i].get() == pJoueur) {
				n++;
			} else if (ttt.grilleProperty()[i][i].get() == 0) {
				r[0] = i;
				r[1] = i;
			}
		}
		if (n != 2) {
			r[0] = -1;
			r[1] = -1;
		}
		return r;
	}

	/**
	 * valide si un joueur peut gagner dans cette diagonale
	 * 
	 * @param pJoueur
	 *            le joueur
	 * @return la position
	 */
	private int[] validerDiagonaleDG(int pJoueur) {
		int[] r = { -1, -1 };
		int n = 0;
		for (int i = 0; i < 3; i++) {
			if (ttt.grilleProperty()[i][ttt.grilleProperty().length - 1 - i]
					.get() == pJoueur) {
				n++;
			} else if (ttt.grilleProperty()[i][ttt.grilleProperty().length - 1
					- i].get() == 0) {
				r[0] = i;
				r[1] = ttt.grilleProperty().length - 1 - i;
			}
		}
		if (n != 2) {
			r[0] = -1;
			r[1] = -1;
		}
		return r;
	}

}
