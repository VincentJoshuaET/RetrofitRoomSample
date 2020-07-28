package com.example.retrofitroomsample.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import com.example.retrofitroomsample.R
import com.example.retrofitroomsample.databinding.DialogProfileBinding
import com.example.retrofitroomsample.viewmodel.DataViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ProfileDialog : BottomSheetDialogFragment() {

    private val viewModel: DataViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_profile, container, false)
        val binding = DialogProfileBinding.bind(view)

        val txtName = binding.txtName
        val txtEmail = binding.txtEmail
        val txtMobile = binding.txtMobile
        val txtCountry = binding.txtCountry
        val txtGender = binding.txtGender
        val txtDateOfBirth = binding.txtDateOfBirth

        val formatter =
            DateTimeFormatter.ofPattern("MMMM d, yyyy").withZone(ZoneId.systemDefault())

        viewModel.user.observe(viewLifecycleOwner) { user ->
            txtName.text = user.name
            txtEmail.text = user.email
            txtMobile.text = user.mobile
            txtCountry.text = user.country
            txtGender.text = user.gender

            val date =
                Instant.ofEpochMilli(user.dateOfBirth).atZone(ZoneId.systemDefault()).toLocalDate()
            txtDateOfBirth.text = date.format(formatter)
        }

        return view
    }

}