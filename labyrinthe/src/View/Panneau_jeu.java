package View;
import javax.swing.*;

import Controller.Scenario;

import java.awt.*;

public class Panneau_jeu extends JPanel {
	public void paintComponent(Graphics g) {		
		int w = getWidth();
		int h = getHeight();
		g.clearRect(0, 0, w, h);
		g.setColor(Color.black);
		for(int i=0; i<Scenario.cloisonsH.length; i++) {
			for(int j=0; j<Scenario.cloisonsH[0].length; j++) {
				if(i<Scenario.cloisonsV.length && !Scenario.pieces[i][j].visitee) {
					g.setColor(new Color(128,128,128));
					g.fillRect((j*w/10)+1, (i*h/7)+1, (w/10), (h/7));
					g.setColor(Color.black);
				}
				if(Scenario.cloisonsH[i][j].getClass().getSimpleName().equals("Porte") && (Scenario.pieces[i][j].visitee || Scenario.pieces[i-1][j].visitee)) {
					g.fillRect((j*w/10), (i*h/7)-1, (w/30), 2);
					g.fillRect((j*w/10)+(2*w/30), (i*h/7)-1, (w/30), 2);
				}
				else {
					g.fillRect((j*w/10), (i*h/7)-1, w/10, 2);
				}				
			}
			System.out.println();
			if(i<Scenario.cloisonsV.length) {
				for(int j=0; j<Scenario.cloisonsV[0].length; j++) {
					if(Scenario.cloisonsV[i][j].getClass().getSimpleName().equals("Porte") && (Scenario.pieces[i][j].visitee || Scenario.pieces[i][j-1].visitee)) {
						g.fillRect((j*w/10)-1, (i*h/7), 2, (h/21));
						g.fillRect((j*w/10)-1, (i*h/7)+(2*h/21), 2, (h/21));
					}
					else {
						g.fillRect((j*w/10)-1, (i*h/7), 2, h/7);
					}
					if(j<Scenario.pieces[0].length && Scenario.pieces[i][j].visitee) {						
						if(Scenario.pieces[i][j].objet!=null) {
							char c = Scenario.pieces[i][j].objet.getClass().getSimpleName().charAt(0);
							g.drawString(String.valueOf(c), (j*w/10)+10, (i*h/7)+20);
						}
						if(Scenario.pieces[i][j].joueur!=null) {
							g.drawString("J", (j*w/10)+40, (i*h/7)+40);
						}
						if(Scenario.pieces[i][j].getClass().getSimpleName().equals("PieceS")) {
							g.drawString("P", (j*w/10)+10, (i*h/7)+70);
						}
						if(Scenario.pieces[i][j].pnj!=null) {
							char c = Scenario.pieces[i][j].pnj.getClass().getSimpleName().charAt(0);
							g.drawString(String.valueOf(c), (j*w/10)+60, (i*h/7)+20);							
						}						
					}
				}
			}
		}
	}
}
