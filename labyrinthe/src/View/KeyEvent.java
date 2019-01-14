package View;
import java.awt.event.*;

import Controller.Scenario;

public class KeyEvent implements KeyListener {

	//Action � effectuer lors d'une pression clavier de l'utilisateur
	public void keyPressed(java.awt.event.KeyEvent e) {
		//System.out.println("Pressed"+e.getKeyCode());
		if(e.getKeyCode()==37) {			//Fl�che de gauche
			Scenario.joueur.deplacer('g');
		}
		else if(e.getKeyCode()==38) {		//Fl�che du haut
			Scenario.joueur.deplacer('h');
		}
		else if(e.getKeyCode()==39) {		//Fl�che de droite
			Scenario.joueur.deplacer('d');
		}
		else if(e.getKeyCode()==40) {		//Fl�che du bas
			Scenario.joueur.deplacer('b');
		}
		else if(e.getKeyCode()==80) {		//On peut aussi emprunter un passage secret en appuyant sur la touche P
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
