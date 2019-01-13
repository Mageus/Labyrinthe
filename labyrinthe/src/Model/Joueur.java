package Model;
import Controller.*;
import View.*;
import java.util.*;


/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class Joueur extends Personnage
{
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	public int Pdv=10;
	
	public int Energie=10;
	
	public ArrayList<Objet> objets;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	public Joueur(){
		super();
		objets = new ArrayList<Objet>();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public void deplacer(char dir) {
		int x = this.x;
		int y = this.y;
		
		if(dir=='h' && Scenario.cloisonsH[y][x].getClass().getSimpleName().equals("Porte")) {
			Scenario.pieces[y][x].joueur=null;
			Scenario.pieces[y-1][x].joueur=this;
			Scenario.pieces[y-1][x].visitee=true;
			this.y--;
			Energie--;
		}
		else if(dir=='b' && Scenario.cloisonsH[y+1][x].getClass().getSimpleName().equals("Porte")) {
			Scenario.pieces[y][x].joueur=null;
			Scenario.pieces[y+1][x].joueur=this;
			Scenario.pieces[y+1][x].visitee=true;
			this.y++;
			Energie--;
		}
		else if(dir=='g' && Scenario.cloisonsV[y][x].getClass().getSimpleName().equals("Porte")) {
			Scenario.pieces[y][x].joueur=null;
			Scenario.pieces[y][x-1].joueur=this;
			Scenario.pieces[y][x-1].visitee=true;
			this.x--;
			Energie--;
		}
		else if(dir=='d' && Scenario.cloisonsV[y][x+1].getClass().getSimpleName().equals("Porte")) {
			Scenario.pieces[y][x].joueur=null;
			Scenario.pieces[y][x+1].joueur=this;
			Scenario.pieces[y][x+1].visitee=true;
			this.x++;
			Energie--;
		}		
	}
	
	public void passageSecret() {
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
			objets.set(n,null);
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
			objets.set(n,null);
		}
		else if(objets.get(n).getClass().getSimpleName().equals("Nourriture")) {
			if(Energie>7) {
				Energie=10;
			}
			else {
				Energie=Energie+3;
			}
			objets.set(n,null);
		}
	}
	
	public void detruire(int n) {
		objets.set(n,null);
	}
	
	public void rencontre(Personnage parameter) {
		
	}

}

