package eu.eutampieri.catacombs.model;

import java.util.List;

import eu.eutampieri.catacombs.model.map.TileMap;
import eu.eutampieri.catacombs.ui.gamefx.AssetManagerProxy;

public final class Projectile extends GameObject implements HealthModifier {
    private final TileMap map;
    private final int strength;
    private boolean toErase;
     private static final int BOX_SIZE = 8;

    public Projectile(final int x, final int y, final int sx, final int sy, final int strength, final TileMap map, final Team team) {
        super(x, y, GameObjectType.BULLET, new CollisionBox(x, y, BOX_SIZE, BOX_SIZE), team);
        this.speedX = sx;
        this.speedY = sy;
        this.strength = strength;
        this.map = map;
    }

    @Override
    public List<GameObject> update(final long delta, final List<GameObject> others) {
        posX += this.speedX;
        posY += this.speedY;
        this.hitBox.move(this.speedX, this.speedY);
        for (final var o : others) {
            if (o instanceof LivingCharacter && o.getHitBox().overlaps(this.getHitBox())
                    && o.getTeam() != this.getTeam()) {
                this.useOn((LivingCharacter) o);
                this.toErase = true;
                break;
            }
        }
        if (!map.at(this.posX / AssetManagerProxy.getMapTileSize(), this.posY / AssetManagerProxy.getMapTileSize())
                .isWalkable()
                || !map.at((this.posX + this.getHitBox().getWidth()) / AssetManagerProxy.getMapTileSize(),
                this.posY / AssetManagerProxy.getMapTileSize()).isWalkable()
                || !map.at(this.posX / AssetManagerProxy.getMapTileSize(),
                (this.posY + this.getHitBox().getHeight()) / AssetManagerProxy.getMapTileSize()).isWalkable()
                || !map.at((this.posX + this.getHitBox().getWidth()) / AssetManagerProxy.getMapTileSize(),
                (this.posY + this.getHitBox().getHeight()) / AssetManagerProxy.getMapTileSize()).isWalkable()) {
            this.toErase = true;
        }
        return List.of();
    }

    @Override
    public boolean isMarkedForDeletion() {
        return this.toErase;
    }

    @Override
    public int getHealthDelta() {
        return -strength;
    }

    @Override
    public String getName() {
        return "bullet";
    }
}
