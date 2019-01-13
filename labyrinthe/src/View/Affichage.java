package View;
import Model.*;
import Controller.*;
import javax.swing.*;
import java.awt.*;

public class Affichage
{	
	public static JFrame jf_jeu;
	public static JFrame jf_inv;
	public static Panneau_jeu p_jeu;
	
	public static void console() {
		effacerConsole();
		for(int i=0; i<Scenario.cloisonsH.length; i++) {
			for(int j=0; j<Scenario.cloisonsH[0].length; j++) {
				if(Scenario.cloisonsH[i][j].getClass().getSimpleName().equals("Porte")) {
					System.out.print("  ");
				}
				else {
					System.out.print(" -");
				}
			}
			System.out.println();
			if(i<Scenario.cloisonsV.length) {
				for(int j=0; j<Scenario.cloisonsV[0].length; j++) {
					if(Scenario.cloisonsV[i][j].getClass().getSimpleName().equals("Porte")) {
						System.out.print(" ");
					}
					else {
						System.out.print("|");
					}
					if(j<Scenario.pieces[0].length) {
						if(!Scenario.pieces[i][j].accessible) {
							System.out.print("X");
						}
						else if(Scenario.pieces[i][j].objet!=null) {
							System.out.print(Scenario.pieces[i][j].objet.getClass().getSimpleName().charAt(0));
						}
						else if(Scenario.pieces[i][j].joueur!=null) {
							System.out.print("J");
						}
						else {
							System.out.print(" ");
						}
					}
				}
			}
			System.out.println();
		}
	}
	
	public static void effacerConsole() {
		try {
			if(System.getProperty("os.name").startsWith("Windows")) {
				Runtime.getRuntime().exec("cls.exe");
			}
			else {
			    Runtime.getRuntime().exec("clear");
			}
		} 
		catch(Exception e) {
			for(int i=0;i<100;i++)
				System.out.println();
		}
	}
	
	public static void victoire() {
		JFrame jf = new JFrame("Victoire");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setSize(200, 100);
		jf.setResizable(false);
		JPanel p = new JPanel();
		jf.setContentPane(p);
		Label l1 = new Label("Félicitations !");
		Label l2 = new Label("Vous avez trouvé le trésor !");
		p.add(l1);
		p.add(l2);
		jf.setVisible(true);
	}
	
	public static void fenetre_jeu() {
		jf_jeu = new JFrame("Labyrinthe");
		jf_jeu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension d = new Dimension(800,600);
		jf_jeu.setSize(d);
		jf_jeu.setResizable(false);
		
		p_jeu = new Panneau_jeu();
		p_jeu.setPreferredSize(d);
		
		jf_jeu.setContentPane(p_jeu);
		
		p_jeu.setFocusable(true);
		p_jeu.addKeyListener(new KeyEvent());
		p_jeu.addMouseListener(new MouseEvent());		
		
		jf_jeu.setLocationRelativeTo(null);
		jf_jeu.setVisible(true);
	}
	
	public static void update() {
		if(jf_jeu==null) {
			fenetre_jeu();
		}
		else {
			p_jeu.repaint();
		}
	}
	
}

