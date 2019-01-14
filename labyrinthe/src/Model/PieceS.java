package Model;
import Controller.*;
import View.*;


public class PieceS extends Piece
{
	
	
	public PieceS dest;
	
	public int x;
	public int y;

	public PieceS(int x, int y){
		super();
		accessible = true;
		visitee = false;
		this.x=x;
		this.y=y;
	}

}

