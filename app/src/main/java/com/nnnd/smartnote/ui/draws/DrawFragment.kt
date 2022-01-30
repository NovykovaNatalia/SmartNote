package com.nnnd.smartnote.ui.draws

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.nnnd.smartnote.DataStoreHandler
import com.nnnd.smartnote.LanguageSupportUtils
import com.nnnd.smartnote.R
import java.util.*

class DrawFragment : Fragment() {
    lateinit var titleEt : EditText
    lateinit var recyclerView: RecyclerView
    lateinit var draws:ArrayList<PaintItem>
    lateinit var drawAdapter: DrawAdapter
    private val  REQUEST_CODE_SPEECH = 100

    @SuppressLint("WrongConstant")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        draws = DataStoreHandler.draws
        val view = inflater.inflate(R.layout.fragment_draw, container, false)
        val ttb = AnimationUtils.loadAnimation(context, R.anim.ttb)
        recyclerView = view.findViewById(R.id.recyclerViewDraw)
        recyclerView.startAnimation(ttb)

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        drawAdapter = DrawAdapter(draws, context)
        recyclerView.adapter = drawAdapter
        drawAdapter.notifyDataSetChanged()

        val sv : SearchView = view.findViewById(R.id.searchViewDraw)
        sv.startAnimation(ttb)
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                var filteredList: ArrayList<PaintItem> = filter(draws, newText)
                recyclerView.adapter = DrawAdapter(filteredList, context)
                (recyclerView.adapter as DrawAdapter).notifyDataSetChanged()
                return true
            }

        })

        val fab : FloatingActionButton = view.findViewById(R.id.floating_btn_draw_fragment)
        fab.startAnimation(ttb)

        fab.setOnClickListener{
            val intent = Intent(activity, DrawActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        menu.findItem(R.id.normal).setVisible(false)
        menu.findItem(R.id.clear).setVisible(false)
        menu.findItem(R.id.rubber).setVisible(false)
        menu.findItem(R.id.delete_checked_list).setVisible(false)
        menu.findItem(R.id.add).setVisible(false)
        menu.findItem(R.id.save_end_store).setVisible(false)
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
                is DrawFragment -> {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    var sharStr = DataStoreHandler.draws.toString()
                    sharStr = sharStr.replace('[', ' ')
                    sharStr = sharStr.replace(']', ' ')
                    sharStr = sharStr.replace(",", "")
                    sharStr = context?.let { LanguageSupportUtils.castToLangDraws(it, sharStr) }!!
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
                is DrawFragment -> {
                    val dialogView = layoutInflater.inflate(R.layout.delete_list_layout, null);
                    val builder = AlertDialog.Builder(context)
                            .setView(dialogView)
                            .setTitle(context?.getString(R.string.delete_the_list))
                    val deleteListAd = builder.show()

                    val noBtn: TextView = dialogView.findViewById(R.id.noBtn)
                    val yesBtn: TextView = dialogView.findViewById(R.id.yesBtn)
                    noBtn.setOnClickListener {
                        deleteListAd.dismiss()
                    }
                    yesBtn.setOnClickListener {
                        if (!DataStoreHandler.notes.isEmpty()) {
                            DataStoreHandler.notes.removeAll(DataStoreHandler.notes)
                            currentFragment.drawAdapter.notifyDataSetChanged()
                            DataStoreHandler.saveArrayListDraws()
                            deleteListAd.dismiss()
                        } else {
                            Toast.makeText(context, getString(R.string.list_is_empty), Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun filter(draws: ArrayList<PaintItem>, query: String): ArrayList<PaintItem> {
        var resultList:ArrayList<PaintItem> = ArrayList()

        for (draw in draws) {
            if(draw.title.contains(query.toLowerCase())) {
                resultList.add(draw)
            }
        }
        return resultList
    }
}


