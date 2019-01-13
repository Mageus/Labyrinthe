package View;

import java.awt.event.*;

import Controller.Scenario;

public class ActionLacher implements ActionListener {
	
	public int id;
	
	public ActionLacher() {
		super();
	}
	
	public ActionLacher(int n) {
		super();
		id=n;
	}

	public void actionPerformed(ActionEvent arg0) {
		Scenario.joueur.lacher(id);
		Scenario.jeu();
	}

}
