package com.example.twttheory.mainPage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.twttheory.R
import kotlinx.android.synthetic.main.item_task.view.*

class ListAdapter: RecyclerView.Adapter<ListAdapter.Holder> {
    private var context : Context? = null
    private var data : ArrayList<DataBean>? = null
    private var type : AdapterType = AdapterType.JOIN   //默认是“我加入的问卷”的adapter
    constructor(context:Context,data:ArrayList<DataBean>,type : AdapterType){
        this.context = context
        this.data  = data
        this.type = type
    }

    class Holder(itemView: View?):RecyclerView.ViewHolder(itemView!!){
        val title = itemView!!.findViewById<TextView>(R.id.title)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        var view : View
        when(type){
            AdapterType.JOIN->{
               view = LayoutInflater.from(context).inflate(R.layout.item_join,parent,false)
            }
            AdapterType.RELEASE->{
                view = LayoutInflater.from(context).inflate(R.layout.item_task,parent,false)
            }
        }
        return Holder(view)
    }

    override fun getItemCount(): Int = data?.size!!
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.title.text = data!![position].title
    }
}