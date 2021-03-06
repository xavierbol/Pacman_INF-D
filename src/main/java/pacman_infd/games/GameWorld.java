/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman_infd.games;

import pacman_infd.elements.*;
import pacman_infd.enums.Direction;
import pacman_infd.enums.ElementType;
import pacman_infd.enums.FruitType;
import pacman_infd.enums.PortalType;
import pacman_infd.listeners.EventHandler;
import pacman_infd.strategies.ghost.ChasePacmanStrategy;
import pacman_infd.strategies.ghost.GhostStrategy;
import pacman_infd.strategies.ghost.MoveRandomStrategy;
import pacman_infd.strategies.pacman.PacmanStrategy;
import pacman_infd.utils.SoundManager;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static pacman_infd.enums.ElementType.*;

/**
 * @author Marinus
 */
public class GameWorld {
    private static final int CELL_SIZE = 26; //pixels

    private int width;
    private int height;

    private View view;
    private EventHandler eventHandler;

    private ArrayList<Cell> cells;
    private Cell[][] cellMap;

    private PacmanStrategy pacmanStrategy;
    private int gameSpeed;
    private int numberOfPelletsAtStart;

    private Portal portalBlue;
    private Portal portalOrange;

    /**
     * Create the game world.
     *
     * @param gameController        the game controller.
     * @param levelMap              the map of the level.
     * @param speed                 the default speed.
     * @param pacmanStrategyClazz   the selected class for the strategy of pacman.
     */
    public GameWorld(GameController gameController, char[][] levelMap, int speed, Class<? extends PacmanStrategy> pacmanStrategyClazz) {
        this.view = gameController.getView();
        this.gameSpeed = speed;
        this.eventHandler = new EventHandler(gameController, this);
        this.pacmanStrategy = this.createPacmanStrategy(pacmanStrategyClazz);
        if (levelMap != null) {

            this.width = levelMap[0].length;
            this.height = levelMap.length;
            createCells();
            findNeighbors();

            placeElements(levelMap, cellMap);

            this.numberOfPelletsAtStart = countPellets(false);
        }
    }

    /**
     * Create the pacman strategy.
     *
     * @param pacmanStrategyClazz the selected class for the strategy of the pacman.
     */
    private PacmanStrategy createPacmanStrategy(Class<? extends PacmanStrategy> pacmanStrategyClazz) {
        PacmanStrategy pacmanStrategy = null;
        try {
            pacmanStrategy = pacmanStrategyClazz.getDeclaredConstructor(GameWorld.class).newInstance(this);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return pacmanStrategy;
    }

    /**
     * Create a grid of cells.
     */
    private void createCells() {
        cells = new ArrayList<>();
        cellMap = new Cell[height][width];

        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                Cell cell = new Cell(y, x, CELL_SIZE);
                cellMap[x][y] = cell;
                cells.add(cell);
            }
        }
    }

    /**
     * Finds all neighbors for each cell and adds them to the neighbors Map of
     * each cell.
     */
    private void findNeighbors() {
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                cellMap[x][y].setNeighbor(Direction.UP, cellMap[(height + x - 1) % height][y]);
                cellMap[x][y].setNeighbor(Direction.DOWN, cellMap[(height + x + 1) % height][y]);
                cellMap[x][y].setNeighbor(Direction.LEFT, cellMap[x][(width + y - 1) % width]);
                cellMap[x][y].setNeighbor(Direction.RIGHT, cellMap[x][(width + y + 1) % width]);
            }
        }
    }

    /**
     * Places walls on the cellMap according to wallMap
     *
     * @param elementMap array of integers representing the walls (1=wall, 0=no wall)
     * @param cellMap    cell array of level.
     */
    private void placeElements(char[][] elementMap, Cell[][] cellMap) {
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                createElement(elementMap[x][y], cellMap[x][y]);
            }
        }
    }

    /**
     * Create the element, designated by the character, on the cell.
     *
     * @param element The character designating the type of element
     * @param cellMap The cell
     */
    private void createElement(char element, Cell cellMap) {
        ElementType elementType = valueOfElement(element);
        switch (elementType) {
            case PELLET:
                new Pellet(cellMap, eventHandler);
                break;
            case SUPER_PELLET:
                new SuperPellet(cellMap, eventHandler);
                break;
            case BLINKY_GHOST:
            case PINKY_GHOST:
            case INKY_GHOST:
            case CLYDE_GHOST:
                createGhost(elementType, cellMap);
                break;
            case PACMAN:
                createPacman(cellMap);
                break;
            case NO_ELEMENT:
                break;
            default:
                new Wall(cellMap, elementType);
                break;
        }
    }

    /**
     * Create pacman to the cell of the map
     *
     * @param cell The cell
     */
    public void createPacman(Cell cell) {
        Pacman pacman = new Pacman(cell, this.eventHandler, this.gameSpeed, this.pacmanStrategy);
        view.addKeyListener(pacman);
    }

    /**
     * Create a ghost to the cell of the map
     *
     * @param element the type of ghost
     * @param cell    the cell of the map
     */
    public void createGhost(ElementType element, Cell cell) {
        // By default, it's the BLINKY_GHOST, because we assume to know it's a ghost when we call this method
        GhostStrategy ghostStrategy = new ChasePacmanStrategy();
        Color color = Color.RED;

        if (element == PINKY_GHOST) {
            ghostStrategy = new ChasePacmanStrategy();
            color = Color.PINK;
        } else if (element == INKY_GHOST) {
            ghostStrategy = new MoveRandomStrategy();
            color = Color.CYAN;
        } else if (element == CLYDE_GHOST) {
            ghostStrategy = new MoveRandomStrategy();
            color = Color.ORANGE;
        }

        new Ghost(cell, eventHandler, gameSpeed, ghostStrategy, color);
    }

    /**
     * Draw each cell in the game world.
     *
     * @param g Graphics object
     */
    private void drawCells(Graphics g) {
        if (cells != null) {
            for (Cell cell : cells) {
                cell.draw(g);
            }
        }
    }

    /**
     * Draw the game world.
     *
     * @param g is the instance of Graphics class
     */
    public void draw(Graphics g) {
        g.clearRect(0, 0, width * CELL_SIZE, height * CELL_SIZE);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width * CELL_SIZE, height * CELL_SIZE);
        drawCells(g);
    }

    /**
     * Counts the number of pellets currently in the GameWorld.
     *
     * @return number of pellets
     */
    public int countPellets(boolean superPellet) {
        int number = 0;
        boolean containPellet = false;
        for (Cell cell : cells) {
            containPellet = superPellet ?
                    cell.getStaticElement() instanceof SuperPellet :
                    cell.getStaticElement() instanceof Pellet;

            if (containPellet) {
                number++;
            }
        }
        return number;
    }

    /**
     * Returns cells containing at least one ghost
     *
     * @return Cells containing at least one ghost
     */
    public List<Cell> getGhostCells() {
        ArrayList<Cell> cells = new ArrayList<>();
        for (Cell cell : this.getCells()) {
            for (MovingGameElement movingGameElement : cell.getMovingElements()) {
                if (movingGameElement instanceof Ghost) {
                    cells.add(cell);
                }
            }
        }
        return cells;
    }

    /**
     * Check if it remains pellets in the game board
     *
     * @return True if it remains pellets in the game, otherwise return false.
     */
    public boolean checkRemainingPellets() {
        int remainingPellets = this.countPellets(false);

        if (remainingPellets == numberOfPelletsAtStart / 2) {
            this.placeFruitRandom();
        } else if (remainingPellets == 0) {
            return false;
        }

        return true;
    }

    /**
     * Place a fruit randomly among the cells that can spawn a fruit.
     */
    private void placeFruitRandom() {
        List<Cell> fruitSpawnCells = this.getFruitSpawnCells();
        Random r = new Random();
        if (!fruitSpawnCells.isEmpty()) {
            new Fruit(FruitType.values()[r.nextInt(FruitType.values().length)],
                    fruitSpawnCells.get(r.nextInt(fruitSpawnCells.size())),
                    eventHandler);
        }
    }

    /**
     * Link the portals.
     */
    private void linkPortals() {
        portalBlue.setLinkedPortal(portalOrange);
        portalOrange.setLinkedPortal(portalBlue);
        portalBlue.warpNeighbors();
        portalOrange.warpNeighbors();
    }

    /**
     * Create the portal.
     *
     * @param cell          the cell where the portal must be created.
     * @param portalType    the type of the portal to create.
     */
    private void createPortal(Cell cell, PortalType portalType) {
        Portal portal = new Portal(cell, portalType);

        if (portalType == PortalType.BLUE) {
            if (getPortalBlue() != null) {
                getPortalBlue().remove();
            }
            setPortalBlue(portal);

            if (portalOrange != null) {
                linkPortals();
            }
        } else {
            if (getPortalOrange() != null) {
                getPortalOrange().remove();
            }
            setPortalOrange(portal);

            if (portalBlue != null) {
                linkPortals();
            }
        }
        SoundManager.playSound("portal");
    }

    /**
     * Spawn the portal
     *
     * @param x             the x position of the portal to spawn
     * @param y             the y position of the portal to spawn
     * @param mouseButton   the mouse button which has been clicked.
     */
    public void spawnPortal(int x, int y, int mouseButton) {
        int cellX = x / CELL_SIZE;
        int cellY = y / CELL_SIZE;
        findNeighbors();
        if (cellY < cellMap.length) {
            if (!cellMap[cellY][cellX].hasWall() && cellMap[cellY][cellX].getStaticElement() == null) {
                if (mouseButton == 1) {
                    createPortal(getCell(cellX, cellY), PortalType.BLUE);
                } else if (mouseButton == 3) {
                    createPortal(getCell(cellX, cellY), PortalType.ORANGE);
                }
            }
        }
    }

    /**
     * Clear the game world.
     */
    public void clearGameWorld() {
        for (Cell cell : cells) {
            cell.clearCell();
        }
        eventHandler = null;
        cellMap = null;
        cells = null;
    }

    /**
     * Returns a list of all cells that can spawn a fruit
     *
     * @return list of cells
     */
    private List<Cell> getFruitSpawnCells() {
        return cells.stream()
                .filter(Cell::isFruitSpawn)
                .collect(Collectors.toList());
    }

    /**
     * Get the initial number of pellets.
     *
     * @return number of Pellets at the start of the game.
     */
    public int getNumberOfPelletsAtStart() {
        return numberOfPelletsAtStart;
    }

    /**
     * Get the blue portal.
     *
     * @return the portalBlue.
     */
    public Portal getPortalBlue() {
        return portalBlue;
    }

    /**
     * Set the blue portal.
     *
     * @param portalBlue the portalBlue to set.
     */
    public void setPortalBlue(Portal portalBlue) {
        this.portalBlue = portalBlue;
    }

    /**
     * Get the orange portal.
     *
     * @return the portalOrange.
     */
    public Portal getPortalOrange() {
        return portalOrange;
    }

    /**
     * Set the orange portal.
     *
     * @param portalOrange the portalOrange to set.
     */
    public void setPortalOrange(Portal portalOrange) {
        this.portalOrange = portalOrange;
    }

    /**
     * Get the cell based on the position given in parameter.
     *
     * @param x     the x position of the wished cell.
     * @param y     the y position of the wished cell.
     * @return      the wished cell.
     */
    public Cell getCell(int x, int y) {
        if (y >= 0 && y < cellMap.length) {
            if (x >= 0 && x < cellMap[y].length) {
                return cellMap[y][x];
            }
        }

        return null;
    }

    /**
     * Get all cells of the game board.
     *
     * @return all cells.
     */
    public ArrayList<Cell> getCells() {
        return cells;
    }

    /**
     * Get event handler
     *
     * @return the event handler
     */
    public EventHandler getEventHandler() {
        return eventHandler;
    }
}
