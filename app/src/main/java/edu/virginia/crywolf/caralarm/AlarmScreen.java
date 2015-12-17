package edu.virginia.crywolf.caralarm;

import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class AlarmScreen extends AppCompatActivity {

    Ringtone r;

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_FULLSCREEN |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        r.play();
    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_screen);

        Uri ringtoneURI = RingtoneManager.getActualDefaultRingtoneUri(getApplicationContext(), RingtoneManager.TYPE_ALARM);
        if (ringtoneURI == null) {
            ringtoneURI = RingtoneManager.getActualDefaultRingtoneUri(getApplicationContext(), RingtoneManager.TYPE_NOTIFICATION);
            if (ringtoneURI == null) {
                ringtoneURI = RingtoneManager.getActualDefaultRingtoneUri(getApplicationContext(), RingtoneManager.TYPE_RINGTONE);
            }
        }
        r = RingtoneManager.getRingtone(getApplicationContext(), ringtoneURI);

        Intent intent = getIntent();
        String KEY_alarm_email_content = getResources().getString(R.string.KEY_alarm_email_content);
        String[] msgContents = intent.getStringExtra(KEY_alarm_email_content).split(";");
        String str_vehicleModel = msgContents[0].trim();
        String str_alarmStatus = msgContents[1].trim();
        // In the future: When OBD code is sent directly, parse OBD code into alarmStatus?
        String str_alarmTimeStamp = msgContents[3].trim();
        TextView view_vehicleModel = (TextView) findViewById(R.id.VehicleModel);
        TextView view_alarmStatus = (TextView) findViewById(R.id.AlarmStatus);
        TextView view_alarmTimeStamp = (TextView) findViewById(R.id.AlarmTimeStamp);
        view_vehicleModel.setText(str_vehicleModel);
        view_alarmStatus.setText(str_alarmStatus);
        view_alarmTimeStamp.setText(str_alarmTimeStamp);

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Update current time?
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_FULLSCREEN |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        r.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
