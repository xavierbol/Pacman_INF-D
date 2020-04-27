package pacman_infd.enums;

public enum ElementType {
    EMPTY(),
    PACMAN('P'),
    CLYDE_GHOST('d'),
    PINKY_GHOST('b'),
    BLINKY_GHOST('a'),
    INKY_GHOST('c'),
    PELLET('0'),
    SUPER_PELLET('2'),
    HORIZONTAL_WALL('S'),
    VERTICAL_WALL('A'),
    UP_LEFT_CORNER_WALL('Q'),
    UP_RIGHT_CORNER_WALL('W'),
    DOWN_LEFT_CORNER_WALL('R'),
    DOWN_RIGHT_CORNER('E'),
    T_UP_WALL('I'),
    T_DOWN_WALL('J'),
    T_LEFT_WALL('G'),
    T_RIGHT_WALL('H'),
    EMPTY_WALL('D'),
    NO_ELEMENT('-');

    private final char element;

    /**
     * Constructor for the values of the element.
     *
     * @param element the element of the enum.
     */
    ElementType(char element) {
        this.element = element;
    }

    /**
     * Default constructor for the values of the element.
     */
    ElementType() {
        this.element = '\u0000';
    }

    /**
     * Return the value of the element given in parameter.
     *
     * @param element the element.
     * @return the value of this element.
     */
    public static ElementType valueOfElement(char element) {
        for (ElementType e : values()) {
            if (e.element == element) {
                return e;
            }
        }
        return EMPTY;
    }
}
