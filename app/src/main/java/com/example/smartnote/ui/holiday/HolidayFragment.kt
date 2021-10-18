package com.example.smartnote.ui.holiday

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartnote.DataStoreHandler
import com.example.smartnote.R
import com.example.smartnote.ui.bank_account.BankAccountFragment
import com.example.smartnote.ui.credentials.CredentialsFragment
import com.example.smartnote.ui.sales.SalesFragment
import com.example.smartnote.ui.settings.SettingsFragment
import com.example.smartnote.ui.shopping.ShoppingFragment
import com.example.smartnote.ui.text_note.TextNoteFragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import java.text.DateFormat
import java.util.*


class HolidayFragment : Fragment() {
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

    private var handlerAnimation = Handler()
    private var statusAnimation = false
    lateinit var imgAnimation: ImageView
    lateinit var imgAnimation1: ImageView
    lateinit var inflater:LayoutInflater
    lateinit var container: ViewGroup

    @SuppressLint("WrongConstant")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        cardListHoliday = DataStoreHandler.holidays
        this.inflater = inflater
        if (container != null) {
            this.container = container
        }

        val root = inflater.inflate(R.layout.fragment_holiday, container, false)

        val ttb = AnimationUtils.loadAnimation(context, R.anim.ttb)
        calendarView = root.findViewById(R.id.calendarView)
        collapsing_toolbar = root.findViewById(R.id.collapsing_toolbar)
        collapsing_toolbar.setTitle(getResources().getString(R.string.event_text));
        toolbarTextAppernce()

        recyclerViwHoliday = root.findViewById(R.id.recyclerViewHoliday)
        recyclerViwHoliday.layoutManager =
                LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        customAdapterHoliday = CustomAdapterHoliday(cardListHoliday, context)

        recyclerViwHoliday.adapter = customAdapterHoliday
        customAdapterHoliday.notifyDataSetChanged()
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
            val fragmentManager: FragmentManager = activity?.supportFragmentManager!!
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            val setFragment = SettingsFragment()
            fragmentTransaction.replace(R.id.settings, setFragment)
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            return true
        }
        if (id == R.id.share) {
            val currentFragment = activity?.supportFragmentManager!!.fragments.first().childFragmentManager.fragments.first()
            when (currentFragment) {
                is HolidayFragment -> {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    var sharStr = DataStoreHandler.holidays.toString()
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
                is HolidayFragment -> {
                    DataStoreHandler.holidays.removeAll(DataStoreHandler.holidays)
                    currentFragment.customAdapterHoliday.notifyDataSetChanged()
                    DataStoreHandler.saveArrayListNotes()
                }
            }
            return true
        }

        if (id == R.id.add) {
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


        }

        return super.onOptionsItemSelected(item)
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


