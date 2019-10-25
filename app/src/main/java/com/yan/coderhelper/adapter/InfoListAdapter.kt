package com.yan.coderhelper.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.yan.coderhelper.R
import com.yan.coderhelper.bean.InfoBean

/**
 * @author Yan
 * @date 2019/10/24.
 * description：
 */
class InfoListAdapter(var mInfoList:MutableList<InfoBean>) : RecyclerView.Adapter<InfoListAdapter.InfoListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoListViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_info,null,false)
        return InfoListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mInfoList.size
    }

    override fun onBindViewHolder(holder: InfoListViewHolder, position: Int) {
        holder.tv_infoName.text = mInfoList[position].infoName
        holder.tv_infoValue.text = mInfoList[position].infoValue
    }


    class InfoListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var tv_infoName: TextView = itemView.findViewById(R.id.tv_infoName)
        var tv_infoValue: TextView = itemView.findViewById(R.id.tv_infoValue)
    }

}