package pl.gmat.users.feature.details.ui

import android.content.Context
import android.content.Intent
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
import pl.gmat.users.databinding.ActivityUserDetailsBinding
import pl.gmat.users.feature.details.ui.widget.AddressesAdapter
import pl.gmat.users.feature.edit.ui.EditUserActivity

@AndroidEntryPoint
class UserDetailsActivity : AppCompatActivity() {

    companion object {

        const val EXTRA_USER_ID = "EXTRA_USER_ID"

        fun createIntent(context: Context, id: Long) =
            Intent(context, UserDetailsActivity::class.java).apply {
                putExtra(EXTRA_USER_ID, id)
            }
    }

    private val viewModel: UserDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityUserDetailsBinding>(
            this,
            R.layout.activity_user_details
        ).apply {
            viewModel = this@UserDetailsActivity.viewModel
            state = this@UserDetailsActivity.viewModel.state
            lifecycleOwner = this@UserDetailsActivity
            setupAddressesAdapter()
        }
        viewModel.effect.observe(this, Observer { handleEffect(it) })
    }

    private fun ActivityUserDetailsBinding.setupAddressesAdapter() {
        addressesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@UserDetailsActivity)
            adapter = AddressesAdapter(this@UserDetailsActivity)
        }
    }

    private fun handleEffect(effect: UserDetailsEffect) = when (effect) {
        is UserDetailsEffect.Finish -> finish()
        is UserDetailsEffect.ShowEditUser ->
            startActivity(EditUserActivity.createIntent(this, effect.user))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_details, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menu_edit -> {
            viewModel.onEditClicked()
            true
        }
        R.id.menu_delete -> {
            viewModel.onDeleteClicked()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
