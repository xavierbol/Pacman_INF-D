/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman_infd.Games;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Marinus
 */
public class ScorePanel extends JPanel {
    private int score;
    private int lives;
    private String time;
    
    private static final int PANEL_WIDTH = 730;
    private static final int PANEL_HEIGHT = 50;

    private static final int DEFAULT_SCORE = 0;
    private static final int NUMBER_LIVES = 3;
    private static final String DEFAULT_TIME = "00:00";
    
    public ScorePanel()
    {
        resetStats();
    }
    
    /**
     * initialize the score and lives to what they should be at the start of the game.
     */
    public void resetStats() {
        score = DEFAULT_SCORE;
        lives = NUMBER_LIVES;
        time = DEFAULT_TIME;
    }
    
    /**
     * Set score
     * @param n amount
     */
    public void setScore(int n)
    {
        score = n;
    }
    
    /**
     * Add to current score
     * @param n amount
     */
    public void addScore(int n)
    {
        score += n;
    }
    
    public int getScore(){
        return score;
    }
    
    /**
     * Subtract one life from the life total.
     */
    public void loseLife()
    {
        lives -= 1;
    }
    
    /**
     * 
     * @return lives
     */
    public int getLives()
    {
        return lives;
    }
    
    /**
     * Set time
     * @param time 
     */
    public void setTime(String time){
        this.time = time;
    }
    
    /**
     * Paint this panel
     * @param g Graphics object.
     */
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        g.drawString("PACMAN", 275, 35);
        g.setColor(Color.ORANGE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        g.drawString("SCORE: " + score, 50, 25);
        g.drawString("TIME: " + time, 150, 25);
        g.drawString("LIVES: " + lives, 520, 25);
    }
}
