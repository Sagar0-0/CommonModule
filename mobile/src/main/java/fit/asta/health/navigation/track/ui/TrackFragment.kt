package fit.asta.health.navigation.track.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fit.asta.health.R
import fit.asta.health.navigation.track.viewmodel.TrackViewModel

class TrackFragment : Fragment() {

    companion object {
        fun newInstance() = TrackFragment()
    }

    private lateinit var viewModel: TrackViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.track_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TrackViewModel::class.java)
        // TODO: Use the ViewModel
    }

}