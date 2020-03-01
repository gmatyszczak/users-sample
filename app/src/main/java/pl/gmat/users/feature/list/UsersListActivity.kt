package pl.gmat.users.feature.list

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pl.gmat.users.R
import pl.gmat.users.common.dagger.Injector
import pl.gmat.users.feature.edit.EditUserActivity
import javax.inject.Inject
import javax.inject.Provider

class UsersListActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelProvider: Provider<UsersListViewModel>

    private val viewModel: UsersListViewModel by viewModels {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>) = viewModelProvider.get() as T
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.appComponent.usersListComponentFactory().create(this).inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_list)
        viewModel.effect.observe(this, Observer { handleEffect(it) })
    }

    private fun handleEffect(effect: UsersListEffect) = when (effect) {
        UsersListEffect.ShowAddUser -> startActivity(EditUserActivity.createIntent(this))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menu_add -> {
            viewModel.onAddClicked()
            true
        }
        R.id.menu_delete_all -> {
            viewModel.onDeleteAllClicked()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
