package me.ramazanenescik04.diken.net;

import java.io.*;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.*;

public class CertificateManager {
	private static ArrayList<SSLSocketFactory> loadedSSLs = new ArrayList<>();
	
	public static SSLSocketFactory createCustomSSLSocketFactory(String certPath) throws Exception {
	    // 1. Sistem varsayılan truststore'unu yükle
	    KeyStore defaultKeyStore = KeyStore.getInstance(KeyStore.getDefaultType());
	    try (FileInputStream fis = new FileInputStream(System.getProperty("java.home") + "/lib/security/cacerts")) {
	        defaultKeyStore.load(fis, "changeit".toCharArray()); // Varsayılan şifre: changeit
	    }

	    // 2. .cer dosyasını yükle ve truststore'a ekle
	    X509Certificate cert = loadCertificate(certPath);
	    defaultKeyStore.setCertificateEntry("custom-cert", cert); // "custom-cert" benzersiz bir alias

	    // 3. Özel TrustManager oluştur
	    TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
	    tmf.init(defaultKeyStore);

	    // 4. SSLContext'i özelleştir
	    SSLContext sslContext = SSLContext.getInstance("TLS");
	    sslContext.init(null, tmf.getTrustManagers(), null);
	    SSLSocketFactory aaa = sslContext.getSocketFactory();
	    add(aaa);
	    return aaa;
	}

	public static X509Certificate loadCertificate(String filePath) throws Exception {
	    CertificateFactory factory = CertificateFactory.getInstance("X.509");
	    try (FileInputStream fis = new FileInputStream(filePath)) {
	        return (X509Certificate) factory.generateCertificate(fis);
	    }
	}
	
	public static SSLSocketFactory getCerf(int id) {
		return loadedSSLs.get(id);
	}
	
	public static int size() {
		return loadedSSLs.size();
	}
	
	public static void add(SSLSocketFactory a) {
		loadedSSLs.add(a);
	}
	
	public static void delete(int id) {
		loadedSSLs.remove(id);
	}
}
