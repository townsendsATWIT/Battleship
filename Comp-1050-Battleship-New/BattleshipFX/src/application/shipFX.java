package application;

import java.util.HashMap;
import java.util.Map;

public class shipFX {
	public int Size;
	public int ID;

	public boolean Sunk = false;
	public boolean Horizontal = true;
	
	public int xPos;
	public int yPos;
	
	public boolean isPlaced = false;
	
	public String board;
	


	public shipFX(int size, boolean horizontal, int xpos, int ypos, int id) {
		  Size = size;
		  xPos = xpos;
		  yPos = ypos;
		  Horizontal = horizontal;
		  
		  ID = id;
			}

	
	
	
	public String toString() {
		
		return ("\nBoard: " + board + " | ID: " + ID + " | Size = " + Size + " | X/Y pos = " + "(" + xPos + "," + yPos + ")" +  "| Horizontal = " + Horizontal + " isPlaced?: " + isPlaced + "\n");
	}
}