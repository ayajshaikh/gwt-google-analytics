package com.alignace.gwt.ganalytics.client;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.ScriptInjector;

public class GAnalytics {
	private static final String GOOGLE_ANALYTICS_JAVASCRIPT_URL = "https://www.google-analytics.com/ga.js";

	private GAnalytics() {

	}

	public static void initialize(String trackingId,
			Callback<Void, Exception> callback) {
		setAnalyticsParams(trackingId);
		inject(callback);
	}

	private static native String setAnalyticsParams(String trackingId) /*-{
		var _gaq = _gaq || [];
		_gaq.push([ '_setAccount', trackingId ]);
		_gaq.push([ '_trackPageview' ]);
	}-*/;

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

	private static native boolean isInjected() /*-{
												return typeof $wnd._gat !== "undefined";
												}-*/;
}
