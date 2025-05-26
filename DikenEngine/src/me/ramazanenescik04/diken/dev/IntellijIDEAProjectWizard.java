package me.ramazanenescik04.diken.dev;

import java.io.*;

public class IntellijIDEAProjectWizard implements IDEProjectWizard {

	public boolean createProject(String projectName, File projectDir) {
		try {
			// Create .idea and .iml files (simplified)
            File iml = new File(projectDir, projectName + ".iml");
            String imlContent = "<module type=\"JAVA_MODULE\" version=\"4\">\n" +
                    "  <component name=\"NewModuleRootManager\">\n" +
                    "    <content url=\"file://$MODULE_DIR$\">\n" +
                    "      <sourceFolder url=\"file://$MODULE_DIR$/src\" isTestSource=\"false\" />\n" +
                    "      <excludeFolder url=\"file://$MODULE_DIR$/out\" />\n" +
                    "    </content>\n" +
                    "    <orderEntry type=\"inheritedJdk\" />\n" +
                    "    <orderEntry type=\"library\" name=\"DikenEngine\" level=\"project\" />\n" +
                    "    <orderEntry type=\"library\" name=\"commons-lang3-3.17.0\" level=\"project\" />\n" +
                    "    <orderEntry type=\"library\" name=\"jinput\" level=\"project\" />\n" +
                    "    <orderEntry type=\"library\" name=\"jna-5.12.1\" level=\"project\" />\n" +
                    "    <orderEntry type=\"library\" name=\"lwjgl_util\" level=\"project\" />\n" +
                    "    <orderEntry type=\"library\" name=\"lwjgl\" level=\"project\" />\n" +
                    "  </component>\n" +
                    "</module>";
            try (FileWriter fw = new FileWriter(iml)) {
                fw.write(imlContent);
            }
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
