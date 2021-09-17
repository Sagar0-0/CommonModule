package fit.asta.health.notify.reminder

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import fit.asta.health.ActivityLauncher
import fit.asta.health.R
import fit.asta.health.notify.reminder.adapter.RemindersAdapter
import kotlinx.android.synthetic.main.reminder_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.KoinComponent

class ReminderFragment : Fragment(), KoinComponent {

    private val launcher: ActivityLauncher by inject()
    private lateinit var viewModel: ReminderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.reminder_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(ReminderViewModel::class.java)

        remindersRecyclerView()

        btnReminderAdd.setOnClickListener {

            launcher.launchSchedulerActivity(it.context)
        }
    }

    private fun remindersRecyclerView() {

        rcvReminders.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rcvReminders.adapter =
            RemindersAdapter(
                requireContext(),
                listOf()
            ) {

                GlobalScope.launch {

                    viewModel.delete(it) {

                        Log.i("Delete", "status/$it")
                    }
                }
            }

        viewModel.getAll().observe(viewLifecycleOwner, Observer { reminders ->

            (rcvReminders.adapter as RemindersAdapter).updateList(reminders)
        })
    }

    override fun onPrimaryNavigationFragmentChanged(isPrimaryNavigationFragment: Boolean) {
        super.onPrimaryNavigationFragmentChanged(isPrimaryNavigationFragment)

        if (!isPrimaryNavigationFragment) {

            rcvReminders?.stopScroll()
        }
    }

}