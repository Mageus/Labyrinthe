package View;
import Model.*;
import Controller.*;

public class Affichage
{	
	public static void console() {
		effacerConsole();
		for(int i=0; i<Scenario.cloisonsH.length; i++) {
			for(int j=0; j<Scenario.cloisonsH[0].length; j++) {
				if(Scenario.cloisonsH[i][j].getClass().getSimpleName().equals("Porte")) {
					System.out.print("  ");
				}
				else {
					System.out.print(" -");
				}
			}
			System.out.println();
			if(i<Scenario.cloisonsV.length) {
				for(int j=0; j<Scenario.cloisonsV[0].length; j++) {
					if(Scenario.cloisonsV[i][j].getClass().getSimpleName().equals("Porte")) {
						System.out.print(" ");
					}
					else {
						System.out.print("|");
					}
					if(j<Scenario.pieces[0].length) {
						if(!Scenario.pieces[i][j].accessible) {
							System.out.print("X");
						}
						else if(Scenario.pieces[i][j].objet!=null) {
							System.out.print(Scenario.pieces[i][j].objet.getClass().getSimpleName().charAt(0));
						}
						else if(Scenario.pieces[i][j].joueur!=null) {
							System.out.print("J");
						}
						else {
							System.out.print(" ");
						}
					}
				}
			}
			System.out.println();
		}
	}
	
	public static void effacerConsole() {
		try {
			if(System.getProperty("os.name").startsWith("Windows")) {
				Runtime.getRuntime().exec("cls.exe");
			}
			else {
			    Runtime.getRuntime().exec("clear");
			}
		} 
		catch(Exception e) {
			for(int i=0;i<100;i++)
				System.out.println();
		}
	}

}

