package com.example.testtask.download;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;


import com.example.testtask.download.ssl.TrustSSLSocketFactory;

public abstract class HttpsChallenge extends Downloader implements HostnameVerifier {

	protected final URL url;
	public HttpsChallenge(URL url) {
		this.url = url;
	}
	
	protected HttpURLConnection makeConnection() throws IOException {
		HttpsURLConnection httpsConnection = (HttpsURLConnection)url.openConnection();
		httpsConnection.setSSLSocketFactory(TrustSSLSocketFactory.getDefault());
		httpsConnection.setHostnameVerifier(this);
		return httpsConnection;
	}


	@Override
	public boolean verify(String hostname, SSLSession session) {
		return true;
	}

}
