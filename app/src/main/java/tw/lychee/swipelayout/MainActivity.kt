package tw.lychee.swipelayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.surfaceView).setOnClickListener {
            Log.d(TAG, "onclick")
        }
        findViewById<View>(R.id.editBtn).setOnClickListener {
            Log.d(TAG, "edit")
        }
        findViewById<View>(R.id.deleteBtn).setOnClickListener {
            Log.d(TAG, "delete")
        }
    }

    companion object {
        const val TAG = "MainActivity"
    }
}