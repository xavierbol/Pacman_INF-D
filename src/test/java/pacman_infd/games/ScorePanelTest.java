package pacman_infd.games;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pacman_infd.games.ScorePanel;

public class ScorePanelTest {
    private ScorePanel scorePanel;

    @BeforeEach
    public void setUp() {
        scorePanel = new ScorePanel();
    }

    @Test
    public void testLifeWhenPacmanLoseALife() {
        int lives = scorePanel.getLives();
        scorePanel.loseLife();
        assert lives - 1 == scorePanel.getLives();
    }
}
