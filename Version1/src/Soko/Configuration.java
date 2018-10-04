package Soko;

import java.util.*;

/**
 * élément dynamique : joueur (variable d instance) + caisse (array list) 
 */
public class Configuration {

    /**
     * Default constructor
     */
    public Configuration() {
    }

    /**
     * 
     */
    public ArrayList<Box> boxs;

    /**
     * 
     */
    public Player player;

    /**
     * 
     */
    public Level level;

    /**
     * @param Level 
     * @param positionPlayer
     */
    public void Configuration(Level level, Position positionPlayer) {
        // TODO implement here
    }

    /**
     * @param Configuration
     */
    public void Configuration(Configuration config) {
        // TODO implement here
    }

    /**
     * @param Position 
     * @return
     */
    public boolean addBox(Position position) {
        // TODO implement here
        return false;
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
     * @return
     */
    public Level getLevel() {
        // TODO implement here
        return null;
    }

    /**
     * @param Position 
     * @return
     */
    public Element get(Position position) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Player getPlayer() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public ArrayList<Box> getBoxes() {
        // TODO implement here
        return null;
    }

    /**
     * @param Position 
     * @return
     */
    public boolean isEmpty(Position position) {
        // TODO implement here
        return false;
    }

    /**
     * @param Position 
     * @return
     */
    public boolean isTarget(Position position) {
        // TODO implement here
        return false;
    }

    /**
     * @param Direction 
     * @return
     */
    public boolean movePlayerTo(Direction direction) {
        // TODO implement here
        return false;
    }

    /**
     * @return
     */
    public boolean win() {
        // TODO implement here
        return false;
    }

}