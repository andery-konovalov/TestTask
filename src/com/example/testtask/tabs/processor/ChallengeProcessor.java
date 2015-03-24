package com.example.testtask.tabs.processor;

import java.net.URL;

import org.json.JSONObject;

import com.example.testtask.download.HttpsChallenge;

class ChallengeProcessor extends AbstractProcessor  {

	private final URL url;
	public ChallengeProcessor(URL requestUrl) {
		url = requestUrl;
	}
	
	@Override
	public void startProcessing(final CompleteListener complete) {
		new HttpsChallenge(url) {
			
			@Override
			public void onDownloadingComplete(JSONObject jResult, int responseCode) {
				complete.onProcessComplete(jResult, responseCode);
			}
		}.execute((Void)null);
	}

}
