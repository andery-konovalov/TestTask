package com.example.testtask.tabs.processor;

import java.net.URL;

import org.json.JSONObject;

import com.example.testtask.download.HttpSolver;

class SolveProcessor extends AbstractProcessor {

	private final URL solverUrl;
	private final JSONObject solutionResult;
	public SolveProcessor(URL solverUrl, JSONObject solution) {
		this.solverUrl = solverUrl;
		solutionResult = solution;
	}

	@Override
	public void startProcessing(final CompleteListener complete) {
		new HttpSolver(solverUrl, solutionResult) {
			
			@Override
			public void onDownloadingComplete(JSONObject jResult, int responseCode) {
				complete.onProcessComplete(jResult, responseCode);
			}
		}.execute((Void)null);
		
	}

}
