package me.ramazanenescik04.diken;

public class Timer {
	
	private Runnable runnable;
	public long currentTime, maxTime;
	
	private boolean isRunnable = false;
	
	public Timer(Runnable r, long cT, long mT) {
		this.runnable = r;
		this.currentTime = cT;
		this.maxTime = mT;
	}
	
	public void start() {
		if (isRunnable) {
			return;
		}
		
		isRunnable = true;
		
		long lastUpdateTime = System.currentTimeMillis();
	    
	    while(isRunnable) {
	        long currentSystemTime = System.currentTimeMillis();
	        long elapsedTime = currentSystemTime - lastUpdateTime;
	        
	        if (elapsedTime > 0) {
	            if (currentTime <= maxTime) {
	                currentTime += elapsedTime; // Geçen gerçek zamanı ekle
	            }
	            lastUpdateTime = currentSystemTime;
	        }
	        
	        if (currentTime == maxTime) {
	        	stop();
	        	return;
	        }
	        
	        runnable.run();
	        
	        try {
	            Thread.sleep(10); // 10ms'de bir güncelleme yap
	        } catch (InterruptedException e) {
	            break;
	        }
	    }
	}
	
	public void stop() {
		isRunnable = false;
	}
}
