package Model;
import Controller.*;
import View.*;


/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public abstract class Personnage
{

	public int x;
	public int y;
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	public Personnage(){
		super();
	}
	
	public Personnage(int x, int y) {
		this.x=x;
		this.y=y;
	}

}

