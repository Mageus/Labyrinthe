package View;

import java.awt.event.*;

import Controller.Scenario;

public class ActionDetruire implements ActionListener {
	
	public int id;
	
	public ActionDetruire() {
		super();
	}
	
	public ActionDetruire(int n) {
		super();
		id=n;
	}

	public void actionPerformed(ActionEvent arg0) {
		Scenario.joueur.detruire(id);
		Scenario.jeu();
	}

}
