package Model;
import Controller.*;
import View.*;
import java.util.*;

public class Joueur extends Personnage
{
	public int Pdv;
	
	public int Energie;
	
	public ArrayList<Objet> objets;
	
	public char last_move;

	
	public Joueur(){
		super();
		objets = new ArrayList<Objet>();
	}
	
	public void deplacer(char dir) {
		int x = this.x;
		int y = this.y;
		last_move = dir;
		
		if(dir=='h' && Scenario.cloisonsH[y][x].getClass().getSimpleName().equals("Porte") && Scenario.cloisonsH[y][x].locked!=true) {
			Scenario.pieces[y][x].joueur=null;
			Scenario.pieces[y-1][x].joueur=this;
			Scenario.pieces[y-1][x].visitee=true;
			this.y--;
			Energie--;
		}
		else if(dir=='b' && Scenario.cloisonsH[y+1][x].getClass().getSimpleName().equals("Porte") && Scenario.cloisonsH[y+1][x].locked!=true) {
			Scenario.pieces[y][x].joueur=null;
			Scenario.pieces[y+1][x].joueur=this;
			Scenario.pieces[y+1][x].visitee=true;
			this.y++;
			Energie--;
		}
		else if(dir=='g' && Scenario.cloisonsV[y][x].getClass().getSimpleName().equals("Porte") && Scenario.cloisonsV[y][x].locked!=true) {
			Scenario.pieces[y][x].joueur=null;
			Scenario.pieces[y][x-1].joueur=this;
			Scenario.pieces[y][x-1].visitee=true;
			this.x--;
			Energie--;
		}
		else if(dir=='d' && Scenario.cloisonsV[y][x+1].getClass().getSimpleName().equals("Porte") && Scenario.cloisonsV[y][x+1].locked!=true) {
			Scenario.pieces[y][x].joueur=null;
			Scenario.pieces[y][x+1].joueur=this;
			Scenario.pieces[y][x+1].visitee=true;
			this.x++;
			Energie--;
		}
		rencontre();
	}
	
	public void passageSecret() {
		last_move = 'p';
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
		rencontre();
	}
	
	public void ramasser() {
		if(Scenario.pieces[y][x].objet!=null && objets.size()<6) {
			objets.add(Scenario.pieces[y][x].objet);
			Scenario.pieces[y][x].objet=null;
		}
	}
	
	public void lacher(int n) {
		if(Scenario.pieces[y][x].objet==null) {
			Scenario.pieces[y][x].objet=objets.get(n);
			objets.remove(n);
		}
	}
	
	public void utiliser(int n) {
		if(objets.get(n).getClass().getSimpleName().equals("Medicament")) {
			if(Pdv>7) {
				Pdv=10;
			}
			else {
				Pdv=Pdv+3;
			}
			objets.remove(n);
		}
		else if(objets.get(n).getClass().getSimpleName().equals("Nourriture")) {
			if(Energie>7) {
				Energie=10;
			}
			else {
				Energie=Energie+3;
			}
			objets.remove(n);
		}
		else if(objets.get(n).getClass().getSimpleName().equals("Clef")) {
			if(Scenario.cloisonsH[y][x].locked || Scenario.cloisonsH[y+1][x].locked || Scenario.cloisonsV[y][x].locked || Scenario.cloisonsV[y][x+1].locked) {
				Scenario.cloisonsH[y][x].locked = false;
				Scenario.cloisonsH[y+1][x].locked = false;
				Scenario.cloisonsV[y][x].locked = false;
				Scenario.cloisonsV[y][x+1].locked = false;		
				objets.remove(n);
			}
		}
	}
	
	public void detruire(int n) {
		if(!objets.get(n).getClass().getSimpleName().equals("Clef")) {
			objets.remove(n);
		}
	}
	
	public void rencontre() {
		if(Scenario.pieces[y][x].pnj!=null) {
			if(Scenario.pieces[y][x].pnj.getClass().getSimpleName().equals("Monstre")) {
				combattre();
			}
			else if(Scenario.pieces[y][x].pnj.getClass().getSimpleName().equals("Medecin")) {
				Pdv=10;
			}
			else if(Scenario.pieces[y][x].pnj.getClass().getSimpleName().equals("Cuisinier")) {
				Energie=10;
			}
		}
	}
	
	public void combattre() {
		Affichage.combat();
	}
	
	public void tour_combat(char c_joueur) {
		Affichage.first_turn=false;
		int m = (int)(Math.random()*3);
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
		if((j==0 && m==2) || (j==1 && m==0) || (j==2 && m==1)) {
			Affichage.gagne_combat=1;
			Scenario.pieces[y][x].pnj=null;
			Scenario.jeu();
		}
		else if ((m==0 && j==2) || (m==1 && j==0) || (m==2 && j==1)) {
			Pdv--;
			Affichage.gagne_combat=2;
		}
		else {
			Affichage.gagne_combat=0;
		}
		Affichage.update_inv();
		Affichage.jf_inv.validate();
		Affichage.update_combat();
		Affichage.jf_combat.validate();
	}
	
	public void fuir() {
		Affichage.jf_combat.dispose();
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
		}		
	}

}

