package pacman_infd.elements;

import org.junit.jupiter.api.BeforeEach;
import pacman_infd.games.GameController;
import pacman_infd.games.GameWorld;
import pacman_infd.games.ScorePanel;
import pacman_infd.games.View;
import pacman_infd.strategies.pacman.KeyControlledStrategy;

public abstract class GameElementTest {
    GameController gameController;
    GameWorld gameWorld;
    ScorePanel scorePanel;

    @BeforeEach
    public void setUp() {
        char[][] levelMap = {
                {'-', '-'},
                {'-', '-'}
        };
        scorePanel = new ScorePanel();
        gameController = new GameController(new View(), scorePanel);
        gameWorld = new GameWorld(gameController, levelMap, 0, new KeyControlledStrategy());
    }
}
