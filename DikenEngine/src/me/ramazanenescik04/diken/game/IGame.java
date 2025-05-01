package me.ramazanenescik04.diken.game;

import me.ramazanenescik04.diken.DikenEngine;

/**
 * Oyunun Çalışmasını Sağlar
 * 
 */
public interface IGame {
	
	/**
	 * Oyun Başladığında kullanabileciğiniz kod
	 * @param engine
	 */
	void startGame(DikenEngine engine);
	
	/**
	 * Oyun başlamadan önce kaynakları yüklemenize sağlar
	 */
	void loadResources();
	
	/**
	 * JNI Kütüphanesiz Native Yüklemesi Yapar
	 * 
	 * @see
	 * IGame.loadAdvancedNative
	 */
	default void loadNatives() {
	}
	
	/**
	 * Bu Oyun yüklenmeden önce jni Native sınıfı lullanarak yükleme yapılır
	 */
	default void loadAdvancedNative() {
	}
	
}
