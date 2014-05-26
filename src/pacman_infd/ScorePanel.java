/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman_infd;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Marinus
 */
public class ScorePanel extends JPanel {
    private int score;
    private int lives;
    
    private static final int PANEL_WIDTH = 130;
    private static final int PANEL_HEIGHT = 800;
    
    public ScorePanel()
    {
        initStats();
    }
    
    public void initStats()
    {
        score = 0;
        lives = 3;
    }
    
    public void setScore(int n)
    {
        score = n;
    }
    
    public void addScore(int n)
    {
        score += n;
    }
    
    public void looseLife()
    {
        lives--;
    }
    
    public int getLives()
    {
        return lives;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.clearRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
        g.setColor(Color.red);
        g.drawString("SCORE: " + score, 5, 100);
        g.drawString("LIVES: " + lives, 5, 200);

    }
}
