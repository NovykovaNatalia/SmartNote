import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartnote.R
import com.example.smartnote.ui.bank_account.Card
import org.w3c.dom.Text

class CustomAdapter(private var items: ArrayList<Card>):
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    lateinit var context: Context;

    constructor(items: ArrayList<Card>, context: Context?): this(items) {

        if (context != null ) {
            this.context = context
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val salaryAcountTV: TextView
        val bankNameTV: TextView
        val numberTV: TextView
        val personNameSurnameTV: TextView
        init {
            // Define click listener for the ViewHolder's View.
            salaryAcountTV = view.findViewById(R.id.salary_account_tv)
            bankNameTV = view.findViewById(R.id.bank_name_tv)
            numberTV = view.findViewById(R.id.number_tv)
            personNameSurnameTV = view.findViewById(R.id.person_name_surname_tv)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bank_account, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CustomAdapter.ViewHolder, position: Int) {
        holder.run {
            salaryAcountTV.setText(items[position].account)
            numberTV.setText(items[position].accountNumber)
            bankNameTV.setText(items[position].bankName)
            personNameSurnameTV.setText(items[position].nameSurname)

            itemView.setOnClickListener {
                val builder = AlertDialog.Builder(holder.bankNameTV.context)

                builder.setMessage("Delete the god?")

                builder.setPositiveButton("YES") { dialog, which ->
                    items.remove(items[position])
                    notifyDataSetChanged()

                }
                builder.setNegativeButton("No") { dialog, which ->
                }

                builder.setNeutralButton("Share") { dialog, which ->
                        val shareIntent = Intent(Intent.ACTION_SEND)
                        shareIntent.type = "text/plain"
                        val shareBody =  items[position].toString()
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