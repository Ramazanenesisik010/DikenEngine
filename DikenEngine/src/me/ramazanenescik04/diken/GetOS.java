package me.ramazanenescik04.diken;

public class GetOS {
	
	public static enum OS {
		Windows, Mac, Linux, Unknown
	}
	
	public static enum Arch {
		x86, x64, Aarch64, Arm, Unknown
	}
	
	public static OS getOS() {
		String os = System.getProperty("os.name").toLowerCase();
		if (os.contains("win")) {
			return OS.Windows;
		} else if (os.contains("mac")) {
			return OS.Mac;
		} else if (os.contains("nix") || os.contains("nux")) {
			return OS.Linux;
		} else {
			return OS.Unknown;
		}
	}
	
	public static Arch getArch() {
		String arch = System.getProperty("os.arch").toLowerCase();
		if (arch.contains("64")) {
			return Arch.x64;
		} else if (arch.contains("32")) {
			return Arch.x86;
		} else if (arch.contains("aarch64") || arch.contains("arm64")) {
			return Arch.Aarch64;
		} else if (arch.contains("arm")) {
			return Arch.Arm;
		} else {
			return Arch.Unknown;
		}
	}

}
