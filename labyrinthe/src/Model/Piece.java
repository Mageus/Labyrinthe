package Model;
import Controller.*;
import View.*;
import java.util.HashSet;
import java.util.Set;


public class Piece
{
	
	public boolean accessible;
	public boolean visitee;
	
	public Objet objet;
	
	public Personnage pnj;
	
	public Joueur joueur;
	

	public Piece(){
		accessible = false;
		visitee = false;
	}

}

