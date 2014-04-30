package com.rane.smcblock;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.gsm.SmsMessage;
import android.util.Log;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SmsReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		// ---get the SMS message passed in---
		Bundle bundle = intent.getExtras();
		SmsMessage[] msgs = null;
		String str = "";
		if (bundle != null) {
			// ---retrieve the SMS message received---
			Object[] pdus = (Object[]) bundle.get("pdus");
			msgs = new SmsMessage[pdus.length];

			String FROM = "";
			String MESSAGE = "";
			
			for (int i = 0; i < msgs.length; i++) {
				Bundle bundle2 = new Bundle();
				msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);

				MESSAGE += msgs[i].getMessageBody().toString();
				
				FROM = msgs[i].getOriginatingAddress();
				
			}
			
			//TODO
			String ctrlCh = String.valueOf((char) 991);
			if (!MESSAGE.startsWith(ctrlCh)) {
				Log.d("DecrL", "------------>");
				// RuntimeException ex = new RuntimeException();

				// throw ex;
				return;
			}

			int NOTIF_ID = (int) (1 + Math.random() * 9999);

			NotificationManager notifManager = (NotificationManager) context
					.getSystemService(context.NOTIFICATION_SERVICE);

			Notification note = new Notification(R.drawable.ic_launcher,
					"New Encrypted SMS", System.currentTimeMillis());

			Intent qx = new Intent(context, MainActivity.class);
			Bundle bundle23 = new Bundle();
			bundle23.putString("text", MESSAGE);
			bundle23.putInt("notID", (NOTIF_ID));
			qx.putExtras(bundle23);
			PendingIntent intent3 = PendingIntent.getActivity(context, 0,
					qx, 0);

			note.setLatestEventInfo(context, "New Encrypted SMS", "From: "
					+ FROM, intent3);

			notifManager.notify(NOTIF_ID, note);
			/*
			 * for (int i=0; i<msgs.length; i++){ msgs[i] =
			 * SmsMessage.createFromPdu((byte[])pdus[i]); str += "SMS from " +
			 * msgs[i].getOriginatingAddress(); str += " :"; str +=
			 * msgs[i].getMessageBody().toString(); str += "\n"; } //---display
			 * the new SMS message--- Toast.makeText(context, str,
			 * Toast.LENGTH_SHORT).show();
			 */

		}
	}
}