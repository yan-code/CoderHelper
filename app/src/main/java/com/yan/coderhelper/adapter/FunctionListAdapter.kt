package com.yan.coderhelper.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yan.coderhelper.R
import com.yan.coderhelper.bean.FunctionBean

/**
 * @author Yan
 * @date 2019/10/23.
 * description：
 */
class FunctionListAdapter(private var mData:MutableList<FunctionBean>, var mOnItemClickListener:onItemClickListener) : RecyclerView.Adapter<FunctionListAdapter.FunctionViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FunctionViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_function,parent,false)
        return FunctionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: FunctionViewHolder, position: Int) {
        holder.tv_function.text = mData[position].name
        holder.iv_function.setImageDrawable(mData[position].resId)
        holder.itemView.setOnClickListener {
            mOnItemClickListener.onItemClickListener(holder.itemView,position)
        }
    }

    interface onItemClickListener{
        fun onItemClickListener(view: View,position:Int)
    }

    class FunctionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tv_function: TextView = itemView.findViewById(R.id.tv_function)
        var iv_function: ImageView = itemView.findViewById(R.id.iv_function)

    }
}