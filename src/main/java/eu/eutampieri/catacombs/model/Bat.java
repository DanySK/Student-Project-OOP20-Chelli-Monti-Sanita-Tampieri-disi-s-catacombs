package eu.eutampieri.catacombs.model;

import eu.eutampieri.catacombs.model.map.TileMap;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.Point;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Bat class - the bat is an enemy that mostly stands still and fires bullets.
 */
public final class Bat extends Entity {

    private static final int HEIGHT = 16;
    private static final int WIDTH = 16;
    private static final int MOVEMENT_SPEED = 3;
    private static final int HEALTH = 8;
    private static final int CB_POS_MOD = 10;
    private static final int CB_DIM_MOD = 35;
    private static final int BASE_DAMAGE = 2;
    private static final int BASE_FIRE_RATE = 4;
    private static final int BASE_PROJECTILE_SPEED = 15;
    private static final String NAME = "Bat";
    private static final long MOVE_DELAY = 5L * 100;
    private static final long PAUSE_DELAY = 10L * 100;

    private final Weapon weapon;
    private boolean isMoving;
    private long delayCounter;
    private long pauseCounter;
    private final CollisionBox radarBox;
    private final Point shootingDirection;

    /**
     * @param x       X spawn position
     * @param y       Y spawn position
     * @param tileMap Tile map in which Bat is spawned
     */
    public Bat(final int x, final int y, final TileMap tileMap) {
        super(x, y, WIDTH, HEIGHT, tileMap, GameObjectType.ENEMY, GameObject.Team.ENEMY);
        setSpeed(MOVEMENT_SPEED);
        setHealth(HEALTH);
        face = Direction.RIGHT;
        radarBox = new CollisionBox(posX - width * CB_POS_MOD, posY - width * CB_POS_MOD, width * CB_DIM_MOD,
                height * CB_DIM_MOD);
        weapon = new Weapon(this, tileMap, this.getHitBox().getPosX(), this.getHitBox().getPosY(),
                BASE_DAMAGE, BASE_PROJECTILE_SPEED, BASE_FIRE_RATE, this.getTeam()) { };
        shootingDirection = new Point(0, 0);
        this.delayCounter = 0;
        this.pauseCounter = 0;
        this.isMoving = true;

    }

    @Override
    public void update(final long delta, final List<GameObject> others) {
        resetShootingDirection();
        if (isMoving) {
            delayCounter += delta;
            if (delayCounter >= MOVE_DELAY) {
                delayCounter = 0;
                isMoving = false;
                resetMovement();
            }
        } else {
            pauseCounter += delta;
            if (pauseCounter >= PAUSE_DELAY) {
                pauseCounter = 0;
                isMoving = true;
                changeDirection();
            }
        }
        if (others.stream().filter((x) -> x instanceof Player)
                .findFirst()
                .get()
                .getHitBox()
                .overlaps(this.radarBox) && this.weapon.canFire) {
            setShootingDirection(others.stream().filter((x) -> x instanceof Player).findFirst().get());
        } else {
            this.weapon.setCanFire(false);
        }
        super.update(delta, others);
        updateRadarBoxLocation();
        weapon.update(delta, others);
    }

    @Override
    public Pair<Action, Direction> getActionWithDirection() {
        // TODO Auto-generated method stub

        return Pair.of(Action.MOVE, this.face);
    }

    @Override
    public boolean canPerform(final Action action) {
        return switch (action) {
            case ATTACK, MOVE -> true;
            default -> false;
        };
    }

    @Override
    public int getHealth() {
        return this.hp;
    }

    @Override
    public void setHealth(final int health) {
        this.hp = health;
    }

    /**
     * Utility class that makes the bat change movement direction. As of now bats
     * can only go left or right.
     */
    private void changeDirection() {
        if (face == Direction.RIGHT) {
            left = true;
            right = false;
            face = Direction.LEFT;
        } else {
            right = true;
            left = false;
            face = Direction.RIGHT;
        }
    }

    /**
     * Updates the aggro radar's Bat box.
     */
    private void updateRadarBoxLocation() {
        radarBox.setLocation(posX - width * CB_POS_MOD, posY - height * CB_POS_MOD);
    }

    public String getName() {
        return Bat.NAME;
    }

    @Override
    public List<GameObject> spawnObject(){
        if (this.weapon.canFire) {
            return weapon.fire((int)this.getShootingDirection().getX(), (int)this.getShootingDirection().getY());
        }
        return List.of();
    }

    public Point getShootingDirection() {
        return this.shootingDirection;
    }

    public void resetShootingDirection() {
        this.shootingDirection.setLocation(0, 0);
    }

    public void setShootingDirection(final GameObject e) {
        if (e == null) {
            return;
        }
        final int x = Integer.compare(e.getHitBox().getPosX(), this.getHitBox().getPosX());
        final int y = Integer.compare(e.getHitBox().getPosY(), this.getHitBox().getPosY());
        this.shootingDirection.setLocation(x, y);
    }

}
