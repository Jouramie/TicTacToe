/**
 * Cette classe g�re les joueurs !
 *
 * @author Jbolduc Jsamson 
 * @version 1.0.0       
 */

package joueur;

import tictactoe.TicTacToe;

public class Joueur {

	protected TicTacToe ttt;
	protected int ID;

	/**
	 * Constructeur de joueur
	 * 
	 * @param pID
	 *            le num�ro du joueur
	 * @param pttt
	 *            la partie de TicTacToe dans lequel il joue
	 */
	public Joueur(int pID, TicTacToe pttt) {
		ID = pID;
		ttt = pttt;
	}

	/**
	 * Cette m�thode permet de joueur dans la grille
	 * 
	 * @param pL
	 *            la ligne
	 * @param pC
	 *            la colonne
	 * @return true si c'est r�ussis
	 */
	public boolean jouerCoup(int pL, int pC) {
		System.out.println("Le joueur " + ID + " joue en (" + pL + ", " + pC
				+ ")");
		return ttt.setCoup(pL, pC);
	}

	/**
	 * Permet de changer le num�ro d'un joueur
	 * 
	 * @param pID
	 *            le num�ro du joueur
	 */
	public void setID(int pID) {
		ID = pID;

	}
}
