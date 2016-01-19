package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tictactoe.TicTacToe;

public class TicTacToeTest {

	TicTacToe ttt, ttt1;

	@Before
	public void testTicTacToe() {
		ttt = new TicTacToe();
		ttt1 = new TicTacToe();
		ttt1.setCoup(0, 0);
		ttt1.setCoup(1, 1);
	}

	@Test
	public void testRejouer() {
		ttt1.rejouer();
		for (int i = 0; i < ttt.grilleProperty().length; i++) {
			for (int j = 0; j < ttt.grilleProperty()[i].length; j++) {
				assertTrue(ttt1.grilleProperty()[i][i].get() == 0);
			}
		}
	}

	@Test
	public void testProchainTour() {
		assertTrue(ttt.tourProperty().get() == 1);
		ttt.prochainTour();
		assertTrue(ttt.tourProperty().get() == 2);
		ttt.prochainTour();
		assertTrue(ttt.tourProperty().get() == 1);
	}

	@Test
	public void testSetCoup() {
		assertTrue(ttt.grilleProperty()[0][0].get() == 0);
		ttt.setCoup(0, 0);
		assertTrue(ttt.grilleProperty()[0][0].get() == 1);
		assertTrue(ttt1.grilleProperty()[0][0].get() == 1);
		ttt1.setCoup(0, 0);
		assertTrue(ttt1.grilleProperty()[0][0].get() == 1);
	}

	@Test
	public void testValiderGrille() {
		assertFalse(ttt.validerGrille());
		ttt.setCoup(0, 0);
		ttt.setCoup(1, 0);
		ttt.setCoup(0, 1);
		ttt.setCoup(1, 1);
		ttt.setCoup(0, 2);
		assertTrue(ttt.validerGrille());
	}
	

	@Test
	public void testGetGagnant() {
		assertTrue(ttt.getGagnant().isEmpty());
		ttt.setCoup(0, 0);
		ttt.setCoup(1, 0);
		ttt.setCoup(0, 1);
		ttt.setCoup(1, 1);
		ttt.setCoup(0, 2);
		assertTrue(ttt.getGagnant().get(0)[0] == 0);	
	}

	@Test
	public void testTourProperty() {
		assertTrue(ttt.tourProperty().get() == 1);
		ttt.setCoup(0, 0);
		assertTrue(ttt.tourProperty().get() == 2);
	}

	@Test
	public void testGagnantProperty() {
		assertTrue(ttt.gagnantProperty().get() == -1);
		ttt.setCoup(0, 0);
		ttt.setCoup(1, 0);
		ttt.setCoup(0, 1);
		ttt.setCoup(1, 1);
		ttt.setCoup(0, 2);
		assertTrue(ttt.gagnantProperty().get() == 1);
	}

	@Test
	public void testGrilleProperty() {
		assertTrue(ttt.grilleProperty()[0][0].get() == 0);
		assertTrue(ttt1.grilleProperty()[0][0].get() == 1);
	}

	@Test
	public void testToString() {
		assertTrue(ttt.toString().length() > 1);
	}

}
