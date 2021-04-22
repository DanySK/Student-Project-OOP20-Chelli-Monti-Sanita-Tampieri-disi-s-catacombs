package eu.eutampieri.catacombs.model.gen;

import eu.eutampieri.catacombs.model.Entity;
import eu.eutampieri.catacombs.model.map.TileMap;

public interface SingleObject {
    Entity create(int x, int y, TileMap tm);
}
