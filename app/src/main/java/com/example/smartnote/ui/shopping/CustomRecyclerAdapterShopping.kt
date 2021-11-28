import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.smartnote.R
import com.example.smartnote.ui.shopping.ShoppingItem


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
        val  quantity_tv: TextView
        lateinit var context:Context


        init {
            goods_tv = view.findViewById(R.id.good_tv)
            quantity_tv = view.findViewById(R.id.quantity_tv)

            context= ctx;

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
            quantity_tv.setText(items[position].quantity.toString())

            quantity_tv.setOnClickListener{
                val mDialogViewShopping = LayoutInflater.from(context).inflate(R.layout.shopping_dialog, null)
                val mBuilder = AlertDialog.Builder(context)
                        .setView(mDialogViewShopping)
                        .setTitle("Shopping")
                val mAlertDialog = mBuilder.show()

                val quantity_adnp = mDialogViewShopping.findViewById<NumberPicker>(R.id.quantityNumberPickerDialog)
                quantity_adnp.minValue = 1
                quantity_adnp.maxValue = 23

                val cancelActionButtonShopping = mDialogViewShopping.findViewById<Button>(R.id.cancel_dialog_shopping)
                val saveActionButtonShopping = mDialogViewShopping.findViewById<Button>(R.id.save_dialog_shopping)

                saveActionButtonShopping.setOnClickListener{

                    if (quantity_adnp.value.toString().isNotEmpty()) {
                        quantity_tv.setText(quantity_adnp.value.toString())
                        items[position].quantity = quantity_adnp.value
                    } else {
                        Toast.makeText(context, context.getString(R.string.put_value), Toast.LENGTH_LONG).show()
                    }
                    mAlertDialog.dismiss()
                }
                cancelActionButtonShopping.setOnClickListener{
                    mAlertDialog.dismiss()
                }

            }

            itemView.setOnClickListener {
                val builder = AlertDialog.Builder(holder.goods_tv.context)
                builder.setMessage(context.getString(R.string.delete_the_goods))
                builder.setPositiveButton(R.string.yes) { dialog, which ->
                    items.remove(items[position])
                    notifyDataSetChanged()
                }
                builder.setNegativeButton(R.string.no) { dialog, which ->

                }
                builder.setNeutralButton(R.string.share) { dialog, which ->
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    val shareBody= items[position].toString()
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


