package com.example.smartnote.ui.holiday

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartnote.DataStoreHandler
import com.example.smartnote.R
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_holiday.*
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
    lateinit var collapsing_toolbar: CollapsingToolbarLayout

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cardListHoliday = DataStoreHandler.holiday

        val root = inflater.inflate(R.layout.fragment_holiday, container, false)
        val ttb = AnimationUtils.loadAnimation(context, R.anim.ttb)
//        floating_btn_event = root.findViewById(R.id.floating_btn_event);
        collapsing_toolbar = root.findViewById(R.id.collapsing_toolbar)
        collapsing_toolbar.setTitle(getResources().getString(R.string.user_name));
//        dynamicToolbarColor()
        toolbarTextAppernce()
        return root
    }

    private fun dynamicToolbarColor() {

        val bitmap = calendarView.background.toBitmap()


        Palette.from(bitmap).generate { palette ->
            collapsing_toolbar.setContentScrimColor(palette!!.getMutedColor(R.attr.colorPrimary))
            collapsing_toolbar.setStatusBarScrimColor(palette.getMutedColor(R.attr.colorPrimaryDark))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity?.window?.statusBarColor = palette.getMutedColor(R.attr.colorPrimaryDark)
            }
        }
    }

    private fun toolbarTextAppernce() {
        collapsing_toolbar.setCollapsedTitleTextAppearance(R.style.collapsedappbar)
        collapsing_toolbar.setExpandedTitleTextAppearance(R.style.expandedappbar)
    }
}
//
//        floating_btn_event.startAnimation(ttb)
//        calendarView = root.findViewById(R.id.calendarView)
////        recyclerViwHoliday = root.findViewById(R.id.recyclerViewHoliday)
//
//        saveActionButtonHoliday = mDialogViewHoliday.findViewById(R.id.save_dialog_holiday)
//        cancelActionButtonHoliday = mDialogViewHoliday.findViewById(R.id.cancel_dialog_holiday)
//        date = mDialogViewHoliday.findViewById(R.id.date)
//        event = mDialogViewHoliday.findViewById(R.id.event)
//
//        date_tv = mItemViewHoliday.findViewById(R.id.date_holiday_tv)
//        event_tv = mItemViewHoliday.findViewById(R.id.event_holiday_tv)
//
//        calendarView.startAnimation(ttb)
//
//        recyclerViwHoliday.layoutManager =
//            LinearLayoutManager(context, LinearLayout.VERTICAL, false)
//
//        customAdapterHoliday = CustomAdapterHoliday(cardListHoliday, context)
//
//        recyclerViwHoliday.adapter = customAdapterHoliday
//        customAdapterHoliday.notifyDataSetChanged()
//
//        floating_btn_event.setOnClickListener(View.OnClickListener {
//            floating_btn_event.startAnimation(ttb)
//
//            val mBuilder = AlertDialog.Builder(context)
//                .setView(mDialogViewHoliday)
//                .setTitle("Holiday")
//            val mAlertDialog = mBuilder.show()
//
//
//
//            cancelActionButtonHoliday = mDialogViewHoliday.findViewById(R.id.cancel_dialog_holiday)
//            saveActionButtonHoliday = mDialogViewHoliday.findViewById<Button>(R.id.save_dialog_holiday);
//
//                saveActionButtonHoliday.setOnClickListener {
//                    mAlertDialog.dismiss()
//                    if (date.text.isNotEmpty() && event.text.isNotEmpty()) {
//                        var cardHoliday = Holiday()
//                        cardHoliday.date = date.text.toString()
//                        cardHoliday.event = event.text.toString()
//
//                        cardListHoliday.add(cardHoliday)
//                    } else {
//                        Toast.makeText(context, "Put values!", Toast.LENGTH_LONG).show()
//                    }
//                    customAdapterHoliday.notifyDataSetChanged()
//
//                }
//                cancelActionButtonHoliday.setOnClickListener() {
//                    mAlertDialog.dismiss()
//                }
//        })


