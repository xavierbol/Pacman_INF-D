/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman_infd;

import org.junit.jupiter.api.*;
import pacman_infd.Enums.Direction;
import pacman_infd.Elements.Portal;
import pacman_infd.Enums.PortalType;
import pacman_infd.Games.GameController;
import pacman_infd.Games.GameWorld;
import pacman_infd.Utils.SoundManager;

/**
 *
 * @author Marinus
 */
public class GameWorldTest {

    GameController gameController;
    GameWorld gameWorld;

    public GameWorldTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        SoundManager.loadSoundFiles();
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        char[][] levelMap = {
            {'A', '-', '0'},
            {'A', '-', '0'},
            {'A', '-', '-'}
        };

        gameController = new GameController(null, null);
        gameWorld = new GameWorld(new GameController(null, null), levelMap, 0);

    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testSpawnPortalGeval1() {
        gameWorld.setPortalOrange(new Portal(gameWorld.getCell(1, 0), PortalType.ORANGE));
        gameWorld.setPortalBlue(new Portal(gameWorld.getCell(1, 2), PortalType.ORANGE));

        //attempt to spawn a portal on cell location [0][0] with mousebutton 1 pressed
        gameWorld.spawnPortal(0, 0, 1);
        //assert that the orange portal is still on the same cell
        assert (gameWorld.getPortalOrange().getCell().getXpos() == 1 && gameWorld.getPortalOrange().getCell().getYPos() == 0);
        //assert that the blue portal is still on the same cell
        assert (gameWorld.getPortalBlue().getCell().getXpos() == 1 && gameWorld.getPortalBlue().getCell().getYPos() == 2);
        //assert that cellMap[0][0] doesnt have a portal
        assert (!(gameWorld.getCell(0,0).getStaticElement() instanceof Portal));

    }

    @Test
    public void testSpawnPortalGeval2() {
        gameWorld.setPortalOrange(null);
        gameWorld.setPortalBlue(null);

        //attempt to spawn a portal on cell location [1][1] with mousebutton 0 pressed (no mousebutton pressed)
        gameWorld.spawnPortal(26, 26, 0);
        //assert that the orange portal is still null
        assert (gameWorld.getPortalOrange() == null);
        //assert that the blue portal is still null
        assert (gameWorld.getPortalBlue() == null);
        //assert that cellMap[1][1] doesnt have a portal
        assert (!(gameWorld.getCell(1,1).getStaticElement() instanceof Portal));

    }

    @Test
    public void testSpawnPortalGeval3() {
        gameWorld.setPortalOrange(null);
        gameWorld.setPortalBlue(null);

        //attempt to spawn a portal on cell location [1][1] with mousebutton 3 pressed
        gameWorld.spawnPortal(26, 26, 3);
        //assert that the orange portal now excists
        assert (gameWorld.getPortalOrange() != null);
        //assert that the blue portal is still null
        assert (gameWorld.getPortalBlue() == null);
        //assert that cellMap[1][1] has a portal
        assert (gameWorld.getCell(1,1).getStaticElement() instanceof Portal);

    }

    @Test
    public void testSpawnPortalGeval4() {
        gameWorld.setPortalOrange(null);
        gameWorld.setPortalBlue(new Portal(gameWorld.getCell(1,2),PortalType.BLUE));

        //attempt to spawn a portal on cell location [0][1] with mousebutton 3 pressed
        gameWorld.spawnPortal(26, 0, 3);
        //assert that the orange portal now excists
        assert (gameWorld.getPortalOrange() != null);
        //assert that the blue portal is still on the same location
        assert (gameWorld.getPortalBlue().getCell().getXpos() == 1 && gameWorld.getPortalBlue().getCell().getYPos() == 2);
        //assert that cellMap[0][1] has a portal
        assert (gameWorld.getCell(1,0).getStaticElement() instanceof Portal);
        //assert that the neigbor of the orange portal links to the blue portal
        assert (gameWorld.getCell(2, 0).getNeighbor(Direction.LEFT) == gameWorld.getPortalBlue().getCell());
        //assert that the neighbor of the blue portal links to the orangeportal
        assert (gameWorld.getCell(2,2).getNeighbor(Direction.LEFT) == gameWorld.getPortalOrange().getCell());

    }

    @Test
    public void testSpawnPortalGeval5() {
        gameWorld.setPortalOrange(new Portal(gameWorld.getCell(1,2), PortalType.ORANGE));
        gameWorld.setPortalBlue(null);

        //attempt to spawn a portal on cell location [0][1] with mousebutton 3 pressed 
        gameWorld.spawnPortal(26, 0, 3);
        //assert that the orange portal is now on the new location
        assert (gameWorld.getPortalOrange().getCell().getXpos() == 1 && gameWorld.getPortalOrange().getCell().getYPos() == 0);
        //assert that the blue portal is still null
        assert (gameWorld.getPortalBlue() == null);
        //assert that the old portal location does not have a portal
        assert (!(gameWorld.getCell(1, 2).getStaticElement() instanceof Portal));
    }

    @Test
    public void testSpawnPortalGeval6() {
        gameWorld.setPortalOrange(new Portal(gameWorld.getCell(1,2), PortalType.ORANGE));
        gameWorld.setPortalBlue(new Portal(gameWorld.getCell(1,0), PortalType.BLUE));

        //attempt to spawn a portal on cell location [1][1] with mousebutton 3 pressed
        gameWorld.spawnPortal(26, 26, 3);
        //assert that the orange portal is now on the new location
        assert (gameWorld.getPortalOrange().getCell().getXpos() == 1 && gameWorld.getPortalOrange().getCell().getYPos() == 1);
        //assert that the blue portal is still on the same location
        assert (gameWorld.getPortalBlue().getCell().getXpos() == 1 && gameWorld.getPortalBlue().getCell().getYPos() == 0);
        //assert that the old portal location does not have a portal
        assert (!(gameWorld.getCell(1,2).getStaticElement() instanceof Portal));
        //assert that the neigbor of the orange portal links to the blue portal
        assert (gameWorld.getCell(2,1).getNeighbor(Direction.LEFT) == gameWorld.getPortalBlue().getCell());
        //assert that the neighbor of the blue portal links to the orangeportal
        assert (gameWorld.getCell(2,0).getNeighbor(Direction.LEFT) == gameWorld.getPortalOrange().getCell());
    }

    @Test
    public void testSpawnPortalGeval7() {
        gameWorld.setPortalOrange(null);
        gameWorld.setPortalBlue(null);

        //attempt to spawn a portal on cell location [1][1] with mousebutton 1 pressed
        gameWorld.spawnPortal(26, 26, 1);
        //assert that the orange portal is still null
        assert (gameWorld.getPortalOrange() == null);
        //assert that the blue portal now excists
        assert (gameWorld.getPortalBlue() != null);
        //assert that cellMap[1][1] has a portal
        assert (gameWorld.getCell(1,1).getStaticElement() instanceof Portal);

    }

    @Test
    public void testSpawnPortalGeval8() {
        gameWorld.setPortalOrange(null);
        gameWorld.setPortalBlue(new Portal(gameWorld.getCell(1,2), PortalType.BLUE));

        //attempt to spawn a portal on cell location [0][1] with mousebutton 1 pressed 
        gameWorld.spawnPortal(26, 0, 1);
        //assert that the blue portal is now on the new location
        assert (gameWorld.getPortalBlue().getCell().getXpos() == 1 && gameWorld.getPortalBlue().getCell().getYPos() == 0);
        //assert that the orange portal is still null
        assert (gameWorld.getPortalOrange() == null);
        //assert that the old portal location does not have a portal
        assert (!(gameWorld.getCell(1,2).getStaticElement() instanceof Portal));
    }

    @Test
    public void testSpawnPortalGeval9() {
        gameWorld.setPortalOrange(new Portal(gameWorld.getCell(1,2), PortalType.ORANGE));
        gameWorld.setPortalBlue(null);

        //attempt to spawn a portal on cell location [0][1] with mousebutton 1 pressed    
        gameWorld.spawnPortal(26, 0, 1);
        //assert that the blue portal now excists
        assert (gameWorld.getPortalBlue() != null);
        //assert that the orange portal is still on the same location
        assert (gameWorld.getPortalOrange().getCell().getXpos() == 1 && gameWorld.getPortalOrange().getCell().getYPos() == 2);
        //assert that cellMap[0][1] has a portal
        assert (gameWorld.getCell(1,0).getStaticElement() instanceof Portal);
        //assert that the neigbor of the blue portal links to the blue portal
        assert (gameWorld.getCell(2,0).getNeighbor(Direction.LEFT) == gameWorld.getPortalOrange().getCell());
        //assert that the neighbor of the orange portal links to the orangeportal
        assert (gameWorld.getCell(2,2).getNeighbor(Direction.LEFT) == gameWorld.getPortalBlue().getCell());

    }

    @Test
    public void testSpawnPortalGeval10() {
        gameWorld.setPortalOrange(new Portal(gameWorld.getCell(1,2), PortalType.ORANGE));
        gameWorld.setPortalBlue(new Portal(gameWorld.getCell(1,0), PortalType.BLUE));

        //attempt to spawn a portal on cell location [1][1] with mousebutton 1 pressed 
        gameWorld.spawnPortal(26, 26, 1);
        
        //assert that the blue portal is now on the new location
        assert (gameWorld.getPortalBlue().getCell().getXpos() == 1 && gameWorld.getPortalBlue().getCell().getYPos() == 1);
        //assert that the orange portal is still on the same location
        assert (gameWorld.getPortalOrange().getCell().getXpos() == 1 && gameWorld.getPortalOrange().getCell().getYPos() == 2);
        //assert that the old portal location does not have a portal.
        assert (!(gameWorld.getCell(1,0).getStaticElement() instanceof Portal));
        //assert that the neigbor of the orange portal links to the blue portal
        assert (gameWorld.getCell(2,2).getNeighbor(Direction.LEFT) == gameWorld.getPortalBlue().getCell());
        //assert that the neighbor of the blue portal links to the orangeportal
        assert (gameWorld.getCell(2,1).getNeighbor(Direction.LEFT) == gameWorld.getPortalOrange().getCell());
    }

}
