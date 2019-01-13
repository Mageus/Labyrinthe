package View;
import java.awt.event.*;

import Controller.Scenario;

public class MouseEvent implements MouseListener {

	public void mouseClicked(java.awt.event.MouseEvent e) {
		int jx=Scenario.joueur.x;
		int jy=Scenario.joueur.y;
		int x=e.getX();
		int y=e.getY();
		int w=Affichage.p_jeu.getWidth();
		int h=Affichage.p_jeu.getHeight();
		if(x>((jx*w/10)-(w/10)) && x<(jx*w/10) && y>(jy*h/7) && y<((jy*h/7)+(h/7))) {
			Scenario.joueur.deplacer('g');
		}
		else if(x>((jx*w/10)+(w/10)) && x<((jx*w/10)+(2*w/10)) && y>(jy*h/7) && y<((jy*h/7)+(h/7))) {
			Scenario.joueur.deplacer('d');
		}
		else if(x>(jx*w/10) && x<((jx*w/10)+(w/10)) && y>((jy*h/7)+(h/7)) && y<((jy*h/7)+(2*h/7))) {
			Scenario.joueur.deplacer('b');
		}
		else if(x>(jx*w/10) && x<((jx*w/10)+(w/10)) && y>(jy*h/7)-(h/7) && y<(jy*h/7)) {
			Scenario.joueur.deplacer('h');
		}
		Scenario.jeu();
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
