import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartnote2.R
import com.example.smartnote2.ui.bank_account.Card
import org.w3c.dom.Text

class CustomAdapter(private val items: List<Card> ):
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

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
        holder.salaryAcountTV.setText(items[position].account)
        holder.numberTV.setText(items[position].accountNumber)
        holder.bankNameTV.setText(items[position].bankName)
        holder.personNameSurnameTV.setText(items[position].nameSurname)

    }


}