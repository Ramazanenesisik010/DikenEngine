package me.ramazanenescik04.diken.resource.sound;

import me.ramazanenescik04.diken.resource.EnumResource;
import me.ramazanenescik04.diken.resource.IResource;

public interface SoundResource extends IResource {
	void play();
	void stop();
	void pause();
	void destorySound();
	
	default void setPosition(float x, float y) {
	}
	
	default void setVolume(float volume) {
	}
	
	default void setPitch(float pitch) {
	}
	
	default void setPosition(float x, float y, float z) {
	}
	
	void setLooping(boolean looping);
	
	default void setDistance(float distance) {
	}
	
	default void setMaxDistance(float maxDistance) {
	}
	
	boolean isPlaying();
	
	boolean isLooping();
	
	default EnumResource getResourceType() {
		return EnumResource.SOUND;
	}
}
