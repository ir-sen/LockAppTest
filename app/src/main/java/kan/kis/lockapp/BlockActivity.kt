package kan.kis.lockapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kan.kis.lockapp.service.MyAccessibilityService
import kan.kis.lockapp.service.MyAccessibilityService.Companion.ACTION_UNLOCK

class BlockActivity : AppCompatActivity() {

    lateinit var btn_back: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_block)

        btn_back = findViewById(R.id.button_back)

        btn_back.setOnClickListener {
            val accessibilityServiceIntent = Intent(this, MyAccessibilityService::class.java)
            accessibilityServiceIntent.action = ACTION_UNLOCK
            startService(accessibilityServiceIntent)
            finish()
        }

    }
}