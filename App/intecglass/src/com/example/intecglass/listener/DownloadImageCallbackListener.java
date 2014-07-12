package com.example.intecglass.listener;

import android.graphics.Bitmap;

public interface DownloadImageCallbackListener {
	/**
	 * This method is called from the AsynTask used to retrieve data from the service.
	 * @param obj the JSONObject to process from the GET request.
	 */
	
	
	public void processImage(Bitmap image);
	
	
	public void showDownloadErrorMessage();
}
