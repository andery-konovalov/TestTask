package com.example.testtask;

import org.json.JSONObject;

public interface DataNotifier {
	public JSONObject getRawData();
	public JSONObject getResultData();
	public JSONObject waitForRawData() throws InterruptedException;
	public JSONObject waitForResultData() throws InterruptedException;
	public void onDataNotifier(JSONObject rawData, JSONObject resultData);
}
