package com.example.retrofitroomsample.ui.auth

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.text.trimmedLength
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.example.retrofitroomsample.R
import com.example.retrofitroomsample.binding.viewBinding
import com.example.retrofitroomsample.databinding.FragmentRegisterBinding
import com.example.retrofitroomsample.model.User
import com.example.retrofitroomsample.util.*
import com.example.retrofitroomsample.viewmodel.DataViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@AndroidEntryPoint
@ExperimentalStdlibApi
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val binding by viewBinding(FragmentRegisterBinding::bind)

    private val viewModel: DataViewModel by activityViewModels()

    @Inject
    lateinit var keyboard: KeyboardUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = requireContext()

        val txtName = binding.txtName
        val txtEmail = binding.txtEmail
        val txtPassword = binding.txtPassword
        val txtConfirmPassword = binding.txtConfirmPassword
        val txtMobile = binding.txtMobile
        val txtDateOfBirth = binding.txtDateOfBirth
        val txtCountry = binding.txtCountry
        val txtGender = binding.txtGender
        val btnRegister = binding.btnRegister

        val countries = resources.getStringArray(R.array.countries)
        val countriesAdapter = ArrayAdapter(
            context,
            R.layout.dropdown_menu_popup_item,
            resources.getStringArray(R.array.countries)
        )
        val genders = ArrayAdapter(
            context,
            R.layout.dropdown_menu_popup_item,
            resources.getStringArray(R.array.genders)
        )

        val formatter =
            DateTimeFormatter.ofPattern("MMMM d, yyyy").withZone(ZoneId.systemDefault())
        val maxDate =
            LocalDate.now()
                .minusYears(18)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
        var dateOfBirth = maxDate

        txtName.setErrorListener()
        txtEmail.setErrorListener()
        txtPassword.setErrorListener()
        txtConfirmPassword.setErrorListener()
        txtMobile.setErrorListener()
        txtDateOfBirth.setErrorListener()
        txtCountry.setErrorListener()
        txtGender.setErrorListener()

        txtCountry.setAdapter(countriesAdapter)
        txtCountry.setOnItemClickListener { _, _, _, _ ->
            keyboard.hide(this)
        }

        txtGender.setAdapter(genders)
        txtGender.setOnClickListener {
            keyboard.hide(this)
        }

        txtDateOfBirth.setOnClickListener {
            keyboard.hide(this)
            val constraints =
                CalendarConstraints.Builder()
                    .setOpenAt(dateOfBirth)
                    .setEnd(maxDate)
                    .build()
            val builder =
                MaterialDatePicker.Builder.datePicker().apply {
                    setCalendarConstraints(constraints)
                    setSelection(dateOfBirth)
                    setTitleText(R.string.hint_date_of_birth)
                }
            val picker =
                builder.build().apply {
                    addOnPositiveButtonClickListener { epochMilli ->
                        dateOfBirth = epochMilli
                        val date =
                            Instant.ofEpochMilli(dateOfBirth)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                        txtDateOfBirth.setText(date.format(formatter))
                    }
                }
            picker.show(parentFragmentManager, picker.toString())
        }

        btnRegister.setOnClickListener {
            keyboard.hide(this)

            val name = txtName.text.toString().capitalizeWords()
            val email = txtEmail.text.toString()
            val password = txtPassword.text.toString()
            val confirmPassword = txtConfirmPassword.text.toString()
            val mobile = txtMobile.text.toString()
            val country = txtCountry.text.toString()
            val gender = txtGender.text.toString()
            val birth = txtDateOfBirth.text.toString()
            var fail = false

            if (name.isEmpty()) {
                txtName.showError(getString(R.string.txt_enter_full_name))
                fail = true
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                txtEmail.showError(getString(R.string.txt_enter_valid_email))
                fail = true
            }
            if (email.isEmpty()) {
                txtEmail.showError(getString(R.string.txt_enter_email))
                fail = true
            }
            if (password.length < 8) {
                txtPassword.showError(getString(R.string.txt_password_short))
                fail = true
            }
            if (password.trimmedLength() != password.length) {
                txtPassword.showError(getString(R.string.txt_invalid_password))
                fail = true
            }
            if (confirmPassword.trimmedLength() != confirmPassword.length) {
                txtConfirmPassword.showError(getString(R.string.txt_invalid_password))
                fail = true
            }
            if (password.trim() != confirmPassword.trim()) {
                txtConfirmPassword.showError(getString(R.string.txt_passwords_not_match))
                fail = true
            }
            if (password.isEmpty()) {
                txtPassword.showError(getString(R.string.txt_enter_password))
                fail = true
            }
            if (confirmPassword.isEmpty()) {
                txtConfirmPassword.showError(getString(R.string.txt_confirm_password))
                fail = true
            }
            if (!Patterns.PHONE.matcher(mobile).matches()) {
                txtMobile.showError(getString(R.string.txt_enter_mobile))
                fail = true
            }
            if (country.isEmpty() || !countries.contains(country)) {
                txtCountry.showError(getString(R.string.txt_enter_country))
                fail = true
            }
            if (gender.isEmpty()) {
                txtGender.showError(getString(R.string.txt_enter_gender))
                fail = true
            }
            if (birth.isEmpty()) {
                txtDateOfBirth.showError(getString(R.string.txt_enter_date_of_birth))
                fail = true
            }

            if (fail) return@setOnClickListener

            val user = User(email, password, name, mobile, country, gender, dateOfBirth)

            viewModel.checkEmail(email).observe(viewLifecycleOwner) { result ->
                if (result) {
                    Snackbar.make(view, "Account already exists!", Snackbar.LENGTH_LONG).show()
                } else {
                    viewModel.register(user)
                    findNavController().navigate(R.id.action_auth_to_home)
                }
            }
        }
    }

}