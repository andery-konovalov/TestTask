package com.example.testtask.processor;

import android.util.Log;

public class DataObject implements Comparable<DataObject> {
	
	private final int index;
	private final String value;
	
	public DataObject(int idx, String value) {
		this.index = idx;
		this.value = value;
	}
	
	@Override
	public String toString() {
//		Log.d(DataObject.class.getCanonicalName(), "toString:" + value);
		return value;
	}
	

	@Override
	public int compareTo(DataObject another) {
		int retVal = -1;
		if(index == another.index) {
			retVal = 0;
		} else if(index > another.index) {
			retVal = 1;
		}
		return retVal;
	}
}
