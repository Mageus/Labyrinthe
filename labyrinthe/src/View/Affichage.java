package View;
import Model.*;
import Controller.*;
import javax.swing.*;
import java.awt.*;

public class Affichage
{	
	public static JFrame jf_jeu;
	public static JFrame jf_inv;
	public static JFrame jf_combat;
	
	public static Panneau_jeu p_jeu;
	public static JPanel p_inv;
	public static JPanel p_combat;
	
	public static boolean fini=false;
	
	public static boolean combat_en_cours=false;
	public static boolean first_turn=true;
	public static int gagne_combat;		//0 : égalité - 1 : victoire joueur - 2 : victoire monstre
	public static String last_move_j;
	public static String last_move_m;
	
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
			jf.setSize(350, 100);
			jf.setResizable(false);
			JPanel p = new JPanel();
			p.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			jf.setContentPane(p);
			Label l = new Label("Félicitations ! Vous avez trouvé le trésor !");
			JButton reset = new JButton("Nouvelle partie");
			reset.addActionListener(new ButtonListener("reset"));
			JButton quitter = new JButton("Quitter");
			quitter.addActionListener(new ButtonListener("exit"));
			c.gridwidth = 3;
			c.gridx = 0;
			c.gridy = 0;
			p.add(l,c);
			c.gridwidth=1;
			c.gridy = 1;
			p.add(reset,c);
			c.gridx = 2;
			p.add(quitter,c);
			jf.setLocationRelativeTo(null);
			jf.setVisible(true);;
		}
	}
	
	public static void defaite() {
		if(!fini) {
			fini=true;
			p_jeu.setFocusable(false);
			JFrame jf = new JFrame("Défaite");
			jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jf.setSize(350, 100);
			jf.setResizable(false);
			JPanel p = new JPanel();
			p.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			jf.setContentPane(p);
			Label l = new Label("Dommage ! Le labyrinthe a eu raison de vous.");
			JButton reset = new JButton("Nouvelle partie");
			reset.addActionListener(new ButtonListener("reset"));
			JButton quitter = new JButton("Quitter");
			quitter.addActionListener(new ButtonListener("exit"));
			c.gridwidth = 3;
			c.gridx = 0;
			c.gridy = 0;
			p.add(l,c);
			c.gridwidth=1;
			c.gridy = 1;
			p.add(reset,c);
			c.gridx = 2;
			p.add(quitter,c);
			jf.setLocationRelativeTo(null);
			jf.setVisible(true);
		}
	}
	
	public static void fenetre_jeu() {
		jf_jeu = new JFrame("Labyrinthe");
		jf_jeu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension d = new Dimension(1200,900);
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
		Dimension d = new Dimension(350,300);
		jf_inv.setSize(d);
		jf_inv.setResizable(true);
		
		update_inv();
		
		jf_inv.setVisible(true);	
	}
	
	public static void update_inv() {
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
				JLabel l_objet = new JLabel();
				if(Scenario.joueur.objets.get(i).getClass().getSimpleName().equals("Medicament")) {
					l_objet.setText("Potion    ");
				}
				else if(Scenario.joueur.objets.get(i).getClass().getSimpleName().equals("Nourriture")) {
					l_objet.setText("Carotte    ");
				}
				else if(Scenario.joueur.objets.get(i).getClass().getSimpleName().equals("Clef")) {
					l_objet.setText("Clé    ");
				}
				else if(Scenario.joueur.objets.get(i).getClass().getSimpleName().equals("Tresor")) {
					l_objet.setText("Trésor    ");
				}
				
				c.gridx=0;
				objets.add(l_objet,c);
				JButton b_utiliser = new JButton("Utiliser");
				b_utiliser.addActionListener(new ButtonListener(i,"utiliser"));
				JButton b_lacher = new JButton("Lâcher");
				b_lacher.addActionListener(new ButtonListener(i,"lacher"));
				JButton b_detruire = new JButton("Détruire");
				b_detruire.addActionListener(new ButtonListener(i,"detruire"));
				
				c.gridx=1;
				objets.add(b_utiliser,c);
				c.gridx=2;
				objets.add(b_lacher,c);
				c.gridx=3;
				objets.add(b_detruire,c);
			}
		}
		
		JButton b_ramasser = new JButton("Ramasser");
		b_ramasser.addActionListener(new ButtonListener("ramasser"));
		JButton b_passage = new JButton("Passage secret");
		b_passage.addActionListener(new ButtonListener("passage"));
		actions.add(b_ramasser);
		actions.add(b_passage);
		
		
		p_inv.add(stats);
		p_inv.add(objets);
		p_inv.add(actions);
		
		jf_inv.setContentPane(p_inv);
		
	}
	
	public static void combat() {
		jf_jeu.setEnabled(false);
		jf_inv.setEnabled(false);
		combat_en_cours = true;
		jf_combat = new JFrame("Combat");
		jf_combat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension d = new Dimension(300,200);
		jf_combat.setSize(d);
		jf_combat.setResizable(false);
		
		update_combat();
		
		jf_combat.setLocationRelativeTo(null);
		jf_combat.setVisible(true);
	}
	
	public static void update_combat() {
		p_combat = new JPanel();
		p_combat.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridy=0;
		
		JButton b_pierre = new JButton("Pierre");
		b_pierre.addActionListener(new ButtonListener("pierre"));
		JButton b_feuille = new JButton("Feuille");
		b_feuille.addActionListener(new ButtonListener("feuille"));
		JButton b_ciseaux = new JButton("Ciseaux");
		b_ciseaux.addActionListener(new ButtonListener("ciseaux"));
		
		c.gridx=0;
		p_combat.add(b_pierre,c);
		c.gridx=1;
		p_combat.add(b_feuille,c);
		c.gridx=2;
		p_combat.add(b_ciseaux,c);
		
		if(!first_turn) {
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridwidth=3;
			c.gridx=0;
			JLabel j_move = new JLabel("Vous avez joué "+last_move_j+".");
			JLabel m_move = new JLabel("L'ennemi a joué "+last_move_m+".");
			c.gridy=1;
			p_combat.add(j_move,c);
			c.gridy=2;
			p_combat.add(m_move,c);
			JLabel resu = new JLabel();
			c.gridy=3;
			if(gagne_combat==1) {
				b_pierre.setEnabled(false);
				b_feuille.setEnabled(false);
				b_ciseaux.setEnabled(false);
				resu.setText("Vous avez gagné le combat !");
				p_combat.add(resu,c);
				JButton close = new JButton("OK");
				close.addActionListener(new ButtonListener("close"));
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx=0;
				c.gridy=4;
				p_combat.add(close,c);
			}
			else if(gagne_combat==2) {
				resu.setText("Vous perdez 1 point de vie.");
				p_combat.add(resu,c);
			}
		}
		if(gagne_combat!=1) {
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridwidth=3;
			c.gridx=0;
			c.gridy++;
			JButton b_fuir = new JButton("Fuir");
			b_fuir.addActionListener(new ButtonListener("fuir"));
			p_combat.add(b_fuir,c);
		}
		
		jf_combat.setContentPane(p_combat);
	}
	
	public static void update() {
		if(jf_jeu==null && jf_inv==null) {
			fenetre_jeu();
			fenetre_inv();
		}
		else {			
			p_jeu.repaint();
			update_inv();
			jf_inv.validate();
		}
		if(!combat_en_cours) {
			p_jeu.requestFocus();
		}
	}
	
}

