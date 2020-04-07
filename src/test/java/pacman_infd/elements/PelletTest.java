package pacman_infd.elements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pacman_infd.enums.Direction;

class PelletTest extends GameElementTest {
    Pacman pacman;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        pacman = new Pacman(gameWorld.getCell(0,0), gameWorld.getEventHandler(), 0);
    }

    /**
     * Test if the pellet is correctly create into the map
     */
    @Test
    public void testCreate() {
        assert (gameWorld.getCell(1,0).getStaticElement() == null);

        new Pellet(gameWorld.getCell(1,0), gameWorld.getEventHandler());

        assert (gameWorld.getCell(1,0).getStaticElement() instanceof Pellet);
    }

    /**
     * Test when pacman eats a pellet, if this pellet is removed the game
     * and if the score increase of 5 points
     */
    @Test
    void eatMe() {
        assert (scorePanel.getScore() == 0);
        new Pellet(gameWorld.getCell(1,0), gameWorld.getEventHandler());
        assert (gameWorld.getCell(1,0).getStaticElement() instanceof Pellet);

        pacman.changeDirection(Direction.RIGHT);
        pacman.move();
        gameWorld.getEventHandler().movingElementActionPerformed(pacman);
        assert (gameWorld.getCell(1,0).getStaticElement() == null);
        assert (scorePanel.getScore() == 5);
    }
}