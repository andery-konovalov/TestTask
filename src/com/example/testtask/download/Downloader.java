package com.example.testtask.download;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

abstract class Downloader extends AsyncTask<Void, Void, JSONObject> implements HttpResultInterface {

	private int responseCode;
	
	protected abstract HttpURLConnection makeConnection() throws IOException;
	
	@Override
	protected JSONObject doInBackground(Void ... vvv) {
		HttpURLConnection urlConnection = null;
		JSONObject resultVal = null;
		try {
			urlConnection = makeConnection();
			
			InputStream httpInputStream = new BufferedInputStream(urlConnection.getInputStream());

			Scanner input = new Scanner(httpInputStream);
			String responseString = input.useDelimiter("\\A").next();
			input.close();
			resultVal = new JSONObject(responseString);

			urlConnection.disconnect();
			
		} catch(JSONException | IOException e) {
			e.printStackTrace();
		} finally {
			if(urlConnection != null) {
				try {
					responseCode = urlConnection.getResponseCode();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return resultVal;
	}

	@Override
	protected void onPostExecute(JSONObject jObj) {
		onDownloadingComplete(jObj, responseCode);
	}

}
