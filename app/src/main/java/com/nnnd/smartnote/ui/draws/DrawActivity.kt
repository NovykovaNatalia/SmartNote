package com.nnnd.smartnote.ui.draws

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.nnnd.smartnote.R
import kotlinx.android.synthetic.main.second_activity.*

class DrawActivity : AppCompatActivity() {
    lateinit var pen: MenuItem
    lateinit var rubber: MenuItem



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        var drawView : DrawView = findViewById(R.id.drawView)
        val metrics = DisplayMetrics()
        this?.getWindowManager()?.getDefaultDisplay()?.getMetrics(metrics)
//        if(position != null) {
////            drawView.init(metrics, DataStoreHandler.draws.get(position))
//        } else {
////        drawView.init(metrics, Paint())
//        }
        drawView.init(metrics, Paint())

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        pen = menu.findItem(R.id.normal)
        rubber = menu.findItem(R.id.rubber)
        menu.findItem(R.id.delete_checked_list).setVisible(false)
        menu.findItem(R.id.settings).setVisible(false)
        menu.findItem(R.id.share_image).setVisible(false)
        menu.findItem(R.id.share).setVisible(false)
        menu.findItem(R.id.delete).setVisible(false)
        menu.findItem(R.id.add).setVisible(false)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.normal) {
            drawView.normal()
            item.setIcon(R.drawable.pen_black)
            rubber.setIcon(R.drawable.rubber_white)
            return true
        }
        if (item.itemId == R.id.clear) {
            drawView.clear()
            return true
        }
        //Todo implement store images to recycler view
        if (item.itemId == R.id.save_end_store) {
            return true
        }

        if (item.itemId == R.id.rubber) {
            item.setIcon(R.drawable.rubber_black)
            pen.setIcon(R.drawable.pen_white)

            drawView.setColor(drawView.getBGColor(Color.WHITE))
            drawView.setBrushSize(120)

            return true
        }

//        if (item.itemId == R.id.color) {
        //TODO: show allert dialog with colors. after click ok. should be used setted color.
        //add listener (will it be drop list? or what) and depend on chouse, call
//            drawView.setColor(Color.GREEN)
//            return true
//        }

        if (item.itemId == R.id.share) {

        }

        return super.onOptionsItemSelected(item)
    }
}