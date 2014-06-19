/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman_infd;

import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pacman_infd.Elements.Pacman;

/**
 *
 * @author ivanweller
 */
public class PathFinderTest {

    GameController gameController;
    GameWorld gameWorld;
    PathFinder pf;

    public PathFinderTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        gameController = new GameController(null, null);
        pf = new PathFinder();

    }

    @After
    public void tearDown() {
    }

    @Test
    public void pathFinderTestGeval2() {
        char[][] levelMap = {
            {'-', 'P'},
            {'A', '-'}
        };

        gameWorld = new GameWorld(gameController, levelMap, gameController.getSoundManager(), new View());

        Cell cell = pf.nextCellInPathToPacman(gameWorld.getCellMap()[0][1]);

        assert (cell == gameWorld.getCellMap()[0][1]);
        assert (cell.getMovingElements().get(0) instanceof Pacman);
    }

    @Test
    public void pathFinderTestGeval3() {

        char[][] levelMap = {
            {'-'},
            {'A'}
        };

        gameWorld = new GameWorld(gameController, levelMap, gameController.getSoundManager(), new View());

        assert (pf.findPathToPacman(gameWorld.getCellMap()[0][0]) == null);

        for (Cell cell : (Collection<Cell>) gameWorld.getCellMap()[0][0].getNeighbors().values()) {
            assert (cell.hasWall());
        }

    }

    @Test
    public void pathFinderTestGeval4() {

        char[][] levelMap = {
            {'-'},
            {'P'}
        };

        gameWorld = new GameWorld(gameController, levelMap, gameController.getSoundManager(), new View());

        Cell cell = pf.nextCellInPathToPacman((gameWorld.getCellMap()[0][0]));
        
        assert (cell.getMovingElements().get(0) instanceof Pacman);
        
        assert(gameWorld.getCellMap()[0][0].getNeighbors().values().contains(cell));

    }

}
