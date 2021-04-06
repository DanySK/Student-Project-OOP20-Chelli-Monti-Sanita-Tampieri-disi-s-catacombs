package eu.eutampieri.catacombs.model;

/**
 * Abstract class for every game object (ex: Player, enemies, items, ecc...).
 */
public abstract class GameObject {

    /**
     * Object positions.
     */
    protected int posX, posY;
    /**
     * Entity kind.
     */
    protected EntityKind kind;
    /**
     * Object speed.
     */
    protected int speedX, speedY;

    /**
     * @param x  object X position
     * @param y  object Y position
     * @param kind Entity Kind @see eu.eutampieri.catacombs.model.EntityKind
     */
    public GameObject(final int x, final int y, final EntityKind kind) {
        this.setPosX(x);
        this.setPosY(y);
        this.kind = kind;
    }

    /**
     * Method used in the game loop that updates all elements of a game obj.
     * 
     * @param delta time between updates
     */
    public abstract void update(float delta);

    /**
     * Renders object with the corresponding sprite.
     */
    public abstract void render();

    /**
     * Getter for object X position.
     * 
     * @return Object X position
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Setter for object X position.
     * 
     * @param posX Object X position to be set
     */
    public void setPosX(final int posX) {
        this.posX = posX;
    }

    /**
     * Getter for object Y position.
     * 
     * @return Object Y position
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Setter for object Y position.
     * 
     * @param posY Object Y position to be set
     */
    public void setPosY(final int posY) {
        this.posY = posY;
    }

    /**
     * Set position corresponding to coordinates (posX, posY).
     * 
     * @param posX Position on the X axis
     * @param posY Position on the Y axis
     */
    public void setPos(final int posX, final int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Getter for object speed on the X axis.
     * 
     * @return Object X speed
     */
    public int getSpeedX() {
        return speedX;
    }

    /**
     * Setter for object speed on the X axis.
     * 
     * @param speedX Speed to be set
     */
    public void setSpeedX(final int speedX) {
        this.speedX = speedX;
    }

    /**
     * Getter for object speed on the Y axis.
     * 
     * @return Object Y speed
     */
    public int getSpeedY() {
        return speedY;
    }

    /**
     * Setter for object speed on the Y axis.
     * 
     * @param speedY Speed to be set
     */
    public void setSpeedY(final int speedY) {
        this.speedY = speedY;
    }

    /**
     * Set both speeds to a specified value (both X and Y).
     * 
     * @param speed Speed to be set
     */
    public void setSpeed(final int speed) {
        this.setSpeedX(speed);
        this.setSpeedY(speed);
    }

    /**
     * Getter for Entity Kind.
     * 
     * @return Entity Kind
     */
    public EntityKind getKind() {
        return kind;
    }
}
