package Model;
import Controller.*;
import View.*;
import java.util.HashSet;
import java.util.Set;


public class Piece
{
	
	public boolean accessible;		//True si la pi�ce est accessible, i.e. si elle fait partie du labyrinthe
	public boolean visitee;			//True si la pi�ce a d�j� �t� visit�e par le joueur
	
	public Objet objet;		//Objet pos� dans la salle
	
	public Pnj pnj;			//Pnj se trouvant dans la salle
	
	public Joueur joueur;	//Joueur se trouvant dans la salle
	

	public Piece(){		//A l'intialisation du jeu, aucune pi�ce n'est accessible ou visit�e.
		accessible = false;
		visitee = false;
	}

}

