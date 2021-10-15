package com.example.smartnote.ui.holiday

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
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
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_holiday.*
import java.text.DateFormat
import java.util.*


class HolidayFragment : Fragment() {
//    lateinit var floating_btn_event: FloatingActionButton
    lateinit var calendarView: CalendarView
    lateinit var recyclerViwHoliday: RecyclerView
    lateinit var cardListHoliday: ArrayList<Holiday>
    lateinit var customAdapterHoliday: CustomAdapterHoliday
    lateinit var event: EditText
    lateinit var date_tv: TextView
    lateinit var event_tv: TextView
    lateinit var saveActionButtonHoliday: Button
    lateinit var cancelActionButtonHoliday: Button
    lateinit var collapsing_toolbar: CollapsingToolbarLayout
    lateinit var date_ev: String
    lateinit var add_btn: ImageView

    @SuppressLint("WrongConstant")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        cardListHoliday = DataStoreHandler.holiday

        val root = inflater.inflate(R.layout.fragment_holiday, container, false)

        val ttb = AnimationUtils.loadAnimation(context, R.anim.ttb)
        calendarView = root.findViewById(R.id.calendarView)


        add_btn  = activity?.findViewById(R.id.add_btn) ?: ImageView(context)
        recyclerViwHoliday = root.findViewById(R.id.recyclerViewHoliday)
        recyclerViwHoliday.layoutManager =
                LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        customAdapterHoliday = CustomAdapterHoliday(cardListHoliday, context)

        recyclerViwHoliday.adapter = customAdapterHoliday
        customAdapterHoliday.notifyDataSetChanged()

        add_btn.setOnClickListener(View.OnClickListener {
            add_btn.startAnimation(ttb)
            Log.e("natl", "asasas")
            val mDialogViewHoliday = inflater.inflate(R.layout.holiday_dialog, container, false)
            val mItemViewHoliday = inflater.inflate(R.layout.item_holiday, container, false)
            event = mDialogViewHoliday.findViewById(R.id.event)

            date_tv = mItemViewHoliday.findViewById(R.id.date_holiday_tv)
            event_tv = mItemViewHoliday.findViewById(R.id.event_holiday_tv)

            val mBuilder = AlertDialog.Builder(context)
                    .setView(mDialogViewHoliday)
                    .setTitle("Holiday")
            val mAlertDialog = mBuilder.show()

            cancelActionButtonHoliday = mDialogViewHoliday.findViewById(R.id.cancel_dialog_holiday)
            saveActionButtonHoliday = mDialogViewHoliday.findViewById<Button>(R.id.save_dialog_holiday);

            saveActionButtonHoliday.setOnClickListener {
                mAlertDialog.dismiss()
                if (event.text.isNotEmpty()) {
                    var cardHoliday = Holiday()

                    val calendar = Calendar.getInstance()
                    val dateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM)
                    calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
                        calendar.set(year, month, dayOfMonth)

                        calendarView.date = calendar.timeInMillis
                    }
                    date_ev = dateFormatter.format(calendarView.date)

                    cardHoliday.event = event.text.toString()
                    cardHoliday.date_ev = date_ev
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

        collapsing_toolbar = root.findViewById(R.id.collapsing_toolbar)
        collapsing_toolbar.setTitle(getResources().getString(R.string.event_text));
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


