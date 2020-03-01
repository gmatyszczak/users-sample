package pl.gmat.users.feature.list

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import pl.gmat.users.R
import pl.gmat.users.common.dagger.Injector
import pl.gmat.users.databinding.ActivityUsersListBinding
import pl.gmat.users.feature.details.UserDetailsActivity
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
        DataBindingUtil.setContentView<ActivityUsersListBinding>(this, R.layout.activity_users_list)
            .apply {
                viewModel = this@UsersListActivity.viewModel
                state = this@UsersListActivity.viewModel.state
                lifecycleOwner = this@UsersListActivity
                setupViews()
            }
        viewModel.effect.observe(this, Observer { handleEffect(it) })
    }

    private fun ActivityUsersListBinding.setupViews() {
        usersRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@UsersListActivity)
            adapter = UsersListAdapter(this@UsersListActivity.viewModel, this@UsersListActivity)
        }
    }

    private fun handleEffect(effect: UsersListEffect) = when (effect) {
        is UsersListEffect.ShowAddUser -> startActivity(EditUserActivity.createIntent(this))
        is UsersListEffect.ShowUserDetails ->
            startActivity(UserDetailsActivity.createIntent(this, effect.id))
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
