package pacman_infd.strategies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pacman_infd.elements.Pacman;
import pacman_infd.games.*;
import pacman_infd.strategies.ghost.ChasePacmanStrategy;
import pacman_infd.strategies.pacman.KeyControlledStrategy;

class ChasePacmanStrategyTest extends GhostStrategyTest {
    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        ghostStrategy = new ChasePacmanStrategy();
    }

    @Test
    public void testGiveNextCell() {
        // Set up moving element in the GameWorld
        Pacman pacman = new Pacman(gameWorld.getCell(0,0), null, 100, new KeyControlledStrategy());

        // Let's imagine the ghost is in (1, 0), we use the giveNextCell to go to pacman
        Cell cellPacman = ghostStrategy.giveNextCell(gameWorld.getCell(1,0));

        assert (cellPacman.equals(pacman.getCell()));
    }
}