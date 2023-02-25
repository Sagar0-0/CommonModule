package fit.asta.health.old.scheduler.tags.ui

import androidx.lifecycle.Observer
import fit.asta.health.old.scheduler.tags.viewmodel.TagsAction

class TagsObserver(private val tagsView: TagsView) : Observer<TagsAction> {
    override fun onChanged(action: TagsAction) {
        when (action) {
            is TagsAction.LoadTag -> {
                val state = TagsView.State.LoadTag(action.tag)
                tagsView.changeState(state)
            }
            is TagsAction.LoadTagList -> {
                val state = TagsView.State.LoadTagList(action.list)
                tagsView.changeState(state)
            }
            is TagsAction.Empty -> {
                val state = TagsView.State.Empty
                tagsView.changeState(state)
            }
            is TagsAction.Error -> {
                val state = TagsView.State.Error(action.message)
                tagsView.changeState(state)
            }
        }
    }
}