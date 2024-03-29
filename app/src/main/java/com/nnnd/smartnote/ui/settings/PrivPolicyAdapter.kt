package com.nnnd.smartnote.ui.settings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nnnd.smartnote.R

class PrivPolicyAdapter(val userList: ArrayList<User>) : RecyclerView.Adapter<PrivPolicyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrivPolicyAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_privacy_policy, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: PrivPolicyAdapter.ViewHolder, position: Int) {
        holder.bindItems(userList[position])
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(user: User) {
            val textViewName = itemView.findViewById(R.id.textViewUsername) as TextView
            val textViewAddress = itemView.findViewById(R.id.textViewAddress) as TextView
            textViewName.text = user.name
            textViewAddress.text = user.address
        }
    }
}