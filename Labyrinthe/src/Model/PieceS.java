package Model;
import Controller.*;
import View.*;


/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class PieceS extends Piece
{
	
	
	public PieceS dest;
	
	public int x;
	public int y;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	public PieceS(int x, int y){
		super();
		accessible = true;
		visitee = false;
		this.x=x;
		this.y=y;
	}

}

