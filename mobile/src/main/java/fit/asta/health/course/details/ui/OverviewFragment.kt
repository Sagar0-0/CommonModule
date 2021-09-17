package fit.asta.health.course.details.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import fit.asta.health.R
import fit.asta.health.course.details.adapter.ExpertsAdapter
import fit.asta.health.course.details.adapter.PointsAdapter
import fit.asta.health.course.details.data.OverViewData
import fit.asta.health.utils.mapStringKey
import kotlinx.android.synthetic.main.course_overview.*


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

        overViewData?.let {
            //Introduction
            courseIntroTitle.text = it.intro.title
            courseIntroDesc.text = it.intro.desc
            courseKeyPointsTitle.text = it.keyPoints.title
            courseKeyPointsRcView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            courseKeyPointsRcView.adapter = PointsAdapter(requireContext(), it.keyPoints.points)

            //Audience
            courseLevel.text = requireContext().mapStringKey(it.audience.level)
            courseAgeGroup.text =
                "${it.audience.ageGroup.from} - ${it.audience.ageGroup.to}"
            rcvCoursePreRequisites.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rcvCoursePreRequisites.adapter =
                PointsAdapter(requireContext(), it.audience.preRequisites)
            rcvCourseNotFor.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rcvCourseNotFor.adapter = PointsAdapter(requireContext(), it.audience.notFor)

            //Experts
            courseExpertsRcView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            courseExpertsRcView.adapter = ExpertsAdapter(requireContext(), it.experts)
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