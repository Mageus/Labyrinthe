package Model;
import Controller.*;
import View.*;


public class PieceS extends Piece		//Pi�ce contenant un passage secret
{
	
	
	public PieceS dest;		//Pi�ce reli�e � celle-ci par le passage secret
	
	public int x;		//Coordonn�es de la pi�ce
	public int y;

	public PieceS(int x, int y){
		super();
		accessible = true;		//Une pi�ce contenant un passage secret sera forc�ment accessible
		visitee = false;		//A l'initailisation du jeu, cette pi�ce n'est pas consid�r�e comme visit�e (comme toute pi�ce)
		this.x=x;
		this.y=y;
	}

}

