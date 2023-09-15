package kan.kis.lockapp.service

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent

class AppAccessibilityService: AccessibilityService() {

    val TAG = "MyAccessibilityService"

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return

        if (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            val packageName = event.packageName?.toString()
            val className = event.className?.toString()
            Log.d(TAG, "Opened: PackageName: $packageName, ClassName: $className")
        }
    }

    override fun onInterrupt() {
        // Метод, вызываемый при прерывании службы
    }


}