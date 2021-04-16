package com.msc.dogsapplication.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.msc.dogsapplication.R
import com.msc.dogsapplication.view.MainActivity

class NotificationHelper(val context: Context) {

    private val CHANNEL_ID = "DogsChanelId"
    private val NOTIFICATION_ID = 100

    private fun createNotificatioChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = CHANNEL_ID
            val descriptionText = "Chanel description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun createNotification() {
        createNotificatioChannel()
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val icon = BitmapFactory.decodeResource(context.resources, R.drawable.dog_icon)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.dog_icon)
            .setLargeIcon(icon)
            .setContentTitle(context.getString(R.string.dogs_retrieved))
            .setContentText(context.getString(R.string.come_see_dogs))
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(icon).bigLargeIcon(null))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)

    }
}