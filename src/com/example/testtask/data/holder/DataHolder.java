package com.example.testtask.data.holder;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;

class DataHolder extends DataNotifier {
	
	private static int RAW_DATA_INDEX = 0;
	private static int RESULT_DATA_INDEX = 1;
	
	private JSONObject rawData;
	private JSONObject resultData;

	public DataHolder() {
	}
	
	public DataHolder(Parcel p) throws JSONException {
		String data[] = new String[2];
		p.readStringArray(data);
		if(data[RAW_DATA_INDEX] != null && data[RESULT_DATA_INDEX] != null) {
			rawData = new JSONObject(data[RAW_DATA_INDEX]);
			resultData = new JSONObject(data[RESULT_DATA_INDEX]);
		}
	}
	
	
	@Override
	public JSONObject getRawData() {
		return rawData;
	}

	@Override
	public JSONObject getResultData() {
		return resultData;
	}

	protected void setData(JSONObject rawData, JSONObject resultData) {
		this.rawData = rawData;
		this.resultData = resultData;
		
	}

	
	//parcelable implementation
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		String strArr[] = new String[2];
		strArr[RAW_DATA_INDEX] = rawData.toString();
		strArr[RESULT_DATA_INDEX] = resultData.toString();
		dest.writeStringArray(strArr);
		
	}

	@Override
	public JSONObject checkResultData() {
		return resultData;
	}

	@Override
	public JSONObject checkRawData() {
		return rawData;
	}

}
