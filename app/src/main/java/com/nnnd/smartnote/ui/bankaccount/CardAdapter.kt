import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.nnnd.smartnote.LanguageSupportUtils.Companion.castToLangBank
import com.nnnd.smartnote.R
import com.nnnd.smartnote.ui.bankaccount.Card

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
        val dot3Image:ImageView
        val ll: LinearLayout
        init {
            salaryAcountTV = view.findViewById(R.id.salary_account_tv)
            bankNameTV = view.findViewById(R.id.bank_name_tv)
            numberTV = view.findViewById(R.id.number_tv)
            personNameSurnameTV = view.findViewById(R.id.person_name_surname_tv)
            dot3Image = view.findViewById(R.id.discount_3dot_image)
            ll = view.findViewById(R.id.line_three)
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
                    val shareBody = castToLangBank(context, items[position].toString())
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody)
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                    context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.choose_one)))
                }

                imageEdit.setOnClickListener {
                    val dialogView = LayoutInflater.from(context).inflate(R.layout.bank_account_dialog, null);
                    val builder = AlertDialog.Builder(context)
                            .setView(dialogView)
                            .setTitle(context.getString(R.string.bank_account))
                    val ad = builder.show()

                    val salaryAccountEt : EditText = dialogView.findViewById(R.id.salary_account)
                    salaryAccountEt.setText(items[position].account)
                    val bankNameEt : EditText = dialogView.findViewById(R.id.bank_name)
                    bankNameEt.setText(items[position].bankName)
                    val numberEt : EditText = dialogView.findViewById(R.id.number)
                    numberEt.setText(items[position].accountNumber)
                    val nameSurnameEt : EditText = dialogView.findViewById(R.id.person_name_surname)
                    nameSurnameEt.setText(items[position].nameSurname)

                    val saveBtn : Button = dialogView.findViewById(R.id.save_dialog_bank)
                    saveBtn.setOnClickListener {
                        if(salaryAccountEt.text.isNotEmpty()
                                && bankNameEt.text.isNotEmpty()
                                && numberEt.text.isNotEmpty()
                                && nameSurnameEt.text.isNotEmpty()) {
                            ad.dismiss()
                            items[position].account = salaryAccountEt.text.toString()
                            items[position].bankName = bankNameEt.text.toString()
                            items[position].accountNumber = numberEt.text.toString()
                            items[position].nameSurname = nameSurnameEt.text.toString()
                        } else {
                            Toast.makeText(context, context.getString(R.string.put_values), Toast.LENGTH_LONG).show()
                        }
                        notifyDataSetChanged()
                    }
                    alertDialog.dismiss()
                    val cancelBtn: Button = dialogView.findViewById(R.id.cancel_dialog_bank)
                    cancelBtn.setOnClickListener() {
                        ad.dismiss()
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
        }
    }
}