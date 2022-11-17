package fit.asta.health.scheduler.view.alarmsetting.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import fit.asta.health.databinding.LayoutDialogAddVariantIntervalBinding
import fit.asta.health.scheduler.model.net.scheduler.Stat
import fit.asta.health.scheduler.view.interfaces.DialogInterface

class AddVariantIntervalDialog(
    context: Context,
    private val listener: DialogInterface
) : Dialog(context) {

    private lateinit var _binding: LayoutDialogAddVariantIntervalBinding
    private val binding get() = _binding

    init {
        setCancelable(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = LayoutDialogAddVariantIntervalBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        _binding.cancelButton.setOnClickListener {
            dismiss()
        }

        _binding.okButton.setOnClickListener {
            if (_binding.inputVariantIntervalLabel.text?.trim().toString().isEmpty()) {
                Toast.makeText(context, "Interval label/name is required", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val hour = _binding.timepicker.currentHour
            val minute = _binding.timepicker.currentMinute

            listener.sendVariantIntervalData(
                Stat(
                    (if (hour >= 12) hour - 12 else hour).toString(),
                    binding.timepicker.currentHour >= 12,
                    minute.toString(),
                    _binding.inputVariantIntervalLabel.text?.trim().toString(),
                    (1..999999999).random()
                )
            )

            dismiss()
        }
    }
}