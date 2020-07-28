package com.example.retrofitroomsample.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.observe
import com.example.retrofitroomsample.R
import com.example.retrofitroomsample.binding.viewBinding
import com.example.retrofitroomsample.databinding.FragmentDetailsBinding
import com.example.retrofitroomsample.model.Item
import com.example.retrofitroomsample.util.GlideApp
import com.example.retrofitroomsample.viewmodel.DataViewModel
import com.google.android.material.transition.MaterialContainerTransform

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val binding by viewBinding(FragmentDetailsBinding::bind)

    private val viewModel: DataViewModel by activityViewModels()

    private fun Item.Geo.generateUrl() =
        "https://open.mapquestapi.com/staticmap/v4/getmap?key=txU8GeU8i3B3lrOA6lMYvaWb7TBhA6fI&size=400,300&zoom=15&center=$lat,$lng"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.fragment
            fadeMode = MaterialContainerTransform.FADE_MODE_THROUGH
            interpolator = FastOutSlowInInterpolator()
            isElevationShadowEnabled = false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val txtName = binding.txtName
        val txtUsername = binding.txtUsername
        val txtEmail = binding.txtEmail
        val txtAddress = binding.txtAddress
        val txtZipCode = binding.txtZipCode
        val imageMap = binding.imageMap
        val txtPhone = binding.txtPhone
        val txtWebsite = binding.txtWebsite
        val txtCompany = binding.txtCompany
        val txtCatchPhrase = binding.txtCatchPhrase
        val txtBs = binding.txtBs

        viewModel.item.observe(viewLifecycleOwner) { item ->
            view.transitionName = item.id.toString()
            txtName.text = item.name
            txtUsername.text = item.username
            txtEmail.text = item.email
            val address = item.address
            val location = "${address.suite}, ${address.street}, ${address.city}"
            txtAddress.text = location
            txtZipCode.text = address.zipcode
            txtPhone.text = item.phone
            txtWebsite.text = item.website
            txtCompany.text = item.company.name
            txtCatchPhrase.text = item.company.catchPhrase
            txtBs.text = item.company.bs

            GlideApp.with(this)
                .load(address.geo.generateUrl())
                .fitCenter()
                .into(imageMap)
        }
    }
}