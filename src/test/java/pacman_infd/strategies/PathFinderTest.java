/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman_infd.strategies;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pacman_infd.elements.Ghost;
import pacman_infd.elements.Pacman;
import pacman_infd.enums.Direction;
import pacman_infd.games.Cell;
import pacman_infd.games.GameController;
import pacman_infd.games.GameWorld;
import pacman_infd.games.View;
import pacman_infd.strategies.ghost.PathFinder;
import pacman_infd.strategies.pacman.KeyControlledStrategy;

import java.awt.*;
import java.util.List;

/**
 *
 * @author ivanweller
 */
public class PathFinderTest {
    View view;
    GameController gameController;
    GameWorld gameWorld;
    PathFinder pathFinder;

    public PathFinderTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @BeforeAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        view = new View();
        gameController = new GameController(view, null);
        pathFinder = new PathFinder();
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void pathFinderTestGeval1() {
        char[][] levelMap = {
            {'-', 'A'},
            {'-', '-'},
            {'-', 'A'},
            {'A', 'A'}
        };
        
        //create a new GameWorld.
        gameWorld = new GameWorld(gameController, levelMap, 0, KeyControlledStrategy.class);
        
        // Set up moving elements in the GameWorld.
        Ghost ghost1 = new Ghost(gameWorld.getCell(0,1), null, 100, null, Color.yellow);
        Ghost ghost2 = new Ghost(gameWorld.getCell(0,1), null, 100, null, Color.yellow);
        Ghost ghost3 = new Ghost(gameWorld.getCell(0,1), null, 100, null, Color.yellow);
        Ghost ghost4 = new Ghost(gameWorld.getCell(0,2), null, 100, null, Color.yellow);
        Pacman pacman = new Pacman(gameWorld.getCell(0,2), null, 100, new KeyControlledStrategy(gameWorld));
        
        //Find the path to Pacman.
        List<Cell> path = pathFinder.breathFirstSearch(gameWorld.getCell(0,0), Pacman.class);
        
        //assert that the list is not empty.
        assert(path != null);
        //assert that the size of the list equals 2.
        assert(path.size() == 2);
        //assert that the path is correct.
        assert(path.get(0).equals(gameWorld.getCell(0,0).getNeighbor(Direction.DOWN)));
        Cell pacCell = path.get(1);
        //assert that the cell that contains Pacman
        assert(pacCell.containsPacman());
    }

    @Test
    public void pathFinderTestGeval2() {
        char[][] levelMap = {
            {'-', 'P'},
            {'A', '-'}
        };
        //create a new GameWorld.   
        gameWorld = new GameWorld(gameController, levelMap, 0, KeyControlledStrategy.class);
        //find the next cell in the shortest path to the cell that holds Pacman from LevelMap[0][1].
        Cell cell = pathFinder.nextCellInPathToPacman(gameWorld.getCell(0,0));
        // check if the cell is not null
        assert (cell != null);
        //assert that cell equals the cell that holds Pacman.
        assert (cell.equals(gameWorld.getCell(1,0)));
        //assert that cell contain Pacman
        assert (cell.containsPacman());
    }

    @Test
    public void pathFinderTestGeval3() {
        char[][] levelMap = {
            {'-'},
            {'A'}
        };
        
        //create a new GameWorld.        
        gameWorld = new GameWorld(gameController, levelMap, 0, KeyControlledStrategy.class);
        //assert that there's no valid path to Pacman.
        assert (pathFinder.breathFirstSearch(gameWorld.getCell(0,0), Pacman.class) == null);
    }

    @Test
    public void pathFinderTestGeval4() {
        char[][] levelMap = {
            {'-'},
            {'P'}
        };
        
        //create a new GameWorld.
        gameWorld = new GameWorld(gameController, levelMap, 0, KeyControlledStrategy.class);
        //Get the next cell in the path to Pacman.
        Cell cell = pathFinder.nextCellInPathToPacman((gameWorld.getCell(0,0)));
        //assert that cell holds an instance of Pacman.
        assert (cell.containsPacman());
        //assert that cell is a valid neighbour of gameWorld.getCell(0, 0).
        assert (gameWorld.getCell(0,0).getNeighbors().containsValue(cell));
    }
}
