package com.example.proyectonotificaciones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService_Activity extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //se verifica el data de la notificación
        if (remoteMessage.getData().isEmpty())
            showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        else
            showNotification(remoteMessage.getData());
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            if (true) {
            } else {
            }
        }
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }

    public void showNotification(Map<String, String> data) {
        //SE EXTRAE LA INFORMACION QUE CONTIENE LA DATA.
        String title = data.get("title").toString();
        String body = data.get("body").toString();
        //SE LE ASIGNA UN CANAL A LA NOTIFICACION
        String NOTIFICATION_CHANNEL_ID = getString(R.string.default_notification_channel_id);
        // SE REDIRECCIONA A LA CLASE REQUERIDA. CUANDO EL USUARIO DA CLICK EN LA NOTIFICACION
        Intent intent = new Intent(this, IniciarSesion_Activity.class);
        //CONTINUANDO SE CREA EL DISEÑO DE NOTIFICACION ESTA SE VE REFLEJADA EN LA BARRA DE NOTIFICACION DEL DISPOSITIVO MOVIL.
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /*
        Request code */, intent, PendingIntent.FLAG_ONE_SHOT);
        //DECTECCION DEL RINGTON DEL MOVIL Y EL CANAL.
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //CREAMOS EL DISEÑO DE LA NOTIFICACION.
        //ICONO
        //VIBRACION
        //SONIDO
        //COLOR
        //BODY
        //EL TITULO
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setSmallIcon(R.drawable.booking).setColor(rgb(255, 160, 0)).setContentTitle(title).setContentText(body).setAutoCancel(true).setVibrate(new long[]{500, 1000, 500, 1000}).setSound(defaultSoundUri).setContentIntent(pendingIntent).setContentInfo("info");
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //SE REALIZA UN VALIDACION DEL SDK DEL DISPOSITO
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Descripcion");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableLights(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    } // NUEVAMENTE SE REPITE LAS LINEAS DE CODIGO, EN CASO QUE LA VERSION DE //SDK SEA IGUAL 0 MAYOR A 8.1

    private void showNotification(String title, String body) {
        Intent intent = new Intent(this, IniciarSesion_Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        String NOTIFICATION_CHANNEL_ID = getString(R.string.default_notification_channel_id);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        notificationBuilder
                .setSmallIcon(R.drawable.booking)
                .setColor(rgb(255, 160, 0))
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setVibrate(new long[]{0, 1000, 500, 1000})
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setContentInfo("info");
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Descripcion");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableLights(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
