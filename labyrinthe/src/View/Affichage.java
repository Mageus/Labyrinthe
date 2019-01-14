package View;
import Model.*;
import Controller.*;
import javax.swing.*;
import java.awt.*;

public class Affichage
{	
	public static JFrame jf_jeu;		//Fenêtre principale de l'affichage du jeu (labyrinthe)
	public static JFrame jf_inv;		//Fenêtre de l'affichage de l'inventaire
	public static JFrame jf_combat;		//Fenêtre de l'affichage des combats
	
	public static Panneau_jeu p_jeu;	//Panneau d'affichage du jeu
	public static JPanel p_inv;			//Panneau d'affichage de l'inventaire
	public static JPanel p_combat;		//Panneau d'affichage des combats
	
	public static boolean fini=false;	//True si la partie est terminée
	
	public static boolean combat_en_cours=false;	//True si un combat est en cours
	public static boolean first_turn=true;		//True pour le premier tour d'un combat
	public static int gagne_combat;		//issue d'une manche de combat, 0 : égalité - 1 : victoire joueur - 2 : victoire monstre
	public static String last_move_j;	//Dernier coup joué lors du combat par le joueur
	public static String last_move_m;	//Dernier coup joué lors du combat par le monstre
	
	//Methode obsolète, affichage console
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
	
	//Méthode obsolète, effacer la console
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
	
	//Affiche la fenêtre de victoire
	public static void victoire() {
		if(!fini) {		//Ne l'affiche qu'une seule fois, lorsque la partie n'est pas encore considérée comme finie
			fini=true;	//La partie est finie.
			jf_jeu.setEnabled(false);	//On désactive les autres fenêtres du jeu
			jf_inv.setEnabled(false);
			if(combat_en_cours) {
				jf_combat.setEnabled(false);
			}
			JFrame jf = new JFrame("Victoire");
			jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jf.setSize(350, 100);
			jf.setResizable(false);
			JPanel p = new JPanel();
			p.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			jf.setContentPane(p);
			Label l = new Label("Félicitations ! Vous avez trouvé le trésor !");
			JButton reset = new JButton("Nouvelle partie");		//Bouton pour recommencer une partie
			reset.addActionListener(new ButtonListener("reset"));
			JButton quitter = new JButton("Quitter");			//Bouton pour quitter le jeu
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
	
	//Affiche la fenêtre de défaitre
	public static void defaite() {
		if(!fini) {		//Ne l'affiche qu'une seule fois, lorsque la partie n'est pas encore considérée comme finie
			fini=true;	//La partie est finie.
			jf_jeu.setEnabled(false);	//On désactive les autres fenêtres du jeu
			jf_inv.setEnabled(false);
			if(combat_en_cours) {
				jf_combat.setEnabled(false);
			}
			JFrame jf = new JFrame("Défaite");
			jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jf.setSize(350, 100);
			jf.setResizable(false);
			JPanel p = new JPanel();
			p.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			jf.setContentPane(p);
			Label l = new Label("Dommage ! Le labyrinthe a eu raison de vous.");
			JButton reset = new JButton("Nouvelle partie");		//Bouton pour recommencer une partie
			reset.addActionListener(new ButtonListener("reset"));
			JButton quitter = new JButton("Quitter");			//Bouton pour quitter le jeu
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
	
	//Affichage de la fenêtre principale du jeu (le labyrinthe)
	public static void fenetre_jeu() {
		jf_jeu = new JFrame("Labyrinthe");
		jf_jeu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension d = new Dimension(1200,900);
		jf_jeu.setSize(d);
		jf_jeu.setResizable(false);
		
		p_jeu = new Panneau_jeu();		//Définition du JPanel qui affichera le labyrinthe, voir Panneau_jeu pour plus d'informations
		p_jeu.setPreferredSize(d);
		
		jf_jeu.setContentPane(p_jeu);
		
		p_jeu.setFocusable(true);
		p_jeu.addKeyListener(new KeyEvent());
		p_jeu.addMouseListener(new MouseEvent());		
		
		jf_jeu.setLocationRelativeTo(null);
		jf_jeu.setVisible(true);
	}
	
	//Affichage de la fenêtre d'inventaire
	public static void fenetre_inv() {
		jf_inv = new JFrame("Inventaire");
		jf_inv.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension d = new Dimension(350,300);
		jf_inv.setSize(d);
		jf_inv.setResizable(true);
		
		update_inv();
		
		jf_inv.setVisible(true);	
	}
	
	//Mise à jour de la fenêtre d'inventaire
	public static void update_inv() {
		p_inv = new JPanel();
		
		JPanel stats = new JPanel();		//Panneau des statistiques (Pdv/Energie)
		JPanel objets = new JPanel();		//Panneau des objets contenus dans l'inventaire du joueur
		JPanel actions = new JPanel();		//Panneau des actions effectuables par le joueur (Ramasser un objet / Emprunter un passage secret)
		
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
	
	//Affichage de la fenêtre de combat
	public static void combat() {
		jf_jeu.setEnabled(false);		//On désactive les fenêtres de jeu et d'inventaire: la fenêtre de combat sera donc au premier plan
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
	
	//Mise à jour de la fenêtre de combat
	public static void update_combat() {
		p_combat = new JPanel();
		p_combat.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridy=0;
		
		//Boutons des coups possibles
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
		
		if(!first_turn) {		//Affiche les derniers coups effectués à partir du 2e tour
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
			if(gagne_combat==1) {		//Victoire du joueur
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
			else if(gagne_combat==2) {	//Victoire du monstre : le joueur perd un pv (voir Joueur.tour_combat)
				resu.setText("Vous perdez 1 point de vie.");
				p_combat.add(resu,c);
			}
		}
		if(gagne_combat!=1) {		//Bouton "Fuir" disponible tant que le joueur n'a pas gagné le combat
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
	
	//Mise à jour de l'affichage du jeu
	public static void update() {
		if(jf_jeu==null && jf_inv==null) {	//Si les fenêtres principales ne sont pas disponibles (si le jeu vient de commencer), les créer
			fenetre_jeu();
			fenetre_inv();
		}
		else {		//Sinon, on met à jour les fenêtre principales
			p_jeu.repaint();
			update_inv();
			jf_inv.validate();
		}
		if(!combat_en_cours) {	//S'il n'y a pas de combat en cours, la fenêtre au premier plan est toujours celle qui affiche le labyrinthe
			p_jeu.requestFocus();
		}
	}
	
}

