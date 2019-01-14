package Model;
import Controller.*;
import View.*;
import java.util.*;

public class Joueur extends Personnage
{
	public int Pdv;		//Points de vie du joueur
	
	public int Energie;	//Energie du joueur
	
	public ArrayList<Objet> objets;		//Liste des objets contenus dans l'inventaire du joueur
	
	public char last_move;		//Dernier d�placement effectu� par le joueur (sert dans fuir() )

	
	public Joueur(){
		super();
		objets = new ArrayList<Objet>();
	}
	
	//D�place le joueur d'une case selon la direction dir si la cloison dans cette direction est une 
	public void deplacer(char dir) {
		last_move = dir;		//Enregistre le dernier mouvement du joueur
		
		if(dir=='h' && Scenario.cloisonsH[y][x].getClass().getSimpleName().equals("Porte") && Scenario.cloisonsH[y][x].locked!=true) {				//Haut
			Scenario.pieces[y][x].joueur=null;
			Scenario.pieces[y-1][x].joueur=this;
			Scenario.pieces[y-1][x].visitee=true;
			this.y--;
			Energie--;		//On baisse l'�nergie du joueur � chaque d�placement
		}
		else if(dir=='b' && Scenario.cloisonsH[y+1][x].getClass().getSimpleName().equals("Porte") && Scenario.cloisonsH[y+1][x].locked!=true) {		//Bas
			Scenario.pieces[y][x].joueur=null;
			Scenario.pieces[y+1][x].joueur=this;
			Scenario.pieces[y+1][x].visitee=true;
			this.y++;
			Energie--;		//On baisse l'�nergie du joueur � chaque d�placement
		}
		else if(dir=='g' && Scenario.cloisonsV[y][x].getClass().getSimpleName().equals("Porte") && Scenario.cloisonsV[y][x].locked!=true) {			//Gauche
			Scenario.pieces[y][x].joueur=null;
			Scenario.pieces[y][x-1].joueur=this;
			Scenario.pieces[y][x-1].visitee=true;
			this.x--;
			Energie--;		//On baisse l'�nergie du joueur � chaque d�placement
		}
		else if(dir=='d' && Scenario.cloisonsV[y][x+1].getClass().getSimpleName().equals("Porte") && Scenario.cloisonsV[y][x+1].locked!=true) {		//Droite
			Scenario.pieces[y][x].joueur=null;
			Scenario.pieces[y][x+1].joueur=this;
			Scenario.pieces[y][x+1].visitee=true;
			this.x++;
			Energie--;		//On baisse l'�nergie du joueur � chaque d�placement
		}
		rencontre();		//On teste si le joueur rencontre un Pnj dans sa nouvelle pi�ce
	}
	
	//Permet au joueur d'emprunter un passage secret: le d�place de Scenario.p1 � Scenario.p2 ou inversement
	public void passageSecret() {
		last_move = 'p';		//Enregistre le dernier d�placement du joueur comme la prise d'un passage secret
		if(Scenario.pieces[y][x]==Scenario.p1) {
			Scenario.pieces[y][x].joueur=null;
			Scenario.pieces[Scenario.p1.dest.y][Scenario.p1.dest.x].joueur=this;
			this.x=Scenario.p1.dest.x;
			this.y=Scenario.p1.dest.y;
			Energie--;
		}
		else if(Scenario.pieces[y][x]==Scenario.p2) {
			Scenario.pieces[y][x].joueur=null;
			Scenario.pieces[Scenario.p2.dest.y][Scenario.p2.dest.x].joueur=this;
			this.x=Scenario.p2.dest.x;
			this.y=Scenario.p2.dest.y;
			Energie--;
		}
		Scenario.pieces[this.y][this.x].visitee=true;
		rencontre();		//On teste si le joueur rencontre un Pnj dans sa nouvelle pi�ce
	}
	
	//Permet au joueur de ramasser l'objet se situant dans sa pi�ce (max 6 objets dans l'inventaire)
	public void ramasser() {
		if(Scenario.pieces[y][x].objet!=null && objets.size()<6) {
			objets.add(Scenario.pieces[y][x].objet);
			Scenario.pieces[y][x].objet=null;
		}
	}
	
	//Permet au joueur de poser dans sa pi�ce l'objet d'indice n de son inventaire
	public void lacher(int n) {
		if(Scenario.pieces[y][x].objet==null) {
			Scenario.pieces[y][x].objet=objets.get(n);
			objets.remove(n);
		}
	}
	
	//Permet au joueur d'utiliser l'objet d'indice n de son inventaire
	public void utiliser(int n) {
		if(objets.get(n).getClass().getSimpleName().equals("Medicament")) {		//Si Medicament, rend 3 pdv au joueur, max 10
			if(Pdv>7) {
				Pdv=10;
			}
			else {
				Pdv=Pdv+3;
			}
			objets.remove(n);
		}
		else if(objets.get(n).getClass().getSimpleName().equals("Nourriture")) {	//Si Medicament, rend 3 point d'�nergie au joueur, max 10
			if(Energie>7) {
				Energie=10;
			}
			else {
				Energie=Energie+3;
			}
			objets.remove(n);
		}
		else if(objets.get(n).getClass().getSimpleName().equals("Clef")) {		//Si Clef, ne peut �tre utilis�e que s'il y a une porte ferm�e contingente � sa pi�ce et l'ouvre
			if(Scenario.cloisonsH[y][x].locked || Scenario.cloisonsH[y+1][x].locked || Scenario.cloisonsV[y][x].locked || Scenario.cloisonsV[y][x+1].locked) {
				Scenario.cloisonsH[y][x].locked = false;
				Scenario.cloisonsH[y+1][x].locked = false;
				Scenario.cloisonsV[y][x].locked = false;
				Scenario.cloisonsV[y][x+1].locked = false;		
				objets.remove(n);
			}
		}
	}
	
	//Permet au joueur de d�truire l'objet d'indice n de son inventaire (pour lib�rer de l'espace par exemple)
	public void detruire(int n) {
		if(!objets.get(n).getClass().getSimpleName().equals("Clef")) {		//On ne peut pas d�truire la Clef
			objets.remove(n);
		}
	}
	
	//Action � lancer s'il y a un pnj dans la pi�ce du joueur
	public void rencontre() {
		if(Scenario.pieces[y][x].pnj!=null) {
			if(Scenario.pieces[y][x].pnj.getClass().getSimpleName().equals("Monstre")) {		//S'il s'agit d'un monstre, lancer un combat
				combattre();
			}
			else if(Scenario.pieces[y][x].pnj.getClass().getSimpleName().equals("Medecin")) {	//S'il s'agit d'un m�decin, remettre les pdv � 10
				Pdv=10;
			}
			else if(Scenario.pieces[y][x].pnj.getClass().getSimpleName().equals("Cuisinier")) {	//S'il s'agit d'un cuisinier, remettre l'�nergie � 10
				Energie=10;
			}
		}
	}
	
	//D�marre un combat
	public void combattre() {
		Affichage.combat();
	}
	
	//Calcul de l'issue d'un tour de combat (selon les r�gles du pierre-feuille-ciseaux) selon le coup c_joueur jou� par le joueur
	public void tour_combat(char c_joueur) {
		Affichage.first_turn=false;
		int m = (int)(Math.random()*3);		//Choix al�atoire du coup jou� par le monstre
		int j=0;
		switch(c_joueur) {
			case 'p' : j=0;
					   Affichage.last_move_j = "Pierre";
					   break;
			case 'f' : j=1;
					   Affichage.last_move_j = "Feuille";
			   		   break;	
			case 'c' : j=2;
					   Affichage.last_move_j = "Ciseaux";
			           break;
		}
		switch(m) {
			case 0 : Affichage.last_move_m = "Pierre";
			   		 break;
			case 1 : Affichage.last_move_m = "Feuille";
			   	     break;	
			case 2 : Affichage.last_move_m = "Ciseaux";
			    	 break;
		}
		if((j==0 && m==2) || (j==1 && m==0) || (j==2 && m==1)) {	//Si le joueur gagne
			Affichage.gagne_combat=1;
			Scenario.pieces[y][x].pnj=null;		//On retire le monstre de la pi�ce
			Scenario.jeu();
		}
		else if ((m==0 && j==2) || (m==1 && j==0) || (m==2 && j==1)) {	//Si le monstre gagne
			Pdv--;	//On retire un pdv au joueur pour chaque manche gagn�e par le monstre
			Affichage.gagne_combat=2;
		}
		else {		//Egalit�
			Affichage.gagne_combat=0;
		}
		Affichage.update_inv();			//Mise � jour de l'inventaire pour l'affichage des pdv
		Affichage.jf_inv.validate();
		Affichage.update_combat();		//Mise � jour de la fen�tre de combat
		Affichage.jf_combat.validate();
	}
	
	//Permet au joueur de fuir lors d'un combat vers l� d'o� il vient
	public void fuir() {
		switch(last_move) {
		case 'h' : deplacer('b');
				   break;
		case 'b' : deplacer('h');
		   		   break;
		case 'g' : deplacer('d');
		           break;
		case 'd' : deplacer('g');
		   		   break;
		case 'p' : passageSecret();
				   break;
		}		
	}

}

