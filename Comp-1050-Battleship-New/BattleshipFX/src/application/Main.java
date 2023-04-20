package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ArrayList;
import java.lang.IndexOutOfBoundsException;
import java.lang.Math;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("BattleshipFX");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//writing a method to place the ship into the array
	
	public static void placeShip(shipFX ship, board board) {
		//Take ship's properties that pertain to putting into board and instantiate them
		int X = ship.xPos -1;
		int Y = ship.yPos -1;
		int length = ship.Size;
		boolean horizontal = ship.Horizontal;
		int boardLengthX = board.lengthLiteralX;
		int boardLengthY = board.lengthLiteralY;
		
		int ID = ship.ID;
		
		boolean canBePlaced = true;
		
	
		
		//if the ship is horizontal, check if can be placed horizontally
		try {
		if(horizontal == true) {
			//check if position in board is valid, must be able to fit inside boardspace
			if((length+X) <= boardLengthX) {
				//check if position on board is free (X)
				for(int i = 0; i < length; i++) {
					if(board.boardArray[X + i][Y][0] != 0) {
						canBePlaced = false;
					}
				}
			}
			
			//otherwise, check if the ship can be placed vertically
		} else {
			if((length+Y) <= boardLengthY) {
				//check if position on board is free (Y)
				for(int i = 0; i < length; i++) {
					if(board.boardArray[X][Y+i][0] != 0) {
						canBePlaced = false;
					}
				}
			}
		}
		
		
		//If the ship can be placed, place it into the array at the coordinates based on it's length
	if(canBePlaced == true) {
		ship.isPlaced = true;
		if(horizontal == true) {
			for(int i = 0; i < length; i++) {
				board.boardArray[X+i][Y][0] = ID;
				
				//potential to change length to ID / remove ID entirely just for marking purposes
			}
		} else {
			for(int i = 0; i < length; i++) {
				board.boardArray[X][Y+i][0] = ID;
				
					}
				}
		board.IDNum++;
		ship.board = board.name;
			} else { 
				//note duplicate prefix, can be fixed later
				String prefix1 = "vertically";
				if(horizontal == true) {
					prefix1 = "horizontally";
					ship.isPlaced = false;
				}
				System.out.println("Ship of size (" + length + ") could not be placed " + prefix1 + " at coords: (" + (X+1) + "," + (Y+1) + ")\n");
			}
	// in the event that the ship cannot be placed when the IndexOutOfBoundsException occurs, reset that portion of the board to 0 
	// at that space and print out that the ship of the designated size can't be placed at that location
		} catch(IndexOutOfBoundsException e) {
			String prefix = "vertically";
			ship.isPlaced = false;
			try {
			if(horizontal == true) {
				prefix = "horizontally";
				
				
				for(int i = 0; i < length; i++) {
					board.boardArray[X+i][Y][0] = 0;
				} 
					} else {
						for(int i = 0; i < length; i++) {
							board.boardArray[X][Y+i][0] = 0;
						}
				}
			//2nd catch block, can't set board back if there's an exception, so it needs a second catch
			} catch(IndexOutOfBoundsException f) {
				ship.isPlaced = false;
				
				
			}
			System.out.println("Ship of size (" + length + ") could not be placed " + prefix + " at coords: (" + (X+1) + "," + (Y+1) + ")\n");
			//destroy ship in memory
			ship = null;
			
		}
	}
	
	//Hashmaps for Ships (Name, Ship) and HealthSystems(HealthSystem,Ship)
public static Map<String, shipFX> shipMap = new HashMap<>();

public static Map<shipFX, HealthSystem>  healthMap = new HashMap<>();
	
	public static shipFX createShipFX(String name, int length, boolean horizontal,  int xPos, int yPos, int id) {
		shipFX ship = new shipFX(length, horizontal, xPos, yPos, id);
		HealthSystem health = new HealthSystem(length);
		shipMap.put(name, ship);
		healthMap.put(ship, health);
		return ship;
	}
	
	public static shipFX getShipName(String name) {
		shipFX ship = shipMap.get(name);
		return ship;
	}
	
	public static HealthSystem getHealthSystemName(shipFX ship) {
		HealthSystem health = healthMap.get(ship);
		return health;
	}
	
	public static void Deconstruct(Object object) {
//		System.out.println("Object " + '"' + object + '"' + " deconstructed");
		object = null;
		System.gc();
		
//		System.out.println("Object is now: " + object);
	}
	
	public static int charToInt(char c) {
		int result = 0;
		
		switch(c) {
		case('0'):
			result = 0;
			break;
		case('1'):
			result = 1;
			break;
		case('2'):
			result = 2;
			break;
		case('3'):
			result = 3;
			break;
		case('4'):
			result = 4;
			break;
		case('5'):
			result = 5;
			break;
		case('6'):
			result = 6;
			break;
		case('7'):
			result = 7;
			break;
		case('8'):
			result = 8;
			break;
		case('9'):
			result = 9;
			break;
		}
		
		return result;
	}
	

	
	
	
	
	public static void main(String[] args) {
//		launch(args);
		
		Scanner input = new Scanner(System.in);
		Random rand = new Random();

		
		//Create player's board
		board player = new board("Player",10,10,2);
		System.out.println(player.toString(0));
		board enemy = new board("Enemy",10,10,1);
		
		AI enemyAI = new AI(player);
		enemyAI.guessState = 1;
		
		boolean locationStored = false;
		
		int gameState = 0;
		
		boolean playerTurn = true;
		boolean sunkShipThisTurn = false;
		
	while(true) {
		while(gameState == 0) {
		//Prompt player to place in their ships
		for(int i = 0; i < 5; i++) {
			
			String nameBuffer = "";
			int lengthBuffer;
//			int xPosBuffer = 0;
//			int yPosBuffer = 0;
//			boolean horizontalBuffer = true;
			
			switch(i) {
			case(0):
				nameBuffer = "Aircraft Carrier";
				lengthBuffer = 5;
//				xPosBuffer = 1;
//				yPosBuffer = 1;
				break;
			case(1):
				nameBuffer = "Battleship";
				lengthBuffer = 4;
//				xPosBuffer = 1;
//				yPosBuffer = 3;
				break;
			case(2):
				nameBuffer = "Cruiser";
				lengthBuffer = 3;
//				xPosBuffer = 1;
//				yPosBuffer = 5;
				break;
			case(3):
				nameBuffer = "Submarine";
				lengthBuffer = 3;
//				xPosBuffer = 1;
//				yPosBuffer = 7;
				break;
			case(4):
				nameBuffer = "Destroyer";
				lengthBuffer = 2;
//				xPosBuffer = 1;
//				yPosBuffer = 9;
				break;
			default:
				nameBuffer = "Foo";
				lengthBuffer = 1;
			}
		System.out.printf("Ship being placed: %S%nLength: %d%n",nameBuffer,lengthBuffer);	
		System.out.printf("Horizontal? (true/false)%n");
		boolean horizontalBuffer = input.nextBoolean();
		System.out.printf("X coord on grid: %n");
		int xPosBuffer = input.nextInt();
		System.out.printf("Y coord on grid: %n");
		int yPosBuffer = input.nextInt();
		
		createShipFX(nameBuffer, lengthBuffer, horizontalBuffer, xPosBuffer, yPosBuffer, player.IDNum);
		placeShip(getShipName(nameBuffer),player);
	
		
		//if the ship was not properly placed, recycle through that iteration
		if(getShipName(nameBuffer).isPlaced == false)
			i--;
		System.out.println(player.toString(0));
		}
		
		
		
		
		
		//Randomly place ships for the enemy board
		for(int i = 0; i < 5; i++) {
			String nameBuffer = "";
			int lengthBuffer;
			boolean horizontalBuffer;
			
			switch(i) {
			case(0):
				nameBuffer = "Enemy Aircraft Carrier";
				lengthBuffer = 5;
				break;
			case(1):
				nameBuffer = "Enemy Battleship";
				lengthBuffer = 4;
				break;
			case(2):
				nameBuffer = "Enemy Cruiser";
				lengthBuffer = 3;
				break;
			case(3):
				nameBuffer = "Enemy Submarine";
				lengthBuffer = 3;
				break;
			case(4):
				nameBuffer = "Enemy Destroyer";
				lengthBuffer = 2;
				break;
			default:
				nameBuffer = "Bar";
				lengthBuffer = 1;
			}
			
			int xMin = 0;
			int xMax = 9;
			int randomXPos = (int)Math.floor(Math.random() * (xMax - xMin + 1) + xMin);
			
			int yMin = 0;
			int yMax = 9;
			int randomYPos = (int)Math.floor(Math.random() * (yMax - yMin + 1) + yMin);
			
			int horizontalMin = 0;
			int horizontalMax = 1;
			int randomHorizontal = (int)Math.floor(Math.random() * (horizontalMax - horizontalMin + 1) + horizontalMin);
			
			if(randomHorizontal == 1)
				horizontalBuffer = true;
			else 
				horizontalBuffer = false;
			
			createShipFX(nameBuffer, lengthBuffer, horizontalBuffer, randomXPos, randomYPos, enemy.IDNum);
			placeShip(getShipName(nameBuffer),enemy);
			
			//if the ship was not properly placed, recycle through that iteration
			if(getShipName(nameBuffer).isPlaced == false) {
				
				i--;
				
				//Destroy/remove object from memory
				healthMap.remove(getShipName(nameBuffer),getHealthSystemName(getShipName(nameBuffer)));
				
				Deconstruct(getHealthSystemName(getShipName(nameBuffer)));
				Deconstruct(getShipName(nameBuffer));
				
			}
			
			//If enemy placement phase is over, switch gameState to 1
			if(getShipName(nameBuffer).isPlaced == true && getShipName(nameBuffer).Size == 2) {
				
				gameState = 1;

			}
		}
		
		System.out.println("Gamestate: " + gameState);
		
		}
		
		System.out.print(enemy.toString(0));
//		System.out.print(shipMap.toString()+"\n\n");
//		System.out.print(healthMap.toString());
		//Create a loop that alternates between the player firing and the enemy firing
		
		
		/* Gamestate key:
		 * gameState = 0 -- In ship placement mode for player and enemy
		 * gameState = 1 -- Player turn
		 * gameState = 2 -- Enemy turn
		 * gameState = 3 -- Perform checks to see if a player has won
		 * gameState = 4 -- Declare victories
		 */
		
		//Player Turn Programming
		
		while(gameState == 1) {
			System.out.println("\n\n\nYOUR TURN!\nSelect a tile to fire at.\nDo this by entering the X/Y coordinate\nof the tile youd like to shoot.\nX Coord:\n");
			int playerTileTargetX = (input.nextInt()-1);
			System.out.println("Y Coord:\n");
			int playerTileTargetY = (input.nextInt()-1);
			
			String prefix = "";
			
			//Check tile on enemy board corresponding to player's input
			if(enemy.getValue(playerTileTargetX,playerTileTargetY,0) == 0 || enemy.getValue(playerTileTargetX, playerTileTargetY, 0) == 10 || enemy.getValue(playerTileTargetX, playerTileTargetY, 0) == 11) {
				enemy.insertValue(playerTileTargetX, playerTileTargetY, 0, 11);
				System.out.println("MISS!\n");
				
				if(enemyAI.guessState == 3 || enemyAI.guessState == 4) {
					
				}
			} else {
				
				
				//Check location to figure out which object is there
				
				String shipTargeted = "";
				
				
				switch(enemy.getValue(playerTileTargetX, playerTileTargetY, 0)) {
				case(1):
					shipTargeted = "Enemy Aircraft Carrier";
					break;
				case(2):
					shipTargeted = "Enemy Battleship";
					break;
				case(3):
					shipTargeted = "Enemy Cruiser";
					break;
				case(4):
					shipTargeted = "Enemy Submarine";
					break;
				case(5):
					shipTargeted = "Enemy Destroyer";
					break;
					
				}
				
				//Deal Damage and check if ship is sunk
				getHealthSystemName(getShipName(shipTargeted)).health --;
				if(getHealthSystemName(getShipName(shipTargeted)).health <= 0) {
					getShipName(shipTargeted).Sunk = true;
					prefix = "You've sunk the enemy " + shipTargeted + "!\n";
					
					sunkShipThisTurn = true;
				}
				//assign value at position in grid to 10 (damaged value)
				enemy.insertValue(playerTileTargetX, playerTileTargetY, 0, 10);
				System.out.println("HIT!\n" + prefix + "\n");
			}
			
			System.out.println(enemy.toString(0));
			
			//Assign gamestate to 2 to allow enemy to handle turn
			//OR if player sunk a ship check for victories.
			if(sunkShipThisTurn == true) {
			gameState = 3;
			playerTurn = true;
			}
			else
				gameState = 2;
		}
		
		
		
		
		//Enemy Turn Programming
		
		while(gameState == 2) {
			//Start off by scanning the board to see what kind of guess should be used vs the player
			//guessState == 1: Random guess (True random)
			//guessState == 2: Standard guess (Ignores positions that have been guessed before)
			//guessState == 3: Adjacent Guess (Based on previous hits, ship must be connected at adjacent points)
			
//			enemyAI.scanBoard();
			
			String guessCoord = "";
			int enemyTileTargetX = 0;
			int enemyTileTargetY = 0;
			
			System.out.println(enemyAI.guessState+"\n");
			if(enemyAI.guessState == 1) {
				guessCoord = enemyAI.guessRandom();
				enemyAI.guessState = 2;
			}
			
			else if(enemyAI.guessState == 2) {
				guessCoord = enemyAI.guessStandard();
			}
			
			else if(enemyAI.guessState == 3) {
				guessCoord = enemyAI.guessTwos();
			}
			
			
			for(int i = 0; i < guessCoord.length(); i++) {
				if(i==0)
					enemyTileTargetX = charToInt(guessCoord.charAt(i));
				if(i==2)
					enemyTileTargetY = charToInt(guessCoord.charAt(i));
				
			}
			
			
			String prefix = "";
			
			
			//Check tile on enemy board corresponding to player's input
			if(player.getValue(enemyTileTargetX,enemyTileTargetY,0) == 0 || player.getValue(enemyTileTargetX, enemyTileTargetY, 0) == 10) {
				player.insertValue(enemyTileTargetX, enemyTileTargetY, 0, 11);
				System.out.println("ENEMY MISS!\n");
				if(enemyAI.guessState == 3) {
					if(player.getValue(enemyTileTargetX, enemyTileTargetY, 1) == 2)
						player.insertValue(enemyTileTargetX, enemyTileTargetY, 1, 1);
					
					
				}
			} else {
				
				
				//Check location to figure out which object is there
				
				String shipTargeted = "";
				
				
				switch(player.getValue(enemyTileTargetX, enemyTileTargetY, 0)) {
				case(1):
					shipTargeted = "Aircraft Carrier";
					break;
				case(2):
					shipTargeted = "Battleship";
					break;
				case(3):
					shipTargeted = "Cruiser";
					break;
				case(4):
					shipTargeted = "Submarine";
					break;
				case(5):
					shipTargeted = "Destroyer";
					break;
					
				}
				
				//Deal Damage and check if ship is sunk
				getHealthSystemName(getShipName(shipTargeted)).health --;
				player.insertValue(enemyTileTargetX, enemyTileTargetY, 1, 3);
				
				//If this is the first time the particular ship has been damaged, store the initial hit position
				
				if((getHealthSystemName(getShipName(shipTargeted)).health < (getShipName(shipTargeted)).Size) && locationStored == false) {
					enemyAI.nativeShipFirstHitX = enemyTileTargetX;
					enemyAI.nativeShipFirstHitY = enemyTileTargetY;
					locationStored = true;
					System.out.println("Ship native first hit location: " + enemyAI.nativeShipFirstHitX + "," + enemyAI.nativeShipFirstHitY);
				} else {
					System.out.println("Ship location not stored");
				}
				
				if(getHealthSystemName(getShipName(shipTargeted)).health <= 0) {
					getShipName(shipTargeted).Sunk = true;
					prefix = "The enemy has sunk your " + shipTargeted + "!\n";
					enemyAI.flushGuessedDirections(1);
					enemyAI.nativeShipFirstHitX = 0;
					enemyAI.nativeShipFirstHitY = 0;
					locationStored = false;
					enemyAI.guessState = 2;
					gameState = 3;
					sunkShipThisTurn = true;
				}
				
				//assign value at position in grid to 10 (damaged value)
				player.insertValue(enemyTileTargetX, enemyTileTargetY, 0, 10);
				System.out.println("ENEMY HIT!\n" + prefix + "\n");
				
				//If the enemy hits while guessing randomly, or standard guessing, set guessState to 3
				if((enemyAI.guessState == 1 || enemyAI.guessState == 2) && gameState == 2) {
				enemyAI.assignAdjacent(enemyTileTargetX, enemyTileTargetY, 2, 0);
				enemyAI.guessState = 3;
				}
				
				//If the enemy hits while guessing adjacently, set a direction to continue guessing in and set guessState to 4
				else if((enemyAI.guessState == 3) && gameState == 2) {
					enemyAI.flushGuessedDirections(0);
					
					

					
					//predicate logic for determining direction 
					//(NOTE: DIRECTIONS ARE REVERSED BECAUSE 2 DIMENSIONAL ARRAYS APPEAR WITH ORIGIN AT TOP LEFT INSTEAD OF BOTTOM LEFT)
					//up
					if((enemyTileTargetX == enemyAI.nativeShipFirstHitX) && (enemyTileTargetY < enemyAI.nativeShipFirstHitY) && (!enemyAI.guessedDirections.contains("up"))) {
					enemyAI.guessDirection = "up";
					System.out.println("Direction changed to: " + enemyAI.guessDirection);
					}
					//down
					else if((enemyTileTargetX == enemyAI.nativeShipFirstHitX) && (enemyTileTargetY > enemyAI.nativeShipFirstHitY) && (!enemyAI.guessedDirections.contains("down"))) {
					enemyAI.guessDirection = "down";
					System.out.println("Direction changed to: " + enemyAI.guessDirection);
					}
					//left
					else if((enemyTileTargetX < enemyAI.nativeShipFirstHitX) && (enemyTileTargetY == enemyAI.nativeShipFirstHitY) && (!enemyAI.guessedDirections.contains("left"))) {
					enemyAI.guessDirection = "left";
					System.out.println("Direction changed to: " + enemyAI.guessDirection);
					}
					//right
					else if((enemyTileTargetX > enemyAI.nativeShipFirstHitX) && (enemyTileTargetY == enemyAI.nativeShipFirstHitY) && (!enemyAI.guessedDirections.contains("right"))) {
					enemyAI.guessDirection = "right";
					System.out.println("Direction changed to: " + enemyAI.guessDirection);
					} else 
						System.out.println("Direction was not changed.");
					
					System.out.println("Tile targeted: " + enemyTileTargetX + "," + enemyTileTargetY + "Initial hit: " + enemyAI.nativeShipFirstHitX + "," + enemyAI.nativeShipFirstHitY + " Direction: " + enemyAI.guessDirection);
					
				
					
					//Based on direction, insert 2 adjacent to either end of guessed hits
					try {
						if(!((enemyTileTargetY == 0 || enemyTileTargetY == 9) && (enemyTileTargetX <= 0 && enemyTileTargetX >= 9)) || ((enemyTileTargetX == 0 || enemyTileTargetX == 9) && (enemyTileTargetY <= 0 && enemyTileTargetY >= 9))) {
					switch(enemyAI.guessDirection) {
					case("up"):
						if(player.getValue(enemyTileTargetX, enemyTileTargetY-1, 1) == 0)
						player.insertValue(enemyTileTargetX, enemyTileTargetY-1, 1, 2);
						if(player.getValue(enemyAI.nativeShipFirstHitX, enemyAI.nativeShipFirstHitY+1, 1) == 0)
						player.insertValue(enemyAI.nativeShipFirstHitX, enemyAI.nativeShipFirstHitY+1, 1, 2);
						System.out.println("2 placed at" + enemyTileTargetX + "," + (enemyTileTargetY-1) + " and at " + enemyAI.nativeShipFirstHitX + "," + (enemyAI.nativeShipFirstHitY+1));
						break;
					case("down"):
						if(player.getValue(enemyTileTargetX, enemyTileTargetY+1, 1) == 0)
						player.insertValue(enemyTileTargetX, enemyTileTargetY+1, 1, 2);
					if(player.getValue(enemyAI.nativeShipFirstHitX, enemyAI.nativeShipFirstHitY-1, 1) == 0)
						player.insertValue(enemyAI.nativeShipFirstHitX, enemyAI.nativeShipFirstHitY-1, 1, 2);
						System.out.println("2 placed at" + enemyTileTargetX + "," + (enemyTileTargetY+1) + " and at " + enemyAI.nativeShipFirstHitX + "," + (enemyAI.nativeShipFirstHitY-1));
						break;
					case("left"):
						if(player.getValue(enemyTileTargetX-1, enemyTileTargetY, 1) == 0)
						player.insertValue(enemyTileTargetX-1, enemyTileTargetY, 1, 2);
						if(player.getValue(enemyAI.nativeShipFirstHitX+1, enemyAI.nativeShipFirstHitY, 1) == 0)
						player.insertValue(enemyAI.nativeShipFirstHitX+1, enemyAI.nativeShipFirstHitY, 1, 2);
						System.out.println("2 placed at" + (enemyTileTargetX-1) + "," + enemyTileTargetY + " and at " + (enemyAI.nativeShipFirstHitX+1) + "," + enemyAI.nativeShipFirstHitY);
						break;
					case("right"):
						if(player.getValue(enemyTileTargetX+1, enemyTileTargetY, 1) == 0)
						player.insertValue(enemyTileTargetX+1, enemyTileTargetY, 1, 2);
						if(player.getValue(enemyAI.nativeShipFirstHitX-1, enemyAI.nativeShipFirstHitY, 1) == 0)
						player.insertValue(enemyAI.nativeShipFirstHitX-1, enemyAI.nativeShipFirstHitY, 1, 2);
						System.out.println("2 placed at" + (enemyTileTargetX+1) + "," + enemyTileTargetY + " and at " + (enemyAI.nativeShipFirstHitX-1) + "," + enemyAI.nativeShipFirstHitY);
						break;
					}

						}
				} catch (IndexOutOfBoundsException e) {
					
				}
				}
				
				
			}
//			enemyAI.fixZDim();
			
			System.out.println(player.toString(1));
			
			//Assign gamestate to 1 to allow player to handle turn
			//OR if the enemy sunk a ship this turn, check for victories
			if(sunkShipThisTurn == true) {
			playerTurn = false;
			gameState = 3;
			} else
				gameState = 1;
			
		}
		
		//Perform Checks to see if someone has won
		
		while(gameState == 3) {
			System.out.println("Checking for victories...");
			//Check to see if all enemy ships are sunk
			if((getShipName("Enemy Aircraft Carrier").Sunk == true) && (getShipName("Enemy Battleship").Sunk == true) && (getShipName("Enemy Cruiser").Sunk == true) && (getShipName("Enemy Submarine").Sunk == true) && (getShipName("Enemy Destroyer").Sunk == true)) {
				System.out.println("YOU WIN!\nVICTORY!");
				input.close();
				System.exit(0);
				
			}
			//Check to see if all of player's ships are sunk
			if((getShipName("Aircraft Carrier").Sunk == true) && (getShipName("Battleship").Sunk == true) && (getShipName("Cruiser").Sunk == true) && (getShipName("Submarine").Sunk == true) && (getShipName("Destroyer").Sunk == true)) {
				System.out.println("YOU LOSE!\nDEFEAT!");
				input.close();
				System.exit(0);
			}
			System.out.println("No victory found!");
			System.out.print(shipMap);
			System.out.print(healthMap);
			
			sunkShipThisTurn = false;
			
			if(playerTurn == true) {
				gameState = 2;
				playerTurn = false;
			}
			else {
				gameState = 1;
				playerTurn = true;
				enemyAI.flushGuessedDirections(1);
				enemyAI.guessState = 2;
			}
			
		}
		
		
		
	
		
		
		
		
		
		
		
		
	}
		
		
		
		
		
	}
	
	
	
}
