/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman_infd;

import pacman_infd.games.View;
import pacman_infd.utils.SoundManager;

/**
 * @author Marinus
 */
public class Pacman_INFD {
    /**
     * The main method to run the Pacman game.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        SoundManager.loadSoundFiles();
        View view = new View();
        view.setVisible(true);
    }
}
