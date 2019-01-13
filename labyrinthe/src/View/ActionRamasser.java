package View;

import Controller.*;
import java.awt.event.*;

public class ActionRamasser implements ActionListener {

	public void actionPerformed(ActionEvent arg0) {
		Scenario.joueur.ramasser();
		Scenario.jeu();
	}
	
}