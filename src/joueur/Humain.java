/**
 * Cette classe g�re la fen�tre de fin de match !
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
	 *            la num�ro du joueur
	 * @param pttt
	 *            le TicTacToe dans lequel le joueur joue
	 */
	public Humain(int pID, TicTacToe pttt) {
		super(pID, pttt);
	}

}
