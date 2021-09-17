package fit.asta.health.testimonials.ui

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import fit.asta.health.R
import fit.asta.health.testimonials.adapter.TestimonialsAdapter
import fit.asta.health.testimonials.data.TestimonialData
import kotlinx.android.synthetic.main.testimonials_activity.view.*
import kotlinx.android.synthetic.main.testimonials_write.view.*


class TestimonialsViewImpl : TestimonialsView {

    private var rootView: View? = null
    private var bottomView: View? = null
    private var bottomDialog: BottomSheetDialog? = null

    override fun setContentView(activity: Activity): View? {

        setupView(activity)
        setupRecyclerView()
        return rootView
    }

    private fun setupView(activity: Activity) {

        rootView =
            LayoutInflater.from(activity).inflate(R.layout.testimonials_activity, null, false)
        bottomView = LayoutInflater.from(activity).inflate(R.layout.testimonials_write, null)
        bottomDialog = BottomSheetDialog(activity)
    }

    private fun setupRecyclerView() {
        rootView?.let {
            val adapter = TestimonialsAdapter()
            it.rcvTestimonials.layoutManager = LinearLayoutManager(it.context)
            it.rcvTestimonials.adapter = adapter
        }
    }

    override fun changeState(state: TestimonialsView.State) {
        when (state) {
            is TestimonialsView.State.LoadTestimonial -> updateWriteView(state.testimonial)
            is TestimonialsView.State.LoadTestimonials -> setAdapter(state.list)
            is TestimonialsView.State.Empty -> showEmpty()
            is TestimonialsView.State.Error -> showError(state.message)
        }
    }

    private fun updateWriteView(testimonial: TestimonialData) {
        bottomView?.let {
            it.edt_testimonial.setText(testimonial.testimonial)
            it.edt_name.setText(testimonial.name)
            it.edt_designation.setText(testimonial.designation)
            it.edt_organization.setText(testimonial.organization)
        }
    }

    private fun setAdapter(list: List<TestimonialData>) {
        rootView?.let {
            (it.rcvTestimonials.adapter as TestimonialsAdapter).updateList(list)
        }
    }

    private fun showEmpty() {
        bottomDialog?.hide()
    }

    private fun showError(msg: String) {

    }

    override fun fabClickListener(listener: ClickListener) {

        rootView?.let { view ->
            view.fab_testimonial_write.setOnClickListener {
                listener.onClickFab()
                launchTestimonialWriteSheet()
            }
        }
    }

    private fun launchTestimonialWriteSheet() {

        bottomView?.let { view ->

            bottomDialog?.setContentView(view)
            view.imgClose.setOnClickListener {
                bottomDialog?.hide()
            }
        }
        bottomDialog?.show()
    }

    override fun submitClickListener(listener: ClickListener) {

        bottomView?.let { view ->
            view.btnSubmit.setOnClickListener {
                if (validation()) {
                    listener.onClickSubmit(collectTestimonial(view))
                }
            }
        }
    }

    private fun validation(): Boolean {
        bottomView?.let { view ->
            when {
                view.edt_testimonial.length() == 0 -> {
                    view.edt_testimonial.error = "This field is not Empty"
                }
                view.edt_testimonial.length() > 160 -> {
                    view.edt_testimonial.error = "max words limit 160 "
                }
                view.edt_name.length() == 0 -> {
                    view.edt_name.error = "This field is not Empty"
                }
                view.edt_name.length() > 32 -> {
                    view.edt_name.error = "max words limit 32 "
                }
                view.edt_designation.length() == 0 -> {
                    view.edt_designation.error = "This field is not Empty"
                }
                view.edt_designation.length() > 24 -> {
                    view.edt_designation.error = "max words limit 24 "
                }
                view.edt_organization.length() == 0 -> {
                    view.edt_organization.error = "This field is not Empty"
                }
                view.edt_organization.length() > 24 -> {
                    view.edt_organization.error = "max words limit 24 "
                }
                else -> {
                    return true
                }
            }
        }
        return false
    }

    private fun collectTestimonial(view: View): TestimonialData {

        return TestimonialData(
            "",
            "",
            "",
            view.edt_testimonial.text.toString().trim(),
            view.edt_name.text.toString().trim(),
            view.edt_designation.text.toString().trim(),
            view.edt_organization.text.toString().trim()
        )
    }

    /*override fun onOptionsItemSelected(
        item: MenuItem,
        onLoadTestimonial: () -> Unit,
        callBack: () -> Boolean
    ): Boolean {
        return when (item.itemId) {
            R.id.menu_testimonial_write -> {
                onLoadTestimonial.invoke()
                launchTestimonialWriteSheet()
                true
            }
            else -> callBack.invoke()
        }
    }

    private fun submitButton() {
        val testimonialText = findViewById<View>(R.id.edt_testimonial) as EditText
        val personName = findViewById<View>(R.id.edt_name) as EditText
        val personDesignation = findViewById<View>(R.id.edt_designation) as EditText
        val organization1 = findViewById<View>(R.id.edt_organization) as EditText

        val testimonial = testimonialText.text.toString().trim()
        val name = personName.text.toString().trim()
        val designation = personDesignation.text.toString().trim()
        val organization = organization1.text.toString().trim()

        if (!testimonial.isEmpty() && !name.isEmpty() && !designation.isEmpty() && !organization.isEmpty()) {

            if (testimonial.matches("^[a-zA-Z]*$".toRegex()) && name.matches("^[a-zA-Z]*$".toRegex()) && designation.matches(
                    "^[a-zA-Z]*$".toRegex()
                ) && organization.matches("^[a-zA-Z]*$".toRegex())
            ) {

                if (testimonial.length < 50 && name.length < 15 && designation.length < 20 && organization.length < 20) {

                    try {
                        val details = TestimonialData()
                        details.testimonial = testimonial
                        details.name = name
                        details.designation = designation
                        details.organization = organization

                        Toast.makeText(this, "Submitted Successfully ", Toast.LENGTH_LONG).show()

                    } catch (e: Exception) {
                        Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this, "you've reached the text limit", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Please enter only alphabetic characters ", Toast.LENGTH_LONG)
                    .show()
            }
        } else {
            Toast.makeText(this, "Please fill up all the fields ", Toast.LENGTH_LONG).show()
        }
    }*/
}