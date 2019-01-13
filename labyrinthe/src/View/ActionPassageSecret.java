package View;

import java.awt.event.*;

import Controller.Scenario;

public class ActionPassageSecret implements ActionListener {

	
	public void actionPerformed(ActionEvent arg0) {
		Scenario.joueur.passageSecret();
		Scenario.jeu();
	}
}
