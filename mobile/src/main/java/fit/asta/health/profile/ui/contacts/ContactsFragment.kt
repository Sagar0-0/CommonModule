package fit.asta.health.profile.ui.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject


class ContactsFragment : Fragment() {

    private val contactsFragmentView: ContactsFragmentView by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View? {
        return contactsFragmentView.setContentView(requireActivity(), container)
    }

}





