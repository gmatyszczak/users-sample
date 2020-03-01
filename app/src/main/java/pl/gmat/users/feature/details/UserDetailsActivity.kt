package pl.gmat.users.feature.details

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pl.gmat.users.R
import pl.gmat.users.common.dagger.Injector
import pl.gmat.users.databinding.ActivityUserDetailsBinding
import javax.inject.Inject
import javax.inject.Provider

class UserDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelProvider: Provider<UserDetailsViewModel>

    private val viewModel: UserDetailsViewModel by viewModels {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>) = viewModelProvider.get() as T
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.appComponent.userDetailsComponentFactory().create(this).inject(this)
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityUserDetailsBinding>(this, R.layout.activity_user_details)
            .apply {
                viewModel = this@UserDetailsActivity.viewModel
                state = this@UserDetailsActivity.viewModel.state
                lifecycleOwner = this@UserDetailsActivity
            }
        viewModel.effect.observe(this, Observer { handleEffect(it) })
    }

    private fun handleEffect(effect: UserDetailsEffect) = Unit

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_details, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menu_edit -> {
            true
        }
        R.id.menu_delete -> {
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
