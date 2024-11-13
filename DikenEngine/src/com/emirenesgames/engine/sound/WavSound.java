package com.emirenesgames.engine.sound;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.emirenesgames.engine.DikenEngine;

public class WavSound {
	private Clip clip = null;
	private AudioFormat format;
	
	public static WavSound loadSound(String path) {
		WavSound wavSound = new WavSound();
		try {
			AudioInputStream aInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(DikenEngine.class.getResourceAsStream(path)));
			wavSound.clip = AudioSystem.getClip();
			wavSound.format = aInputStream.getFormat();
			wavSound.clip.open(aInputStream);
		} catch (Exception e) {
			e.printStackTrace();
			return new WavSound();
		}
		
		return wavSound;
	}
	
	public static WavSound loadSoundFile(String path) {
		WavSound wavSound = new WavSound();
		try {
			AudioInputStream aInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(path)));
			wavSound.clip = AudioSystem.getClip();
			wavSound.format = aInputStream.getFormat();
			wavSound.clip.open(aInputStream);
		} catch (Exception e) {
			e.printStackTrace();
			return new WavSound();
		}
		
		return wavSound;
	}
	
	
	
	public float getFrameRate() {
		if(this.format != null) {
			return format.getFrameRate();
		}
		return 0;
	}
	
	public void startSound() {
		if(this.clip != null) {
			synchronized (clip) {
				clip.stop();
				clip.setFramePosition(0);
				clip.start();
			}
		}
	}
	
	public void startSoundThread() {
		if(this.clip != null) {
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
	}
	
    public void stopSound() {
		if(this.clip != null) {
			this.clip.stop();
		}
	}
    
    public float getFrameLenght() {
    	if(this.clip != null) {
    		return clip.getFrameLength();
		}
    	return 0;
    }
    
    public float getFramePosition() {
    	if(this.clip != null) {
    		return clip.getFramePosition();
		}
    	return 0;
    }

}
