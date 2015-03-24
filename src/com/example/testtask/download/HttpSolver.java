package com.example.testtask.download;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

import com.example.testtask.download.ssl.TrustSSLSocketFactory;

public abstract class HttpSolver extends HttpsChallenge {
	
	private final JSONObject postData;
	public HttpSolver(URL url, JSONObject data) {
		super(url);
		postData = data;
	}
	
	
	
	@Override
	protected HttpURLConnection makeConnection() throws IOException {
		
		HttpsURLConnection httpsConnection = getConnection();
		
		setupBody(httpsConnection);
		
		httpsConnection.connect();
		return httpsConnection;
	}



	private void setupBody(HttpsURLConnection httpsConnection)
			throws IOException {
		OutputStream os = httpsConnection.getOutputStream();
		os.write("solution=".getBytes());
		os.write(postData.toString().getBytes());
		os.close();
	}



	private HttpsURLConnection getConnection() throws IOException, ProtocolException {
		HttpsURLConnection httpsConnection = (HttpsURLConnection)url.openConnection();
		httpsConnection.setSSLSocketFactory(TrustSSLSocketFactory.getDefault());
		httpsConnection.setHostnameVerifier(this);
		httpsConnection.setRequestMethod("POST");
		httpsConnection.setDoInput(true);
		httpsConnection.setDoOutput(true);
		return httpsConnection;
	}
	
}
