package pacman_infd.strategies;

import org.junit.jupiter.api.BeforeEach;
import pacman_infd.games.GameController;
import pacman_infd.games.GameWorld;
import pacman_infd.games.ScorePanel;
import pacman_infd.games.View;
import pacman_infd.strategies.ghost.GhostStrategy;
import pacman_infd.strategies.pacman.KeyControlledStrategy;

public abstract class GhostStrategyTest {
    GameController gameController;
    ScorePanel scorePanel;
    GameWorld gameWorld;
    GhostStrategy ghostStrategy;

    @BeforeEach
    public void setUp() {
        char[][] levelMap =  {
                {'-', '-'},
                {'-', '-'},
        };

        scorePanel = new ScorePanel();
        gameController = new GameController(new View(), scorePanel);
        gameWorld = new GameWorld(gameController, levelMap, 0, new KeyControlledStrategy());
    }

    public abstract void testGiveNextCell();
}
