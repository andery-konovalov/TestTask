package com.example.testtask.data.holder;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;

public abstract class DataNotifier implements DataNotifierInterface, DataAccessoryInterface {

	protected abstract void setData(JSONObject rawData, JSONObject resultData);
	
	
	public static DataNotifierInterface makeFromParcel(Parcel p) {
		DataHolder retVal = null;
		try {
			retVal = new DataHolder(p);
		} catch(JSONException e) {
		}
		return retVal;
	}
	
	public static DataNotifierInterface makeInstance() {
		return new DataHolder();
	}

	@Override
	public JSONObject waitForRawData() throws InterruptedException {
		synchronized (this) {
			wait();
		}
		return getRawData();
	}

	@Override
	public JSONObject waitForResultData() throws InterruptedException {
		synchronized (this) {
			wait();
		}
		return getResultData();
	}

	@Override
	public void dataArrivedNotify(JSONObject rawData, JSONObject resultData) {
		synchronized(this) {
			setData(rawData, resultData);
			notifyAll();
		}
	}
	
	@Override
	public void updateDataNotify() {
		synchronized(this) {
			notifyAll();
		}
	}

}
