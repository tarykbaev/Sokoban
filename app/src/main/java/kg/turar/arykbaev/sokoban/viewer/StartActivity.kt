package kg.turar.arykbaev.sokoban.viewer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kg.turar.arykbaev.sokoban.R


class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        supportActionBar?.hide()
        val button = findViewById<Button>(R.id.button_easy)
        button.setOnClickListener {
            val intent = Intent(this, Viewer::class.java)
            startActivity(intent)
            finish()
        }
    }
}