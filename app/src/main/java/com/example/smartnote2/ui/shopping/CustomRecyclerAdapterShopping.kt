import android.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.smartnote2.R
import com.example.smartnote2.ui.shopping.ShoppingItem

class CustomAdapterShopping(private val items: ArrayList<ShoppingItem> ):
    RecyclerView.Adapter<CustomAdapterShopping.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val goods_tv: TextView
        val quantity_et: EditText
        val delete_btn: ImageButton
        init {
            // Define click listener for the ViewHolder's View.
            goods_tv = view.findViewById(R.id.good_tv)
            quantity_et = view.findViewById(R.id.quantityEt)
            delete_btn = view.findViewById(R.id.deleteBtn)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapterShopping.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_shopping, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CustomAdapterShopping.ViewHolder, position: Int) {
        holder.run {
            goods_tv.setText(items[position].itemname)
            quantity_et.setText(items[position].quantity.toString())

            quantity_et.addTextChangedListener(object : TextWatcher{
                override fun afterTextChanged(p0: Editable?) {
                    if(!quantity_et.text.toString().equals("")){
                        items[position].quantity = quantity_et.text.toString().toInt()
                    }
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })

            delete_btn.setOnClickListener {
                val builder = AlertDialog.Builder(holder.goods_tv.context)

                builder.setMessage("Delete the god?")

                builder.setPositiveButton("YES") { dialog, which ->
                    items.remove(items[position])
                    notifyDataSetChanged()

                }
                builder.setNegativeButton("No") { dialog, which ->

                }
                val dialog: AlertDialog = builder.create()

                dialog.show()
                notifyDataSetChanged()
            }


            }
        }

    }
