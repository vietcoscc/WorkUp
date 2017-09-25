package com.example.viet.workup.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.viet.workup.R;
import com.example.viet.workup.manager.AccountManager;
import com.example.viet.workup.model.BoardUserActivity;
import com.example.viet.workup.model.image.OtherBoard;
import com.example.viet.workup.utils.CalendarUtils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.arrActivityRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.otherBoardRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.starBoardRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.unstarBoardRef;

/**
 * Created by viet on 24/09/2017.
 */

public class NotificationService extends Service {
    public static final String TAG = "NotificationService";
    boolean enable;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final String uid = AccountManager.getsInstance().getUserInfo().getUid();
        final ChildEventListener activity = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (!enable) {
                    return;
                }
                BoardUserActivity boardUserActivity = dataSnapshot.getValue(BoardUserActivity.class);
                String from = boardUserActivity.getFrom();
                String message = boardUserActivity.getMessage();
                String target = boardUserActivity.getTarget();
                String timeStamp = CalendarUtils.getCurrentTime() + " " + CalendarUtils.getCurrentDate();
                showNotification(from + message + target, timeStamp);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        final ChildEventListener board = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                arrActivityRef(dataSnapshot.getKey()).limitToLast(1).addChildEventListener(activity);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        starBoardRef(uid).addChildEventListener(board);
        unstarBoardRef(uid).addChildEventListener(board);
        otherBoardRef(uid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                OtherBoard otherBoard = dataSnapshot.getValue(OtherBoard.class);
                starBoardRef(otherBoard.getUid()).limitToLast(1).addChildEventListener(board);
                unstarBoardRef(otherBoard.getUid()).limitToLast(1).addChildEventListener(board);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        otherBoardRef(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                enable = true;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return START_STICKY;
    }

    private void showNotification(String title, String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle(title);
        builder.setContentText(content);
        BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_board);
        builder.setLargeIcon(drawable.getBitmap());
        builder.setSmallIcon(R.drawable.ic_board);
        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify((int) System.currentTimeMillis(), notification);
    }
}
