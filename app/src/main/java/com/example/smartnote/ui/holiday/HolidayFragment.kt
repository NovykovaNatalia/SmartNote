package com.example.smartnote.ui.holiday

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartnote.DataStoreHandler
import com.example.smartnote.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*


class HolidayFragment : Fragment() {
    lateinit var floating_btn_event: FloatingActionButton
    lateinit var calendarView: CalendarView
    lateinit var recyclerViwHoliday: RecyclerView

    lateinit var cardListHoliday: ArrayList<Holiday>

    lateinit var customAdapterHoliday: CustomAdapterHoliday
    lateinit var date: EditText
    lateinit var event: EditText
    lateinit var date_tv: TextView
    lateinit var event_tv: TextView
    lateinit var saveActionButtonHoliday: Button
    lateinit var cancelActionButtonHoliday: Button

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cardListHoliday = DataStoreHandler.holiday

        val root = inflater.inflate(R.layout.fragment_holiday, container, false)
        val ttb = AnimationUtils.loadAnimation(context, R.anim.ttb)
        val atb = AnimationUtils.loadAnimation(context, R.anim.atb)
        val btt = AnimationUtils.loadAnimation(context, R.anim.btt)
        val btn = AnimationUtils.loadAnimation(context, R.anim.btn)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val mDialogViewHoliday =
            LayoutInflater.from(context).inflate(R.layout.holiday_dialog, null);
        val mItemViewHoliday = LayoutInflater.from(context).inflate(R.layout.item_holiday, null)

        floating_btn_event = root.findViewById(R.id.floating_btn_event);
        floating_btn_event.startAnimation(ttb)
        calendarView = root.findViewById(R.id.calendarView)
        recyclerViwHoliday = root.findViewById(R.id.recyclerViewHoliday)

        saveActionButtonHoliday = mDialogViewHoliday.findViewById(R.id.save_dialog_holiday)
        cancelActionButtonHoliday = mDialogViewHoliday.findViewById(R.id.cancel_dialog_holiday)
        date = mDialogViewHoliday.findViewById(R.id.date)
        event = mDialogViewHoliday.findViewById(R.id.event)

        date_tv = mItemViewHoliday.findViewById(R.id.date_holiday_tv)
        event_tv = mItemViewHoliday.findViewById(R.id.event_holiday_tv)

        calendarView.startAnimation(ttb)

        recyclerViwHoliday.layoutManager =
            LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        customAdapterHoliday = CustomAdapterHoliday(cardListHoliday, context)

        recyclerViwHoliday.adapter = customAdapterHoliday
        customAdapterHoliday.notifyDataSetChanged()

        floating_btn_event.setOnClickListener(View.OnClickListener {
            floating_btn_event.startAnimation(ttb)

            val mBuilder = AlertDialog.Builder(context)
                .setView(mDialogViewHoliday)
                .setTitle("Holiday")
            val mAlertDialog = mBuilder.show()



            cancelActionButtonHoliday = mDialogViewHoliday.findViewById(R.id.cancel_dialog_holiday)
            saveActionButtonHoliday = mDialogViewHoliday.findViewById<Button>(R.id.save_dialog_holiday);
            
                saveActionButtonHoliday.setOnClickListener {
                    mAlertDialog.dismiss()
                    if (date.text.isNotEmpty() && event.text.isNotEmpty()) {
                        var cardHoliday = Holiday()
                        cardHoliday.date = date.text.toString()
                        cardHoliday.event = event.text.toString()

                        cardListHoliday.add(cardHoliday)
                    } else {
                        Toast.makeText(context, "Put values!", Toast.LENGTH_LONG).show()
                    }
                    customAdapterHoliday.notifyDataSetChanged()

                }
                cancelActionButtonHoliday.setOnClickListener() {
                    mAlertDialog.dismiss()
                }
        })
        return  root
    }
}