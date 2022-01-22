package com.nnnd.smartnote.ui.draws

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.nnnd.smartnote.DataStoreHandler
import com.nnnd.smartnote.R

//TODO: should be called by another drawFragment (from menu)
class DrawFragment : Fragment() {
    lateinit var drawView: DrawView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.paint, container, false)
        drawView = rootView.findViewById(R.id.drawView)
        val metrics = DisplayMetrics()
            activity?.getWindowManager()?.getDefaultDisplay()?.getMetrics(metrics)
//        if(position != null) {
////            drawView.init(metrics, DataStoreHandler.draws.get(position))
//        } else {
////        drawView.init(metrics, Paint())
//        }
        drawView.init(metrics, Paint())
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
//        if (item.itemId == R.id.color) {
            //TODO: show allert dialog with colors. after click ok. should be used setted color.
            //add listener (will it be drop list? or what) and depend on chouse, call
//            drawView.setColor(Color.GREEN)
//            return true
//        }
//        if (item.itemId == R.id.rubber) {
//          drawView.setColor(drawView.getBGColor())
//        drawView.setBrashSize(100)
//        }


        if (item.itemId == R.id.share) {

        }

        return super.onOptionsItemSelected(item)
    }
}