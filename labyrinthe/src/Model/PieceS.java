package Model;
import Controller.*;
import View.*;


public class PieceS extends Piece		//Pièce contenant un passage secret
{
	
	
	public PieceS dest;		//Pièce reliée à celle-ci par le passage secret
	
	public int x;		//Coordonnées de la pièce
	public int y;

	public PieceS(int x, int y){
		super();
		accessible = true;		//Une pièce contenant un passage secret sera forcément accessible
		visitee = false;		//A l'initailisation du jeu, cette pièce n'est pas considérée comme visitée (comme toute pièce)
		this.x=x;
		this.y=y;
	}

}

