package View;

import java.awt.Frame;
import java.awt.event.*;

import javax.swing.JFrame;

import Controller.Scenario;

public class ButtonListener implements ActionListener {
	
	public int id;				//id de l'objet sur lequel effectuer une action
	public String action;		//action à effectuer
	
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

	//Action effectuée lors d'un clic sur un bouton
	public void actionPerformed(ActionEvent arg0) {
		if(action.equals("detruire")) {					//Détruire un objet
			Scenario.joueur.detruire(id);
			Scenario.jeu();		//Nouveau tour de jeu, attente d'une nouvelle action
		}
		else if(action.equals("lacher")) {				//Lâcher un objet
			Scenario.joueur.lacher(id);
			Scenario.jeu();		//Nouveau tour de jeu, attente d'une nouvelle action
		}
		else if(action.equals("utiliser")) {			//Utiliser un objet
			Scenario.joueur.utiliser(id);
			Scenario.jeu();		//Nouveau tour de jeu, attente d'une nouvelle action
		}
		else if(action.equals("passage")) {				//Emprunter un passage secret
			Scenario.joueur.passageSecret();
			Scenario.jeu();		//Nouveau tour de jeu, attente d'une nouvelle action
		}
		else if(action.equals("ramasser")) {			//Ramasser un objet
			Scenario.joueur.ramasser();
			Scenario.jeu();		//Nouveau tour de jeu, attente d'une nouvelle action
		}
		else if(action.equals("fuir")) {				//Fuir un combat
			Affichage.jf_combat.dispose();
			Affichage.jf_jeu.setEnabled(true);
			Affichage.jf_inv.setEnabled(true);
			Affichage.p_inv.requestFocus();
			Affichage.p_jeu.requestFocus();
			Scenario.joueur.fuir();
			Scenario.jeu();		//Nouveau tour de jeu, attente d'une nouvelle action
		}
		else if(action.equals("pierre")) {				//Jouer "pierre" lors d'un combat
			Scenario.joueur.tour_combat('p');
		}
		else if(action.equals("feuille")) {				//Jouer "feuille" lors d'un combat
			Scenario.joueur.tour_combat('f');
		}
		else if(action.equals("ciseaux")) {				//Jouer "ciseaux" lors d'un combat
			Scenario.joueur.tour_combat('c');
		}
		else if(action.equals("close")) {				//Fermer la fenêtre de combat (en cas de de victoire du joueur ou de fuite)
			Affichage.combat_en_cours=false;
			Affichage.jf_combat.dispose();
			Affichage.jf_jeu.setEnabled(true);		//On réactive les fenêtres de jeu et d'inventaire
			Affichage.jf_inv.setEnabled(true);
			Affichage.p_inv.requestFocus();			//On passe la fenêtre d'inventaire au second plan et celle de jeu au premier plan
			Affichage.p_jeu.requestFocus();
			Affichage.first_turn=true;				//Le prochain combat commencera par son premier tour
			Affichage.gagne_combat=0;				//gagne_combat est réinitialisé
		}
		else if(action.equals("reset")) {				//Recommencer une partie
			for(Frame frame : JFrame.getFrames()) {	//On ferme toutes les fenêtres du jeu
				frame.dispose();
			}
			Affichage.jf_jeu=null;		//Réinitialisation des JFrame
			Affichage.jf_inv=null;
			Affichage.jf_combat=null;
			Scenario.init();		//Redémarrage du jeu
			Scenario.jeu();
		}
		else if(action.equals("exit")) {				//Quitter le jeu
			System.exit(0);
		}
	}

}