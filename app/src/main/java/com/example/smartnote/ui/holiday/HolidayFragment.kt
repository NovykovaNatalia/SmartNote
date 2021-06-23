package com.example.smartnote.ui.holiday

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.CalendarView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.smartnote.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*


class HolidayFragment : Fragment() {
    lateinit var floating_btn_event: FloatingActionButton
    lateinit var calendarView: CalendarView
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_holiday, container, false)
        val ttb = AnimationUtils.loadAnimation(context, R.anim.ttb)
        val atb = AnimationUtils.loadAnimation(context, R.anim.atb)
        val btt = AnimationUtils.loadAnimation(context, R.anim.btt)
        val btn = AnimationUtils.loadAnimation(context, R.anim.btn)

        floating_btn_event = root.findViewById(R.id.floating_btn_event);
        floating_btn_event.startAnimation(ttb)
        calendarView = root.findViewById(R.id.calendarView)
        calendarView.startAnimation(ttb)
        floating_btn_event.setOnClickListener(View.OnClickListener {
            addNote()
        })

        calendarView.setOnDateChangeListener(object : CalendarView.OnDateChangeListener {
            override fun onSelectedDayChange(p0: CalendarView, year: Int, month: Int, dayOfMonth: Int) {
                Toast.makeText(
                    context,
                    "The selected date is $dayOfMonth.${month + 1}.$year",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })


        return root
    }

    fun addNote() {
        val calendarEvent: Calendar = Calendar.getInstance()
        val intent = Intent(Intent.ACTION_EDIT)
        intent.type = "vnd.android.cursor.item/event"
        intent.putExtra("beginTime", calendarEvent.timeInMillis)
        intent.putExtra("allDay", true)
        intent.putExtra("rule", "FREQ=YEARLY")
        intent.putExtra("endTime", calendarEvent.timeInMillis + 60 * 60 * 1000)
        intent.putExtra("title", "Calendar Event")
        startActivity(intent)
    }
}