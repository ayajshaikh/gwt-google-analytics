package com.alignace.gwt.ganalytics.client;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.ScriptInjector;

/**
 * GWT - Google Analytics
 * 
 * @author Ayajahmed Shaikh
 * @version 1.0
 */
public class GAnalytics {
	private static final String GOOGLE_ANALYTICS_JAVASCRIPT_URL = "https://www.google-analytics.com/ga.js";

	/**
	 * Constructor is private just to avoid accidental use without
	 * initialization
	 */
	private GAnalytics() {

	}

	/**
	 * This should always be called first to initialize Google Analytics.
	 * 
	 * @param trackingId - Tracking Id of Google Analytics (e.g. UA-XXXXXXXX-X)
	 * @param callback
	 */
	public static void initialize(String trackingId,
			Callback<Void, Exception> callback) {
		setAnalyticsParams(trackingId);
		inject(callback);
	}

	/**
	 * Native Method to initialize
	 * @param trackingId
	 * @return
	 */
	private static native String setAnalyticsParams(String trackingId) /*-{
		 $wnd._gaq =  $wnd._gaq || [];
		 $wnd._gaq.push([ '_setAccount', trackingId ]);
		 $wnd._gaq.push([ '_trackPageview' ]);
	}-*/;

	/**
	 * Injects Google Analytics Javascript
	 * @param callback
	 */
	private static void inject(final Callback<Void, Exception> callback) {
		if (!isInjected()) {
			ScriptInjector.fromUrl(GOOGLE_ANALYTICS_JAVASCRIPT_URL)
					.setWindow(ScriptInjector.TOP_WINDOW)
					.setCallback(new Callback<Void, Exception>() {
						public void onFailure(Exception reason) {
							callback.onFailure(reason);
						}

						public void onSuccess(Void result) {
							callback.onSuccess(result);
						}

					}).inject();
		}
	}

	/**
	 * Checks if GA JS is already imported
	 * @return
	 */
	private static native boolean isInjected() /*-{
		return typeof $wnd._gat !== "undefined";
	}-*/;
}
