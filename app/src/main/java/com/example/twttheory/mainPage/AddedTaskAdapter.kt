package com.example.twttheory.mainPage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.twttheory.R
import kotlinx.android.synthetic.main.item_maketask.view.*

class AddedTaskAdapter : RecyclerView.Adapter<AddedTaskAdapter.Holder> {

    interface OnItemClickListener{
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)     //长按删除题目
    }
    private lateinit var onItemClickListener: ListAdapter.OnItemClickListener
    //在activity中调用，传入一个接口（点击跳转事件）
    fun setOnItemClickListener(listener: ListAdapter.OnItemClickListener) {
        this.onItemClickListener = listener
    }

    //这两个是条目右边的
    private var context : Context? = null
    private var data : ArrayList<AddQuestion>? = null

    constructor(context: Context, data:ArrayList<AddQuestion>, type : AdapterType){
        this.context = context
        this.data  = data

    }
    class Holder(itemView: View?): RecyclerView.ViewHolder(itemView!!){
        val typeImage = itemView!!.findViewById<ImageView>(R.id.type)                //输入框左边表示题目类型的图片
        val questionStem = itemView!!.findViewById<EditText>(R.id.question)         //题干的输入框
        val chooseImageForStem = itemView!!.findViewById<EditText>(R.id.add_image) //为题干选择图片
        val value = itemView!!.findViewById<EditText>(R.id.value)//分值
        val necessary = itemView!!.findViewById<CheckBox>(R.id.necessary)//是否必答
        val random = itemView!!.findViewById<CheckBox>(R.id.random)//选项随机
        val jump = itemView!!.findViewById<CheckBox>(R.id.random)//跳转到其他题

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        var view : View = LayoutInflater.from(context).inflate(R.layout.item_maketask,parent,false)
        return Holder(view)
    }
    override fun getItemCount(): Int = data?.size!!
    override fun onBindViewHolder(holder: Holder, position: Int) {
//        holder.itemView.setOnClickListener{
//            onItemClickListener?.onItemClick(holder.itemView,position)
//        }
        holder.itemView.number.text = (position+1).toString()
        //暂时不考虑中途退出的情况
        var stem = holder.questionStem.text
        var value = holder.value.text



    }
    fun getDate() = data


}