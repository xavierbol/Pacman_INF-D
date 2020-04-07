package pacman_infd.strategies;

import org.junit.jupiter.api.BeforeEach;
import pacman_infd.games.GameController;
import pacman_infd.games.GameWorld;
import pacman_infd.games.ScorePanel;
import pacman_infd.games.View;

public abstract class StrategyTest {
    GameController gameController;
    ScorePanel scorePanel;
    GameWorld gameWorld;
    Strategy strategy;

    @BeforeEach
    public void setUp() {
        char[][] levelMap =  {
                {'-', '-'},
                {'-', '-'},
        };

        scorePanel = new ScorePanel();
        gameController = new GameController(new View(), scorePanel);
        gameWorld = new GameWorld(gameController, levelMap, 0);
    }

    public abstract void testGiveNextCell();
}
