package me.ramazanenescik04.diken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class NativeManager {
	private static File nativeDir;
	
	static {
		nativeDir = new File(System.getProperty("user.home") + "/AppData/Local/diken-natives");
		nativeDir.mkdirs();
		nativeDir.deleteOnExit();
	}
	
	public static void loadLibraryFromJar(String path) throws IOException {
		GetOS.OS os = GetOS.getOS();
		
		path = "/natives/" + path;
		
		if(!path.endsWith(getLibraryExtension(os))) {
			DikenEngine.log("Native kütüphanenin uzantısı uyumsuz: " + path + " != " + getLibraryExtension(os));
			return;
		}
		
        // InputStream ile kaynağı oku
        InputStream in = NativeManager.class.getResourceAsStream(path);
        if (in == null) {
            throw new IOException("Library not found: " + path);
        }
        
        // Dosya uzantısını belirle (Windows: .dll, Linux: .so, Mac: .dylib)
        String libExtension = getLibraryExtension();
        String libName = getLibraryName(path);
        
        if (!getLibraryExtension(os).equals(libExtension)) {
			DikenEngine.log("Native kütüphanenin uzantısı uyumsuz: " + libExtension);
			return;
		}
        
        // Geçici dosya oluştur
        File temp = new File(nativeDir, libName + libExtension);
        
        cloneNativeFile(in, temp);
        
        // Native kütüphaneyi yükle
        System.load(temp.getAbsolutePath());
    }
	
	public static void loadLibraryFromOSPathFromJar(String path) throws IOException {
		GetOS.OS os = GetOS.getOS();
		
		path = "/natives/" + os.name().toLowerCase() + path;
		
		if(!path.endsWith(getLibraryExtension(os))) {
			DikenEngine.log("Native kütüphanenin uzantısı uyumsuz: " + path + " != " + getLibraryExtension(os));
			return;
		}
		
        // InputStream ile kaynağı oku
        InputStream in = NativeManager.class.getResourceAsStream(path);
        if (in == null) {
            throw new IOException("Library not found: " + path);
        }
        
        // Dosya uzantısını belirle (Windows: .dll, Linux: .so, Mac: .dylib)
        String libExtension = getLibraryExtension();
        String libName = getLibraryName(path);
        
        if (!getLibraryExtension(os).equals(libExtension)) {
			DikenEngine.log("Native kütüphanenin uzantısı uyumsuz: " + libExtension);
			return;
		}
        
        // Geçici dosya oluştur
        File temp = new File(nativeDir, libName + libExtension);
        
        cloneNativeFile(in, temp);
        
        // Native kütüphaneyi yükle
        System.load(temp.getAbsolutePath());
    }
	
	private static void cloneNativeFile(InputStream in, File temp) throws IOException {
		if (!temp.exists()) {
			// InputStream'den geçici dosyaya kopyala
	        try (FileOutputStream out = new FileOutputStream(temp)) {
	            byte[] buffer = new byte[4096];
	            int bytesRead;
	            while ((bytesRead = in.read(buffer)) != -1) {
	                out.write(buffer, 0, bytesRead);
	            }
	        } finally {
	            in.close();
	        }
		}
	}
	
	public static void loadedNativeSetProperty(String property) {
		System.setProperty(property, nativeDir.getAbsolutePath());
	}
    
    private static String getLibraryExtension() {
        GetOS.OS os = GetOS.getOS();
        if (os == GetOS.OS.Windows) {
            return ".dll";
        } else if (os == GetOS.OS.Mac) {
            return ".dylib";
        } else {
            return ".so";
        }
    }
    
    private static String getLibraryExtension(GetOS.OS os) {
        if (os == GetOS.OS.Windows) {
            return ".dll";
        } else if (os == GetOS.OS.Mac) {
            return ".dylib";
        } else {
            return ".so";
        }
    }

    private static String getLibraryName(String path) {
        String[] parts = path.split("/");
        String fileName = parts[parts.length - 1];
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex > 0) ? fileName.substring(0, dotIndex) : fileName;
    }
}
