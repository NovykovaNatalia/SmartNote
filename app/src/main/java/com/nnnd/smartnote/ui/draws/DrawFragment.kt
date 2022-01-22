package com.nnnd.smartnote.ui.draws

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.nnnd.smartnote.R
import java.io.File
import java.io.FileOutputStream
import java.util.*


class DrawFragment : Fragment() {
    lateinit var drawView: DrawView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.paint, container, false)
        drawView = rootView.findViewById(R.id.paintView)
        val metrics = DisplayMetrics()
            activity?.getWindowManager()?.getDefaultDisplay()?.getMetrics(metrics)
        drawView.init(metrics)
        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        menu.findItem(R.id.delete_checked_list).setVisible(false)
        menu.findItem(R.id.delete).setVisible(false)
        menu.findItem(R.id.share).setVisible(false)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (id == R.id.settings) {
            return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
        }
        if (item.itemId == R.id.normal) {
            drawView.normal()
            return true
        }
        if(item.itemId == R.id.emboss){
            drawView.emboss()
            return true
        }
        if (item.itemId == R.id.blur) {
            drawView.blur()
            return true
        }
        if (item.itemId == R.id.clear) {
            drawView.clear()
            return true
        }
        if (item.itemId == R.id.share) {

        }


        return super.onOptionsItemSelected(item)
    }
}