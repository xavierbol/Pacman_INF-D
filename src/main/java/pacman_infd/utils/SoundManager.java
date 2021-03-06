/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman_infd.utils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Marinus
 */
public final class SoundManager {
    private static Map<String, File> soundFiles;

    /**
     * Load all sound files.
     */
    public static void loadSoundFiles() {
        soundFiles = new HashMap<>();

        try {
            soundFiles.put("chomp", new File(ClassLoader.getSystemResource("SFX/wakawaka.wav").toURI()));
            soundFiles.put("fruit", new File(ClassLoader.getSystemResource("SFX/eatFruit.wav").toURI()));
            soundFiles.put("ghost", new File(ClassLoader.getSystemResource("SFX/eatGhost.wav").toURI()));
            soundFiles.put("death", new File(ClassLoader.getSystemResource("SFX/death.wav").toURI()));
            soundFiles.put("win", new File(ClassLoader.getSystemResource("SFX/intermission.wav").toURI()));
            soundFiles.put("portal", new File(ClassLoader.getSystemResource("SFX/portalOpen3 2.wav").toURI()));
            soundFiles.put("superPellet", new File(ClassLoader.getSystemResource("SFX/chomp.wav").toURI()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Play the sound based on the key given in parameter
     *
     * @param sound the sound to play.
     */
    public static void playSound(String sound) {
        if (soundFiles == null || soundFiles.isEmpty()) {
            loadSoundFiles();
        }

        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(soundFiles.get(sound));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
