package study.project.pokelytics.fragments.main

import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.android.inject
import study.project.pokelytics.R
import study.project.pokelytics.activities.ActivityBase
import study.project.pokelytics.databinding.FragmentProfileBinding
import study.project.pokelytics.fragments.FragmentBase
import study.project.pokelytics.services.KeyConstants
import study.project.pokelytics.services.PreferenceService
import study.project.pokelytics.setImageToUrl

class ProfileFragment : FragmentBase<FragmentProfileBinding>() {

    private val fAuth: FirebaseAuth by inject()
    private val fStore: FirebaseFirestore by inject()
    private val preferenceService: PreferenceService by inject()
    override fun getResourceLayout(): Int = R.layout.fragment_profile

    override fun initializeView() {
        val nameUser = (preferenceService.getPreference(KeyConstants.NAME_KEY) ?: resources.getString(R.string.welcomeUser)).ifEmpty { resources.getString(R.string.welcomeUser) }
        val photoUser = fAuth.currentUser?.photoUrl
        val emailUser = preferenceService.getPreference(KeyConstants.EMAIL_KEY)

        binding.apply {
            profileName.text = nameUser
            profileEmail.text = emailUser
            profileImage.setImageToUrl(photoUser.toString(), R.drawable.ic_user)
            deleteAccount.setOnClickListener(){
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle(resources.getString(R.string.deleteaccount))
                builder.setMessage(resources.getString(R.string.deleteAccountQuestion))
                builder.setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
                    if (emailUser != null) {
                        fStore.collection("users").document(emailUser).delete()
                    }
                    fAuth.currentUser?.delete()
                    Toast.makeText(requireContext(), resources.getString(R.string.deleteAccountSuccesful), Toast.LENGTH_LONG).show()
                    preferenceService.removePreference(KeyConstants.EMAIL_KEY)
                    (activity as ActivityBase<*>).navigator.goToLogin()
                    activity?.finish()
                }
                builder.setNegativeButton(resources.getString(R.string.cancel)) { dialog, which ->}
                builder.show()
            }
            privacyPolicy.setOnClickListener(){
                (activity as ActivityBase<*>).navigator.goToPolicy()
            }
        }


    }

    override fun bindViewModel() {
        binding.apply {
            lifecycleOwner = this@ProfileFragment
        }
    }

    override fun subscribe() {
    }

}