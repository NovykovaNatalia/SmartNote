package com.nnnd.smartnote.ui.draws

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.nnnd.smartnote.DataStoreHandler
import com.nnnd.smartnote.R
import kotlinx.android.synthetic.main.item_draw.*
import kotlinx.android.synthetic.main.item_shopping.*
import kotlinx.android.synthetic.main.second_activity.*
import java.text.SimpleDateFormat
import java.util.*

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
            val dialogView = layoutInflater.inflate(R.layout.draw_dialog, null);
            val builder = AlertDialog.Builder(this)
                .setView(dialogView)
                .setTitle("")
            val ad = builder.show()

            val title: EditText = dialogView.findViewById(R.id.title_draw)
            val cancelBtn: TextView = dialogView.findViewById(R.id.cancel_dialog_draw)
            val saveBtn: TextView = dialogView.findViewById(R.id.save_dialog_draw)

            cancelBtn.setOnClickListener {
                ad.dismiss()
            }
            saveBtn.setOnClickListener {
                ad.dismiss()
                var paintItem : PaintItem = PaintItem()
                paintItem.title = title.text.toString()
                paintItem.paths = drawView.paths
                paintItem.date = Date().time
                DataStoreHandler.draws.add(paintItem)
                finish()
            }
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