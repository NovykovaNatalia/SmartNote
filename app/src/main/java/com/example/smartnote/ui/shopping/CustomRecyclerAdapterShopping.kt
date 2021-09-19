import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.NumberPicker
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.smartnote.BuildConfig
import com.example.smartnote.MyApplication
import com.example.smartnote.R
import com.example.smartnote.ui.bank_account.Card
import com.example.smartnote.ui.shopping.ShoppingItem
import org.w3c.dom.Text


class CustomAdapterShopping(private val items: ArrayList<ShoppingItem>) :
    RecyclerView.Adapter<CustomAdapterShopping.ViewHolder>() {

    lateinit var context: Context;

    constructor(items: ArrayList<ShoppingItem>, context: Context?): this(items) {

        if (context != null ) {
            this.context = context
        }
    }

    class ViewHolder(view: View, ctx:Context) : RecyclerView.ViewHolder(view), NumberPicker.OnValueChangeListener {
        val goods_tv: TextView
        val quantity_np: NumberPicker
        val  quantity_tv: TextView
        lateinit var context:Context


        init {
            // Define click listener for the ViewHolder's View.
            goods_tv = view.findViewById(R.id.good_tv)
            quantity_tv = view.findViewById(R.id.quantity_tv)
            quantity_np = view.findViewById(R.id.quantityNumberPicker)
            quantity_np.minValue = 1
            quantity_np.maxValue = 23
            quantity_np.setOnValueChangedListener(this)
            context= ctx;

        }
        fun onCreateDialogQuantityPicker() {
            quantity_tv.setOnClickListener{
                val mDialogViewDatePicker = LayoutInflater.from(context).inflate(R.layout.shopping_dialog, null);
                val mItemViewShoppingItem = LayoutInflater.from(context).inflate(R.layout.item_shopping, null )

                val mBuilder = AlertDialog.Builder(context)
                        .setView(mDialogViewDatePicker)
                        .setTitle("Set quantity")
                val mAlertDialog = mBuilder.show()

            }
        }

        override fun onValueChange(p0: NumberPicker?, p1: Int, p2: Int) {
            quantity_tv.setText("" + p2)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomAdapterShopping.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_shopping, parent, false)
        return ViewHolder(view, context)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CustomAdapterShopping.ViewHolder, position: Int) {
        holder.run {
            goods_tv.setText(items[position].itemname)
            quantity_np.value = items[position].quantity
            quantity_tv.setText(items[position].quantity.toString())
            quantity_np.setOnValueChangedListener{ picker, oldVal, newVal ->
                quantity_tv.setText(newVal.toString())
                items[position].quantity = newVal
            }

            itemView.setOnClickListener {
                val builder = AlertDialog.Builder(holder.goods_tv.context)
                builder.setMessage("Delete the god?")
                Log.e("natlight", "Delete option")

                builder.setPositiveButton("YES") { dialog, which ->
                    items.remove(items[position])
                    notifyDataSetChanged()
                    Log.e("natlight", "Deleted option")
                }
                builder.setNegativeButton("No") { dialog, which ->

                }
                builder.setNeutralButton("Share") { dialog, which ->
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    val shareBody= items.toString()
                    val shareSub = "items[position]"
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody)
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                    context.startActivity(Intent.createChooser(shareIntent, "choose one"))
                    true
                }
                val dialog: AlertDialog = builder.create()

                dialog.show()
                notifyDataSetChanged()
            }

        }

    }

}

private fun NumberPicker.addOnLayoutChangeListener(textWatcher: TextWatcher) {

}

private fun NumberPicker.setOnValueChangedListener(toString: String) {

}


