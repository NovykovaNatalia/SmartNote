import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartnote.LanguageSupportUtils.Companion.castToLangBank
import com.example.smartnote.R
import com.example.smartnote.ui.bankaccount.Card

class CardAdapter(private var items: ArrayList<Card>):
    RecyclerView.Adapter<CardAdapter.ViewHolder>() {
    lateinit var context: Context

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
            salaryAcountTV = view.findViewById(R.id.salary_account_tv)
            bankNameTV = view.findViewById(R.id.bank_name_tv)
            numberTV = view.findViewById(R.id.number_tv)
            personNameSurnameTV = view.findViewById(R.id.person_name_surname_tv)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bank_account, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CardAdapter.ViewHolder, position: Int) {
        holder.run {
            salaryAcountTV.setText(items[position].account)
            numberTV.setText(items[position].accountNumber)
            bankNameTV.setText(items[position].bankName)
            personNameSurnameTV.setText(items[position].nameSurname)

            itemView.setOnClickListener {
                val dialogView = LayoutInflater.from( context).inflate(R.layout.delete_share_layout, null);
                val builder = AlertDialog.Builder(context)
                    .setView(dialogView)
                    .setTitle(context.getString(R.string.delete_the_item))
                val alertDialog = builder.show()
                val imageShare : ImageView = dialogView.findViewById(R.id.shareIv)
                val noBtn : TextView = dialogView.findViewById(R.id.noBtn)
                val yesBtn : TextView = dialogView.findViewById(R.id.yesBtn)

                imageShare.setOnClickListener {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    val shareBody = castToLangBank(context, items[position].toString())
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody)
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                    context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.choose_one)))
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
        }
    }
}