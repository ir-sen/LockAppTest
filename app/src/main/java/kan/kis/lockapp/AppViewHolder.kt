import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kan.kis.lockapp.R

class AppViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val appName: TextView = itemView.findViewById(R.id.appName)
    val appIcon: ImageView = itemView.findViewById(R.id.appIcon)
}