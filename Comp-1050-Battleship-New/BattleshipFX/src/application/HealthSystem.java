package application;

public class HealthSystem {

	
	public int size;
	public int health;
	


public HealthSystem(int Size) {
	size = Size;
	health = size;
	}

public String toString() {
	return "Size: " + size + " | Health = " + health;
}
}