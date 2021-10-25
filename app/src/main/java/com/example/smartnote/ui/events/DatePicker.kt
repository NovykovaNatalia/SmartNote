package com.example.smartnote.ui.events

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.smartnote.R
import java.text.SimpleDateFormat
import java.util.*

class DatePicker : AppCompatActivity() {
    private var dateFormatter: SimpleDateFormat? = null
    lateinit var sendButton: Button
    lateinit var toDate:TextView
    lateinit var fromDate: TextView
//    lateinit var image_cal_from: ImageView
//    lateinit var image_cal_to: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.date_picker)

        sendButton = findViewById(R.id.button_filter)
        fromDate = findViewById(R.id.from_date)
        toDate = findViewById(R.id.to_date)
//        image_cal_from = findViewById(R.id.image_calendar)
//        image_cal_from = findViewById(R.id.image_date)

        val calendar = Calendar.getInstance(Locale.getDefault())
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        fromDate.setOnClickListener {

                val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    // Display Selected date in TextView
                    fromDate.setText("" + dayOfMonth + " " + month + ", " + year)
                }, year, month, day)
                dpd.show()
        }

        toDate.setOnClickListener {

                val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    // Display Selected date in TextView
                    toDate.setText("" + dayOfMonth + " " + month + ", " + year)
                }, year, month, day)
                dpd.show()

         }


    }
}