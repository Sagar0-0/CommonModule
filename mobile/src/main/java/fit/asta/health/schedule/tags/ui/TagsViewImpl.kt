package fit.asta.health.schedule.tags.ui

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import fit.asta.health.R
import fit.asta.health.schedule.tags.adapter.TagsAdapter
import fit.asta.health.schedule.tags.data.ScheduleTagData
import fit.asta.health.schedule.tags.listner.ClickListener
import kotlinx.android.synthetic.main.tags_activity.view.*
import kotlinx.android.synthetic.main.tags_add.view.*


class TagsViewImpl : TagsView {

    private var rootView: View? = null
    private var tagCreateView: View? = null
    private var bottomDialog: BottomSheetDialog? = null

    override fun setContentView(activity: Activity): View? {
        rootView =
            LayoutInflater.from(activity).inflate(R.layout.tags_activity, null, false)

        setupRecyclerView()
        setupTagSheet(activity)
        return rootView
    }

    private fun setupTagSheet(activity: Activity) {
        tagCreateView = LayoutInflater.from(activity).inflate(R.layout.tags_add, null)
        bottomDialog = BottomSheetDialog(activity)
    }

    private fun setupRecyclerView() {
        rootView?.let {
            val adapter = TagsAdapter()
            it.rcvTags.layoutManager = LinearLayoutManager(it.context)
            it.rcvTags.adapter = adapter
        }
    }

    override fun changeState(state: TagsView.State) {
        when (state) {
            is TagsView.State.LoadTagList -> setAdapter(state.list)
            is TagsView.State.LoadTag -> updateTagView(state.tag)
            is TagsView.State.Empty -> showEmpty()
            is TagsView.State.Error -> showError(state.message)
        }
    }

    private fun setAdapter(list: List<ScheduleTagData>) {
        rootView?.let {
            (it.rcvTags.adapter as TagsAdapter).updateList(list)
        }
    }

    override fun setAdapterClickListener(listener: ClickListener) {
        rootView?.let {
            (it.rcvTags.adapter as TagsAdapter).setAdapterListener(listener)
        }
    }

    private fun updateTagView(tagData: ScheduleTagData) {
        tagCreateView?.let {
            it.edtTagName.setText(tagData.tagName)
            it.cbxDouble.isChecked = tagData.type as Boolean
            //it.imgTag.setImageURI(tagData.url)
        }
    }

    private fun showEmpty() {

    }

    private fun showError(msg: String) {

    }

    override fun fabClickListener(listener: ClickListener) {
        rootView?.let { view ->
            view.fab_tag_create.setOnClickListener {
                launchTagSheet()
            }
        }
    }

    private fun launchTagSheet() {

        tagCreateView?.let { view ->

            view.imgClose.setOnClickListener {
                bottomDialog?.hide()
            }
            bottomDialog?.setContentView(view)
        }
        bottomDialog?.show()
    }

    override fun submitClickListener(listener: ClickListener) {

        tagCreateView?.let { view ->
            view.btnTagCreate.setOnClickListener {
                if (validation()) {
                    listener.onClickSubmit()
                }
            }
        }
    }

    fun collectTestimonial() {
        tagCreateView?.let {
            /*ScheduleTagData(
                "",
                "",
                it.edt_testimonial.text.toString().trim(),
                it.edt_name.text.toString().trim(),
                it.edt_designation.text.toString().trim(),
                it.edt_organization.text.toString().trim()
            )*/
        }
    }

    private fun validation(): Boolean {

        return false
    }
}