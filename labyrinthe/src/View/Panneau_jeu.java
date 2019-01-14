package View;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

import Controller.Scenario;

import java.awt.*;

public class Panneau_jeu extends JPanel {
	Image carotte, clef, cuisinier, infirmiere, lapin, potion, renard, terrier, tresor; 
	
	Panneau_jeu(){
		super();
		try {
			carotte = ImageIO.read(new File("carotte.png"));
			clef = ImageIO.read(new File("clef1.png"));
			cuisinier = ImageIO.read(new File("cuisinier3.png"));
			infirmiere = ImageIO.read(new File("infirmiere.png"));
			lapin = ImageIO.read(new File("lapin2.png"));
			potion = ImageIO.read(new File("potion.png"));
			renard = ImageIO.read(new File("renard.png"));
			terrier = ImageIO.read(new File("terrier1.png"));
			tresor = ImageIO.read(new File("tresor.png"));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
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
					if(Scenario.cloisonsH[i][j].locked==true) {
						g.fillRect((j*w/10), (i*h/7)-1, (w/30), 2);
						g.setColor(Color.RED);
						g.fillRect((j*w/10)+(w/30), (i*h/7)-1, (w/30), 2);
						g.setColor(Color.black);
						g.fillRect((j*w/10)+(2*w/30), (i*h/7)-1, (w/30), 2);
					}
					else {
						g.fillRect((j*w/10), (i*h/7)-1, (w/30), 2);
						g.setColor(Color.WHITE);
						g.fillRect((j*w/10)+(w/30), (i*h/7)-1, (w/30), 2);
						g.setColor(Color.black);
						g.fillRect((j*w/10)+(2*w/30), (i*h/7)-1, (w/30), 2);
					}
				}
				else {
					g.fillRect((j*w/10), (i*h/7)-1, w/10, 2);
				}				
			}
			System.out.println();
			if(i<Scenario.cloisonsV.length) {
				for(int j=0; j<Scenario.cloisonsV[0].length; j++) {
					if(Scenario.cloisonsV[i][j].getClass().getSimpleName().equals("Porte") && (Scenario.pieces[i][j].visitee || Scenario.pieces[i][j-1].visitee)) {
						if(Scenario.cloisonsV[i][j].locked==true) {
							g.fillRect((j*w/10)-1, (i*h/7), 2, (h/21));
							g.setColor(Color.RED);
							g.fillRect((j*w/10)-1, (i*h/7)+(h/21), 2, (h/21));
							g.setColor(Color.black);
							g.fillRect((j*w/10)-1, (i*h/7)+(2*h/21), 2, (h/21));
						}
						else {							
							g.fillRect((j*w/10)-1, (i*h/7), 2, (h/21));
							g.setColor(Color.WHITE);
							g.fillRect((j*w/10)-1, (i*h/7)+(h/21), 2, (h/21));
							g.setColor(Color.black);
							g.fillRect((j*w/10)-1, (i*h/7)+(2*h/21), 2, (h/21));
						}
					}
					else {
						g.fillRect((j*w/10)-1, (i*h/7), 2, h/7);
					}
					if(j<Scenario.pieces[0].length && Scenario.pieces[i][j].visitee) {						
						if(Scenario.pieces[i][j].objet!=null) {
							if(Scenario.pieces[i][j].objet.getClass().getSimpleName().equals("Medicament")) {
								g.drawImage(potion,(j*w/10)+1,(i*h/7)+1,(w/30),(h/21),this);
							}
							else if(Scenario.pieces[i][j].objet.getClass().getSimpleName().equals("Nourriture")) {
								g.drawImage(carotte,(j*w/10)+1,(i*h/7)+1,(w/30),(h/21),this);
							}
							else if(Scenario.pieces[i][j].objet.getClass().getSimpleName().equals("Clef")) {
								g.drawImage(clef,(j*w/10)+1,(i*h/7)+1,(w/30),(h/21),this);
							}
							else if(Scenario.pieces[i][j].objet.getClass().getSimpleName().equals("Tresor")) {
								g.drawImage(tresor,(j*w/10)+1,(i*h/7)+1,(w/30),(h/21),this);
							}
						}
						if(Scenario.pieces[i][j].joueur!=null) {
							g.drawImage(lapin,(j*w/10)+(w/30),(i*h/7)+(h/21),(w/30),(h/21),this);
						}
						if(Scenario.pieces[i][j].getClass().getSimpleName().equals("PieceS")) {
							g.drawImage(terrier,(j*w/10)+1,(i*h/7)+(2*h/21)-1,(w/30),(h/21),this);
						}
						if(Scenario.pieces[i][j].pnj!=null) {
							if(Scenario.pieces[i][j].pnj.getClass().getSimpleName().equals("Monstre")) {
								g.drawImage(renard,(j*w/10)+(2*w/30)-1,(i*h/7)+1,(w/30),(h/21),this);
							}
							else if(Scenario.pieces[i][j].pnj.getClass().getSimpleName().equals("Medecin")) {
								g.drawImage(infirmiere,(j*w/10)+(2*w/30)-1,(i*h/7)+1,(w/30),(h/21),this);
							}
							else if(Scenario.pieces[i][j].pnj.getClass().getSimpleName().equals("Cuisinier")) {
								g.drawImage(cuisinier,(j*w/10)+(2*w/30)-1,(i*h/7)+1,(w/30),(h/21),this);
							}														
						}						
					}
				}
			}
		}
	}
}
