package com.kh.ite.rupp.edu.trendy.Ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.kh.ite.rupp.edu.trendy.Model.UserOrderingModel
import com.kh.ite.rupp.edu.trendy.R
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnItemClick
import com.kh.ite.rupp.edu.trendy.Ui.viewHolder.UserOrderingItemViewHolder
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class UserOrderingAdapter(
    private val context: Context,
    private val mItem: ArrayList<UserOrderingModel.UserOrderingItem>,
    private val mListener: OnItemClick<UserOrderingModel.UserOrderingItem>
):Adapter<UserOrderingItemViewHolder>() {

    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
    val sortedItems = mItem.sortedBy {
        ZonedDateTime.parse(it.onDate, dateFormatter)
    }.reversed()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserOrderingItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.ordering_item_viewholder, parent, false)
        return  UserOrderingItemViewHolder(view, context)
    }

    override fun getItemCount(): Int {
        return mItem.size
    }

    override fun onBindViewHolder(holder: UserOrderingItemViewHolder, position: Int) {
        holder.onBind(sortedItems[position], position, context)
    }
}