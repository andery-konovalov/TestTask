package com.example.testtask.data.holder;

import org.json.JSONObject;

import android.os.Parcelable;

public interface DataNotifierInterface extends Parcelable{
	
	public JSONObject waitForRawData() throws InterruptedException;
	public JSONObject waitForResultData() throws InterruptedException;
	
	public JSONObject checkResultData();
	public JSONObject checkRawData();
	
	public void dataArrivedNotify(JSONObject rawData, JSONObject resultData);
	public void updateDataNotify();
}
