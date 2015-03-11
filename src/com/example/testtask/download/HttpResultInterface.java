package com.example.testtask.download;

import org.json.JSONObject;

interface HttpResultInterface {
	
	public void onDownloadingComplete(JSONObject jResult, int responseCode);

}
