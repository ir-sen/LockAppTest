package kan.kis.lockapp

import android.app.AppOpsManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.core.content.ContextCompat
import kan.kis.lockapp.service.AppOpenReceiver


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent2 = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        startActivity(intent2)

//        openOverlayPermissionSettings(this)

//        val intent = Intent()
//        intent.action = Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS
//        startActivity(intent)

        val intent = Intent()
        intent.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
        intent.data = Uri.parse("package:$packageName")
        startActivity(intent)
//
//        val intent = Intent()
//        intent.action = android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
//        intent.addCategory(Intent.CATEGORY_DEFAULT)
//        intent.data = Uri.parse("package:" + packageName)
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
//        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
//        startActivity(intent)



//        val br: BroadcastReceiver = AppOpenReceiver()
//
//        val filter = IntentFilter()
//
//        val listenToBroadcastsFromOtherApps = false
//        val receiverFlags = if (listenToBroadcastsFromOtherApps) {
//            ContextCompat.RECEIVER_EXPORTED
//        } else {
//            ContextCompat.RECEIVER_NOT_EXPORTED
//        }
//
//        ContextCompat.registerReceiver(this, br, filter, receiverFlags)

        if (isUsageAccessPermissionGranted(this)) {

            Log.d("klsjdfoi", "true")
        } else {
            Log.d("klsjdfoi", "false")
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            startActivity(intent)
        }
    }

    fun openOverlayPermissionSettings(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + context.packageName)
            )
            context.startActivity(intent)
        }
    }

    fun isUsageAccessPermissionGranted(context: Context): Boolean {
        val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager?
        return appOps?.checkOpNoThrow(
            "android:get_usage_stats",
            android.os.Process.myUid(),
            context.packageName
        ) == AppOpsManager.MODE_ALLOWED
    }

}