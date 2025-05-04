package me.ramazanenescik04.diken.game;

import java.io.*;
import java.util.Enumeration;
import java.util.jar.*;
import java.util.zip.Deflater;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.json.JSONObject;

import me.ramazanenescik04.diken.DikenEngine;
import me.ramazanenescik04.diken.gui.compoment.Button;
import me.ramazanenescik04.diken.gui.compoment.Text;
import me.ramazanenescik04.diken.gui.screen.Screen;
import me.ramazanenescik04.diken.net.CertificateManager;
import me.ramazanenescik04.diken.net.WebGet;
import me.ramazanenescik04.diken.resource.Bitmap;

public class DevGame extends Screen implements IGame {
	
	public void startGame(DikenEngine engine) {
		engine.setCurrentScreen(this);
	}
	
	public void openScreen() {
		this.getContentPane().addCompoment(new Button("addGamePath", 2, 2, 100, 20).setRunnable(() -> {
			try {
				JarFile jarFile = null;
			
				JFileChooser fChooser = new JFileChooser();
				fChooser.setFileFilter(new FileFilter() {
					public boolean accept(File f) {
						if((f.getName().endsWith("jar") || f.getName().endsWith("jmod")) && f.exists()) {
							return true;
						}
						return false;
					}

					public String getDescription() {
						return "JAR Dosyası";
					}
				});
				fChooser.setMultiSelectionEnabled(false);
				fChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int selectMode = fChooser.showOpenDialog(null);
				if(selectMode == JFileChooser.APPROVE_OPTION) {
					jarFile =  new JarFile(fChooser.getSelectedFile());
				} else if (selectMode == JFileChooser.CANCEL_OPTION) {
					return;
				}
				
				String classPath = JOptionPane.showInputDialog("Class Path'ı girin");
			
				Manifest manifest = jarFile.getManifest();

				// Manifest yoksa yeni oluştur
				if (manifest == null) {
	                manifest = new Manifest();
	                manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
	            }
	
	            // 2. Manifest'i değiştir (Örnek: Main-Class ekle)
	            Attributes mainAttributes = manifest.getMainAttributes();
	            mainAttributes.put(new Attributes.Name("Game-Class"), classPath);
				
				// 3. Yeni JAR'ı oluştur
	            try (JarOutputStream jos = new JarOutputStream(new FileOutputStream("./modified.jar"), manifest)) {
	                jos.setLevel(Deflater.BEST_COMPRESSION); // İsteğe bağlı: Sıkıştırma seviyesi
	
	                // 4. Orijinal JAR'daki tüm girişleri kopyala (manifest hariç)
	                Enumeration<JarEntry> entries = jarFile.entries();
	                while (entries.hasMoreElements()) {
	                    JarEntry entry = entries.nextElement();
	                    if (entry.getName().equalsIgnoreCase("META-INF/MANIFEST.MF")) {
	                        continue; // Eski manifesti atla
	                    }
	
	                    // Girişi yeni JAR'a ekle
	                    try (InputStream is = jarFile.getInputStream(entry)) {
	                        jos.putNextEntry(new JarEntry(entry.getName()));
	                        byte[] buffer = new byte[4096];
	                        int bytesRead;
	                        while ((bytesRead = is.read(buffer)) != -1) {
	                            jos.write(buffer, 0, bytesRead);
	                        }
	                        jos.closeEntry();
	                    }
	                }
	            }
	            jarFile.close();
				} catch (IOException e) {
					e.printStackTrace();
			}
		}));
	}
	
	public void render(Bitmap bitmap) {
		super.render(bitmap);
		Text.render("DikenEngine " + DikenEngine.VERSION + " - ProtocolVer: " + DikenEngine.protocolVersion, bitmap, 2, engine.getHeight() - 10);
	}

	public void loadResources() {
		
	}

}
