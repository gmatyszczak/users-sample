package pl.gmat.users.feature.edit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pl.gmat.users.R

class EditUserActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, EditUserActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_edit)
    }
}