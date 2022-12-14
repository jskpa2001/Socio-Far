package com.terabyte.techexpogps;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Timestamp;


public class TrackingService extends Service {
    Location previouslocation=null;
    private static final String TAG = TrackingService.class.getSimpleName();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
        buildNotification();
        loginToFirebase();
    }

//Create the persistent notification//

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void buildNotification() {
        String stop = "stop";
        registerReceiver(stopReceiver, new IntentFilter(stop));
        PendingIntent broadcastIntent = PendingIntent.getBroadcast(
                this, 0, new Intent(stop), PendingIntent.FLAG_UPDATE_CURRENT);
        // Create the persistent notification
        String NOTIFICATION_CHANNEL_ID="com.terabyte.techexpogps";
        String channelName="My Background Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.tracking_enabled_notif))
                .setContentIntent(broadcastIntent)
                .setSmallIcon(R.drawable.tracking_enabled).build();
        startForeground(2, notification);
    }

    protected BroadcastReceiver stopReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

//Unregister the BroadcastReceiver when the notification is tapped//

            unregisterReceiver(stopReceiver);

//Stop the Service//

            stopSelf();
        }
    };

    private void loginToFirebase() {

//Authenticate with Firebase, using the email and password we created earlier//

        String email = getString(R.string.test_email);
        String password = getString(R.string.test_password);

//Call OnCompleteListener if the user is signed in successfully//


        FirebaseAuth.getInstance().signInWithEmailAndPassword(
                email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {

//If the user has been authenticated...//

                if (task.isSuccessful()) {

//...then call requestLocationUpdates//

                    requestLocationUpdates();
                } else {

//If sign in fails, then log the error//

                    Log.d(TAG, "Firebase authentication failed");
                }
            }
        });
    }

//Initiate the request to track the device's location//

    private void requestLocationUpdates() {
        LocationRequest request = new LocationRequest();

//Specify how often your app should request the device???s location//

        request.setInterval(10000);

//Get the most accurate location data available//

        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        final String path = getString(R.string.firebase_path);
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

//If the app currently has access to the location permission...//

        if (permission == PackageManager.PERMISSION_GRANTED) {

//...then request location updates//

            client.requestLocationUpdates(request, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {

//Get a reference to the database, so your app can perform read and write operations//
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference(path);
                    Location location = locationResult.getLastLocation();

                        //&&(location.getLatitude()!=previouslocation.getLatitude())&&(location.getLongitude()!=previouslocation.getLongitude())
                        if (location != null ) {

//Save the location data to the database//
                            String key =  ref.push().getKey();
                            ref.child("user 1").setValue(location);
                            previouslocation=location;
                            Toast.makeText(TrackingService.this, "hihihihih", Toast.LENGTH_SHORT).show();

                        }




                }
            }, null);
        }
    }
}
