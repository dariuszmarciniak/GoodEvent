package daromon.goodevent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class GoodEvent : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.good_event)

        val loginActivity = findViewById<Button>(R.id.startLoginButton)
        loginActivity.setOnClickListener {
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
        }

        val registerActivity = findViewById<Button>(R.id.startRegisterButton)
        registerActivity.setOnClickListener {
            val intent = Intent(applicationContext, Register::class.java)
            startActivity(intent)
        }
    }
}
