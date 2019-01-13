package View;
import Model.*;
import Controller.*;
import javax.swing.*;
import java.awt.*;

public class Affichage
{	
	public static JFrame jf_jeu;
	public static JFrame jf_inv;
	public static JPanel p_inv;
	public static Panneau_jeu p_jeu;
	public static boolean fini=false;
	
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
		if(!fini) {
			fini=true;
			p_jeu.setFocusable(false);
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
			jf.setLocationRelativeTo(null);
			jf.setVisible(true);
		}
	}
	
	public static void defaite() {
		if(!fini) {
			fini=true;
			p_jeu.setFocusable(false);
			JFrame jf = new JFrame("Défaite");
			jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jf.setSize(200, 100);
			jf.setResizable(false);
			JPanel p = new JPanel();
			jf.setContentPane(p);
			Label l1 = new Label("Dommage !");
			Label l2 = new Label("Le labyrinthe a eu raison de vous.");
			p.add(l1);
			p.add(l2);
			jf.setLocationRelativeTo(null);
			jf.setVisible(true);
		}
	}
	
	public static void fenetre_jeu() {
		jf_jeu = new JFrame("Labyrinthe");
		jf_jeu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension d = new Dimension(1024,768);
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
	
	public static void fenetre_inv() {
		jf_inv = new JFrame("Inventaire");
		jf_inv.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension d = new Dimension(350,600);
		jf_inv.setSize(d);
		jf_inv.setResizable(true);
		
		maj_inv();
		
		jf_inv.setVisible(true);	
	}
	
	public static void maj_inv() {
		p_inv = new JPanel();
		
		JPanel stats = new JPanel();
		JPanel objets = new JPanel();
		JPanel actions = new JPanel();
		
		stats.setLayout(new BorderLayout());
		JLabel pdv = new JLabel();
		pdv.setText("Points de vie : "+Scenario.joueur.Pdv);
		stats.add(pdv,BorderLayout.NORTH);
		JLabel energie = new JLabel();
		energie.setText("Energie : "+Scenario.joueur.Energie);
		stats.add(energie,BorderLayout.SOUTH);
		
		objets.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		for(int i=0; i<Scenario.joueur.objets.size(); i++) {
			if(Scenario.joueur.objets.get(i)!=null) {
				c.gridy=i;
				JLabel l_objet = new JLabel(Scenario.joueur.objets.get(i).getClass().getSimpleName());
				c.gridx=0;
				objets.add(l_objet,c);
				JButton b_utiliser = new JButton("Utiliser");
				b_utiliser.addActionListener(new ActionUtiliser(i));
				JButton b_lacher = new JButton("Lâcher");
				b_lacher.addActionListener(new ActionLacher(i));
				JButton b_detruire = new JButton("Détruire");
				b_detruire.addActionListener(new ActionDetruire(i));
				
				c.gridx=1;
				objets.add(b_utiliser,c);
				c.gridx=2;
				objets.add(b_lacher,c);
				c.gridx=3;
				objets.add(b_detruire,c);
			}
		}
		
		JButton b_ramasser = new JButton("Ramasser");
		b_ramasser.addActionListener(new ActionRamasser());
		JButton b_passage = new JButton("Passage secret");
		b_passage.addActionListener(new ActionPassageSecret());
		actions.add(b_ramasser);
		actions.add(b_passage);
		
		
		p_inv.add(stats);
		p_inv.add(objets);
		p_inv.add(actions);
		
		jf_inv.setContentPane(p_inv);
		
	}
	
	public static void update() {
		if(jf_jeu==null) {
			fenetre_jeu();
			fenetre_inv();
		}
		else {			
			p_jeu.repaint();
			maj_inv();
			jf_inv.validate();
		}
	}
	
}

