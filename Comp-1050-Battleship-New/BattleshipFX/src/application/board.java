package application;

public class board {

	int boardArray[][][];
	int lengthLiteralX, lengthLiteralY, lengthLiteralZ;
	String name;
	
	int IDNum = 1;
	
	public board(String Name, int x, int y, int z) {
		boardArray = new int[x][y][z];
		lengthLiteralX = x;
		lengthLiteralY = y;
		lengthLiteralZ = z;
		name = Name;
		
	}
		
	
	public String toString(int verbose) {
		
		String returnFinal = "" + name + "\n";
		
		
		
		//creating Z DIMENSION
		for (int z = 0; z < lengthLiteralZ; z++) {
			returnFinal = returnFinal + ("Z DIMENSION #" + (z+1) + "\n\n");
		//creating Y COLUMNS
			for (int y = 0; y < lengthLiteralY; y++) {
		//creating X ROW
				for (int x = 0; x < lengthLiteralX; x++) {
					
					//ADDED TO TOSTRING METHOD TO SHOW AN X ON A HIT SPOT
					String prefix = "" + boardArray[x][y][z];
					if(boardArray[x][y][z] == 10)
						prefix = "X";
					else if(boardArray[x][y][z] == 11)
						prefix = "!";
					
					
				returnFinal = returnFinal + "[" + prefix + "]";
			}
			returnFinal = returnFinal + (" | " + (y+1) + "  (" + (y) + ") \n");
			}
		returnFinal = returnFinal + " 1  2  3  4  5  6  7  8  9  10\n(0)(1)(2)(3)(4)(5)(6)(7)(8)(9)\n\n\n";
		if(verbose != 1) 
			break;
		}
		
		return returnFinal;
	}
	
	public void insertValue(int x, int y, int z, int input) {
		boardArray[x][y][z] = input;
	}
	
	public int getValue(int x, int y, int z) {
		return boardArray[x][y][z];
	}
}


