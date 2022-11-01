package fit.asta.health.old_scheduler.tags.ui

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputEditText
import fit.asta.health.R
import fit.asta.health.old_scheduler.tags.adapter.TagsAdapter
import fit.asta.health.old_scheduler.tags.data.ScheduleTagData
import fit.asta.health.old_scheduler.tags.listner.ClickListener


class TagsViewImpl : TagsView {

    private var rootView: View? = null
    private var tagCreateView: View? = null
    private var bottomDialog: BottomSheetDialog? = null

    override fun setContentView(activity: Activity): View? {
        rootView =
            LayoutInflater.from(activity).inflate(R.layout.schedule_tags_activity, null, false)

        setupRecyclerView()
        setupTagSheet(activity)
        return rootView
    }

    private fun setupTagSheet(activity: Activity) {
        tagCreateView = LayoutInflater.from(activity).inflate(R.layout.schedule_tags_add, null)
        bottomDialog = BottomSheetDialog(activity)
    }

    private fun setupRecyclerView() {
        rootView?.let {
            val adapter = TagsAdapter()
            val rcvTags = it.findViewById<RecyclerView>(R.id.rcvTags)
            rcvTags.layoutManager = LinearLayoutManager(it.context)
            rcvTags.adapter = adapter
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
            (it.findViewById<RecyclerView>(R.id.rcvTags).adapter as TagsAdapter).updateList(list)
        }
    }

    override fun setAdapterClickListener(listener: ClickListener) {
        rootView?.let {
            (it.findViewById<RecyclerView>(R.id.rcvTags).adapter as TagsAdapter).setAdapterListener(
                listener
            )
        }
    }

    private fun updateTagView(tagData: ScheduleTagData) {
        tagCreateView?.let {
            it.findViewById<TextInputEditText>(R.id.edtTagName).setText(tagData.tagName)
            it.findViewById<MaterialCheckBox>(R.id.cbxDouble).isChecked = tagData.type as Boolean
            //it.findViewById<ImageView>(R.id.imgTag).setImageURI(tagData.url)
        }
    }

    private fun showEmpty() {

    }

    @Suppress("UNUSED_PARAMETER")
    private fun showError(msg: String) {

    }

    override fun fabClickListener(listener: ClickListener) {
        rootView?.let { view ->
            view.findViewById<ExtendedFloatingActionButton>(R.id.fab_tag_create)
                .setOnClickListener {
                    launchTagSheet()
                }
        }
    }

    private fun launchTagSheet() {

        tagCreateView?.let { view ->

            view.findViewById<ShapeableImageView>(R.id.imgClose).setOnClickListener {
                bottomDialog?.hide()
            }
            bottomDialog?.setContentView(view)
        }
        bottomDialog?.show()
    }

    override fun submitClickListener(listener: ClickListener) {

        tagCreateView?.let { view ->
            view.findViewById<MaterialButton>(R.id.btnTagCreate).setOnClickListener {
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