package com.example.testtask.processor;

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
	
	private JSONObject processData(JSONObject jObject) {
		JSONObject jResultObj = null;
		try {
			JSONArray jDataOrder = jObject.getJSONArray("dataOrder");
			JSONArray jDataValuesArr = jObject.getJSONArray("dataValues");
			ArrayList<DataObject> solutionArray = new ArrayList<DataObject>();
			for(int i = 0;i < jDataOrder.length();i ++) {
				solutionArray.add(new DataObject(jDataOrder.getInt(i), jDataValuesArr.getString(i)));
			}
			Collections.sort(solutionArray);
			jResultObj = new JSONObject();
			jResultObj.put("solution", new JSONArray(solutionArray));
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
