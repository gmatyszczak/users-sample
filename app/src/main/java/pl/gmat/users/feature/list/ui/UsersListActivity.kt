package pl.gmat.users.feature.list.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import pl.gmat.users.R
import pl.gmat.users.databinding.ActivityUsersListBinding
import pl.gmat.users.feature.details.ui.UserDetailsActivity
import pl.gmat.users.feature.edit.ui.EditUserActivity
import pl.gmat.users.feature.list.ui.widget.UsersListAdapter

@AndroidEntryPoint
class UsersListActivity : AppCompatActivity() {

    private val viewModel: UsersListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
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
            adapter = UsersListAdapter(
                this@UsersListActivity.viewModel,
                this@UsersListActivity
            )
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
