package Soko;

import java.util.*;

/**
 *  élément statique : mur + case déplacement en tab 2D
 *  JE PENSE QUE C EST SUPPRIMABLE
 */
public class Level {

    /**
     * Default constructor
     */
    public Level() {
    }

    /**
     * 
     */
    private Immobile[][] grid;

    /**
     * 
     */
    private ArrayList<Position> targets;

    /**
     * @param x 
     * @param y
     */
    public void Level(int x, int y) {
        // TODO implement here
    }

    /**
     * @return
     */
    public int getX() {
        // TODO implement here
        return 0;
    }

    /**
     * @return
     */
    public int getY() {
        // TODO implement here
        return 0;
    }

    /**
     * @param Position 
     * @return
     */
    public boolean addTarget(Position position) {
        // TODO implement here
        return false;
    }

    /**
     * @param Position 
     * @return
     */
    public boolean addWall(Position position) {
        // TODO implement here
        return false;
    }

    /**
     * @param Position 
     * @return
     */
    public boolean beTarget(Position position) {
        // TODO implement here
        return false;
    }

    /**
     * @param Position 
     * @return
     */
    public boolean beEmpty(Position position) {
        // TODO implement here
        return false;
    }

    /**
     * @param Position 
     * @return
     */
    public Element get(Position position) {
        // TODO implement here
        return null;
    }

}