package Model;
import Controller.*;
import View.*;
import java.util.HashSet;
import java.util.Set;


public class Piece
{
	
	public boolean accessible;		//True si la pièce est accessible, i.e. si elle fait partie du labyrinthe
	public boolean visitee;			//True si la pièce a déjà été visitée par le joueur
	
	public Objet objet;		//Objet posé dans la salle
	
	public Pnj pnj;			//Pnj se trouvant dans la salle
	
	public Joueur joueur;	//Joueur se trouvant dans la salle
	

	public Piece(){		//A l'intialisation du jeu, aucune pièce n'est accessible ou visitée.
		accessible = false;
		visitee = false;
	}

}

