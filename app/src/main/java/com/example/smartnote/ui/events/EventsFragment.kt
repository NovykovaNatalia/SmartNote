package com.example.smartnote.ui.events

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.*
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartnote.DataStoreHandler
import com.example.smartnote.LanguageSupportUtils.Companion.castToLangEvent
import com.example.smartnote.R
import com.google.android.material.appbar.CollapsingToolbarLayout
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList


class EventsFragment : Fragment() {
    lateinit var calendarView: CalendarView
    lateinit var eventsRv: RecyclerView
    lateinit var listEvent: ArrayList<Event>
    lateinit var eventAdapter: AdapterEvent
    lateinit var timeTv: TextView
    lateinit var inflater: LayoutInflater
    lateinit var container: ViewGroup
    lateinit var eventAllTv: TextView
    lateinit var eventDayTv: TextView
    lateinit var eventWeekTv: TextView
    lateinit var eventMonthTv: TextView
    lateinit var eventCustomTv: TextView
    var color : Int = 0

    @SuppressLint("WrongConstant")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        listEvent = DataStoreHandler.events

        this.inflater = inflater

        if (container != null) {
            this.container = container
        }

        val root = inflater.inflate(R.layout.fragment_event, container, false)
        val ttb = AnimationUtils.loadAnimation(context, R.anim.ttb)
        calendarView = root.findViewById(R.id.calendarView)
        calendarView.startAnimation(ttb)
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)

            calendarView.date = calendar.timeInMillis
        }

        eventAllTv = root.findViewById(R.id.event_all)
        eventDayTv = root.findViewById(R.id.event_day)
        eventWeekTv = root.findViewById(R.id.event_week)
        eventMonthTv = root.findViewById(R.id.event_month)
        eventCustomTv = root.findViewById(R.id.event_custom)

        setFiltersClickListeners()
        setupCollapsingTb(root)

        eventsRv = root.findViewById(R.id.recyclerViewEvents)
        eventsRv.layoutManager =
                LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        eventAdapter = AdapterEvent(listEvent, context,container, inflater)
        eventsRv.adapter = eventAdapter
        eventAdapter.notifyDataSetChanged()

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
        if (id == R.id.settings) {
            return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
        }
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
                    sharStr = context?.let { castToLangEvent(it, sharStr) }!!

                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, sharStr)
                    shareIntent.putExtra(Intent.EXTRA_TEXT, sharStr)
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
                    currentFragment.eventAdapter.notifyDataSetChanged()
                    DataStoreHandler.saveArrayListNotes()
                }
            }
            return true
        }

        if (id == R.id.add) {
            val dialogEventV = inflater.inflate(R.layout.event_dialog, container, false)
            val itemViewEvent = inflater.inflate(R.layout.item_event, container, false)
            val eventEd: EditText = dialogEventV.findViewById(R.id.event)
            timeTv = itemViewEvent.findViewById(R.id.time_event_tv)
            OnClickTime(dialogEventV)

            val mBuilder = AlertDialog.Builder(context)
                    .setView(dialogEventV)
            val mAlertDialog = mBuilder.show()

            val saveBtn: Button = dialogEventV.findViewById(R.id.save_dialog_event);

            saveBtn.setOnClickListener {
                mAlertDialog.dismiss()
                if (eventEd.text.isNotEmpty() && timeTv.text.isNotEmpty()) {
                    var newEvent = Event()


                    newEvent.event = eventEd.text.toString()
                    newEvent.date_ev = calendarView.date
                    newEvent.time = timeTv.text.toString()
                    listEvent.add(newEvent)
                } else {
                    Toast.makeText(context, "Put values!", Toast.LENGTH_LONG).show()
                }
                eventAdapter.notifyDataSetChanged()
            }

            val cancelBtn: Button = dialogEventV.findViewById(R.id.cancel_date_btn)
            cancelBtn.setOnClickListener() {
                mAlertDialog.dismiss()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun OnClickTime(dialogEventV : View) {
        val timePicker: TimePicker = dialogEventV.findViewById(R.id.timePicker)
        timePicker.setOnTimeChangedListener { _, hour, minute ->
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
            if (timeTv != null) {
                val hour = if (hour < 10) "0" + hour else hour
                val min = if (minute < 10) "0" + minute else minute
                // display format of time
                val msg = "$hour : $min $am_pm"
                timeTv.text = msg
                timeTv.visibility = ViewGroup.VISIBLE
            }
        }
    }

    private fun setFiltersClickListeners() {
        color =  eventAllTv.textColors.defaultColor

        var dateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM)

        eventAllTv.setTextColor(Color.WHITE)
        eventAllTv.setOnClickListener {
            resetHighlightFilter()

            eventAllTv.setTextColor(Color.WHITE)

            eventAdapter = AdapterEvent(listEvent, context, container, inflater)
            eventsRv.adapter = eventAdapter
            eventAdapter.notifyDataSetChanged()
        }

        eventDayTv.setOnClickListener {
            var filterCollectionByDay = ArrayList<Event>()

            for(event in listEvent) {
                if(dateFormatter.format(event.date_ev).equals(dateFormatter.format(calendarView.date))) {
                    filterCollectionByDay.add(event)
                }
            }

            resetHighlightFilter()

            eventDayTv.setTextColor(Color.WHITE)

            eventAdapter = AdapterEvent(filterCollectionByDay, context, container, inflater)
            eventsRv.adapter = eventAdapter
            eventAdapter.notifyDataSetChanged()
        }

        eventWeekTv.setOnClickListener {
            var calendar = Calendar.getInstance()
            calendar.clear()
            calendar.setTimeInMillis(calendarView.date)
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)

            var startWeek = calendar.getTimeInMillis()
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
            var endWeek = calendar.getTimeInMillis()
            var filterCollectionByWeek = ArrayList<Event>()

            for(event in listEvent) {
                if(event.date_ev >= startWeek && event.date_ev <= endWeek) {
                    filterCollectionByWeek.add(event)
                }
            }

            resetHighlightFilter()
            eventWeekTv.setTextColor(Color.WHITE)
            eventAdapter = AdapterEvent(filterCollectionByWeek, context, container, inflater)

            eventsRv.adapter = eventAdapter
            eventAdapter.notifyDataSetChanged()
        }

        eventMonthTv.setOnClickListener {
            var calendar = Calendar.getInstance()
            calendar.clear()
            calendar.setTimeInMillis(calendarView.date)
            calendar.set(Calendar.DAY_OF_MONTH, 1)

            var startMonth = calendar.getTimeInMillis()
            calendar.add(Calendar.MONTH, 1)
            var endMonth = calendar.getTimeInMillis()
            var filterCollectionByMonth = ArrayList<Event>()

            for(event in listEvent) {
                if(event.date_ev >= startMonth && event.date_ev < endMonth) {
                    filterCollectionByMonth.add(event)
                }
            }
            resetHighlightFilter()
            eventMonthTv.setTextColor(Color.WHITE)
            eventAdapter = AdapterEvent(filterCollectionByMonth, context, container, inflater)

            eventsRv.adapter = eventAdapter
            eventAdapter.notifyDataSetChanged()

        }

        eventCustomTv.setOnClickListener {
            val dialogDatePicker = inflater.inflate(R.layout.date_picker, container, false)

            val filterBtn: Button = dialogDatePicker.findViewById(R.id.button_filter)
            val calendar = Calendar.getInstance()

            val fromDate: TextView = dialogDatePicker.findViewById(R.id.from_date)
            val toDate: TextView = dialogDatePicker.findViewById(R.id.to_date)
            fromDate.setText(dateFormatter.format(calendar.timeInMillis))
            toDate.setText(dateFormatter.format(calendar.timeInMillis))

            val mBuilder = AlertDialog.Builder(context)
                    .setView(dialogDatePicker)
            val alertdlg = mBuilder.show()

            val lineOneEventRl: RelativeLayout = dialogDatePicker.findViewById(R.id.line_one_date_picker)
            val lineTwoEvent: RelativeLayout = dialogDatePicker.findViewById(R.id.line_two_time_picker)
            lineOneEventRl.setOnClickListener{
                val dialogDatePicker = inflater.inflate(R.layout.date_from, container, false)
                val mBuilder = AlertDialog.Builder(context)
                        .setView(dialogDatePicker)
                val alertDialog = mBuilder.show()

                val buttonOk = dialogDatePicker.findViewById<Button>(R.id.button_ok_from)
                buttonOk.setOnClickListener {
                    val datePicker = dialogDatePicker.findViewById<DatePicker>(R.id.date_from_date_picker)
                    val day = datePicker.dayOfMonth
                    val month = datePicker.month
                    val year = datePicker.year

                    calendar.set(year, month, day)
                    fromDate.setText(dateFormatter.format(calendar.timeInMillis))
                    alertDialog.dismiss()
                }
                val buttonCancel = dialogDatePicker.findViewById<Button>(R.id.button_cancel_from)
                buttonCancel.setOnClickListener {
                    alertDialog.dismiss()
                }


            }

            lineTwoEvent.setOnClickListener {
                val dialogDatePicker = inflater.inflate(R.layout.date_to, container, false)
                val mBuilder = AlertDialog.Builder(context)
                        .setView(dialogDatePicker)
                val alertDialog = mBuilder.show()

                val buttonOk = dialogDatePicker.findViewById<Button>(R.id.button_ok)
                buttonOk.setOnClickListener {
                    val datePicker = dialogDatePicker.findViewById<DatePicker>(R.id.date_to_date_picker)
                    val day = datePicker.dayOfMonth
                    val month = datePicker.month
                    val year = datePicker.year

                    calendar.set(year, month, day)
                    toDate.setText(dateFormatter.format(calendar.timeInMillis))
                    alertDialog.dismiss()
                }
                val buttonCancel = dialogDatePicker.findViewById<Button>(R.id.button_cancel)
                buttonCancel.setOnClickListener {
                    alertDialog.dismiss()
                }
            }

            filterBtn.setOnClickListener {
                var startDate = dateFormatter.parse(fromDate.text.toString()).time
                var endDate = dateFormatter.parse(toDate.text.toString()).time
                var filterCollectionByCustom = ArrayList<Event>()

                for(event in listEvent) {
                    if(event.date_ev >= startDate && event.date_ev <= endDate) {
                        filterCollectionByCustom.add(event)
                    }
                }
                resetHighlightFilter()
                eventCustomTv.setTextColor(Color.WHITE)
                eventAdapter = AdapterEvent(filterCollectionByCustom, context, container, inflater)

                eventsRv.adapter = eventAdapter
                eventAdapter.notifyDataSetChanged()
                alertdlg.dismiss()
            }

            val dialogCancelBtn: Button = dialogDatePicker.findViewById(R.id.cancel_date_btn)
            dialogCancelBtn.setOnClickListener {
                alertdlg.dismiss()
            }
        }
    }

    fun resetHighlightFilter() {
        eventAllTv.setTextColor(color)
        eventDayTv.setTextColor(color)
        eventWeekTv.setTextColor(color)
        eventCustomTv.setTextColor(color)
        eventMonthTv.setTextColor(color)

    }

    fun setupCollapsingTb(view : View) {
        val collapsingToolbar: CollapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar)
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.collapsedappbar)
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.expandedappbar)
    }
}


