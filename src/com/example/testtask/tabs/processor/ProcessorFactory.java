package com.example.testtask.tabs.processor;

import java.net.URL;

import org.json.JSONObject;

public class ProcessorFactory {
	
	public static AbstractProcessor getChallengeProcessor(URL requestUrl) {
		return new ChallengeProcessor(requestUrl);
	}
	
	public static AbstractProcessor getSolveProcessor(URL solveUrl, JSONObject solution) {
		return new SolveProcessor(solveUrl, solution);
	}
	
}
