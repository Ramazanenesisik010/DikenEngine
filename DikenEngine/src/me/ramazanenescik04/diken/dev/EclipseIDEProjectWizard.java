package me.ramazanenescik04.diken.dev;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EclipseIDEProjectWizard implements IDEProjectWizard {

	public boolean createProject(String projectName, File projectDir) {
		try {
			// .project file
	        String projectXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
	                "<projectDescription>\n" +
	                "  <name>" + projectName + "</name>\n" +
	                "  <comment></comment>\n" +
	                "  <projects></projects>\n" +
	                "  <buildSpec>\n" +
	                "    <buildCommand>\n" +
	                "      <name>org.eclipse.jdt.core.javabuilder</name>\n" +
	                "      <arguments></arguments>\n" +
	                "    </buildCommand>\n" +
	                "  </buildSpec>\n" +
	                "  <natures>\n" +
	                "    <nature>org.eclipse.jdt.core.javanature</nature>\n" +
	                "  </natures>\n" +
	                "</projectDescription>";
	        try (FileWriter fw = new FileWriter(new File(projectDir, ".project"))) {
	            fw.write(projectXml);
	        }
	        // .classpath file
	        String classpathXml = "<classpath>\n" +
	                "  <classpathentry kind=\"src\" path=\"src\"/>\n" +
	                "  <classpathentry kind=\"lib\" path=\"lib/DikenEngine.jar\"/>\n" +
	                "  <classpathentry kind=\"lib\" path=\"lib/commons-lang3-3.17.0.jar\"/>\n" +
	                "  <classpathentry kind=\"lib\" path=\"lib/jinput.jar\"/>\n" +
	                "  <classpathentry kind=\"lib\" path=\"lib/jna-5.12.1.jar\"/>\n" +
	                "  <classpathentry kind=\"lib\" path=\"lib/lwjgl_util.jar\"/>\n" +
	                "  <classpathentry kind=\"lib\" path=\"lib/lwjgl.jar\"/>\n" +
	                "  <classpathentry kind=\"con\" path=\"org.eclipse.jdt.launching.JRE_CONTAINER\"/>\n" +
	                "  <classpathentry kind=\"output\" path=\"bin\"/>\n" +
	                "</classpath>";
	        try (FileWriter fw = new FileWriter(new File(projectDir, ".classpath"))) {
	            fw.write(classpathXml);
	        }
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
