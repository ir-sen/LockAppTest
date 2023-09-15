package kan.kis.lockapp.service

import android.app.AppOpsManager
import android.app.Service
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Handler
import android.os.IBinder
import android.util.Log

class AppUsageTrackingService: Service() {

    private var isServiceRunning = false
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        isServiceRunning = true
        handler = Handler()
        runnable = Runnable {
            if (isServiceRunning) {
                checkForegroundApp()
                handler.postDelayed(runnable, 1000) // Проверять каждую секунду (или другой интервал)
            }
        }
        handler.post(runnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        isServiceRunning = false
        handler.removeCallbacks(runnable)
    }

    private fun checkForegroundApp() {
        if (hasUsageStatsPermission()) {
            val usageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
            val endTime = System.currentTimeMillis()
            val beginTime = endTime - 1000 // Последняя секунда
            val usageStatsList = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_DAILY, beginTime, endTime
            )

            if (usageStatsList.isNotEmpty()) {
                val sortedStats = usageStatsList.sortedByDescending { it.lastTimeUsed }
                val topPackage = sortedStats[0].packageName
                val appName = getAppName(topPackage)
                Log.d("sdkfjoiejr", "Opens application $appName")
                // Вывод информации о текущем приложении
                // appName - имя приложения, topPackage - пакет приложения
            }
        }
    }

    private fun getAppName(packageName: String): String {
        val pm = packageManager
        try {
            val info = pm.getApplicationInfo(packageName, 0)
            return pm.getApplicationLabel(info).toString()
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return packageName
    }

    private fun hasUsageStatsPermission(): Boolean {
        val appOps = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            android.os.Process.myUid(),
            packageName
        )
        return mode == AppOpsManager.MODE_ALLOWED
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }
}