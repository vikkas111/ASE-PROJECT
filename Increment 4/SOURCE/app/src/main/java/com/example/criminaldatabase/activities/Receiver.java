package com.example.criminaldatabase.activities;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import static android.content.Context.ALARM_SERVICE;

public class Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context arg0, Intent arg1) {
        if(MainActivity.policehome){
            Intent intent = new Intent();
            intent.setAction("com.police.home");
            intent.putExtra("url", "sudarshan");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(arg0, 1, intent, 0);
            AlarmManager alarmManager = (AlarmManager) arg0.getSystemService(ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);
            displayDialog(arg0);

        }

    }
    public void displayDialog(Context context){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("Notification");
        builder1.setMessage("Emergency Message :  Alert From Public ");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
