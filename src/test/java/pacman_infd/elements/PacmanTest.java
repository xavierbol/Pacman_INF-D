package pacman_infd.elements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pacman_infd.enums.Direction;
import pacman_infd.enums.ElementType;
import pacman_infd.strategies.pacman.KeyControlledStrategy;

public class PacmanTest extends GameElementTest {
    Pacman p;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        p = new Pacman(gameWorld.getCell(0,0), gameWorld.getEventHandler(), 0, new KeyControlledStrategy(gameWorld));
    }

    @Test
    public void testCreate() {
        // Check if pacman is correctly created into the cell (0,0)
        assert (p.getCell().getXPos() == 0 && p.getCell().getYPos() == 0);
        assert (p.getStartCell().equals(p.getCell()));
        assert (p.getCurrentDirection() == null);

        // Check that pacman doesn't move, because the current direction is not set
        p.move();
        gameWorld.getEventHandler().movingElementActionPerformed(p);
        assert (p.getStartCell().equals(p.getCell()));
    }

    /**
     * Check if pacman move to the correct direction when we set the direction
     */
    @Test
    public void testMove() {
        assert (p.getCell().getXpos() == 0 && p.getCell().getYPos() == 0);

        p.changeDirection(Direction.RIGHT);
        p.move();
        gameWorld.getEventHandler().movingElementActionPerformed(p);

        assert (p.getCell().getXPos() == 1 && p.getCell().getYPos() == 0);
    }

    /**
     * test if the changement of the direction of pacman is correctly manage
     */
    @Test
    public void testChangeDirection() {
        new Wall(gameWorld.getCell(1,1), ElementType.HORIZONTAL_WALL);
        p.changeDirection(Direction.RIGHT);
        p.move();
        gameWorld.getEventHandler().movingElementActionPerformed(p);

        assert (p.getCurrentDirection().equals(Direction.RIGHT));
        assert (p.getCell().getXPos() == 1 && p.getCell().getYPos() == 0);

        // Try to go up, normally it is not possible because it is a wall
        // Then the current direction must always be RIGHT
        p.changeDirection(Direction.DOWN);
        assert (p.getCurrentDirection().equals(Direction.RIGHT));

        p.changeDirection(Direction.LEFT);
        p.move();
        gameWorld.getEventHandler().movingElementActionPerformed(p);

        // Now, we check that Pacman can change the direction to the down
        p.changeDirection(Direction.DOWN);
        assert (p.getCurrentDirection().equals(Direction.DOWN));
    }
}
