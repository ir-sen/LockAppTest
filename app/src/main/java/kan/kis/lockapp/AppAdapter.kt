package kan.kis.lockapp

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class AppAdapter(private val context: Context) : BaseAdapter() {
    private val packageManager: PackageManager = context.packageManager
    private val appList: List<ApplicationInfo> = packageManager.getInstalledApplications(0)

    override fun getCount(): Int {
        return appList.size
    }

    override fun getItem(position: Int): ApplicationInfo {
        return appList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        val holder: ViewHolder

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.app_item, parent, false)
            holder = ViewHolder()
            holder.appName = convertView.findViewById(R.id.appName)
            holder.appIcon = convertView.findViewById(R.id.appIcon)
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        val appInfo = appList[position]
        holder.appName?.text = appInfo.loadLabel(packageManager)
        holder.appIcon?.setImageDrawable(appInfo.loadIcon(packageManager))

        return convertView!!
    }

    private inner class ViewHolder {
        var appName: TextView? = null
        var appIcon: ImageView? = null
    }
}