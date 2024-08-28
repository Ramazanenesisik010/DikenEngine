package com.emirenesgames.engine.sound;

import java.io.BufferedInputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.emirenesgames.engine.DikenEngine;

public class Sound {
	private Clip clip;
	
	public static Sound loadSound(String path) {
		Sound snd = new Sound();
		try {
			snd.clip = AudioSystem.getClip();
			AudioInputStream aInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(DikenEngine.class.getResourceAsStream(path)));
			snd.clip.open(aInputStream);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return snd;
	}
	
	public void play() {
		try {
			if (clip != null) {
				new Thread() {
					public void run() {
						synchronized (clip) {
							clip.stop();
							clip.setFramePosition(0);
							clip.start();
						}
					}
				}.start();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
