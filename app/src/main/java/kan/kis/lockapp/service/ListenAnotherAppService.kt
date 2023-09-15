package kan.kis.lockapp.service

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import kan.kis.lockapp.MainActivity
import kan.kis.lockapp.R
import kan.kis.lockapp.service.ListenAnotherAppService.Companion.CHANNEL_ID
import kan.kis.lockapp.service.ListenAnotherAppService.Companion.CHANNEL_NAME
import java.util.*

class ListenAnotherAppService: Service() {

    private val TAG = "AppUsageTrackingService"
    private lateinit var timer: Timer

    override fun onBind(intent: Intent?): IBinder? {
        TODO("not need")
//
//        return super.onStartCommand(intent, flags, startId)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel(this)
        startForeground(NOTIFICATION_ID, createNotification(this))
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
        timer = Timer()
        timer.scheduleAtFixedRate(AppUsageTask(), 0, 60 * 1000)
    }

    inner class AppUsageTask : TimerTask() {
        override fun run() {
            val topPackageName = getTopPackageName()
            Log.d(TAG, "Top Package Name: $topPackageName")
        }
    }

    private fun getTopPackageName(): String? {
        // Реализуйте код получения имени текущего открытого приложения здесь
        // Используйте предыдущий код для получения имени приложения
        // и его вызов из этого метода
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
        Log.d(TAG, "Service onDestroy")
    }


    companion object {
        val CHANNEL_ID = "islistenerService"
        val NOTIFICATION_ID = 123
        val CHANNEL_NAME = "llskdjfi"

    }

    fun getIntent(context: Context): Intent {
        return Intent(context, this@ListenAnotherAppService::class.java)
    }


}

private fun serviceStarted() {

}

@SuppressLint("WrongConstant")
@RequiresApi(Build.VERSION_CODES.S)
private fun createNotification(context: Context): Notification {
    // just open app
    val splashScreenIntent = Intent(context, MainActivity::class.java)
    var flagIntent = PendingIntent.FLAG_MUTABLE
    if (android.os.Build.VERSION.SDK_INT >= 23) {
        flagIntent = PendingIntent.FLAG_IMMUTABLE
    } else {
        flagIntent = PendingIntent.FLAG_MUTABLE
    }

    val mainMenuPendingIntent = PendingIntent.getActivity(
        context, 3,
        splashScreenIntent, flagIntent
    )
    // create game Intent  ( go to game )

    // create cache delete intent ( go to cache delete )


    return NotificationCompat.Builder(context, CHANNEL_ID)
        .setDefaults(NotificationCompat.DEFAULT_ALL)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(mainMenuPendingIntent)
        .setGroup("AlwaysNotification")
        .setContentText("hello")
        .setContentTitle("lol")
        .setSound(null)
        .setLargeIcon(
            BitmapFactory.decodeResource(
                context.resources,
                R.mipmap.ic_launcher
            )
        )
        .setSmallIcon(R.mipmap.ic_launcher)
        .build()

}

private fun createNotificationChannel(context: Context) {

    val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(notificationChannel)
    }


}

