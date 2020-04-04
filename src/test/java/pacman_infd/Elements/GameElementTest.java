package pacman_infd.Elements;

import org.junit.jupiter.api.BeforeEach;
import pacman_infd.Games.GameController;
import pacman_infd.Games.GameWorld;
import pacman_infd.Games.ScorePanel;
import pacman_infd.Games.View;

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
        gameWorld = new GameWorld(gameController, levelMap, 0);
    }
}
