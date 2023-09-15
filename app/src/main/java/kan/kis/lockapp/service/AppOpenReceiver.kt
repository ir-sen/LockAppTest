package kan.kis.lockapp.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AppOpenReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "com.example.applock.APP_OPENED") {
            val packageName = intent.getStringExtra("package_name")
            val className = intent.getStringExtra("class_name")

            // Обработайте информацию о запущенном приложении здесь
            Log.d("AppLockOpenApp", "Приложение открыто: $packageName, $className")
        }
    }


}