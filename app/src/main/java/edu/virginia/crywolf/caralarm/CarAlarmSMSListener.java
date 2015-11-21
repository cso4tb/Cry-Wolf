package edu.virginia.crywolf.caralarm;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.provider.Telephony.Sms.Intents;
import android.widget.Toast;
import android.util.Log;


/**
 * Created by Hanbin on 10/18/2015.
 * // Read the incoming SMS and start AlarmScreen if the criteria are met.
 */
public class CarAlarmSMSListener extends BroadcastReceiver {

    @Override
    @TargetApi(19)
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {

            SmsMessage[] receivedMessages = Intents.getMessagesFromIntent(intent);
            if (receivedMessages != null) {

                for (int j = 0; j < receivedMessages.length; j++) {
                    SmsMessage message = receivedMessages[j];

                    String senderAddress_registered = context.getResources()
                            .getString(R.string.alarm_email_address_of_vehicle_owner);
                    String senderAddress_received = message.getDisplayOriginatingAddress();
                    if (senderAddress_received.equals(senderAddress_registered)) {
                        String messageBody = message.getDisplayMessageBody();
                        String msgSubject_registered = context.getResources().getString(R.string.alarm_email_subject);
                        String msgSubject_received = messageBody.substring(0, msgSubject_registered.length());
                        if (msgSubject_received.equals(msgSubject_registered)) {
                            Intent intent_start_alarmScreen = new Intent(context, AlarmScreen.class);
                            intent_start_alarmScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            String KEY_alarm_email_content = context.getResources()
                                    .getString(R.string.KEY_alarm_email_content);
                            intent_start_alarmScreen.putExtra(KEY_alarm_email_content,
                                    messageBody.substring(msgSubject_registered.length()).trim());
                            context.startActivity(intent_start_alarmScreen);
                        }
                    }
                }
            }
        }
    }
}
