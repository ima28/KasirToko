package com.example.kasirtoko.ui.about

import android.content.Intent
import android.os.Bundle
import android.se.omapi.Session
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kasirtoko.databinding.FragmentAboutBinding
import com.example.kasirtoko.ui.login.LoginActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.properties.ReadOnlyProperty

@AndroidEntryPoint
class AboutFragment : Fragment() {

    private val binding: FragmentAboutBinding by viewBindings()

    private fun viewBindings(): ReadOnlyProperty<AboutFragment, FragmentAboutBinding> {
        TODO("Not yet implemented")
    }

    @Inject
    lateinit var session: Session

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            //tvName.text = session.getAuthUser().nama
            //tvEmail.text = session.getAuthUser().email

            btnLogout.setOnClickListener {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Logout Account")
                    .setMessage("Are you sure Logout your account?")
                    .setPositiveButton("Logout") { _, _ ->
                        session.logout()
                        startActivity(Intent(requireContext(), LoginActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        }).also {
                            requireActivity().finish()
                        }
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }.show()
            }
        }
    }
}

private fun Session.logout() {
    TODO("Not yet implemented")
}

private fun Session.getAuthUser(): Any {
    TODO("Not yet implemented")
}
