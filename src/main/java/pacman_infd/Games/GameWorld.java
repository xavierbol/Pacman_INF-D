/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman_infd.Games;

import pacman_infd.Enums.Direction;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import pacman_infd.Elements.*;
import pacman_infd.Enums.PortalType;
import pacman_infd.Listeners.EventHandler;
import pacman_infd.Strategies.ChasePacmanStrategy;
import pacman_infd.Strategies.MoveRandomStrategy;

/**
 *
 * @author Marinus
 */
public class GameWorld {

    private static final int CELL_SIZE = 26; //pixels

    // Perhaps create an Enum or a config file with this
    private static final char PELLET = '0';
    private static final char SUPER_PELLET = '2';
    private static final char BLINKY_GHOST = 'a';
    private static final char PINKY_GHOST = 'b';
    private static final char INKY_GHOST = 'c';
    private static final char CLYDE_GHOST = 'd';
    private static final char PACMAN = 'P';
    private static final char NO_ELEMENT = '-';

    private int width;
    private int height;

    private SoundManager soundManager;
    private View view;
    private EventHandler eventHandler;

    private ArrayList<Cell> cells;
    private Cell[][] cellMap;

    private int gameSpeed;
    private int numberOfPelletsAtStart;

    private Portal portalBlue;
    private Portal portalOrange;

    public GameWorld(GameController gameController, char[][] levelMap, SoundManager sMger, View view, int speed) {

        soundManager = sMger;
        this.view = view;
        gameSpeed = speed;

        eventHandler = new EventHandler(gameController, this);

        if (levelMap != null) {

            width = levelMap[0].length;
            height = levelMap.length;
            createCells();
            findNeighbors();

            placeElements(levelMap, cellMap);

            numberOfPelletsAtStart = countPellets();
        }
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
                if (x - 1 >= 0) {
                    cellMap[x][y].setNeighbor(Direction.UP, cellMap[x - 1][y]);
                }
                if (x + 1 < height) {
                    cellMap[x][y].setNeighbor(Direction.DOWN, cellMap[x + 1][y]);
                }
                if (y - 1 >= 0) {
                    cellMap[x][y].setNeighbor(Direction.LEFT, cellMap[x][y - 1]);
                }
                if (y + 1 < width) {
                    cellMap[x][y].setNeighbor(Direction.RIGHT, cellMap[x][y + 1]);
                }
            }
        }

    }

    /**
     * Places walls on the cellMap according to wallMap
     *
     * @param elementMap array of integers representing the walls (1=wall, 0=no wall)
     * @param cellMap cell array of level.
     */
    private void placeElements(char[][] elementMap, Cell[][] cellMap) {

        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                selectElement(elementMap[x][y], cellMap[x][y]);
            }
        }
    }

    private void selectElement(char element, Cell cellMap) {
        switch (element) {
            case PELLET:
                new Pellet(cellMap, eventHandler, soundManager);
                break;
            case SUPER_PELLET:
                new SuperPellet(cellMap, eventHandler, soundManager);
                break;
            case BLINKY_GHOST:
                new Ghost(cellMap, eventHandler, gameSpeed, new ChasePacmanStrategy(), Color.RED, soundManager);
                break;
            case PINKY_GHOST:
                new Ghost(cellMap, eventHandler, gameSpeed, new ChasePacmanStrategy(), Color.PINK, soundManager);
                break;
            case INKY_GHOST:
                new Ghost(cellMap, eventHandler, gameSpeed, new MoveRandomStrategy(), Color.CYAN, soundManager);
                break;
            case CLYDE_GHOST:
                new Ghost(cellMap, eventHandler, gameSpeed, new MoveRandomStrategy(), Color.ORANGE, soundManager);
                break;
            case PACMAN:
                Pacman pacman = new Pacman(cellMap, eventHandler, gameSpeed, soundManager);
                view.addKeyListener(pacman);
                break;
            case NO_ELEMENT:
                break;
            default:
                new Wall(cellMap, element);
                break;
        }
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
    public int countPellets() {
        int number = 0;
        for (Cell cell : cells) {
            if (cell.getStaticElement() instanceof Pellet) {
                number++;
            }
        }
        return number;
    }

    /**
     * Places a cherry on a random cell that has no static element.
     */
    public void placeCherryOnRandomEmptyCell() {
        if (countPellets() == numberOfPelletsAtStart / 2) {
            ArrayList<Cell> emptyCells = getEmptyCells();
            Random r = new Random();
            if (!emptyCells.isEmpty()) {
                Cherry c = new Cherry(emptyCells.get(r.nextInt(emptyCells.size() - 1)), eventHandler, soundManager);
            }
        }
    }

    public void spawnPortal(int x, int y, int mouseButton) {
        int cellX = x / CELL_SIZE;
        int cellY = y / CELL_SIZE;
        findNeighbors();
        if (cellY < cellMap.length) {
            if (!cellMap[cellY][cellX].hasWall() && cellMap[cellY][cellX].getStaticElement() == null) {
                if (mouseButton == 1) {
                    if (getPortalBlue() != null) {
                        getPortalBlue().remove();
                    }
                    setPortalBlue(new Portal(cellMap[cellY][cellX], PortalType.BLUE, soundManager));
                    soundManager.playSound("portal");
                    if (getPortalOrange() != null) {
                        getPortalBlue().setLinkedPortal(getPortalOrange());
                        getPortalOrange().setLinkedPortal(getPortalBlue());
                        getPortalBlue().warpNeighbors();
                        getPortalOrange().warpNeighbors();
                    }
                } else if (mouseButton == 3) {
                    if (getPortalOrange() != null) {
                        getPortalOrange().remove();
                    }
                    setPortalOrange(new Portal(cellMap[cellY][cellX], PortalType.ORANGE, soundManager));
                    soundManager.playSound("portal");
                    if (getPortalBlue() != null) {
                        getPortalOrange().setLinkedPortal(getPortalBlue());
                        getPortalBlue().setLinkedPortal(getPortalOrange());
                        getPortalOrange().warpNeighbors();
                        getPortalBlue().warpNeighbors();
                    }
                }

            }
        }
    }
    
    public void clearGameWorld(){
        for(Cell cell: cells){
            cell.clearCell();
        }
        eventHandler = null;
        cellMap = null;
        cells = null;
    }

    /**
     * Returns a list of all cells that have no static element placed on them.
     *
     * @return list of cells
     */
    private ArrayList<Cell> getEmptyCells() {
        ArrayList<Cell> emptyCells = new ArrayList<>();
        for (Cell cell : cells) {
            if (cell.getStaticElement() == null) {
                emptyCells.add(cell);
            }
        }
        return emptyCells;
    }

    /**
     *
     * @return number of Pellets at the start of the game.
     */
    public int getNumberOfPelletsAtStart() {
        return numberOfPelletsAtStart;
    }

    /**
     * @return the portalBlue
     */
    public Portal getPortalBlue() {
        return portalBlue;
    }

    /**
     * @param portalBlue the portalBlue to set
     */
    public void setPortalBlue(Portal portalBlue) {
        this.portalBlue = portalBlue;
    }

    /**
     * @return the portalOrange
     */
    public Portal getPortalOrange() {
        return portalOrange;
    }

    /**
     * @param portalOrange the portalOrange to set
     */
    public void setPortalOrange(Portal portalOrange) {
        this.portalOrange = portalOrange;
    }

    public Cell[][] getCellMap() {
        return cellMap;
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }

}
