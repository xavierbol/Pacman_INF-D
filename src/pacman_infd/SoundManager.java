/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman_infd;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author Marinus
 */
public class SoundManager {


    Map<String, File> soundFiles;

    public SoundManager() {
        soundFiles = new HashMap();
        loadSoundFiles();
    }

    private void loadSoundFiles() {
        try {
            soundFiles.put("chomp", new File(ClassLoader.getSystemResource("Resources/SFX/wakawaka.wav").toURI()));
            soundFiles.put("cherry", new File(ClassLoader.getSystemResource("Resources/SFX/eatCherry.wav").toURI()));
            soundFiles.put("ghost", new File(ClassLoader.getSystemResource("Resources/SFX/eatGhost.wav").toURI()));
            soundFiles.put("death", new File(ClassLoader.getSystemResource("Resources/SFX/death.wav").toURI()));
            soundFiles.put("win", new File(ClassLoader.getSystemResource("Resources/SFX/intermission.wav").toURI()));
            soundFiles.put("portal", new File(ClassLoader.getSystemResource("Resources/SFX/portalOpen3 2.wav").toURI()));

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void playSound(String sound) {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(soundFiles.get(sound));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

//    public static void playSFXPellet() {
//
//        if (wakaClip == null || !wakaClip.isRunning()) {
//            try {
//                audio = AudioSystem.getAudioInputStream(new File(ClassLoader.getSystemResource("Resources/SFX/chomp.wav").toURI()));
//                wakaClip = AudioSystem.getClip();
//                wakaClip.open(audio);
//                wakaClip.start();
//
//            } catch (Exception e) {
//                System.out.println(e);
//            }
//        }
//    }
//
//    public static void playSFXCherry() {
//        try {
//            audio = AudioSystem.getAudioInputStream(new File(ClassLoader.getSystemResource("Resources/SFX/eatCherry.wav").toURI()));
//            cherryClip = AudioSystem.getClip();
//            cherryClip.open(audio);
//            cherryClip.start();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
//
//    public static void playSFXPacmanEatsGhost() {
//        if (pegClip == null || !pegClip.isRunning()) {
//            try {
//                audio = AudioSystem.getAudioInputStream(new File(ClassLoader.getSystemResource("Resources/SFX/eatGhost.wav").toURI()));
//                pegClip = AudioSystem.getClip();
//                pegClip.open(audio);
//                pegClip.start();
//            } catch (Exception e) {
//                System.out.println(e);
//            }
//        }
//    }
//
//    public static void playSFXDeath() {
//        try {
//            audio = AudioSystem.getAudioInputStream(new File(ClassLoader.getSystemResource("Resources/SFX/death.wav").toURI()));
//            deathClip = AudioSystem.getClip();
//            deathClip.open(audio);
//            deathClip.start();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
//
//    public static void playSFXIntermission() {
//
//        try {
//            audio = AudioSystem.getAudioInputStream(new File(ClassLoader.getSystemResource("Resources/SFX/intermission.wav").toURI()));
//            intermissionClip = AudioSystem.getClip();
//            intermissionClip.open(audio);
//            intermissionClip.start();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }

}
