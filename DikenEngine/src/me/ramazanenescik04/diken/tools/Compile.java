package me.ramazanenescik04.diken.tools;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

import javax.tools.*;

/**
 * Java kodunu derlemek ve yüklemek için kullanılan sınıf
 * 
 * @author Ramazanenescik04
 */
public class Compile {
	
	/**
	 * Derleme işlemini gerçekleştiren method
	 * @param className
	 * @param code
	 * @return URLClassLoader
	 */
	public static URLClassLoader compile(String className, String code) {
		try {
            // Çıktı dizini (mevcut dizin)
            File outputDir = new File("./compiled_code/");
            outputDir.mkdirs(); // Dizin yoksa oluştur
            
            // Java derleyicisini al
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
            StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
            
            // Çıktı dizinini ayarla
            fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(outputDir));
            
            // String'den JavaFileObject oluştur
            JavaFileObject file = new JavaSourceFromString(className, code);
            
            Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(file);
            JavaCompiler.CompilationTask task = compiler.getTask(
                null, fileManager, diagnostics, null, null, compilationUnits);
            
            boolean success = task.call();
            
            if (success) {
                System.out.println("Derleme başarılı.");
                
                // URLClassLoader kullanarak derlenen sınıfı yükle
                URL[] urls = new URL[] { outputDir.toURI().toURL() };
                URLClassLoader classLoader = new URLClassLoader(urls);
                return classLoader;
            } else {
                System.out.println("Derleme başarısız:");
                for (Diagnostic<?> diagnostic : diagnostics.getDiagnostics()) {
                    System.out.format("Satır %d: Hata: %s%n",
                            diagnostic.getLineNumber(),
                            diagnostic.getMessage(null));
                }
            }
            
            fileManager.close();
        } catch (Exception e) {
            System.err.println("İşlem sırasında hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
		 return null; // Geri dönüş tipi URLClassLoader, ancak burada kullanılmıyor
	}
	
	public static URLClassLoader getCompiledClassLoader() {
		try {
			File outputDir = new File("./compiled_code/");
			outputDir.mkdirs(); // Dizin yoksa oluştur
			URL[] urls = new URL[] { outputDir.toURI().toURL() };
			URLClassLoader classLoader = new URLClassLoader(urls);
			return classLoader;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Java kodunu temsil eden sınıf
	 */
	private static class JavaSourceFromString extends SimpleJavaFileObject {
        final String code;
        
        JavaSourceFromString(String name, String code) {
            super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
            this.code = code;
        }
        
        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return code;
        }
    }

}
