package indg.com.cover2protect.data.fcm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import android.util.Log
import android.widget.Toast

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import indg.com.cover2protect.R
import indg.com.cover2protect.views.activity.login.LoginActivity

import java.io.IOException
import java.net.URL


class MyFirebaseMessagingService : FirebaseMessagingService() {
    private var numMessages = 0

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)
        //val notification = remoteMessage!!.notification
        val data = remoteMessage!!.data
        //  Log.d("FROM", remoteMessage.from)
        if (!data.isNullOrEmpty()) {
            sendNotification(data)
        }
    }

    private fun sendNotification(data: Map<String, String>) {
        val bundle = Bundle()
        bundle.putString(FCM_PARAM, data[FCM_PARAM])
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtras(bundle)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notificationBuilder = NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id))
            .setContentTitle(data.get("title"))
            .setContentText(data.get("body"))
            .setAutoCancel(true)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
           // .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.doc))
            .setContentIntent(pendingIntent)
            .setContentInfo("Hello")
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
            .setColor(resources.getColor(R.color.colorAccent))
            .setLights(Color.RED, 1000, 300)
            .setDefaults(Notification.DEFAULT_VIBRATE)
            .setNumber(++numMessages)
            .setSmallIcon(R.drawable.ic_notifications_black_24dp)

        try {
            val picture = data.get(FCM_PARAM)
            if (picture != null && "" != picture) {
                val url = URL(picture)
                val bigPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                notificationBuilder.setStyle(
                    NotificationCompat.BigPictureStyle().bigPicture(bigPicture).setSummaryText(data.get("body"))
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                getString(R.string.default_notification_channel_id),
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = CHANNEL_DESC
            channel.setShowBadge(true)
            channel.canShowBadge()
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500)

            assert(notificationManager != null)
            notificationManager.createNotificationChannel(channel)
        }

        assert(notificationManager != null)
        notificationManager.notify(0, notificationBuilder.build())
    }

    companion object {
        val FCM_PARAM = "picture"
        private val CHANNEL_NAME = "FCM"
        private val CHANNEL_DESC = "Firebase Cloud Messaging"
    }
}
