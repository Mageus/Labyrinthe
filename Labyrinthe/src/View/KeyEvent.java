package View;
import java.awt.event.*;

import Controller.Scenario;

public class KeyEvent implements KeyListener {

	public void keyPressed(java.awt.event.KeyEvent e) {
		//System.out.println("Pressed"+e.getKeyCode());
		if(e.getKeyCode()==37) {
			Scenario.joueur.deplacer('g');
		}
		else if(e.getKeyCode()==38) {
			Scenario.joueur.deplacer('h');
		}
		else if(e.getKeyCode()==39) {
			Scenario.joueur.deplacer('d');
		}
		else if(e.getKeyCode()==40) {
			Scenario.joueur.deplacer('b');
		}
		else if(e.getKeyCode()==80) {
			Scenario.joueur.passageSecret();
		}
		Scenario.jeu();
	}

	public void keyReleased(java.awt.event.KeyEvent e) {
		//System.out.println("Released"+e.getKeyCode());
	}

	public void keyTyped(java.awt.event.KeyEvent e) {
		//System.out.println("Typed"+e.getKeyCode());
	}

}
