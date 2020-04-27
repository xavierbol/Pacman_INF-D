package pacman_infd.enums;

import java.awt.*;

public enum FruitType {
    CHERRY(Color.red, 100),
    STRAWBERRY(Color.MAGENTA, 300),
    ORANGE(Color.orange, 500),
    APPLE(Color.green, 700),
    MELON(Color.yellow, 1000);

    private final Color color;
    private final int points;

    /**
     * Constructor of FruitType.
     *
     * @param color     the color of the fruit type.
     * @param points    the points that the Pacman can gain when he eats this type of fruit.
     */
    FruitType(Color color, int points) {
        this.color = color;
        this.points = points;
    }

    /**
     * Return the points based on the current type of fruit.
     *
     * @return the points.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Return the color of the current type fruit
     *
     * @return the color.
     */
    public Color getColor() {
        return color;
    }
}
