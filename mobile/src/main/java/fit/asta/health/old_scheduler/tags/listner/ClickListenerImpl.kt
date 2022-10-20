package fit.asta.health.old_scheduler.tags.listner

import fit.asta.health.old_scheduler.tags.data.ScheduleTagData
import fit.asta.health.old_scheduler.tags.viewmodel.TagsViewModel

class ClickListenerImpl(val viewModel: TagsViewModel, val listener: TagActivityListener? = null) :
    ClickListener {

    override fun onClickFab() {

    }

    override fun onClickSubmit() {
        viewModel.submitTag()
    }

    override fun onSelectionUpdate(tagData: ScheduleTagData, updatedValue: Boolean) {
        viewModel.updateData(tagData.uid, updatedValue)
        listener?.onSelectionResult(tagData)
    }
}