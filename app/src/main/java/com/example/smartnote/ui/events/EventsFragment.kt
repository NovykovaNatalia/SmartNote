package com.example.smartnote.ui.events

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartnote.DataStoreHandler
import com.example.smartnote.R
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.date_picker.*
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList


class EventsFragment : Fragment() {
    lateinit var calendarView: CalendarView
    lateinit var recyclerViwEvents: RecyclerView
    lateinit var cardListEvent: ArrayList<Event>
    lateinit var adapterEvent: AdapterEvent
    lateinit var event: EditText
    lateinit var time_tv: TextView
    lateinit var date_tv: TextView
    lateinit var event_tv: TextView
    lateinit var actionButtonSaveEvent: Button
    lateinit var actionButtonCancelEvent: Button
    lateinit var collapsing_toolbar: CollapsingToolbarLayout
    lateinit var inflater:LayoutInflater
    lateinit var container: ViewGroup
    lateinit var event_all: TextView
    lateinit var event_day: TextView
    lateinit var event_week: TextView
    lateinit var event_month: TextView
    lateinit var event_custom: TextView
    var color : Int = 0
    lateinit var time_picker: TimePicker
    lateinit var submitBtn: Button
    lateinit var cancelBtn: Button
    lateinit var toDate:TextView
    lateinit var fromDate: TextView
    lateinit var line_one_event: RelativeLayout
    lateinit var line_two_event: RelativeLayout


    @SuppressLint("WrongConstant")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        cardListEvent = DataStoreHandler.events
        this.inflater = inflater
        if (container != null) {
            this.container = container
        }

        val root = inflater.inflate(R.layout.fragment_event, container, false)
//        val dialogDatePicker = inflater.inflate(R.layout.date_picker, container, false)


        calendarView = root.findViewById(R.id.calendarView)

//                    val dateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM)
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)

            calendarView.date = calendar.timeInMillis
            Log.e("den", " changed " + calendarView.date)
        }


        collapsing_toolbar = root.findViewById(R.id.collapsing_toolbar)

        event_all = root.findViewById(R.id.event_all)
        event_day = root.findViewById(R.id.event_day)
        event_week = root.findViewById(R.id.event_week)
        event_month = root.findViewById(R.id.event_month)
        event_custom = root.findViewById(R.id.event_custom)
        setFiltersClickListeners()

        toolbarTextAppernce()

        recyclerViwEvents = root.findViewById(R.id.recyclerViewEvents)
        recyclerViwEvents.layoutManager =
                LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        adapterEvent = AdapterEvent(cardListEvent, context)

        recyclerViwEvents.adapter = adapterEvent
        adapterEvent.notifyDataSetChanged()
        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id = item.itemId

        if (id == R.id.share) {
            val currentFragment = activity?.supportFragmentManager!!.fragments.first().childFragmentManager.fragments.first()
            when (currentFragment) {
                is EventsFragment -> {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    var sharStr = DataStoreHandler.events.toString()
                    sharStr = sharStr.replace('[', ' ')
                    sharStr = sharStr.replace(']', ' ')
                    sharStr = sharStr.replace(",", "")
                    val shareBody = sharStr
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody)
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                    startActivity(Intent.createChooser(shareIntent, "choose one"))
                }
            }
            return true
        }
        if (id == R.id.delete) {
            val currentFragment = activity?.supportFragmentManager!!.fragments.first().childFragmentManager.fragments.first()
            when (currentFragment) {
                is EventsFragment -> {
                    DataStoreHandler.events.removeAll(DataStoreHandler.events)
                    currentFragment.adapterEvent.notifyDataSetChanged()
                    DataStoreHandler.saveArrayListNotes()
                }
            }
            return true
        }

        if (id == R.id.add) {
            val dialogViewEvemt = inflater.inflate(R.layout.event_dialog, container, false)
            val itemViewEvent = inflater.inflate(R.layout.item_event, container, false)
            event = dialogViewEvemt.findViewById(R.id.event)
            time_tv = itemViewEvent.findViewById(R.id.time_event_tv)
            time_picker = dialogViewEvemt.findViewById(R.id.timePicker)
            OnClickTime()
            date_tv = itemViewEvent.findViewById(R.id.date_event_tv)
            event_tv = itemViewEvent.findViewById(R.id.text_event_tv)

            val mBuilder = AlertDialog.Builder(context)
                    .setView(dialogViewEvemt)
                    .setTitle("Event")
            val mAlertDialog = mBuilder.show()

            actionButtonCancelEvent = dialogViewEvemt.findViewById(R.id.cancel_date_btn)
            actionButtonSaveEvent = dialogViewEvemt.findViewById<Button>(R.id.save_dialog_event);

            actionButtonSaveEvent.setOnClickListener {
                mAlertDialog.dismiss()
                if (event.text.isNotEmpty() && time_tv.text.isNotEmpty()) {
                    var newEvent = Event()


                    newEvent.event = event.text.toString()
                    newEvent.date_ev = calendarView.date
                    newEvent.time = time_tv.text.toString()
                    cardListEvent.add(newEvent)
                } else {
                    Toast.makeText(context, "Put values!", Toast.LENGTH_LONG).show()
                }
                adapterEvent.notifyDataSetChanged()

            }
            actionButtonCancelEvent.setOnClickListener() {
                mAlertDialog.dismiss()
            }


        }

        return super.onOptionsItemSelected(item)
    }

    private fun toolbarTextAppernce() {
        collapsing_toolbar.setCollapsedTitleTextAppearance(R.style.collapsedappbar)
        collapsing_toolbar.setExpandedTitleTextAppearance(R.style.expandedappbar)
    }

    private fun OnClickTime() {

        time_picker.setOnTimeChangedListener { _, hour, minute ->
            var hour = hour
            var am_pm = ""
            // AM_PM decider logic
            when {hour == 0 -> { hour += 12
                am_pm = "AM"
            }
            hour == 12 -> am_pm = "PM"
            hour > 12 -> { hour -= 12
                am_pm = "PM"
            }
                else -> am_pm = "AM"
            }
            if (time_tv != null) {
                val hour = if (hour < 10) "0" + hour else hour
                val min = if (minute < 10) "0" + minute else minute
                // display format of time
                val msg = "$hour : $min $am_pm"
                time_tv.text = msg
                time_tv.visibility = ViewGroup.VISIBLE
            }
        }
    }

    private fun setFiltersClickListeners() {
       color =  event_all.textColors.defaultColor
        Log.e("den", "start function")
        var dateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM)


        event_all.setOnClickListener {
            resetHighlightFilter()
            event_all.setTextColor(Color.WHITE)
            adapterEvent = AdapterEvent(cardListEvent, context)
            recyclerViwEvents.adapter = adapterEvent
            adapterEvent.notifyDataSetChanged()
        }
        event_day.setOnClickListener {
            var filterCollectionByDay = ArrayList<Event>()
            for(event in cardListEvent) {
                if(dateFormatter.format(event.date_ev).equals(dateFormatter.format(calendarView.date))) {
                    filterCollectionByDay.add(event)
                }
            }
            resetHighlightFilter()
            event_day.setTextColor(Color.WHITE)
            adapterEvent = AdapterEvent(filterCollectionByDay, context)
            recyclerViwEvents.adapter = adapterEvent
            adapterEvent.notifyDataSetChanged()
        }
        event_week.setOnClickListener {
            var calendar = Calendar.getInstance()
            calendar.clear();
            calendar.setTimeInMillis(calendarView.date);
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            var startWeek = calendar.getTimeInMillis()
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            var endWeek = calendar.getTimeInMillis()
            var filterCollectionByWeek = ArrayList<Event>()

            for(event in cardListEvent) {
                if(event.date_ev >= startWeek && event.date_ev <= endWeek) {
                    filterCollectionByWeek.add(event)
                }
            }
            resetHighlightFilter()
            event_week.setTextColor(Color.WHITE)
            adapterEvent = AdapterEvent(filterCollectionByWeek, context)

            recyclerViwEvents.adapter = adapterEvent
            adapterEvent.notifyDataSetChanged()
        }
        event_month.setOnClickListener {
            var calendar = Calendar.getInstance()
            calendar.clear();
            calendar.setTimeInMillis(calendarView.date);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            var startMonth = calendar.getTimeInMillis()
            calendar.add(Calendar.MONTH, 1);
            var endMonth = calendar.getTimeInMillis()
            var filterCollectionByMonth = ArrayList<Event>()

            for(event in cardListEvent) {
                if(event.date_ev >= startMonth && event.date_ev < endMonth) {
                    filterCollectionByMonth.add(event)
                }
            }
            resetHighlightFilter()
            event_month.setTextColor(Color.WHITE)
            adapterEvent = AdapterEvent(filterCollectionByMonth, context)

            recyclerViwEvents.adapter = adapterEvent
            adapterEvent.notifyDataSetChanged()

        }
        event_custom.setOnClickListener {
            val dialogDatePicker = inflater.inflate(R.layout.date_picker, container, false)
            submitBtn = dialogDatePicker.findViewById(R.id.send_date_btn)
            cancelBtn = dialogDatePicker.findViewById(R.id.cancel_date_btn)
            fromDate = dialogDatePicker.findViewById(R.id.from_date)
            toDate = dialogDatePicker.findViewById(R.id.to_date)
            line_one_event = dialogDatePicker.findViewById(R.id.line_one_date_picker)
            line_two_event = dialogDatePicker.findViewById(R.id.line_two_time_picker)


            line_one_event.setOnClickListener{
                val dialogDatePicker = inflater.inflate(R.layout.date_from, container, false)

                val mBuilder = AlertDialog.Builder(context)
                        .setView(dialogDatePicker)
                val mAlertDialog = mBuilder.show()


            }

            line_two_event.setOnClickListener {
                val dialogDatePicker = inflater.inflate(R.layout.date_to, container, false)

                val mBuilder = AlertDialog.Builder(context)
                        .setView(dialogDatePicker)
                val mAlertDialog = mBuilder.show()
            }

            submitBtn.setOnClickListener {

            }


            val mBuilder = AlertDialog.Builder(context)
                    .setView(dialogDatePicker)
            val mAlertDialog = mBuilder.show()
            val c = Calendar.getInstance()


        }

    }
    fun resetHighlightFilter() {
        event_all.setTextColor(color)
        event_day.setTextColor(color)
        event_week.setTextColor(color)
        event_custom.setTextColor(color)
        event_month.setTextColor(color)

    }
}


