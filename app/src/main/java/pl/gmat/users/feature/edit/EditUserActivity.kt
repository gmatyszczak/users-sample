package pl.gmat.users.feature.edit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import pl.gmat.users.R
import pl.gmat.users.common.model.Gender
import pl.gmat.users.databinding.ActivityUserEditBinding

class EditUserActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, EditUserActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityUserEditBinding>(this, R.layout.activity_user_edit)
            .apply {
                setupViews()
            }
    }

    private fun ActivityUserEditBinding.setupViews() {
        val spinnerAdapter = ArrayAdapter(
            this@EditUserActivity,
            android.R.layout.simple_spinner_item,
            Gender.values().map { getString(it.nameResId) })
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner.adapter = spinnerAdapter
    }
}