package com.example.twttheory.mainPage

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LayoutAnimationController
import android.view.animation.LinearInterpolator
import android.view.animation.ScaleAnimation
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.twttheory.R
import com.example.twttheory.manage.ManageActivity
import kotlinx.android.synthetic.main.item_task.view.*

class ListAdapter: RecyclerView.Adapter<ListAdapter.Holder> {
    //写列表条目的点击事件接口
    interface OnItemClickListener{
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)
    }
    private lateinit var onItemClickListener: OnItemClickListener

    //在activity中调用，传入一个接口（点击跳转事件）
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }
    //这两个是条目右边的
    lateinit var onManageButtonClickListener : View.OnClickListener
    lateinit var onDataAnalysisBottonClickListener : View.OnClickListener
    private var context : Context? = null
    private var data : ArrayList<DataBean>? = null
    private var type : AdapterType = AdapterType.JOIN   //默认是“我加入的问卷”的adapter
    public var animate = false  //在notifidatasetChanged之后会把它设置为true
    var foldSomeItem = true
    constructor(context:Context,data:ArrayList<DataBean>,type : AdapterType){
        this.context = context
        this.data  = data
        this.type = type
    }
    class Holder(itemView: View?):RecyclerView.ViewHolder(itemView!!){
        val manageBotton = itemView!!.findViewById<Button>(R.id.manage) //该按钮点击后跳转到“管理我发布的某一任务”页面
        val dadaAnalysisButton = itemView!!.findViewById<Button>(R.id.analysis) //该按钮点击后跳转到“数据分析”页面
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
        holder.itemView.setOnClickListener{
            onItemClickListener?.onItemClick(holder.itemView,position)
        }
        holder.manageBotton.setOnClickListener(onManageButtonClickListener)
        holder.dadaAnalysisButton.setOnClickListener(onDataAnalysisBottonClickListener)
    }



}