package pacman_infd.enums;

import java.awt.*;

public enum FruitType {
    CHERRY(Color.red, 100),
    STRAWBERRY(Color.MAGENTA, 300),
    ORANGE(Color.orange, 500),
    APPLE(Color.green, 700),
    MELON(Color.yellow, 1000);

    private Color color;
    private int points;

    FruitType(Color color, int points) {
        this.color = color;
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public Color getColor() {
        return color;
    }
}
