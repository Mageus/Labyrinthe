package Controller;
import View.*;
import Model.*;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import java.io.File;


public class Scenario
{
	
	public static int spawn_x;		//Coordonn�es du point de d�part du joueur
	public static int spawn_y;
	public static int tresor_x;		//Coordonn�es du tr�sor
	public static int tresor_y;
	
	public static boolean porte_fermee; 	//Si le tr�sor est derri�re une porte ferm�e ou non
	
	public static PieceS p1;	//Les deux pi�ces qui seront reli�es par un passage secret
	public static PieceS p2;
	
	public static Piece[][] pieces;			//Piece[Ordonn�e][Abscisse]
	
	public static Cloison[][] cloisonsV;		//Cloison[Ordonn�e][Abscisse] -> cloisons verticales
	public static Cloison[][] cloisonsH;		//Cloison[Ordonn�e][Abscisse] -> cloisons verticales

	public static Joueur joueur;
	
	//Initialisation du jeu
	public static void init() {
		Affichage.fini=false;
		Affichage.first_turn=true;
		pieces = new Piece[7][10];
		cloisonsV = new Cloison[7][11];
		cloisonsH = new Cloison[8][10];
		
		porte_fermee = (Math.random()>0.5);		//1/2 chance que le labyrinthe contienne une clef et une porte ferm�e
		
		for(int i=0; i<pieces.length;i++) {
			for(int j=0; j<pieces[0].length; j++) {
				pieces[i][j]=new Piece();
			}
		}
		
		//Placement du tr�sor
		Tresor tresor = new Tresor();
		tresor_x = (int)(Math.random()*pieces[0].length);
		tresor_y = (int)(Math.random()*pieces.length);
		pieces[tresor_y][tresor_x].objet=tresor;
		
		//Placement du tr�sor � une distance d'au moins 5 d�placements du tr�sor
		joueur = new Joueur();
		do{
			spawn_x = (int)(Math.random()*pieces[0].length);
			spawn_y = (int)(Math.random()*pieces.length);
		}while(spawn_x==tresor_x && spawn_y==tresor_y || distanceTrajetCourt(spawn_x, spawn_y, tresor_x, tresor_y)<5);
		joueur.x=spawn_x;
		joueur.y=spawn_y;
		pieces[spawn_y][spawn_x].joueur=joueur;
		
		int distance = distanceTrajetCourt(spawn_x, spawn_y, tresor_x, tresor_y);
		int nbAccessibles = distance*2;		//Deux fois plus de pi�ces dans le labyrinthe que la distance joueur-tr�sor
		
		String criticalPath = trajetPrincipal(nbAccessibles);
		construireTrajet(criticalPath, nbAccessibles);
		if(Math.random()<(double)0.5) {		//1/2 chance que le labyrinthe contienne un passage secret
			genererPassageSecret();
		}	
		
		//Aucune des pi�ces, � part celle de d�part, n'a �t� visit�e
		for(int i=0; i<pieces.length;i++) {
			for(int j=0; j<pieces[0].length; j++) {
				pieces[i][j].visitee=false;
			}
		}
		pieces[spawn_y][spawn_x].visitee=true;
		
		//Placement al�atoire des monstres, ni sur la pi�ce de d�part, ni sur celle du tr�sor, un seul pnj par salle
		int Nbmonstre = compterPieces()/3;		//3 fois moins d'ennemis que de pi�ces
		for(int i =0;i<Nbmonstre;i++) {
			Monstre monstre = new Monstre();
			int spawn_xx;
			int spawn_yy;
			do{
				spawn_xx = (int)(Math.random()*(pieces[0].length));
				spawn_yy = (int)(Math.random()*(pieces.length));
			}while((spawn_xx==tresor_x && spawn_yy==tresor_y) || (spawn_xx==spawn_x && spawn_yy==spawn_y) || !pieces[spawn_yy][spawn_xx].accessible || pieces[spawn_yy][spawn_xx].pnj!=null);
			monstre.x=spawn_xx;
			monstre.y=spawn_yy;
			pieces[spawn_yy][spawn_xx].pnj=monstre;
		}
		
		//Placement al�atoire des objets Medicament / Nourriture, un seul objet par salle
		Medicament medicament = new Medicament();
		int medicament_x = 0;
		int medicament_y = 0;
		Nourriture nourriture = new Nourriture();
		int nourriture_x = 0;
		int nourriture_y = 0;
		int NbObjet = compterPieces()/3;	//3 fois moins d'objets que de pi�ces
		
		for(int i =0;i<NbObjet;i++) {
			double alea = Math.random();
			
			if(alea < 0.5) {	//1/2 chance que l'objet g�n�r� soit un Medicament, sinon Nourriture 
				do{
				medicament_x = (int)(Math.random()*pieces[0].length);
				medicament_y = (int)(Math.random()*pieces.length);
				
				} while(pieces[medicament_y][medicament_x].objet != null || !pieces[medicament_y][medicament_x].accessible);
				pieces[medicament_y][medicament_x].objet=medicament;
			}
			
			else {
				do{
					nourriture_x = (int)(Math.random()*pieces[0].length);
					nourriture_y = (int)(Math.random()*pieces.length);
				} while(pieces[nourriture_y][nourriture_x].objet != null || !pieces[nourriture_y][nourriture_x].accessible);
				pieces[nourriture_y][nourriture_x].objet=nourriture;
			}
		
		}
		
		if(Math.random()>0.5) {		//1/2 chance que le labyrinthe contienne un Medecin, plac� al�atoirement comme les monstres
			Medecin medecin = new Medecin();
			int spawn_medecin_x;
			int spawn_medecin_y;
			do{
				spawn_medecin_x = (int)(Math.random()*pieces[0].length);
				spawn_medecin_y = (int)(Math.random()*pieces.length);
			}while((spawn_medecin_x == tresor_x && spawn_medecin_y==tresor_y) || (spawn_medecin_x==spawn_x && spawn_medecin_y==spawn_y) || pieces[spawn_medecin_y][spawn_medecin_x].accessible == false || pieces[spawn_medecin_y][spawn_medecin_x].pnj!=null);
			medecin.x=spawn_medecin_x;
			medecin.y=spawn_medecin_y;
			pieces[spawn_medecin_y][spawn_medecin_x].pnj=medecin;
		}
		
		if(Math.random()>0.5) {		//1/2 chance que le labyrinthe contienne un Cuisinier, plac� al�atoirement comme les monstres / le Medecin
			Cuisinier cuisinier = new Cuisinier();
			int spawn_cuisinier_x;
			int spawn_cuisinier_y;
			do{
				spawn_cuisinier_x = (int)(Math.random()*pieces[0].length);
				spawn_cuisinier_y = (int)(Math.random()*pieces.length);
			}while((spawn_cuisinier_x==tresor_x && spawn_cuisinier_y==tresor_y) || (spawn_cuisinier_x==spawn_x && spawn_cuisinier_y==spawn_y) || pieces[spawn_cuisinier_y][spawn_cuisinier_x].accessible == false || pieces[spawn_cuisinier_y][spawn_cuisinier_x].pnj!=null);
			cuisinier.x=spawn_cuisinier_x;
			cuisinier.y=spawn_cuisinier_y;
			pieces[spawn_cuisinier_y][spawn_cuisinier_x].pnj=cuisinier;
		}
		
		//Initialisation des points de vie / de l'�nergie du joueur � 10
		joueur.Pdv=10;
		joueur.Energie=10;
	}
	
	//Placement des salles contenant un passage secret
	public static void genererPassageSecret() {
		int x1, y1, x2, y2;
		do{		//Choix de la premi�re salle, pas celle du tr�sor
			x1 = (int)(Math.random()*pieces[0].length);
			y1 = (int)(Math.random()*pieces.length);
		}while((x1==tresor_x && y1==tresor_y) || !pieces[y1][x1].accessible);
		
		do{		//Choix de la seconde salle, pas celle du tr�sor ni de la premi�re contenant un passage secret
			x2 = (int)(Math.random()*pieces[0].length);
			y2 = (int)(Math.random()*pieces.length);
		}while((x2==tresor_x && y2==tresor_y) || (x1==x2 && y1==y2) || !pieces[y2][x2].accessible);
		
		p1 = new PieceS(x1,y1);
		p2 = new PieceS(x2,y2);
		p1.dest = p2;	//Liaison des deux pi�ces
		p2.dest = p1;
		pieces[y1][x1] = p1;
		pieces[y2][x2] = p2;
	}
	
	//Construction des trajets possibles dans le labyrinthe : placement des portes sur ces trajets
	public static void construireTrajet(String criticalPath, int nbAccessibles) {
		//Initialisation des murs (murs partout au d�part)
		for(int i=0; i<cloisonsH.length;i++) {
			for(int j=0; j<cloisonsH[0].length;j++) {
				cloisonsH[i][j] = new Mur();
			}
		}
		for(int i=0; i<cloisonsV.length;i++) {
			for(int j=0; j<cloisonsV[0].length;j++) {
				cloisonsV[i][j] = new Mur();
			}
		}
		
		//Construction du trajet principal (criticalPath, du joueur au tr�sor), placement des portes sur ce trajet
		pieces[spawn_y][spawn_x].accessible=true;
		pieces[tresor_y][tresor_x].accessible=true;
		int distance_cle = (int)(Math.random()*criticalPath.length());
		int x = spawn_x, y = spawn_y;
		for(int i=0; i<criticalPath.length(); i++) {
			Porte porte = new Porte();
			if(i==criticalPath.length()-1  && porte_fermee) {		//La derni�re pi�ce (i.e. celle contenant le tr�sor) sera derri�re une porte ferm�e
				porte.locked=true;
			}
			if(i==distance_cle && porte_fermee) {		//Placement de la cl� � un endroit al�atoire sur ce trajet
				pieces[y][x].objet = new Clef(); 
			}
			if(criticalPath.charAt(i)=='h' && y!=0) {
				cloisonsH[y][x]=porte;
				y--;
			}
			else if(criticalPath.charAt(i)=='b' && y!=pieces.length-1) {
				y++;
				cloisonsH[y][x]=porte;
			}
			else if(criticalPath.charAt(i)=='g' && x!=0) {
				cloisonsV[y][x]=porte;
				x--;
			}
			else if(criticalPath.charAt(i)=='d' && x!=pieces[0].length-1) {
				x++;
				cloisonsV[y][x]=porte;
			}
			pieces[y][x].accessible=true;
		}
		
		//Construction des salles suppl�mentaires plac�es al�atoirement autour du trajet principal
		while(compterPieces()!=nbAccessibles) {
			int dirAlea = (int)(Math.random()*4);
			x = (int)(Math.random()*pieces[0].length);
			y = (int)(Math.random()*pieces.length);
			if(pieces[y][x].accessible) {
				if(dirAlea==0 && y!=0 && !pieces[y-1][x].accessible) {							//Haut
					cloisonsH[y][x] = new Porte();
					y--;
				}
				else if(dirAlea==1 && y!=pieces.length-1 && !pieces[y+1][x].accessible) {		//Bas
					y++;
					cloisonsH[y][x] = new Porte();
				}
				else if(dirAlea==2 && x!=0 && !pieces[y][x-1].accessible) {						//Gauche
					cloisonsV[y][x] = new Porte();
					x--;
				}
				else if(dirAlea==3 && x!=pieces[0].length-1 && !pieces[y][x+1].accessible) {	//Droite
					x++;
					cloisonsV[y][x] = new Porte();
				}
				pieces[y][x].accessible=true;
			}
		}		
	}
	
	//Renvoie le nombre de pi�ces accessibles du labyrinthe
	public static int compterPieces() {
		int n=0;
		for(int i=0; i<pieces.length; i++) {
			for(int j=0; j<pieces[0].length; j++) {
				if(pieces[i][j].accessible) {
					n++;
				}
			}
		}
		return n;
	}
	
	//Renvoie le nombre de d�placement minimal pour aller de la case (x1,y1) � la case (x2,y2)
	public static int distanceTrajetCourt(int x1, int y1, int x2, int y2) {
		int n=0;
		while(x1!=x2 || y1!=y2) {
			n++;
			if(x1>x2) {
				x1--;
			}
			else if(x1<x2) {
				x1++;
			}
			else if(y1>y2) {
				y1--;
			}
			else if(y1<y2){
				y1++;
			}
		}
		return n;
	}
	
	//D�termine un trajet al�atoire du joueur au tr�sor
	public static String trajetPrincipal(int max) {
		//Toutes les cloisons, sauf les extr�mit�s, sont des portes afin de pouvoir s'y d�placer pour d�terminer le trajet principal
		for(int i=0; i<cloisonsH.length;i++) {
			for(int j=0; j<cloisonsH[0].length;j++) {
				if(i<cloisonsH.length-1 && i>0 && j<cloisonsH[0].length-1 && j>0) {
					cloisonsH[i][j] = new Porte();
				}
				else {
					cloisonsH[i][j] = new Mur();
				}
			}
		}
		for(int i=0; i<cloisonsV.length;i++) {
			for(int j=0; j<cloisonsV[0].length;j++) {
				if(i<cloisonsV.length-1 && i>0 && j<cloisonsV[0].length-1 && j>0) {
					cloisonsV[i][j] = new Porte();
				}
				else {
					cloisonsV[i][j] = new Mur();
				}
			}
		}
		String resu = new String();
		do{
			resu="";		//Contiendra le trajet principal sous forme de String (un caract�re pour chaque direction emprunt�e)
			pieces[joueur.y][joueur.x].joueur=null;
			joueur.x=spawn_x;
			joueur.y=spawn_y;
			pieces[spawn_y][spawn_x].joueur=joueur;
			while(resu.length()<max && (joueur.x!=tresor_x || joueur.y!=tresor_y)) {		//Choisir une nouvelle direction al�atoire jusqu'� arriver au tr�sor, recommence s'il d�passe le max de pi�ces du labyrinthe
				int dir = (int)(Math.random()*4);
				if(dir==0) {
					joueur.deplacer('h');
					resu=resu+"h";
				}
				else if(dir==1) {
					joueur.deplacer('b');
					resu=resu+"b";
				}
				else if(dir==2) {
					joueur.deplacer('g');
					resu=resu+"g";
				}
				else if(dir==3) {
					joueur.deplacer('d');
					resu=resu+"d";
				}
			}
		}while(joueur.x!=tresor_x || joueur.y!=tresor_y);
		pieces[joueur.y][joueur.x].joueur=null;		//On replace le joueur � son point de d�part
		joueur.x=spawn_x;
		joueur.y=spawn_y;
		pieces[spawn_y][spawn_x].joueur=joueur;
		return resu;
	}
	
	//Boucle principale de jeu
	public static void jeu() {
		//Affichage.console();
		Affichage.update();		//Met � jour l'affichage du jeu
		if(victoire()) {		//En cas de victoire, on affiche l'�cran de victoire et on joue un son
			Affichage.victoire();
			URL url = null;
			try {
				url = new File("victoire.wav").toURI().toURL();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			AudioClip clip = Applet.newAudioClip(url);
			clip.play();
		}
		else if(defaite()) {	//En cas de d�faite, on affiche l'�cran de d�faite et on joue un son
			Affichage.defaite();
			URL url = null;
			try {
				url = new File("defaite.wav").toURI().toURL();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			AudioClip clip = Applet.newAudioClip(url);
			clip.play();
		}
	}
	
	//V�rifie si le joueur a gagn�, i.e. s'il a ramass� le tr�sor
	public static boolean victoire() {
		for(int i=0; i<joueur.objets.size(); i++) {
			if(joueur.objets.get(i)!=null) {
				if(joueur.objets.get(i).getClass().getSimpleName().equals("Tresor")) {
					return true;
				}
			}
		}
		return false;
	}
	
	//V�rifie si le joueur a perdu, i.e. son �nergie ou ses points de vie sont tomb�s � 0
	public static boolean defaite() {
		return joueur.Energie==0 || joueur.Pdv==0;
	}
	
	public static void main(String[] args) {
		init();
		jeu();
	}

}

