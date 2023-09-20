package kan.kis.lockapp

import AppViewHolder
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ApplicationAdapter(private val context: Context) : RecyclerView.Adapter<AppViewHolder>() {

    private val TAG = "ApplicationAdapterTag"

    private val packageManager: PackageManager = context.packageManager
    private val appList: List<ApplicationInfo> = packageManager.getInstalledApplications(0)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.app_item, parent, false)
        return AppViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val appInfo = appList[position]
        Log.d(TAG, appInfo.packageName)
        holder.appName.text = appInfo.loadLabel(packageManager)
        holder.appIcon.setImageDrawable(appInfo.loadIcon(packageManager))
    }

    override fun getItemCount(): Int {
        return appList.size
    }
}