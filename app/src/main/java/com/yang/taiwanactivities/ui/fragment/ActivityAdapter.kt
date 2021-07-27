package com.yang.taiwanactivities.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yang.taiwanactivities.R
import com.yang.taiwanactivities.data.model.Info
import com.yang.taiwanactivities.util.formatToServerDateTimeDefaults

class ActivityAdapter(private val infoClickListener: (ImageView, Info, Int) -> Unit) :
    RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder>() {

    var infoList: List<Info> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_activity, parent, false)
        return ActivityViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        holder.bind(infoList[position])
    }

    override fun getItemCount(): Int {
        return infoList.size
    }

    fun updateList(infoList: List<Info>) {
        this.infoList = infoList
        notifyDataSetChanged()
    }

    inner class ActivityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivPicture = itemView.findViewById<ImageView>(R.id.ivPicture)
        var tvTime = itemView.findViewById<TextView>(R.id.tvTime)
        var tvName = itemView.findViewById<TextView>(R.id.tvName)

        fun bind(info: Info) {
            itemView.setOnClickListener {
                infoClickListener(ivPicture, infoList[adapterPosition], adapterPosition)
            }
            ivPicture.transitionName = info.Id

            Picasso.get().load(info.Picture1).error(R.drawable.ic_image)
                .placeholder(R.drawable.ic_image).fit().centerCrop().into(ivPicture)
            val strTime =
                info.Start.formatToServerDateTimeDefaults() + "~" + info.End.formatToServerDateTimeDefaults()
            tvTime.text = strTime
            tvName.text = info.Name
        }
    }
}