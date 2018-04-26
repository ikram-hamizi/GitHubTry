package com.example.usp05.githubtry.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;

import com.example.usp05.githubtry.AppContext;
import com.example.usp05.githubtry.R;
import com.example.usp05.githubtry.data_model.DBItemsHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by nathan on 4/26/18.
 */

public class NotificationPublisher extends BroadcastReceiver{

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    @Override
    public void onReceive(final Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int notificationId = intent.getIntExtra(NOTIFICATION_ID, 0);
        notificationManager.notify(notificationId, notification);
    }

//    @Override
//    public void onReceive(Context context, Intent intent) {
//        DBItemsHelper helper = new DBItemsHelper(context);
//
//        Cursor cursor = helper.getItems();
//        ArrayList<Date> dates = new ArrayList<>();
//
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//
//
//        Date today = calendar.getTime();
//
//        if (cursor.moveToFirst()) {
//            do {
//                dates.add(DateHelper.getDateFromDatabaseString(cursor.getString(cursor.getColumnIndex(DBItemsHelper.ITEM_COL_DATE_EXPIRED))));
//            } while(cursor.moveToNext());
//        }
//
//        ArrayList<Integer> expItem = new ArrayList<>();
//
//        for (int i = 0; i < dates.size(); i++) {
//            if (dates.get(i).compareTo(today) <= 0) {
//                expItem.add(i);
//            }
//        }
//
//        if (!expItem.isEmpty()) {
//            Notification.Builder builder = new Notification.Builder(context);
//            builder.setContentTitle("Inventory expiration notification");
//
//
//            StringBuffer sb = new StringBuffer();
//
//            cursor.moveToPosition(expItem.get(0));
//            sb.append(cursor.getString(cursor.getColumnIndex(DBItemsHelper.ITEM_COL_NAME)));
//
//            if (expItem.size() > 1) {
//                sb.append(" and ").append(expItem.size()-1);
//                sb.append(" other items have expired!");
//            } else {
//                sb.append(" has expired!");
//            }
//            builder.setContentText(sb.toString());
//            builder.setSmallIcon(R.drawable.ic_launcher_background);
//
//            NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
//
//            notificationManager.notify(1, builder.build());
//
//        }
//    }

//    private void checkDatabaseEntries(){
//        DBItemsHelper helper = new DBItemsHelper(AppContext.getContext());
//
//        Cursor cursor = helper.getItems();
//        ArrayList<Date> dates = new ArrayList<>();
//
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//
//
//        Date today = calendar.getTime();
//
//        if (cursor.moveToFirst()) {
//            do {
//                dates.add(DateHelper.getDateFromDatabaseString(cursor.getString(cursor.getColumnIndex(DBItemsHelper.ITEM_COL_DATE_EXPIRED))));
//            } while(cursor.moveToNext());
//        }
//
//        ArrayList<Integer> expItem = new ArrayList<>();
//
//        for (int i = 0; i < dates.size(); i++) {
//            if (dates.get(i).compareTo(today) <= 0) {
//                expItem.add(i);
//            }
//        }
//
//        if (!expItem.isEmpty()) {
//            Notification.Builder builder = new Notification.Builder(AppContext.getContext());
//            builder.setContentTitle("Inventory expiration notification");
//            builder.setContentText(content);
//            builder.setSmallIcon(R.drawable.ic_launcher_background);
//
//            NotificationManager notificationManager = (NotificationManager)AppContext.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
//
//            Notification notification = intent.getParcelableExtra(NOTIFICATION);
//            int id = intent.getIntExtra(NOTIFICATION_ID, 0);
//            notificationManager.notify(id, notification);
//
//        }
//
//    }
}
