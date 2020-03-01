package pl.gmat.users.feature.edit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pl.gmat.users.R
import pl.gmat.users.common.dagger.Injector
import pl.gmat.users.common.model.Gender
import pl.gmat.users.databinding.ActivityEditUserBinding
import javax.inject.Inject
import javax.inject.Provider

class EditUserActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, EditUserActivity::class.java)
    }

    @Inject
    lateinit var viewModelProvider: Provider<EditUserViewModel>

    private val viewModel: EditUserViewModel by viewModels {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>) = viewModelProvider.get() as T
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.appComponent.editUserComponentFactory().create(this).inject(this)
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityEditUserBinding>(this, R.layout.activity_edit_user)
            .apply {
                viewModel = this@EditUserActivity.viewModel
                state = this@EditUserActivity.viewModel.state
                lifecycleOwner = this@EditUserActivity
                setupViews()
            }
        viewModel.effect.observe(this, Observer { handleEffect(it) })
    }

    private fun ActivityEditUserBinding.setupViews() {
        setupGenderSpinner()
        setupAddClickListener()
    }

    private fun ActivityEditUserBinding.setupGenderSpinner() {
        val spinnerAdapter = ArrayAdapter(
            this@EditUserActivity,
            android.R.layout.simple_spinner_item,
            Gender.values().map { getString(it.nameResId) })
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner.adapter = spinnerAdapter
    }

    private fun ActivityEditUserBinding.setupAddClickListener() {
        addButton.setOnClickListener {
            val form = EditUserForm(
                firstNameEditText.textString(),
                lastNameEditText.textString(),
                ageEditText.textString(),
                genderSpinner.selectedItemPosition,
                addressSpinner.selectedItemPosition,
                addressEditText.textString()
            )
            this@EditUserActivity.viewModel.onAddClicked(form)
        }
    }

    private fun EditText.textString() = text.toString()

    private fun handleEffect(effect: EditUserEffect) = when (effect) {
        EditUserEffect.Finish -> finish()
    }
}