package pacman_infd.elements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pacman_infd.enums.Direction;
import pacman_infd.enums.PortalType;
import pacman_infd.strategies.pacman.KeyControlledStrategy;

class PortalTest extends GameElementTest {
    Pacman pacman;
    Portal bluePortal;
    Portal orangePortal;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        pacman = new Pacman(gameWorld.getCell(0,0), gameWorld.getEventHandler(), 0, new KeyControlledStrategy(gameWorld));
    }

    @Test
    public void testCreate() {
        assert (gameWorld.getCell(1,0).getStaticElement() == null);
        assert (gameWorld.getCell(0,1).getStaticElement() == null);
        bluePortal = new Portal(gameWorld.getCell(1,0), PortalType.BLUE);
        orangePortal = new Portal(gameWorld.getCell(0,1), PortalType.ORANGE);
        assert (gameWorld.getCell(1,0).getStaticElement() instanceof Portal);
        assert (gameWorld.getCell(0,1).getStaticElement() instanceof Portal);

        bluePortal.setLinkedPortal(orangePortal);
        assert (bluePortal.getLinkedPortal().equals(orangePortal));
        orangePortal.setLinkedPortal(bluePortal);
        assert (orangePortal.getLinkedPortal().equals(bluePortal));
    }

    @Test
    void remove() {
        bluePortal = new Portal(gameWorld.getCell(1,0), PortalType.BLUE);
        orangePortal = new Portal(gameWorld.getCell(0,1), PortalType.ORANGE);

        bluePortal.remove();
        assert (gameWorld.getCell(1,0).getStaticElement() == null);
    }

    @Test
    public void testPortalOnPacman() {
        bluePortal = new Portal(gameWorld.getCell(1,0), PortalType.BLUE);
        orangePortal = new Portal(gameWorld.getCell(0,1), PortalType.ORANGE);

        bluePortal.setLinkedPortal(orangePortal);
        orangePortal.setLinkedPortal(bluePortal);
        bluePortal.warpNeighbors();
        orangePortal.warpNeighbors();

        pacman.changeDirection(Direction.RIGHT);
        pacman.move();
        gameWorld.getEventHandler().movingElementActionPerformed(pacman);

        assert (pacman.getCell().equals(orangePortal.getCell()));
    }
}