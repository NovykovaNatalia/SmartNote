import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.nnnd.smartnote.LanguageSupportUtils
import com.nnnd.smartnote.R
import com.nnnd.smartnote.ui.shopping.ShoppingItem
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList


class ShoppingAdapter(private val items: ArrayList<ShoppingItem>) :
    RecyclerView.Adapter<ShoppingAdapter.ViewHolder>() {

    lateinit var context: Context;

    constructor(items: ArrayList<ShoppingItem>, context: Context?): this(items) {

        if (context != null ) {
            this.context = context
        }
    }

    class ViewHolder(view: View, ctx:Context) : RecyclerView.ViewHolder(view), NumberPicker.OnValueChangeListener {
        val goods_tv: TextView
        val  quantity_tv: TextView
        val  parentll: LinearLayout
        val ll2: LinearLayout
        val  ll: LinearLayout
        val isFilled_cb: CheckBox
        val date: TextView

        var context:Context


        init {
            goods_tv = view.findViewById(R.id.good_tv)
            quantity_tv = view.findViewById(R.id.quantity_tv)
            parentll = view.findViewById(R.id.parent_linear_layout)
            ll = view.findViewById(R.id.line_three)
            ll2 = view.findViewById(R.id.line_two)
            isFilled_cb = view.findViewById(R.id.isFilled)
            date = view.findViewById(R.id.date)

            context= ctx
        }

        override fun onValueChange(p0: NumberPicker?, p1: Int, p2: Int) {
            quantity_tv.setText(p2.toString())
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShoppingAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_shopping, parent, false)
        return ViewHolder(view, context)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ShoppingAdapter.ViewHolder, position: Int) {
        holder.run {
            goods_tv.setText(items[position].itemname)
            quantity_tv.setText(items[position].quantity.toString())
            isFilled_cb.isChecked = items[position].isFilled
            if(items[position].isFilled) {
                parentll.setBackgroundColor(0x55C1E1C1)
            } else {
                parentll.setBackgroundColor(Color.TRANSPARENT)
            }

            setFormatDate(date, position)

            ll2.setOnClickListener{
                val dialogViewShopping = LayoutInflater.from(context).inflate(R.layout.shopping_dialog, null)
                val builder = AlertDialog.Builder(context)
                        .setView(dialogViewShopping)
                        .setTitle(R.string.shoppings)
                val alertDialog = builder.show()

                val quantity_adnp = dialogViewShopping.findViewById<NumberPicker>(R.id.quantityNumberPickerDialog)
                quantity_adnp.minValue = 1
                quantity_adnp.maxValue = 50

                val cancelActionButtonShopping = dialogViewShopping.findViewById<Button>(R.id.cancel_dialog_shopping)
                val saveActionButtonShopping = dialogViewShopping.findViewById<Button>(R.id.save_dialog_shopping)

                saveActionButtonShopping.setOnClickListener{

                    if (quantity_adnp.value.toString().isNotEmpty()) {
                        quantity_tv.setText(quantity_adnp.value.toString())
                        items[position].quantity = quantity_adnp.value
                    } else {
                        Toast.makeText(context, context.getString(R.string.put_values), Toast.LENGTH_LONG).show()
                    }
                    alertDialog.dismiss()
                }
                cancelActionButtonShopping.setOnClickListener{
                    alertDialog.dismiss()
                }

            }
            ll.setOnClickListener {
                val dialogView = LayoutInflater.from( context).inflate(R.layout.delete_share_layout, null);
                val builder = AlertDialog.Builder(context)
                    .setView(dialogView)
                    .setTitle(context.getString(R.string.delete_the_item))
                val alertDialog = builder.show()

                val imageShare : ImageView = dialogView.findViewById(R.id.shareIv)
                val imageEdit : ImageView = dialogView.findViewById(R.id.editIv)
                val noBtn : TextView = dialogView.findViewById(R.id.noBtn)
                val yesBtn : TextView = dialogView.findViewById(R.id.yesBtn)

                imageShare.setOnClickListener {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    val shareBody =
                        LanguageSupportUtils.castToLangBank(context, items[position].toString())
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody)
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                    context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.choose_one)))
                }

                imageEdit.setOnClickListener {

                    val dialogViewGoodsShopping = LayoutInflater.from(context).inflate(R.layout.shopping_goods_dialog, null)
                    val builder = AlertDialog.Builder(context)
                            .setView(dialogViewGoodsShopping)
                            .setTitle(context.getString(R.string.shoppings))
                    val ad = builder.show()

                    val goodsEt = dialogViewGoodsShopping.findViewById<EditText>(R.id.goods_et)
                    goodsEt.setText(items[position].itemname)
                    val cancelActionButtonShopping = dialogViewGoodsShopping.findViewById<Button>(R.id.cancel_dialog_shopping)
                    val saveActionButtonShopping = dialogViewGoodsShopping.findViewById<Button>(R.id.save_dialog_shopping)

                    saveActionButtonShopping.setOnClickListener{
                        ad.dismiss()
                        if (goodsEt.text.toString().isNotEmpty()) {
                            goods_tv.setText(goodsEt.text.toString())
                            items[position].itemname = goodsEt.text.toString()
                        } else {
                            Toast.makeText(context, context.getString(R.string.put_values), Toast.LENGTH_LONG).show()
                        }
                        alertDialog.dismiss()
                    }
                    cancelActionButtonShopping.setOnClickListener{
                        ad.dismiss()
                    }
                    yesBtn.setOnClickListener {
                        items.remove(items[position])
                        notifyDataSetChanged()
                        alertDialog.dismiss()
                    }
                }

                noBtn.setOnClickListener{
                    alertDialog.dismiss()
                }
                yesBtn.setOnClickListener {
                    items.remove(items[position])
                    notifyDataSetChanged()
                    alertDialog.dismiss()
                }
            }
            isFilled_cb.setOnCheckedChangeListener { compoundButton: CompoundButton, b: Boolean ->
                items[position].isFilled = b
                if(b) {
                    parentll.setBackgroundColor(0x55C1E1C1)
                } else {
                    parentll.setBackgroundColor(Color.TRANSPARENT)
                }
            }
        }
    }

    fun setFormatDate(date: TextView, position: Int) {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(items[position].date)
        date.setText(currentDate)
    }

}
