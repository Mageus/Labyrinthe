package Controller;
import View.*;
import Model.*;
import java.util.*;


/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class Scenario
{
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	public static int spawn_x;
	public static int spawn_y;
	public static int tresor_x;
	public static int tresor_y;
	
	public static Piece[][] pieces;			//Piece[Ordonnée][Abscisse]
	
	public static Cloison[][] cloisonsV;		//Cloison[Ordonnée][Abscisse] -> cloisons verticales
	public static Cloison[][] cloisonsH;		//Cloison[Ordonnée][Abscisse] -> cloisons verticales

	public static Joueur joueur;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public static void init() {
		pieces = new Piece[7][10];
		cloisonsV = new Cloison[7][11];
		cloisonsH = new Cloison[8][10];
		
		for(int i=0; i<pieces.length;i++) {
			for(int j=0; j<pieces[0].length; j++) {
				pieces[i][j]=new Piece();
			}
		}
		
		Tresor tresor = new Tresor();
		tresor_x = (int)(Math.random()*pieces[0].length);
		tresor_y = (int)(Math.random()*pieces.length);
		pieces[tresor_y][tresor_x].objet=tresor;
		
		joueur = new Joueur();
		do{
			spawn_x = (int)(Math.random()*pieces[0].length);
			spawn_y = (int)(Math.random()*pieces.length);
		}while(spawn_x==tresor_x && spawn_y==tresor_y);
		joueur.x=spawn_x;
		joueur.y=spawn_y;
		pieces[spawn_y][spawn_x].joueur=joueur;
		
		int distance = distanceTrajetCourt(spawn_x, spawn_y, tresor_x, tresor_y);
		int nbAccessibles = distance*2;
		
		String criticalPath = trajetPrincipal(nbAccessibles);
		construireTrajet(criticalPath, nbAccessibles);
	}
	
	public static void construireTrajet(String criticalPath, int nbAccessibles) {
		//Initialisation des murs
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
		
		//Construction du trajer principal (criticalPath)
		pieces[spawn_y][spawn_x].accessible=true;
		pieces[tresor_y][tresor_x].accessible=true;
		int x = spawn_x, y = spawn_y;
		for(int i=0; i<criticalPath.length(); i++) {
			if(criticalPath.charAt(i)=='h' && y!=0) {
				cloisonsH[y][x] = new Porte();
				y--;
			}
			else if(criticalPath.charAt(i)=='b' && y!=pieces.length-1) {
				y++;
				cloisonsH[y][x] = new Porte();
			}
			else if(criticalPath.charAt(i)=='g' && x!=0) {
				cloisonsV[y][x] = new Porte();
				x--;
			}
			else if(criticalPath.charAt(i)=='d' && x!=pieces[0].length-1) {
				x++;
				cloisonsV[y][x] = new Porte();
			}
			pieces[y][x].accessible=true;
		}
		
		//Construction salles supplémentaires
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
	
	public static String trajetPrincipal(int max) {
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
		while(joueur.x!=tresor_x || joueur.y!=tresor_y) {
			resu="";
			pieces[joueur.y][joueur.x].joueur=null;
			joueur.x=spawn_x;
			joueur.y=spawn_y;
			pieces[spawn_y][spawn_x].joueur=joueur;
			while(resu.length()<max && (joueur.x!=tresor_x || joueur.y!=tresor_y)) {
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
		}
		pieces[joueur.y][joueur.x].joueur=null;
		joueur.x=spawn_x;
		joueur.y=spawn_y;
		pieces[spawn_y][spawn_x].joueur=joueur;
		return resu;
	}
	
	
	public static void jeu() {
		Affichage.console();
		Scanner sc = new Scanner(System.in);
		char dir = sc.next().charAt(0);
		joueur.deplacer(dir);
		if(fin()) {
			//Affichage.effacerConsole();
			System.out.println("Gagné !");
			sc.next();
		}
		else {
			jeu();
		}
	}
	
	public static boolean fin() {
		return joueur.x==tresor_x && joueur.y==tresor_y;
	}
	
	public static void main(String[] args) {
		init();
		jeu();
	}

}

