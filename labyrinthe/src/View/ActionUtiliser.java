package View;

import java.awt.event.*;

import Controller.Scenario;

public class ActionUtiliser implements ActionListener {

	public int id;
	
	public ActionUtiliser() {
		super();
	}
	
	public ActionUtiliser(int n) {
		super();
		id=n;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		Scenario.joueur.utiliser(id);
		Scenario.jeu();
	}
}
