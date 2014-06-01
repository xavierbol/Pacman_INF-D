/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman_infd.Elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import pacman_infd.Cell;
import pacman_infd.GameElement;
import pacman_infd.Strategy;
import pacman_infd.GameEventListener;
import pacman_infd.PathFinder;
import pacman_infd.Strategies.FleeStrategy;

/**
 *
 * @author Marinus
 */
public class Ghost extends MovingGameElement implements ActionListener {

    private Strategy strategy;
    private Strategy initialStrategy;
    private boolean isFleeing;

    PathFinder pathFinder;

    public Ghost(Cell cell, GameEventListener gameEventListener, int speed, Strategy strategy) {
        super(cell, gameEventListener, speed);
        this.strategy = strategy;
        initialStrategy = strategy;
        isFleeing = false;
    }

    /**
     * Draw this Ghost
     * @param g 
     */
    public void draw(Graphics g) {
        if(isFleeing){
            g.setColor(Color.BLUE);
        }
        else{
            g.setColor(Color.RED);
        }
        g.fillRoundRect(
                (int) getPosition().getX(),
                (int) getPosition().getY(),
                getCell().getSize(),
                getCell().getSize(),
                10, 5
        );
    }

    /**
     * Move to the next cell. Uses its current strategy to determine which cell to move to.
     */
    protected void move() {
        Cell nextCell = strategy.giveNextCell(cell);
        if (nextCell != null) {
            setCell(nextCell);
        }

        checkCollisions();
    }

    /**
     * Looks for GameElements that are in the same cell and interacts with them accordingly.
     */
    protected void checkCollisions() {
        for (GameElement e : getCell().getElements()) {
            if (e instanceof Pacman) {
                interactWithPacman((Pacman) e);
                break;
            }
        }
    }

    /**
     * Interact with Pacman. If Pacman is invincible then the ghost will die. 
     * If not then Pacman will die.
     * @param pacman 
     */
    private void interactWithPacman(Pacman pacman) {
        if (pacman.isInvincible()) {
            //timer.stop();
            //getCell().removeElement(this);
            //gameEventListener.pacmanEatsGhost(this);

        } else {
            cell.removeElement(pacman);
            gameEventListener.pacmanDied(pacman);
        }
    }
    

    /**
     * Change current strategy to FleeStrategy and lowers the speed of this Ghost by 50%
     * This is called when Pacman eats a superPellet.
     */
    public void runFromPacman() {
        this.strategy = new FleeStrategy();
        isFleeing = true;
        setSpeed(150);
    }

    /**
     * Revert back to the initial Strategy and initial speed.
     */
    public void backToNormal() {
        strategy = initialStrategy;
        isFleeing = false;
        setSpeed(100);
    }

    /**
     * This is called each 'tick' of the timer.
     * This is used by the GameWorld to 
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        gameEventListener.gameElementPerfomedAction(this);
    }

}
