package com.kentsong.gcm.tool;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class GcmIntentService extends IntentService {

	private final String TAG = "gcmPush";

	public GcmIntentService() {
		super("GcmIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub

		Bundle extras = intent.getExtras();
		Log.d(TAG, extras.toString());


//		if (Values.GCM_SENDER_ID.equals(extras.getString("from"))) {

			Log.d(TAG, "GET PUSH");
			Log.d(TAG, "extras:" + extras);



//		}

		Intent boardCastIntent = new Intent();
		boardCastIntent.putExtras(extras);
		boardCastIntent.setAction("android.intent.action.test");
		Log.d(TAG, "extras:" + extras.get("message"));
		sendBroadcast(boardCastIntent);
		sendNotification(extras);

		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	private void sendNotification(Bundle extras) {
		String text = extras.getString("message");

		NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

//		Intent intent = new Intent(this, MainActivity.class);
//		intent.putExtras(extras);
//
//		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_NO_CREATE);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
				.setDefaults(Notification.DEFAULT_ALL)
				.setAutoCancel(true)
				.setSmallIcon(R.drawable.common_google_signin_btn_icon_light)
				.setTicker(this.getResources().getString(R.string.app_name) + ":" + text)
				.setContentTitle(this.getResources().getString(R.string.app_name))
				.setContentText(text);
//				.setContentIntent(contentIntent);
		mNotificationManager.notify("", 0, mBuilder.build());
	}



}