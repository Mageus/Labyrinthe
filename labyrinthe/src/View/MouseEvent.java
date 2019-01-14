package View;
import java.awt.event.*;

import Controller.Scenario;

public class MouseEvent implements MouseListener {

	//Action à effectuer lorsqu'un clic est détecter
	public void mouseClicked(java.awt.event.MouseEvent e) {
		int jx=Scenario.joueur.x;		//Coordonnées du joueur
		int jy=Scenario.joueur.y;
		int x=e.getX();		//Coordonnées du clic
		int y=e.getY();
		int w=Affichage.p_jeu.getWidth();		//Taille de la fenêtre
		int h=Affichage.p_jeu.getHeight();
		if(x>((jx*w/10)-(w/10)) && x<(jx*w/10) && y>(jy*h/7) && y<((jy*h/7)+(h/7))) {					//Clic sur la case à gauche du joueur
			Scenario.joueur.deplacer('g');
		}
		else if(x>((jx*w/10)+(w/10)) && x<((jx*w/10)+(2*w/10)) && y>(jy*h/7) && y<((jy*h/7)+(h/7))) {	//Clic sur la case à droite du joueur
			Scenario.joueur.deplacer('d');
		}
		else if(x>(jx*w/10) && x<((jx*w/10)+(w/10)) && y>((jy*h/7)+(h/7)) && y<((jy*h/7)+(2*h/7))) {	//Clic sur la case en bas du joueur
			Scenario.joueur.deplacer('b');
		}
		else if(x>(jx*w/10) && x<((jx*w/10)+(w/10)) && y>(jy*h/7)-(h/7) && y<(jy*h/7)) {				//Clic sur la case en haut du joueur
			Scenario.joueur.deplacer('h');
		}
		Scenario.jeu();		//Nouveau tour de jeu, attente d'une nouvelle action
	}

	public void mouseEntered(java.awt.event.MouseEvent e) {
		
	}

	public void mouseExited(java.awt.event.MouseEvent e) {
		
	}

	public void mousePressed(java.awt.event.MouseEvent e) {
		
	}

	public void mouseReleased(java.awt.event.MouseEvent e) {
		
	}

}
