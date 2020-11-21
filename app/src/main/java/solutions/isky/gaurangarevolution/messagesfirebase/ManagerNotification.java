package solutions.isky.gaurangarevolution.messagesfirebase;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.aplication.App;
import solutions.isky.gaurangarevolution.data.event.GetUserInfo;
import solutions.isky.gaurangarevolution.data.network.ServerMethod;
import solutions.isky.gaurangarevolution.presentation.databases.Constants;
import solutions.isky.gaurangarevolution.presentation.databases.ErrorMessage;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.pref.UserData;
import solutions.isky.gaurangarevolution.presentation.ui.correspondence.DialogsActivity;
import solutions.isky.gaurangarevolution.presentation.ui.correspondence.MessageActivity;
import solutions.isky.gaurangarevolution.presentation.ui.main.MainActivity;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;
import solutions.isky.gaurangarevolution.presentation.utils.JsonObjBody;

public class ManagerNotification {
    private static final String NOTIFICATION_ID = "NOTIFICATION_ID";
    private static final String CLOSE_NOTIFICATION = "CLOSE_NOTIFICATION";
    @Inject
    ServerMethod serverMethod;
    private static final int NOTIFY_ID = 1;
    String url_avatar="http";
    public void createNotificationNEW(Context context, Map<String, String> stringStringMap) {

        if (MessageActivity.active || DialogsActivity.active || stringStringMap == null) {
            playSoundMessage(context);
            return;
        }

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(Constants.NEW_MESSAGE, true);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);

        PendingIntent pendingIntentClose = PendingIntent.getBroadcast(context, 0,
                closeNotificationIntent(context), 0);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_custom);
        remoteViews.setTextViewText(R.id.title_post_push, stringStringMap.get(Constants.NAME_USER_PUSH));
        remoteViews.setTextViewText(R.id.text_message_push, stringStringMap.get(Constants.MESSAGE_PUSH));
        remoteViews.setImageViewResource(R.id.closeNotification, R.mipmap.ic_launcher);
        remoteViews.setOnClickPendingIntent(R.id.closeNotification, pendingIntentClose);

        NotificationCompat.Builder notificationBuilder = showNotification(context);
        notificationBuilder.setContent(remoteViews);
        notificationBuilder.setContentIntent(pIntent);
        notificationBuilder.build().flags = Notification.FLAG_AUTO_CANCEL;
        // notificationManager.notify(1, notificationBuilder.build());
        Handler uiHandler = new Handler(Looper.getMainLooper());
        int view_id;
        try {
            if(stringStringMap.containsKey(Constants.FROM_USER_ID)){
                view_id = Integer.parseInt(stringStringMap.get(Constants.FROM_USER_ID));
            }else{
                view_id = 0;
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            view_id = 0;
        }
        catch (Exception ex){
            view_id = 0;
        }
        int finalView_id = view_id;
        url_avatar="http";
        if(stringStringMap.containsKey(Constants.USER_AVATAR_PUSH)){
            url_avatar=stringStringMap.get(Constants.USER_AVATAR_PUSH);
        }

        uiHandler.post(() -> {
            Picasso.get()
                    .load(url_avatar)
                    .error(R.drawable.ic_no_avatar)
                    .transform(new RoundTransformation(45, 0))
                    .into(remoteViews, R.id.avatar_user_push, finalView_id, notificationBuilder.build());

        });
       // getUserInfo();
    }

    private static NotificationCompat.Builder showNotification(Context context) {
        String channelId = context.getString(R.string.default_notification_channel_id);
        //Uri sound = Uri.parse("android.resource://" + context.getPackageName() + "/"+R.raw.sound_push);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        int icon = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? R.drawable.ic_stat_gauranga : R.mipmap.ic_launcher;

        return new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(icon)
                .setPriority(Notification.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSound(defaultSoundUri);
    }

    private Intent openDialogIntent(Context context, String userId) {
        Intent intent = new Intent(context, DialogsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    private Intent closeNotificationIntent(Context context) {
        Intent intent = new Intent(context.getApplicationContext(), CloseButtonReceiver.class);
        intent.putExtra(NOTIFICATION_ID, NOTIFY_ID);
        intent.setAction(CLOSE_NOTIFICATION);
        return intent;
    }

    public static class CloseButtonReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            NotificationManager manager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.cancelAll();
        }
    }


    private static class RoundTransformation implements com.squareup.picasso.Transformation {

        private int radius;
        private final int margin;

        public RoundTransformation(final int radius, final int margin) {
            this.radius = radius;
            this.margin = margin;
        }

        @Override
        public Bitmap transform(Bitmap source) {
            final Paint paint = new Paint();
            this.radius = source.getHeight() / 2;
            paint.setAntiAlias(true);
            paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

            Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            canvas.drawRoundRect(new RectF(margin, margin, source.getWidth() - margin, source.getHeight() - margin), radius, radius, paint);

            if (source != output) {
                source.recycle();
            }
            return output;
        }

        @Override
        public String key() {
            return "rounded";
        }
    }

    protected void playSoundMessage(Context context) {
        try {
            Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            //  Uri sound = Uri.parse("android.resource://" + context.getPackageName() + "/"+R.raw.sound_in_activitys);
            Ringtone r = RingtoneManager.getRingtone(context, sound);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getUserInfo() {
        JsonObject object = JsonObjBody.getMyInfo(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID), UserData.getInstance(App.getInstance()).getInteger(UserData.Key.USER_ID));
        final Single<GetUserInfo> observable = new ServerMethod().getInfo(object);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getUserInfo -> {
                    if (getUserInfo.getResult() > 0) {
                        AppUtils.setUserData(getUserInfo.getUser());

                    } else {
                        if (getUserInfo.getMessage() != null && getUserInfo.getMessage().size() > 0) {
                            String text_error = ErrorMessage.getError(getUserInfo.getMessage().get(0).getMsg(), App.getInstance());
                            if (ErrorMessage.NO_SESSION.equalsIgnoreCase(getUserInfo.getMessage().get(0).getMsg())) {
                                AppState.getInstance(App.getInstance()).setBoolean(AppState.Key.LOGGED_IN, false);
                                AppUtils.clearUserData();
                            }

                        }
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                });

    }
}
