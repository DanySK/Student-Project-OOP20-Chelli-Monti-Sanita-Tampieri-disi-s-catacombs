package eu.eutampieri.catcombs.model;

public abstract class GameObject {

	protected int posX, posY;
	protected ID id;
	protected velX, velY;
	
	public GameObject(int x, int y, ID id) {
		this.posX = x;
		this.posY = y;
		this.id = id;
	}

	//tba : setters getters 
}
