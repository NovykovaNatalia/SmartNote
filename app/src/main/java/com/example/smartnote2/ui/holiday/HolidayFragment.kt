package com.example.smartnote2.ui.holiday

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import android.widget.SearchView.OnQueryTextListener
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.smartnote2.R

class HolidayFragment : Fragment() {
    lateinit var textViewHoliday: TextView
    lateinit var listView: ListView
    lateinit var list: ArrayList<String>
    lateinit var adapter: ArrayAdapter<*>
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_holiday, container, false)
        textViewHoliday = root.findViewById(R.id.textViewHoiday)

        return root
    }

}