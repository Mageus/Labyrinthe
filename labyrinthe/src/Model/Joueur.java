package Model;
import Controller.*;
import View.*;
import java.util.*;


/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class Joueur extends Combattant
{
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
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
			this.y--;
		}
		else if(dir=='b' && Scenario.cloisonsH[y+1][x].getClass().getSimpleName().equals("Porte")) {
			Scenario.pieces[y][x].joueur=null;
			Scenario.pieces[y+1][x].joueur=this;
			this.y++;
		}
		else if(dir=='g' && Scenario.cloisonsV[y][x].getClass().getSimpleName().equals("Porte")) {
			Scenario.pieces[y][x].joueur=null;
			Scenario.pieces[y][x-1].joueur=this;
			this.x--;
		}
		else if(dir=='d' && Scenario.cloisonsV[y][x+1].getClass().getSimpleName().equals("Porte")) {
			Scenario.pieces[y][x].joueur=null;
			Scenario.pieces[y][x+1].joueur=this;
			this.x++;
		}		
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

