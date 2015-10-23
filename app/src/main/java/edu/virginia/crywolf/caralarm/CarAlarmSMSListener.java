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
 */
public class CarAlarmSMSListener extends BroadcastReceiver {
    private final String CARALARM_OWNER_ADDRESS = "crywolf.f15@gmail.com";
    private final String CARALARM_MESSAGE_TITLE = "(From LabVIEW with Love)";
    @Override
    @TargetApi(19)
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();

        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {
                    SmsMessage[] currentMessages = Intents.getMessagesFromIntent(intent);

                    if (currentMessages != null) {
                        for (int j = 0; j < currentMessages.length; j++) {
                            SmsMessage currentMessage = currentMessages[j];
                            String senderAddress = currentMessage.getDisplayOriginatingAddress();
                            if (senderAddress.equals(CARALARM_OWNER_ADDRESS)) {
                                String messageBody = currentMessage.getDisplayMessageBody();
                                if (messageBody.substring(0, CARALARM_MESSAGE_TITLE.length()) == CARALARM_MESSAGE_TITLE) {
                                    String messageContent = messageBody.substring(CARALARM_MESSAGE_TITLE.length()).trim();
                                /*Check messageContent and respond appropriately*/
                                /* if (messageContent.equals("ALARM!") { } */
                                    Toast toast = Toast.makeText(context, "From: " + CARALARM_OWNER_ADDRESS + "\n"
                                            + messageContent, Toast.LENGTH_SHORT);
                                    toast.show();
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e("CarAlarmSMSListener", "Exception CarAlarmSMSListener" + e);
        }
    }
}
