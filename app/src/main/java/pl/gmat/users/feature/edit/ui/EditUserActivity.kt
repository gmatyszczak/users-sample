package pl.gmat.users.feature.edit.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import pl.gmat.users.R
import pl.gmat.users.common.model.Gender
import pl.gmat.users.common.model.User
import pl.gmat.users.databinding.ActivityEditUserBinding
import pl.gmat.users.feature.edit.model.EditUserForm
import pl.gmat.users.feature.edit.ui.widget.EditAddressesAdapter

@AndroidEntryPoint
class EditUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "EXTRA_USER"

        fun createIntent(context: Context, user: User? = null) =
            Intent(context, EditUserActivity::class.java).apply {
                user?.let { putExtra(EXTRA_USER, user) }
            }
    }

    private var binding: ActivityEditUserBinding? = null

    private val viewModel: EditUserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityEditUserBinding>(
            this,
            R.layout.activity_edit_user
        ).apply {
            viewModel = this@EditUserActivity.viewModel
            state = this@EditUserActivity.viewModel.state
            lifecycleOwner = this@EditUserActivity
            setupViews()
        }
        viewModel.effect.observe(this, Observer { handleEffect(it) })
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    private fun ActivityEditUserBinding.setupViews() {
        setupGenderSpinner()
        setupAddClickListener()
        setupAddressesAdapter()
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
        submitButton.setOnClickListener {
            val form = EditUserForm(
                firstNameEditText.textString(),
                lastNameEditText.textString(),
                ageEditText.textString(),
                genderSpinner.selectedItemPosition
            )
            this@EditUserActivity.viewModel.onSubmitClicked(form)
        }
    }

    private fun ActivityEditUserBinding.setupAddressesAdapter() {
        addressesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@EditUserActivity)
            adapter = EditAddressesAdapter(this@EditUserActivity.viewModel, this@EditUserActivity)
        }
    }

    private fun EditText.textString() = text.toString()

    private fun handleEffect(effect: EditUserEffect) = when (effect) {
        is EditUserEffect.Finish -> finish()
        is EditUserEffect.InitializeForm -> binding?.initializeForm(effect.form)
        EditUserEffect.ClearNewAddressEditText -> binding?.addressEditText?.setText("")
    }

    private fun ActivityEditUserBinding.initializeForm(form: EditUserForm) {
        firstNameEditText.setText(form.firstName)
        lastNameEditText.setText(form.lastName)
        ageEditText.setText(form.age)
        genderSpinner.setSelection(form.genderIndex)
    }
}