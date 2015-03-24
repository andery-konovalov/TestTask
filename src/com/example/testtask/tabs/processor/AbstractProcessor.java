package com.example.testtask.tabs.processor;

import org.json.JSONObject;


public abstract class AbstractProcessor {
	
	public interface CompleteListener {
		public void onProcessComplete(JSONObject result, int responseCode);
	}
	
	public abstract void startProcessing(CompleteListener complete);
}
