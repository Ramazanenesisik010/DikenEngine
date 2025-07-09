package me.ramazanenescik04.diken.dev;

import java.io.*;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.jar.*;
import java.util.zip.Deflater;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import me.ramazanenescik04.diken.DikenEngine;
import me.ramazanenescik04.diken.game.IGame;
import me.ramazanenescik04.diken.gui.compoment.Button;
import me.ramazanenescik04.diken.gui.compoment.Text;
import me.ramazanenescik04.diken.gui.screen.Screen;
import me.ramazanenescik04.diken.resource.Bitmap;

public class DevGame extends Screen implements IGame {
	
	public void startGame(DikenEngine engine) {
		engine.setCurrentScreen(this);
	}
	
	public void openScreen() {
		this.engine.setFullscreen(true);
		
		this.getContentPane().clear();
		
		this.getContentPane().add(new Button("addGamePath", 2, 2, 100, 20).setRunnable(() -> {
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
		
		// Inside the Runnable for "createProject" button
		this.getContentPane().add(new Button("createProject", 2, 26, 100, 20).setRunnable(() -> {
		    String[] options = {"Eclipse", "IntelliJ IDEA"};
		    int ideChoice = JOptionPane.showOptionDialog(null, "Select IDE", "Project Type",
		            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		    if (ideChoice == -1) return;

		    String projectName = JOptionPane.showInputDialog("Enter project name:");
		    if (projectName == null || projectName.trim().isEmpty()) return;

		    JFileChooser chooser = new JFileChooser();
		    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    int result = chooser.showSaveDialog(null);
		    if (result != JFileChooser.APPROVE_OPTION) return;

		    File projectDir = new File(chooser.getSelectedFile(), projectName);
		    if (!projectDir.mkdirs()) {
		        JOptionPane.showMessageDialog(null, "Failed to create project directory.");
		        return;
		    }

		    try {
		        // Common: src and lib folders
		        File srcDir = new File(projectDir, "src");
		        File libDir = new File(projectDir, "lib");
		        srcDir.mkdirs();
		        libDir.mkdirs();

		        // Copy DikenEngine.jar to lib (assume it's in current dir)
		        String jarPath = "";
				try {
					jarPath = new File(DikenEngine.class.getProtectionDomain()
					        .getCodeSource().getLocation().toURI()).getPath();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        @SuppressWarnings("unused")
				File dikenJar = new File(jarPath);
		        String[] libFiles = {"engineLib.jar", "commons-lang3-3.17.0.jar", "jinput.jar", "lwjgl.jar", "lwjgl-uril.jar", "jna-5.12.1.jar"};
		        for (String libFile : libFiles) {
		            File sourceFile = new File(libFile);
		            if (sourceFile.exists() && sourceFile.isFile()) {
		                try (InputStream in = new FileInputStream(sourceFile);
		                     OutputStream out = new FileOutputStream(new File(libDir, libFile))) {
		                    byte[] buf = new byte[4096];
		                    int len;
		                    while ((len = in.read(buf)) > 0) out.write(buf, 0, len);
		                }
		            } else {
		                JOptionPane.showMessageDialog(null, "Required library file " + libFile + " not found.");
		                break;
		            }
		        }
		        
		        IDEProjectWizard ideProjectWizard = null;
		        
		        if (ideChoice == 0) { // Eclipse
		        	ideProjectWizard = new EclipseIDEProjectWizard();
		        } else if (ideChoice == 1) { // IntelliJ IDEA
		        	ideProjectWizard = new IntellijIDEAProjectWizard();
		        }
		        
		        if (!ideProjectWizard.createProject(projectName, projectDir)) {
		        	JOptionPane.showMessageDialog(null, "Failed to create project files.");
		        	return;
		        }

		        // Create a Main.java file
		        File mainJava = new File(srcDir, "Main.java");
		        String mainContent = "public class Main implements IGame {\n" +
		        		"	 public void loadResources() {" +
		        		"    }\n" +
		        		"	 " +
		        		"    public void startGame(DikenEngine engine) {\n" +
		        		"	 	System.out.println(\"Hello from " + projectName + "!\");\n" +
		        		"    }\n" +
		                "    public static void main(String[] args) {\n" +
		                "    	DikenEngine.startTestGame(new Main());    " +
		                "    }\n" +
		                "}";
		        try (FileWriter fw = new FileWriter(mainJava)) {
		            fw.write(mainContent);
		        }

		        JOptionPane.showMessageDialog(null, "Project created successfully!");

		    } catch (IOException ex) {
		        ex.printStackTrace();
		        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
		    }
		}));

		
		this.getContentPane().add(new Button("openGuiEditor", 2, 50, 100, 20).setRunnable(() -> {			
			this.engine.setCurrentScreen(new GuiEditorScreen(this));
		}));
	}
	
	public void render(Bitmap bitmap) {
		super.render(bitmap);
		Text.render("DikenEngine " + DikenEngine.VERSION + " - ProtocolVer: " + DikenEngine.protocolVersion, bitmap, 2, engine.getHeight() - 10);
	}

	public void loadResources() {	
	}
	
}