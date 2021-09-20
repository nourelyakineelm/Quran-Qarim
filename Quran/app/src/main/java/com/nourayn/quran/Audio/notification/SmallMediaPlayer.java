package com.nourayn.quran.Audio.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;

import com.fekracomputers.quran.R;
import com.nourayn.quran.UI.Activities.QuranPageReadActivity;
import com.nourayn.quran.Utilities.AppConstants;

/**
 * Class to show small notification Media Player
 */
public class SmallMediaPlayer {
    private static SmallMediaPlayer smallMediaPlayer;
    private static NotificationManager notificationManager;
    private static NotificationCompat.Builder builder;
    private RemoteViews notificationSmallView;
    private Intent displayActivity;


    /**
     * Private constructor for the notification media player small
     *
     * @param context Application context
     */
    private SmallMediaPlayer(Context context) {

        notificationSmallView = new RemoteViews(context.getPackageName(), R.layout.notification_player_small);

        displayActivity = new Intent(context, QuranPageReadActivity.class);
        displayActivity.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, displayActivity, 0);

        //pause button in notification
        notificationSmallView.setOnClickPendingIntent(R.id.ib_pause,
                PendingIntent.getBroadcast(context, 1, new Intent(AppConstants.MediaPlayer.PAUSE),
                        PendingIntent.FLAG_UPDATE_CURRENT));

        //previous button in notification
        notificationSmallView.setOnClickPendingIntent(R.id.ib_previous,
                PendingIntent.getBroadcast(context, 1, new Intent(AppConstants.MediaPlayer.BACK),
                        PendingIntent.FLAG_UPDATE_CURRENT));

        //next button in notification
        notificationSmallView.setOnClickPendingIntent(R.id.ib_next,
                PendingIntent.getBroadcast(context, 1, new Intent(AppConstants.MediaPlayer.FORWARD),
                        PendingIntent.FLAG_UPDATE_CURRENT));

        //play button in notification
        notificationSmallView.setOnClickPendingIntent(R.id.ib_play,
                PendingIntent.getBroadcast(context, 1, new Intent(AppConstants.MediaPlayer.PLAY),
                        PendingIntent.FLAG_UPDATE_CURRENT));

        //stop button in notification
        notificationSmallView.setOnClickPendingIntent(R.id.ib_stop,
                PendingIntent.getBroadcast(context, 1, new Intent(AppConstants.MediaPlayer.STOP)
                        , PendingIntent.FLAG_UPDATE_CURRENT));

        //retrieve open application
        notificationSmallView.setOnClickPendingIntent(R.id.im_logo, configPendingIntent);

        builder = new NotificationCompat.Builder(context)
                .setSmallIcon(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                        ? R.drawable.ic_quran_trans : R.drawable.logo)
                .setPriority(Notification.PRIORITY_MAX)
                .setContent(notificationSmallView).setOngoing(true);

        notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());

        resume();
    }

    /**
     * static function to single-tone the notification media player
     *
     * @param context Application context
     * @return Object of small media player
     */
    public static SmallMediaPlayer getInstance(Context context) {
        if (smallMediaPlayer == null) {
            synchronized (SmallMediaPlayer.class) {
                smallMediaPlayer = new SmallMediaPlayer(context);
            }
        }
        notificationManager.notify(0, builder.build());
        return smallMediaPlayer;
    }

    /**
     * Function display pause view in media player
     */
    public void pause() {
        notificationSmallView.setViewVisibility(R.id.linearLayout, View.GONE);
        notificationSmallView.setViewVisibility(R.id.linearLayout11, View.VISIBLE);
        notificationManager.notify(0, builder.build());
    }

    /**
     * Function display resume view in media player
     */
    public void resume() {
        notificationSmallView.setViewVisibility(R.id.linearLayout, View.VISIBLE);
        notificationSmallView.setViewVisibility(R.id.linearLayout11, View.GONE);
        notificationManager.notify(0, builder.build());
    }

    /**
     * Function to cancel media player notification
     */
    public void dismiss() {
        //dismiss the notification
        notificationManager.cancelAll();
    }

}
