package Model;
import Controller.*;
import View.*;


public abstract class Personnage
{

	public int x;		//Coordonn�es du personnage
	public int y;

	public Personnage(){
		super();
	}
	
	public Personnage(int x, int y) {
		this.x=x;
		this.y=y;
	}

}

