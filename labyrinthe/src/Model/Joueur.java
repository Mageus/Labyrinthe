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
	public int Pdv;
	
	public int Force;
	
	public ArrayList<Objet> objet;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	public Joueur(){
		super();
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
		}
		else if(dir=='b' && Scenario.cloisonsH[y+1][x].getClass().getSimpleName().equals("Porte")) {
			Scenario.pieces[y][x].joueur=null;
			Scenario.pieces[y+1][x].joueur=this;
			Scenario.pieces[y+1][x].visitee=true;
			this.y++;
		}
		else if(dir=='g' && Scenario.cloisonsV[y][x].getClass().getSimpleName().equals("Porte")) {
			Scenario.pieces[y][x].joueur=null;
			Scenario.pieces[y][x-1].joueur=this;
			Scenario.pieces[y][x-1].visitee=true;
			this.x--;
		}
		else if(dir=='d' && Scenario.cloisonsV[y][x+1].getClass().getSimpleName().equals("Porte")) {
			Scenario.pieces[y][x].joueur=null;
			Scenario.pieces[y][x+1].joueur=this;
			Scenario.pieces[y][x+1].visitee=true;
			this.x++;
		}		
	}
	
	public void passageSecret() {
		if(Scenario.pieces[y][x]==Scenario.p1) {
			Scenario.pieces[y][x].joueur=null;
			Scenario.pieces[Scenario.p1.dest.y][Scenario.p1.dest.x].joueur=this;
			this.x=Scenario.p1.dest.x;
			this.y=Scenario.p1.dest.y;
		}
		else if(Scenario.pieces[y][x]==Scenario.p2) {
			Scenario.pieces[y][x].joueur=null;
			Scenario.pieces[Scenario.p2.dest.y][Scenario.p2.dest.x].joueur=this;
			this.x=Scenario.p2.dest.x;
			this.y=Scenario.p2.dest.y;			
		}
		Scenario.pieces[this.y][this.x].visitee=true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public void ramasser() {
		// TODO implement me
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public void lacher(Objet parameter) {
		// TODO implement me
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public void rencontre(Personnage parameter) {
		// TODO implement me
	}

}

