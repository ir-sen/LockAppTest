package kan.kis.lockapp.service

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.CountDownTimer
import android.view.accessibility.AccessibilityEvent
import android.util.Log
import android.view.LayoutInflater
import android.view.WindowManager
import kan.kis.lockapp.BlockActivity
import kan.kis.lockapp.MainActivity
import kan.kis.lockapp.R

class MyAccessibilityService : AccessibilityService() {

    // telegram name: org.telegram.messenger

    private var unlocker = true

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event?.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            val packageName = event.packageName?.toString()
            val className = event.className?.toString()
            Log.d("AppOpenTracker", "Package Name: $packageName, Class Name: $className")
            if (unlocker) {

                if (packageName.equals("org.telegram.messenger")) {
                    startOverWindows()
                }
            }

        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == ACTION_UNLOCK) {
            unlocker = false
            timerUlockAgain()
        }
        return super.onStartCommand(intent, flags, startId)

    }

    private fun timerUlockAgain() {
        val timer = object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                unlocker = true
            }

        }.start()

    }


    override fun onServiceConnected() {
        super.onServiceConnected()
        val info = AccessibilityServiceInfo()
        info.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
        info.notificationTimeout = 10
        serviceInfo = info
        Log.d("AppOpenTracker", "Accessibility service connected")
    }

    override fun onInterrupt() {
        // Метод вызывается при прерывании службы
    }

    private fun startOverWindows() {
//        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
//        val layoutParams = WindowManager.LayoutParams(
//            WindowManager.LayoutParams.WRAP_CONTENT,
//            WindowManager.LayoutParams.WRAP_CONTENT,
//            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
//            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//            PixelFormat.TRANSLUCENT
//        )
//
//        val yourView = LayoutInflater.from(this).inflate(R.layout.activity_main, null)
//
//        windowManager.addView(yourView, layoutParams)

        val intent = Intent(this, BlockActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)

    }

    companion object {
        const val ACTION_UNLOCK = "com.example.lockapp.ACTION_UNLOCK"
    }
}