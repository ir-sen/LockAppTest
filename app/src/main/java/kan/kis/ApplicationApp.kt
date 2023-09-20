package kan.kis

import android.app.Application
import android.app.usage.UsageStatsManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

class ApplicationApp: Application() {


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        val answer = getTopPackageName(this)
        Log.d("TAGApplication", "some $answer")

    }

 }



fun getTopPackageName(context: Context): String? {
    val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
    val currentTime = System.currentTimeMillis()

    // Определите интервал времени, за который вы хотите получить статистику
    val interval = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        UsageStatsManager.INTERVAL_WEEKLY
    } else {
        UsageStatsManager.INTERVAL_DAILY
    }

    val stats = usageStatsManager.queryUsageStats(interval, currentTime - 1000 * 60, currentTime)

    if (stats.isNotEmpty()) {
        val sortedStats = stats.sortedByDescending { it.lastTimeUsed }
        val topPackageName = sortedStats[0].packageName
        val packageManager = context.packageManager
        val applicationInfo = packageManager.getApplicationInfo(topPackageName, 0)
        val appName = packageManager.getApplicationLabel(applicationInfo).toString()
        return appName
    }

    return null
}