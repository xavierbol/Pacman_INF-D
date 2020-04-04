package pacman_infd.Elements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pacman_infd.Enums.Direction;
import pacman_infd.Enums.ElementType;

import static org.junit.jupiter.api.Assertions.*;

class WallTest extends GameElementTest {
    Pacman pacman;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        pacman = new Pacman(gameWorld.getCell(0,0), gameWorld.getEventHandler(), 0);
    }

    @Test
    public void testCreate() {
        assert (gameWorld.getCell(1,0).getStaticElement() == null);
        new Wall(gameWorld.getCell(1,0), ElementType.VERTICAL_WALL);
        assert (gameWorld.getCell(1,0).getStaticElement() instanceof Wall);
    }

    @Test
    public void testPacmanBlocked() {
        new Wall(gameWorld.getCell(1,0), ElementType.VERTICAL_WALL);
        pacman.changeDirection(Direction.RIGHT);
        pacman.move();
        gameWorld.getEventHandler().movingElementActionPerformed(pacman);

        // Check pacman doesn't move
        assert (pacman.getCell().equals(pacman.getStartCell()));
        assert (pacman.getCurrentDirection() == null);
    }
}