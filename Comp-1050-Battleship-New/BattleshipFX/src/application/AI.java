package application;

import java.util.ArrayList;

public class AI {
//Variables
board Board;
static final int BOARD_DIM_Z = 1;
public int guessState;

public int prevX;
public int prevY;

public int nativeShipFirstHitX;
public int nativeShipFirstHitY;

public String guessDirection = "";

public ArrayList<String> guessedDirections = new ArrayList<String>();
ArrayList<String> knownTwos = new ArrayList<String>();

public AI(board TARGET) {
	Board = TARGET;
	}

//Run through the board's 2nd Z Dimension and determine which guessState the AI should use
public void scanBoard() {
	//Scan each row
	for(int y = 0; y < Board.lengthLiteralY; y++) {
		//Scan each value 
		for(int x = 0; x < Board.lengthLiteralX; x++) {
			switch(Board.getValue(x, y, BOARD_DIM_Z)) {
			case(0):
				guessState = 1;
			case(1):
				guessState = 2;
			case(2):
				guessState = 3;
				break;
			}
		}
	}
}

//True random guessing
public String guessRandom() {
	String guessCoord = "";
	
	int xMin = 0;
	int xMax = 9;
	int randomXPos = (int)Math.floor(Math.random() * (xMax - xMin + 1) + xMin);
	
	int yMin = 0;
	int yMax = 9;
	int randomYPos = (int)Math.floor(Math.random() * (yMax - yMin + 1) + yMin);
	
	guessCoord = guessCoord + randomXPos + "," + randomYPos;
	
	Board.insertValue(randomXPos, randomYPos, BOARD_DIM_Z, 1);
	return guessCoord;
	}



public String guessStandard() {
	String guessCoord = "";
	boolean guessComplete = false;
	
	while(guessComplete == false) {
		int xMin = 0;
		int xMax = 9;
		int randomXPos = (int)Math.floor(Math.random() * (xMax - xMin + 1) + xMin);
		
		int yMin = 0;
		int yMax = 9;
		int randomYPos = (int)Math.floor(Math.random() * (yMax - yMin + 1) + yMin);
		
		if(Board.getValue(randomXPos, randomYPos, BOARD_DIM_Z) != 0)
			guessComplete = false;
		else {
			Board.insertValue(randomXPos, randomYPos, BOARD_DIM_Z, 1);
			guessComplete = true;
			guessCoord = guessCoord + randomXPos + "," + randomYPos;
		}
		
	}
	
	
	return guessCoord;
	}

public String guessAdjacent(int posX, int posY) {
	String guessCoord = "";
	
	boolean guessComplete = false;
	boolean goodDirection = false;
	int guess = 0;
	
	while(guessComplete == false) {
		while(goodDirection == false) {
	int guessMin = 1;
	int guessMax = 4;
	guess = (int)Math.floor(Math.random() * (guessMax - guessMin + 1) + guessMin);
	if((guess == 1) && (!guessedDirections.contains("up"))) {
		goodDirection = true;
	} else if ((guess == 2) && (!guessedDirections.contains("right"))) {
		goodDirection = true;
	} else if ((guess == 3) && (!guessedDirections.contains("down"))) {
		goodDirection = true;
	} else if ((guess == 4) && (!guessedDirections.contains("left"))) {
		goodDirection = true;
	}
		}
		
		try {
	switch(guess) {
		//up
		case(1):
			if(Board.getValue(posX, posY-1, BOARD_DIM_Z)==2) {
			guessCoord = guessCoord + posX + "," + (posY-1);
			guessComplete = true;
			}
			break;
		//right
		case(2):
			if(Board.getValue(posX+1, posY, BOARD_DIM_Z)==2) {
				guessCoord = guessCoord + (posX+1) + "," + posY;
				guessComplete = true;
			}
			break;
		//down
		case(3):
			if(Board.getValue(posX, posY+1, BOARD_DIM_Z)==2) {
			guessCoord = guessCoord + posX + "," + (posY+1);
			guessComplete = true;
			}
			break;
		//left
		case(4):
			if(Board.getValue(posX-1, posY, BOARD_DIM_Z)==2) {
			guessCoord = guessCoord + (posX-1) + "," + posY;
			guessComplete = true;
			}
			break;
			}
		} catch (IndexOutOfBoundsException e) {
			guessComplete = false;
			goodDirection = false;
		}
	}
		
	return guessCoord;
}

public String guessOppDirection(String direction) {
	String guessCoord = "";
	try {
		switch(direction) {
		case("up"):
			guessCoord = guessCoord + Board.getValue(nativeShipFirstHitX, nativeShipFirstHitY+1, BOARD_DIM_Z);
			guessDirection = "down";
			break;
		case("down"):
			guessCoord = guessCoord + Board.getValue(nativeShipFirstHitX, nativeShipFirstHitY-1, BOARD_DIM_Z);
			guessDirection = "up";
			break;
		case("left"):
			guessCoord = guessCoord + Board.getValue(nativeShipFirstHitX+1, nativeShipFirstHitY, BOARD_DIM_Z);
			guessDirection = "right";
			break;
		case("right"):
			guessCoord = guessCoord + Board.getValue(nativeShipFirstHitX-1, nativeShipFirstHitY, BOARD_DIM_Z);
			guessDirection = "left";
			break;
		}
	} catch (IndexOutOfBoundsException e) {
		
	}
	return guessCoord;
}

public String guessTwos() {
	String guessCoord = "";
	knownTwos.clear();
	//Find how many twos there are in the board. Add them to an array, 
	//and randomly select from the array and grab the position as the guess
	
	for(int y = 0; y < Board.lengthLiteralY; y++) {
		for(int x = 0; x < Board.lengthLiteralX; x++) {
			if(Board.getValue(x, y, BOARD_DIM_Z)==2) {
				knownTwos.add(x+","+y);
				}
			}
		}
	

	
	int min = 1;
	int max = knownTwos.size();
	int chosen = (int)Math.floor(Math.random() * (max - min + 1) + min);
	try {
		guessCoord = knownTwos.get(chosen-1);
	//If guessing when direction has been chosen, there should be 2 2's generated. 
	//Based on the direction, we know which value must be chosen.
	if(knownTwos.size()==2) {
		switch(guessDirection) {
		case("up"):
			guessCoord = knownTwos.get(0);
			break;
		case("down"):
			guessCoord = knownTwos.get(1);
			break;
		case("left"):
			guessCoord = knownTwos.get(0);
			break;
		case("right"):
			guessCoord = knownTwos.get(1);
			break;
		}
	}
	
	} catch (IndexOutOfBoundsException e) {
		System.out.println("error grabbing value at position " + chosen + " in array (" + knownTwos + ")");
	}
	return guessCoord;
}


public void assignAdjacent(int x, int y, int value, int valueReplaced) {
	try {
		if(Board.getValue(x+1, y, BOARD_DIM_Z)== valueReplaced)
		Board.insertValue(x+1, y, BOARD_DIM_Z, value);
		
		if(Board.getValue(x-1, y, BOARD_DIM_Z)== valueReplaced)
		Board.insertValue(x-1, y, BOARD_DIM_Z, value);
		
		if(Board.getValue(x, y+1, BOARD_DIM_Z)== valueReplaced)
		Board.insertValue(x, y+1, BOARD_DIM_Z, value);
		
		if(Board.getValue(x, y-1, BOARD_DIM_Z)== valueReplaced)
		Board.insertValue(x, y-1, BOARD_DIM_Z, value);
		
	} catch(IndexOutOfBoundsException e) {
		//corner
		if((x==0 && y == 0)) {
			if(Board.getValue(x+1, y, BOARD_DIM_Z)== valueReplaced)
			Board.insertValue(x+1, y, BOARD_DIM_Z, value);
			if(Board.getValue(x, y+1, BOARD_DIM_Z)== valueReplaced)
			Board.insertValue(x, y+1, BOARD_DIM_Z, value);
		} else if((x==0 && y == 9)) {
			if(Board.getValue(x+1, y, BOARD_DIM_Z)== valueReplaced)
			Board.insertValue(x+1, y, BOARD_DIM_Z, value);
			if(Board.getValue(x, y-1, BOARD_DIM_Z)== valueReplaced)
			Board.insertValue(x, y-1, BOARD_DIM_Z, value);
		} else if((x==9 && y == 0)) {
			if(Board.getValue(x-1, y, BOARD_DIM_Z)== valueReplaced)
			Board.insertValue(x-1, y, BOARD_DIM_Z, value);
			if(Board.getValue(x, y+1, BOARD_DIM_Z)== valueReplaced)
			Board.insertValue(x, y+1, BOARD_DIM_Z, value);
		} else if((x==9 && y == 9)) {
			if(Board.getValue(x-1, y, BOARD_DIM_Z)== valueReplaced)
			Board.insertValue(x-1, y, BOARD_DIM_Z, value);
			if(Board.getValue(x, y-1, BOARD_DIM_Z)== valueReplaced)
			Board.insertValue(x, y-1, BOARD_DIM_Z, value);
			//top edge
		} else if ((x > 0 && x < 9) && (y==0)) {
			if(Board.getValue(x-1, y, BOARD_DIM_Z)== valueReplaced)
			Board.insertValue(x-1, y, BOARD_DIM_Z, value);
			if(Board.getValue(x+1, y, BOARD_DIM_Z)== valueReplaced)
			Board.insertValue(x+1, y, BOARD_DIM_Z, value);
			if(Board.getValue(x, y+1, BOARD_DIM_Z)== valueReplaced)
			Board.insertValue(x, y+1, BOARD_DIM_Z, value);
			//bottom edge
		} else if ((x > 0 && x < 9) && (y==9)) {
			if(Board.getValue(x-1, y, BOARD_DIM_Z)== valueReplaced)
			Board.insertValue(x-1, y, BOARD_DIM_Z, value);
			if(Board.getValue(x+1, y, BOARD_DIM_Z)== valueReplaced)
			Board.insertValue(x+1, y, BOARD_DIM_Z, value);
			if(Board.getValue(x, y-1, BOARD_DIM_Z)== valueReplaced)
			Board.insertValue(x, y-1, BOARD_DIM_Z, value);
			//left edge
		} else if ((y > 0 && y < 9) && (x==0)) {
			if(Board.getValue(x, y-1, BOARD_DIM_Z)== valueReplaced)
			Board.insertValue(x, y-1, BOARD_DIM_Z, value);
			if(Board.getValue(x, y+1, BOARD_DIM_Z)== valueReplaced)
			Board.insertValue(x, y+1, BOARD_DIM_Z, value);
			if(Board.getValue(x+1, y, BOARD_DIM_Z)== valueReplaced)
			Board.insertValue(x+1, y, BOARD_DIM_Z, value);
			//right edge
		} else if ((y > 0 && y < 9) && (x==9)) {
			if(Board.getValue(x, y-1, BOARD_DIM_Z)== valueReplaced)
			Board.insertValue(x, y-1, BOARD_DIM_Z, value);
			if(Board.getValue(x, y+1, BOARD_DIM_Z)== valueReplaced)
			Board.insertValue(x, y+1, BOARD_DIM_Z, value);
			if(Board.getValue(x-1, y, BOARD_DIM_Z)== valueReplaced)
			Board.insertValue(x-1, y, BOARD_DIM_Z, value);
		}
	}
}

public void flushGuessedDirections(int includeDirections) {
	if(includeDirections == 1)
	guessedDirections.clear();
	
	for(int y = 0; y < Board.lengthLiteralY; y++) {
		for(int x = 0; x < Board.lengthLiteralX; x++) {
			
			if(Board.getValue(x, y, BOARD_DIM_Z) == 2)
				Board.insertValue(x, y, BOARD_DIM_Z, 0);
		}
	}
	
}




public void fixZDim() {
	//In this method, assign locations that ships could not be in based off data that is already known.
	
	//If a location is surrounded by already guessed/impossible locations, it must be impossible to hit a ship at that location.
	
	for(int y = 0; y < Board.lengthLiteralY; y++) {
		for(int x = 0; x < Board.lengthLiteralX; x++) {
			try {
			if(Board.getValue(x, y, BOARD_DIM_Z) == 0) {
				
				//EDGE CASE
				//If the value being checked is on an edge of the board, the check needs to be different. Ignore it for now.
				if(!((y == 0 || y == 9) && (x <= 0 && x >= 9)) || ((x == 0 || x == 9) && (y <= 0 && y >= 9))) {
					//Otherwise, if it is not on the edge of the board, it must be inside the board. Checks
					//the values surrounding that point, and if all values are equal to 1, the center point
					//is set to 1 as well.
					if((Board.getValue(x+1, y, BOARD_DIM_Z) == 1) && (Board.getValue(x-1, y, BOARD_DIM_Z) == 1) && (Board.getValue(x, y+1, BOARD_DIM_Z) == 1) && (Board.getValue(x, y-1, BOARD_DIM_Z) == 1)) {
						Board.insertValue(x, y, BOARD_DIM_Z, 1);
					} 
					
				} else {
					
					
					//Checking tops and bottoms of board (except corners)
					if(y == 0) {
						if((x != 0 || x != 9) && (Board.getValue(x+1, y, BOARD_DIM_Z) == 1) && (Board.getValue(x-1, y, BOARD_DIM_Z) == 1) && (Board.getValue(x, y+1, BOARD_DIM_Z) == 1)) {
						Board.insertValue(x, y, BOARD_DIM_Z, 1);
						}
					} else if (y == 9) {
						if((x != 0 || x != 9) && (Board.getValue(x+1, y, BOARD_DIM_Z) == 1) && (Board.getValue(x-1, y, BOARD_DIM_Z) == 1) && (Board.getValue(x, y-1, BOARD_DIM_Z) == 1)) {
						Board.insertValue(x, y, BOARD_DIM_Z, 1);
						}
						//Checking sides of the board (except corners)
					else if(x == 0) {
						if((y != 0 || y != 9) && (Board.getValue(x, y+1, BOARD_DIM_Z) == 1) && (Board.getValue(x, y-1, BOARD_DIM_Z) == 1) && (Board.getValue(x+1, y, BOARD_DIM_Z) == 1)) {
						Board.insertValue(x, y, BOARD_DIM_Z, 1);
						}			
					} else if (x == 9) {
							if((y != 0 || y != 9) && (Board.getValue(x, y+1, BOARD_DIM_Z) == 1) && (Board.getValue(x, y-1, BOARD_DIM_Z) == 1) && (Board.getValue(x-1, y, BOARD_DIM_Z) == 1)) {
								Board.insertValue(x, y, BOARD_DIM_Z, 1);
								}
							
							
							
							//corner check
						} else if ((x == 0 && y == 0)) {
							if((Board.getValue(x+1, y, BOARD_DIM_Z) == 1) && (Board.getValue(x, y+1, BOARD_DIM_Z) == 1)) {
								Board.insertValue(x, y, BOARD_DIM_Z, 1);
							}
							
						} else if ((x == 0 && y == 9)) {
							if((Board.getValue(x+1, y, BOARD_DIM_Z) == 1) && (Board.getValue(x, y-1, BOARD_DIM_Z) == 1)) {
								Board.insertValue(x, y, BOARD_DIM_Z, 1);
							}
						} else if ((x == 9 && y == 0)) {
							if((Board.getValue(x-1, y, BOARD_DIM_Z) == 1) && (Board.getValue(x, y+1, BOARD_DIM_Z) == 1)) {
								Board.insertValue(x, y, BOARD_DIM_Z, 1);
							}
						} else if ((x == 9 && y == 9)) {
							if((Board.getValue(x-1, y, BOARD_DIM_Z) == 1) && (Board.getValue(x, y-1, BOARD_DIM_Z) == 1)) {
								Board.insertValue(x, y, BOARD_DIM_Z, 1);
										}
									}
								}
							}
				
				
				
						}
					} catch (IndexOutOfBoundsException e) {
						//corner
						if((x==0 && y == 0)) {
							if(Board.getValue(x+1, y, BOARD_DIM_Z)!= 1)
							Board.insertValue(x+1, y, BOARD_DIM_Z,1);
							if(Board.getValue(x, y+1, BOARD_DIM_Z)!= 1)
							Board.insertValue(x, y+1, BOARD_DIM_Z,1);
						} else if((x==0 && y == 9)) {
							if(Board.getValue(x+1, y, BOARD_DIM_Z)!= 1)
							Board.insertValue(x+1, y, BOARD_DIM_Z,1);
							if(Board.getValue(x, y-1, BOARD_DIM_Z)!= 1)
							Board.insertValue(x, y-1, BOARD_DIM_Z,1);
						} else if((x==9 && y == 0)) {
							if(Board.getValue(x-1, y, BOARD_DIM_Z)!= 1)
							Board.insertValue(x-1, y, BOARD_DIM_Z,1);
							if(Board.getValue(x, y+1, BOARD_DIM_Z)!= 1)
							Board.insertValue(x, y+1, BOARD_DIM_Z,1);
						} else if((x==9 && y == 9)) {
							if(Board.getValue(x-1, y, BOARD_DIM_Z)!= 1)
							Board.insertValue(x-1, y, BOARD_DIM_Z,1);
							if(Board.getValue(x, y-1, BOARD_DIM_Z)!= 1)
							Board.insertValue(x, y-1, BOARD_DIM_Z,1);
							//top edge
						} else if ((x > 0 && x < 9) && (y==0)) {
							if(Board.getValue(x-1, y, BOARD_DIM_Z)!= 1)
							Board.insertValue(x-1, y, BOARD_DIM_Z,1);
							if(Board.getValue(x+1, y, BOARD_DIM_Z)!= 1)
							Board.insertValue(x+1, y, BOARD_DIM_Z,1);
							if(Board.getValue(x, y+1, BOARD_DIM_Z)!= 1)
							Board.insertValue(x, y+1, BOARD_DIM_Z,1);
							//bottom edge
						} else if ((x > 0 && x < 9) && (y==9)) {
							if(Board.getValue(x-1, y, BOARD_DIM_Z)!= 1)
							Board.insertValue(x-1, y, BOARD_DIM_Z,1);
							if(Board.getValue(x+1, y, BOARD_DIM_Z)!= 1)
							Board.insertValue(x+1, y, BOARD_DIM_Z,1);
							if(Board.getValue(x, y-1, BOARD_DIM_Z)!= 1)
							Board.insertValue(x, y-1, BOARD_DIM_Z,1);
							//left edge
						} else if ((y > 0 && y < 9) && (x==0)) {
							if(Board.getValue(x, y-1, BOARD_DIM_Z)!= 1)
							Board.insertValue(x, y-1, BOARD_DIM_Z,1);
							if(Board.getValue(x, y+1, BOARD_DIM_Z)!= 1)
							Board.insertValue(x, y+1, BOARD_DIM_Z,1);
							if(Board.getValue(x+1, y, BOARD_DIM_Z)!= 1)
							Board.insertValue(x+1, y, BOARD_DIM_Z,1);
							//right edge
						} else if ((y > 0 && y < 9) && (x==9)) {
							if(Board.getValue(x, y-1, BOARD_DIM_Z)!= 1)
							Board.insertValue(x, y-1, BOARD_DIM_Z,1);
							if(Board.getValue(x, y+1, BOARD_DIM_Z)!= 1)
							Board.insertValue(x, y+1, BOARD_DIM_Z,1);
							if(Board.getValue(x-1, y, BOARD_DIM_Z)!= 1)
							Board.insertValue(x-1, y, BOARD_DIM_Z,1);
						}
					}
				}	
			}
			
	}
}