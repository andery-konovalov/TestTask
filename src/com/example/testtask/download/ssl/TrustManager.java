package com.example.testtask.download.ssl;

import java.math.BigInteger;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

import android.util.Log;

class TrustManager implements X509TrustManager {
	
	private static final String TAG = TrustManager.class.getCanonicalName();

	public void checkClientTrusted(X509Certificate[] cert, String authType)
			throws CertificateException {
		BigInteger certSerialNumber = cert[0].getSerialNumber();
		Log.d(TAG, "checkClientTrusted" + certSerialNumber.toString(16));
		
	}

	public void checkServerTrusted(X509Certificate[] cert, String authType)
			throws CertificateException {
		BigInteger bi = new BigInteger("4e411d20", 16);
		BigInteger certsn = cert[0].getSerialNumber();
		Log.d(TAG, "checkServerTrusted: '" + certsn.toString(16) + "'");
		if(!bi.equals(certsn) ) {
			throw new CertificateException("Invalid certificate serial number");
		}
	}
	

	public X509Certificate[] getAcceptedIssuers() {
		Log.d(TAG, "getAcceptedIssuers");
		return null;
	}
}