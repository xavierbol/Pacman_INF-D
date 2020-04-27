package pacman_infd.elements;

import pacman_infd.enums.FruitType;
import pacman_infd.games.Cell;
import pacman_infd.listeners.ElementEventListener;
import pacman_infd.utils.SoundManager;

import java.awt.*;

public class Fruit extends GameElement implements Eatable {
    private final FruitType type;

    /**
     * Constructor of the class
     *
     * @param fruitType The type of fruit.
     * @param cell      The cell containing the fruit
     * @param evtl      The EventHandler of the game
     */
    public Fruit(FruitType fruitType, Cell cell, ElementEventListener evtl) {
        super(cell, evtl);
        this.type = fruitType;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.type.getColor());
        g.drawOval(
                (int) getPosition().getX(),
                (int) getPosition().getY(),
                15,
                15
        );
    }

    @Override
    public void eatMe() {
        elementEventListener.eatableElementEaten(this);

        if (cell.getStaticElement().equals(this)) {
            cell.setStaticElement(null);
        }
        SoundManager.playSound("fruit");
    }

    @Override
    public int getValue() {
        return this.type.getPoints();
    }
}
