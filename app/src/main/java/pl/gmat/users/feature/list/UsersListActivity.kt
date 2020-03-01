package pl.gmat.users.feature.list

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import pl.gmat.users.R

class UsersListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_list)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list, menu)
        return true
    }
}
