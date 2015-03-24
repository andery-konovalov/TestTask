package com.example.testtask.data.processor;

import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public abstract class DataProcessor extends AsyncTask<JSONObject, Void, JSONObject> {

	protected abstract void onProcessingComplete(JSONObject result);
	
	@Override
	protected JSONObject doInBackground(JSONObject... params) {
		return processData(params[0]);
	}
	
/*
	private String reverse(String str) {
		String retVal = "";
//		new StringBuilder(str).reverse();
		for(int i = str.length() - 1; i != 0; i--) {
			retVal += str.charAt(i);
		}
		return retVal;
	}
*/
	
	private JSONObject processData(JSONObject jObject) {
		JSONObject jResultObj = null;
		try {
			JSONArray jDataOrder = jObject.getJSONArray("dataOrder");
			JSONArray jDataValuesArr = jObject.getJSONArray("dataValues");
			
			JSONArray testArr = new JSONArray();
			ArrayList<DataObject> solutionArray = new ArrayList<DataObject>();
			for(int i = 0;i < jDataOrder.length();i ++) {
				DataObject dObj = new DataObject(jDataOrder.getInt(i), jDataValuesArr.getString(i));
				solutionArray.add(dObj);
				testArr.put(dObj);
			}
			
			Collections.sort(solutionArray);
			JSONArray resultArr = new JSONArray();
			for (DataObject dataObject : solutionArray) {
				resultArr.put(dataObject);
			}
			
			jResultObj = new JSONObject();
			
			jResultObj.put("solution", resultArr);
			Log.d("", jResultObj.toString());
			
			

		} catch(JSONException e) {
		}
		return jResultObj;
	}
	
	@Override
	protected void onPostExecute(JSONObject jObj) {
		onProcessingComplete(jObj);
	}

}
