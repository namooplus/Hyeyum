package com.namoo.hyeyum.adapter

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.namoo.hyeyum.R
import com.namoo.hyeyum.activity.DetailActivity
import com.namoo.hyeyum.activity.ListActivity
import com.namoo.hyeyum.database.HyeyumEntity
import com.namoo.hyeyum.util.DatabaseUtil
import com.namoo.hyeyum.util.ResourceUtil

class HyeyumAdapter(private val activity: Activity, private val hyeyumList: List<HyeyumEntity>) : RecyclerView.Adapter<HyeyumAdapter.Holder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
    {
        val view = LayoutInflater.from(activity).inflate(R.layout.view_hyeyum_card, parent, false)
        return Holder(view)
    }
    override fun onBindViewHolder(holder: Holder, position: Int)
    {
        val data = hyeyumList[position]
        val layout = holder.itemView

        holder.bind(data)
        layout.setOnClickListener {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra("information", layout.findViewById<TextView>(R.id.hyeyum_card_information).text.toString())
            intent.putExtra("date", data.date)
            intent.putExtra("state", data.state)
            intent.putExtra("hyeyum", data.hyeyum)
            intent.putExtra("emotion", data.emotion)

            val options = ActivityOptions.makeSceneTransitionAnimation(activity, layout, "hyeyumDetail")

            activity.startActivity(intent, options.toBundle())
        }
    }
    override fun getItemCount(): Int
    {
        return hyeyumList.size
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        private val backgroundView = itemView.findViewById<CardView>(R.id.hyeyum_card_background)
        private val informationView = itemView.findViewById<TextView>(R.id.hyeyum_card_information)
        private val hyeyumView = itemView.findViewById<TextView>(R.id.hyeyum_card_hyeyum)

        fun bind(data: HyeyumEntity)
        {
            //색상
            when (data.state)
            {
                DatabaseUtil.STATE_KEEP ->
                {
                    backgroundView.setCardBackgroundColor(ResourceUtil.getColor(R.color.card_bright))
                    informationView.setTextColor(ResourceUtil.getColor(R.color.text_semi_dark))
                    hyeyumView.setTextColor(ResourceUtil.getColor(R.color.text_dark))
                }
                DatabaseUtil.STATE_FORGET ->
                {
                    backgroundView.setCardBackgroundColor(ResourceUtil.getColor(R.color.card_dark))
                    informationView.setTextColor(ResourceUtil.getColor(R.color.text_semi_bright))
                    hyeyumView.setTextColor(ResourceUtil.getColor(R.color.text_bright))

                    informationView.alpha = 0.5f
                    hyeyumView.alpha = 0.1f
                }
            }

            //텍스트
            informationView?.text = "${layoutPosition+1}${ResourceUtil.getString(R.string.hyeyum_guide_order)} · ${data.date}"
            hyeyumView?.text = data.hyeyum
        }
    }

}