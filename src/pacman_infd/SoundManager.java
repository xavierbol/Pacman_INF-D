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

    public SoundManager() {

    }

    public void playWaka() {
        if (wakaClip == null || !wakaClip.isRunning()) {
            try {
                audio = AudioSystem.getAudioInputStream(new File(this.getClass().getResource("assets/pacman_chomp.wav").toURI()));
                wakaClip = AudioSystem.getClip();
                wakaClip.open(audio);
                wakaClip.start();

            } catch (Exception e) {
                System.out.println(e);
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
    }
}
