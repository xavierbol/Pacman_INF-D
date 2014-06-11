/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman_infd;

import java.net.URI;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Marinus
 */
public class ResourceManager {
    Queue<char[][]> levels;

    
    public ResourceManager(){
        levels = new LinkedList();
        loadLevels();
    }
    
    private void loadLevels(){
         loadLevel("Resources/Levels/level3.txt");
        loadLevel("Resources/Levels/level2.txt");
        loadLevel("Resources/Levels/level1.txt");
        
       
    }
    
    private void loadLevel(String path){
        try{
            URI filePath = ClassLoader.getSystemResource(path).toURI();
            char[][] level = new FileLoader(filePath).openMap();
            if(level != null){
                levels.add(level);
            }
            
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    public char[][] getFirstLevel(){
        levels = new LinkedList();
        loadLevels();
        return getNextLevel();
    }
    
    public char[][] getNextLevel(){
        
        char[][] nextLevel = levels.poll();
        levels.offer(nextLevel);
        return nextLevel;
    }
}
