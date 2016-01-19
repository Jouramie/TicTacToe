/**
 * Cette classe gère la fenètre de fin de match !
 *
 * @author Jbolduc Jsamson 
 *     @Version 1.0.0       
 */

package joueur;

import tictactoe.TicTacToe;

public class Humain extends Joueur {

	/**
	 * Constructeur de joueur humain
	 * 
	 * @param pID
	 *            la numéro du joueur
	 * @param pttt
	 *            le TicTacToe dans lequel le joueur joue
	 */
	public Humain(int pID, TicTacToe pttt) {
		super(pID, pttt);
	}

}
