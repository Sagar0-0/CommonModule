package fit.asta.health.course.details.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import fit.asta.health.R
import fit.asta.health.course.details.adapter.ModulesAdapter
import fit.asta.health.course.details.data.SessionData
import kotlinx.android.synthetic.main.fragment_sessions.*


/**
 * A simple [Fragment] subclass.
 * Use the [SessionsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SessionsFragment : Fragment() {

    private var sessions: ArrayList<SessionData>? = null
    private var courseId: String = ""

    companion object {

        private const val ARG_COURSE_ID = "metaData"
        private const val ARG_MODULES = "courseModules"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param sessions Course Modules.
         * @return A new instance of fragment SessionsFragment.
         */
        @JvmStatic
        fun newInstance(courseId: String, sessions: ArrayList<SessionData>?) =
            SessionsFragment().apply {

                arguments = Bundle().apply {

                    putString(ARG_COURSE_ID, courseId)
                    putParcelableArrayList(ARG_MODULES, sessions)
                }
            }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {

            courseId = it.getString(ARG_COURSE_ID)!!
            sessions = it.getParcelableArrayList(ARG_MODULES)
            courseModulesRcView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            courseModulesRcView.adapter = ModulesAdapter(requireContext(), courseId, sessions!!)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sessions, container, false)
    }
}