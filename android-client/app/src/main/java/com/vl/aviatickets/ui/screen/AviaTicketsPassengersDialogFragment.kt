package com.vl.aviatickets.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.ActionBar.LayoutParams
import androidx.fragment.app.DialogFragment
import com.vl.aviatickets.databinding.FragmentPassengersBinding
import com.vl.aviatickets.ui.entity.SeatsClass

/**
 * Hardcoded dialog to choose seats class and passengers count
 */
class AviaTicketsPassengersDialogFragment( // TODO refactor
    private val listener: (count: Int, seats: SeatsClass) -> Unit
): DialogFragment() {
    private lateinit var binding: FragmentPassengersBinding
    private var count: Int = 1
    private var seatsClass: SeatsClass = SeatsClass.ECONOMY

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPassengersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.count.progress = count
        binding.seatClass.progress = SeatsClass.entries.indexOf(seatsClass)

        binding.countTitle.text = count.toString()
        binding.seatClassTitle.text = getString(seatsClass.title)

        binding.count.setOnSeekBarChangeListener(object: OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                count = binding.count.progress
                binding.countTitle.text = count.toString()
            }
            override fun onStartTrackingTouch(p0: SeekBar?) = Unit
            override fun onStopTrackingTouch(p0: SeekBar?) = Unit
        })
        binding.seatClass.setOnSeekBarChangeListener(object: OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                seatsClass = SeatsClass.entries[binding.seatClass.progress]
                binding.seatClassTitle.text = getString(seatsClass.title)
            }
            override fun onStartTrackingTouch(p0: SeekBar?) = Unit
            override fun onStopTrackingTouch(p0: SeekBar?) = Unit
        })

        binding.approve.setOnClickListener {
            dismiss()
            listener(count, seatsClass)
        }
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setBackgroundDrawable(null)
    }
}