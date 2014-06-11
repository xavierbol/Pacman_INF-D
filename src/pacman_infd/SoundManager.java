/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman_infd;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author Marinus
 */
public class SoundManager {

    AudioInputStream audio;

    Clip wakaClip;
    Clip cherryClip;
    Clip pegClip;
    Clip deathClip;
    Clip intermissionClip;

    public SoundManager() {

    }

    public void playSFXPellet() {
        if (wakaClip == null || !wakaClip.isRunning()) {
            try {
                audio = AudioSystem.getAudioInputStream(new File(ClassLoader.getSystemResource("Resources/SFX/chomp.wav").toURI()));
                wakaClip = AudioSystem.getClip();
                wakaClip.open(audio);
                wakaClip.start();

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void playSFXCherry() {
        try {
            audio = AudioSystem.getAudioInputStream(new File(ClassLoader.getSystemResource("Resources/SFX/eatCherry.wav").toURI()));
            cherryClip = AudioSystem.getClip();
            cherryClip.open(audio);
            cherryClip.start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void playSFXPacmanEatsGhost() {
        if (pegClip == null || !pegClip.isRunning()) {
            try {
                audio = AudioSystem.getAudioInputStream(new File(ClassLoader.getSystemResource("Resources/SFX/eatGhost.wav").toURI()));
                pegClip = AudioSystem.getClip();
                pegClip.open(audio);
                pegClip.start();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void playSFXDeath() {
        try {
            audio = AudioSystem.getAudioInputStream(new File(ClassLoader.getSystemResource("Resources/SFX/death.wav").toURI()));
            deathClip = AudioSystem.getClip();
            deathClip.open(audio);
            deathClip.start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void playSFXIntermission() {
        try {
            audio = AudioSystem.getAudioInputStream(new File(ClassLoader.getSystemResource("Resources/SFX/intermission.wav").toURI()));
            intermissionClip = AudioSystem.getClip();
            intermissionClip.open(audio);
            intermissionClip.start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}

//        else{
//            try {
//                audio = AudioSystem.getAudioInputStream(new File(chompPath));
//
//                wakaClip = AudioSystem.getClip();
//                wakaClip.open(audio);
//                wakaClip.start();
//
//            } catch (Exception e) {
//                System.out.println(e);
//            }
//        }
