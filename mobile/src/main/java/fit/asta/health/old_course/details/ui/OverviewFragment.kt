package fit.asta.health.old_course.details.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import fit.asta.health.R
import fit.asta.health.old_course.details.adapter.ExpertsAdapter
import fit.asta.health.old_course.details.adapter.PointsAdapter
import fit.asta.health.old_course.details.data.OverViewData
import fit.asta.health.utils.mapStringKey


/**
 * A simple [Fragment] subclass.
 * Use the [OverviewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OverviewFragment : Fragment() {

    private var overViewData: OverViewData? = null

    companion object {

        private const val ARG_OVERVIEW = "courseOverView"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param overview Course Overview.
         * @return A new instance of fragment OverviewFragment.
         */
        @JvmStatic
        fun newInstance(overview: OverViewData) = OverviewFragment().apply {

            arguments = Bundle().apply {
                putParcelable(ARG_OVERVIEW, overview)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {

            overViewData = it.getParcelable(ARG_OVERVIEW)
            updateUIData()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUIData() {

        overViewData?.let { ovd ->
            //Introduction
            view?.let {
                it.findViewById<MaterialTextView>(R.id.courseIntroTitle).text = ovd.intro.title
                it.findViewById<MaterialTextView>(R.id.courseIntroDesc).text = ovd.intro.desc
                it.findViewById<MaterialTextView>(R.id.courseKeyPointsTitle).text =
                    ovd.keyPoints.title

                val courseKeyPointsRcView =
                    it.findViewById<RecyclerView>(R.id.courseKeyPointsRcView)
                courseKeyPointsRcView.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                courseKeyPointsRcView.adapter =
                    PointsAdapter(requireContext(), ovd.keyPoints.points)

                //Audience
                it.findViewById<MaterialTextView>(R.id.courseLevel).text =
                    requireContext().mapStringKey(ovd.audience.level)
                it.findViewById<MaterialTextView>(R.id.courseAgeGroup).text =
                    "${ovd.audience.ageGroup.from} - ${ovd.audience.ageGroup.to}"

                val rcvCoursePreRequisites =
                    it.findViewById<RecyclerView>(R.id.rcvCoursePreRequisites)
                rcvCoursePreRequisites.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                rcvCoursePreRequisites.adapter =
                    PointsAdapter(requireContext(), ovd.audience.preRequisites)

                val rcvCourseNotFor = it.findViewById<RecyclerView>(R.id.rcvCourseNotFor)
                rcvCourseNotFor.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                rcvCourseNotFor.adapter = PointsAdapter(requireContext(), ovd.audience.notFor)

                //Experts
                val courseExpertsRcView = it.findViewById<RecyclerView>(R.id.courseExpertsRcView)
                courseExpertsRcView.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                courseExpertsRcView.adapter = ExpertsAdapter(requireContext(), ovd.experts)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.course_overview, container, false)
    }
}