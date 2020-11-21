package solutions.isky.gaurangarevolution.messagesfirebase;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import solutions.isky.gaurangarevolution.aplication.App;
import solutions.isky.gaurangarevolution.presentation.databases.Constants;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.pref.UserData;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    private Map<String, String> stringStringMap;
    private int user_id = 0;

    public MyFirebaseMessagingService() {
        super();
    }

    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            stringStringMap = remoteMessage.getData();
            if (stringStringMap.containsKey(Constants.NEW_MESS) && stringStringMap.get(Constants.NEW_MESS).equalsIgnoreCase("1")) {
                if (AppState.getInstance(App.getInstance()).getBoolean(AppState.Key.LOGGED_IN) && UserData.getInstance(App.getInstance()).getInteger(UserData.Key.USER_ID)
                        == (Integer.parseInt(stringStringMap.get(Constants.FOR_USER_ID)))) {
                    user_id = Integer.parseInt(stringStringMap.get("user_id"));
                    new ManagerNotification().createNotificationNEW(this, stringStringMap);
                }
            }


            //sendNotification("проверка");
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
        handleNow(user_id);

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]


    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow(int user_id) {
        Intent intent = new Intent(Constants.NEW_MESSAGE);
        intent.putExtra("user_id", user_id);
        //send broadcast
        sendBroadcast(intent);
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody) {
//        Intent intent = new Intent(this, ListActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        String channelId = getString(R.string.default_notification_channel_id);
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder =
//                new NotificationCompat.Builder(this, channelId)
//                        .setSmallIcon(R.mipmap.ic_launcher_round)
//                        .setContentTitle("FCM Message")
//                        .setContentText(messageBody)
//                        .setAutoCancel(true)
//                        .setSound(defaultSoundUri)
//                        .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }
    private void sendRegistrationToServer(String token) {
        AppState.getInstance(App.getInstance()).setString(AppState.Key.ID_PUSH,token);

    }
}
