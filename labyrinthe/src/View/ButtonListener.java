package View;

import java.awt.Frame;
import java.awt.event.*;

import javax.swing.JFrame;

import Controller.Scenario;

public class ButtonListener implements ActionListener {
	
	public int id;
	public String action;
	
	public ButtonListener() {
		super();
	}
	
	public ButtonListener(String s) {
		super();
		action=s;
	}
	
	public ButtonListener(int n, String s) {
		super();
		action=s;
		id=n;
	}

	public void actionPerformed(ActionEvent arg0) {
		if(action.equals("detruire")) {
			Scenario.joueur.detruire(id);
			Scenario.jeu();
		}
		else if(action.equals("lacher")) {
			Scenario.joueur.lacher(id);
			Scenario.jeu();
		}
		else if(action.equals("utiliser")) {
			Scenario.joueur.utiliser(id);
			Scenario.jeu();
		}
		else if(action.equals("passage")) {
			Scenario.joueur.passageSecret();
			Scenario.jeu();
		}
		else if(action.equals("ramasser")) {
			Scenario.joueur.ramasser();
			Scenario.jeu();
		}
		else if(action.equals("fuir")) {
			Affichage.jf_combat.dispose();
			Affichage.jf_jeu.setEnabled(true);
			Affichage.jf_inv.setEnabled(true);
			Affichage.p_inv.requestFocus();
			Affichage.p_jeu.requestFocus();
			Scenario.joueur.fuir();
			Scenario.jeu();
		}
		else if(action.equals("pierre")) {
			Scenario.joueur.tour_combat('p');
		}
		else if(action.equals("feuille")) {
			Scenario.joueur.tour_combat('f');
		}
		else if(action.equals("ciseaux")) {
			Scenario.joueur.tour_combat('c');
		}
		else if(action.equals("close")) {
			Affichage.combat_en_cours=false;
			Affichage.jf_combat.dispose();
			Affichage.jf_jeu.setEnabled(true);
			Affichage.jf_inv.setEnabled(true);
			Affichage.p_inv.requestFocus();
			Affichage.p_jeu.requestFocus();
			Affichage.first_turn=true;
			Affichage.gagne_combat=0;
		}
		else if(action.equals("reset")) {
			for(Frame frame : JFrame.getFrames()) {
				frame.dispose();
			}
			Affichage.jf_jeu=null;
			Affichage.jf_inv=null;
			Affichage.jf_combat=null;
			Scenario.init();
			Scenario.jeu();
		}
		else if(action.equals("exit")) {
			System.exit(0);
		}
	}

}