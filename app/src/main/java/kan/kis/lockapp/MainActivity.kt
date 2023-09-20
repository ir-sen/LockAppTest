package kan.kis.lockapp

import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    lateinit var recycleView: RecyclerView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycleView = findViewById(R.id.recycle_app)
        recycleView.layoutManager = LinearLayoutManager(this)

        val adapters = ApplicationAdapter(this)
        recycleView.adapter = adapters

        showAllApps()

        accessAccessibility()

        accessSystemAleartWindow()

        val intent = Intent()
        intent.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
        intent.data = Uri.parse("package:$packageName")
        startActivity(intent)



        if (isUsageAccessPermissionGranted(this)) {
            Log.d("klsjdfoi", "true")
        } else {
            Log.d("klsjdfoi", "false")
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            startActivity(intent)
        }

        getAllAppName()
    }

    // get permission to Accessibility Service
    private fun accessAccessibility() {
        val intent2 = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        startActivity(intent2)
    }

    private fun accessSystemAleartWindow() {
        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
        intent.data = Uri.parse("package:${packageName}")
        startActivity(intent)
    }




    private fun getAllAppName() {
        val packageAll = packageManager.getInstalledApplications(0)
        for (i in packageAll) {
            Log.d("klsjdfio", i.loadLabel(packageManager).toString())
        }
    }

    fun showAllApps() {
        val infos = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        // create a list with size of total number of apps
        // create a list with size of total number of apps
        val apps = arrayOfNulls<String>(infos.size)
        var i = 0
        // add all the app name in string list
        // add all the app name in string list
        for (info in infos) {
            apps[i] = info.packageName
            Log.d("AllApplication", "Apps ${apps[i]}")
            i++
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