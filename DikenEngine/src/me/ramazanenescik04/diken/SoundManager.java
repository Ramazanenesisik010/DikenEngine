package me.ramazanenescik04.diken;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

import me.ramazanenescik04.diken.resource.*;
import me.ramazanenescik04.diken.resource.sound.*;

public class SoundManager {	
	private static List<Integer> soundIDs = new ArrayList<Integer>();
	
	public static void init() {
		try {
			AL.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	public static void destroy() {
		for (int id : soundIDs) {
			AL10.alDeleteBuffers(id);
		}
		
		AL.destroy();
	}

	public static IResource loadSound(InputStream stream) {
		if (!AL.isCreated()) {
			init();
		}
		WaveData data = WaveData.create(stream);
		int bufferID = AL10.alGenBuffers();
		AL10.alBufferData(bufferID, data.format, data.data, data.samplerate);
		data.dispose();
		
		int sourceID = AL10.alGenSources();
		AL10.alSourcei(sourceID, AL10.AL_BUFFER, bufferID);
		SoundResource soundResource = new WaveSoundResource(sourceID);
		return soundResource;
	}
}