package com.example.testtask.processor;

public class DataObject implements Comparable<DataObject> {
	private final int idx;
	private final String value;
	public DataObject(int idx, String value) {
		this.idx = idx;
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
	

	@Override
	public int compareTo(DataObject another) {
		int retVal = -1;
		if(idx == another.idx) {
			retVal = 0;
		} else if(idx > another.idx) {
			retVal = 1;
		}
		return retVal;
	}
}
