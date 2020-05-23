package com.example.twttheory.mainPage

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.twttheory.R
import com.example.twttheory.exam.PostQuestion
import kotlinx.android.synthetic.main.item_maketask.view.*

class CreatTaskAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {

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
    private var data : ArrayList<PostQuestion>? = null

    constructor(context: Context, data:ArrayList<PostQuestion>){
        this.context = context
        this.data  = data

    }
    //出单选题的view holder
    class SingleHolder(itemView: View?):RecyclerView.ViewHolder(itemView!!){
        val typeImage: ImageView = itemView!!.findViewById<ImageView>(R.id.type)                //输入框左边表示题目类型的图片
        val questionStem: EditText = itemView!!.findViewById<EditText>(R.id.question)         //题干的输入框
        //  val chooseImageForStem = itemView!!.findViewById<EditText>(R.id.add_image) //为题干选择图片 现在暂时不支持
        val valueEt : EditText = itemView!!.findViewById<EditText>(R.id.value_input)//分值
        val necessary: CheckBox = itemView!!.findViewById<CheckBox>(R.id.necessary)//是否必答
        val random: CheckBox = itemView!!.findViewById<CheckBox>(R.id.random)//选项随机
        val jump: CheckBox = itemView!!.findViewById<CheckBox>(R.id.random)//跳转到其他题
        val selectionsLinearLO : LinearLayout = itemView!!.findViewById(R.id.selections)
        val addSelection : Button = itemView!!.findViewById(R.id.add_selection)
        val myTextWatcher = MyTextWatcher()
        init {

        }

    }
    //出多选题的view holder
    class MultipleHolder(itemView: View?):RecyclerView.ViewHolder(itemView!!){
        val typeImage: ImageView = itemView!!.findViewById<ImageView>(R.id.type)                //输入框左边表示题目类型的图片
        val questionStem: EditText = itemView!!.findViewById<EditText>(R.id.question)         //题干的输入框
        //  val chooseImageForStem = itemView!!.findViewById<EditText>(R.id.add_image) //为题干选择图片 现在暂时不支持
        val valueEt : EditText = itemView!!.findViewById<EditText>(R.id.value_input)//分值
        val necessary: CheckBox = itemView!!.findViewById<CheckBox>(R.id.necessary)//是否必答
        val random: CheckBox = itemView!!.findViewById<CheckBox>(R.id.random)//选项随机
        val jump: CheckBox = itemView!!.findViewById<CheckBox>(R.id.random)//跳转到其他题
        val selectionsLinearLO : LinearLayout = itemView!!.findViewById(R.id.selections)
        val addSelection : Button = itemView!!.findViewById(R.id.add_selection)
    //出文本题的view holder
    }
    class TextHolder(itemView: View?):RecyclerView.ViewHolder(itemView!!){
        val typeImage: ImageView = itemView!!.findViewById<ImageView>(R.id.type)                //输入框左边表示题目类型的图片
        val questionStem: EditText = itemView!!.findViewById<EditText>(R.id.question)         //题干的输入框
        //  val chooseImageForStem = itemView!!.findViewById<EditText>(R.id.add_image) //为题干选择图片 现在暂时不支持
        val valueEt : EditText = itemView!!.findViewById<EditText>(R.id.value_input)//分值
        val necessary: CheckBox = itemView!!.findViewById<CheckBox>(R.id.necessary)//是否必答
        val random: CheckBox = itemView!!.findViewById<CheckBox>(R.id.random)//选项随机
        val jump: CheckBox = itemView!!.findViewById<CheckBox>(R.id.random)//跳转到其他题
        val selectionsLinearLO : LinearLayout = itemView!!.findViewById(R.id.selections)
        val addSelection : Button = itemView!!.findViewById(R.id.add_selection)

    }
    //出量表题的 view holder
    class InventoryHolder(itemView: View?):RecyclerView.ViewHolder(itemView!!){
        val typeImage: ImageView = itemView!!.findViewById<ImageView>(R.id.type)                //输入框左边表示题目类型的图片
        val questionStem: EditText = itemView!!.findViewById<EditText>(R.id.question)         //题干的输入框
        //  val chooseImageForStem = itemView!!.findViewById<EditText>(R.id.add_image) //为题干选择图片 现在暂时不支持
        val valueEt : EditText = itemView!!.findViewById<EditText>(R.id.value_input)//分值
        val necessary: CheckBox = itemView!!.findViewById<CheckBox>(R.id.necessary)//是否必答
        val random: CheckBox = itemView!!.findViewById<CheckBox>(R.id.random)//选项随机
        val jump: CheckBox = itemView!!.findViewById<CheckBox>(R.id.random)//跳转到其他题
        val selectionsLinearLO : LinearLayout = itemView!!.findViewById(R.id.selections)
        val addSelection : Button = itemView!!.findViewById(R.id.add_selection)

    }
    //出排序题的view holder
    class SequenceHolder(itemView: View?):RecyclerView.ViewHolder(itemView!!){
        val typeImage: ImageView = itemView!!.findViewById<ImageView>(R.id.type)                //输入框左边表示题目类型的图片
        val questionStem: EditText = itemView!!.findViewById<EditText>(R.id.question)         //题干的输入框
        //  val chooseImageForStem = itemView!!.findViewById<EditText>(R.id.add_image) //为题干选择图片 现在暂时不支持
        val valueEt : EditText = itemView!!.findViewById<EditText>(R.id.value_input)//分值
        val necessary: CheckBox = itemView!!.findViewById<CheckBox>(R.id.necessary)//是否必答
        val random: CheckBox = itemView!!.findViewById<CheckBox>(R.id.random)//选项随机
        val jump: CheckBox = itemView!!.findViewById<CheckBox>(R.id.random)//跳转到其他题
        val selectionsLinearLO : LinearLayout = itemView!!.findViewById(R.id.selections)
        val addSelection : Button = itemView!!.findViewById(R.id.add_selection)

    }

    class MyTextWatcher : TextWatcher{
        lateinit var answer : String
        override fun afterTextChanged(s: Editable?) {
            answer = s.toString()

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val typeArray : Array<Int> = arrayOf(
            R.layout.item_maketask,
            R.layout.set_mutliple_choice,
            R.layout.set_text,
            R.layout.set_inventory,
            R.layout.set_sequence)
        var view : View = LayoutInflater.from(context).inflate(typeArray[viewType],parent,false)

        return when(viewType){
            0->SingleHolder(view)
            1->MultipleHolder(view)
            2->TextHolder(view)
            3->InventoryHolder(view)
            4->SequenceHolder(view)
            else -> SingleHolder(view)
        }
    }
    override fun getItemCount(): Int = data?.size!!
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        holder.itemView.setOnClickListener{
//            onItemClickListener?.onItemClick(holder.itemView,position)
//        }
        when(holder.itemViewType){
            0->{
                //单选题出题
                holder as SingleHolder
                holder.itemView.number.text = (position+1).toString()+"."

                var question = holder.questionStem.text
                var score = holder.valueEt.text
                var alphaNum : Int = 0
                holder.addSelection.setOnClickListener {
                    val optionView : OptionView = OptionView(context!!,0,alphaNum)
                    holder.selectionsLinearLO.addView(optionView)
                    var params = optionView.layoutParams
                    alphaNum += 1


                }
                if (data == null) data = ArrayList()
            }
            1->{
                //多选题出题
                holder as MultipleHolder
                holder.itemView.number.text = (position+1).toString()+"."
                var question = holder.questionStem.text
                var score = holder.valueEt.text
                holder.addSelection.setOnClickListener {
                    val editText = EditText(context)
                    holder.selectionsLinearLO.addView(editText)
                    var params = editText.layoutParams
                    params.width = 200
                    editText.layoutParams = params
                }
                if (data == null) data = ArrayList()

            }
            2->{
                //文本题出题
                holder as SingleHolder
                holder.itemView.number.text = (position+1).toString()

                var question = holder.questionStem.text
                var score = holder.valueEt.text
                holder.addSelection.setOnClickListener {
                    val editText = EditText(context)
                    holder.selectionsLinearLO.addView(editText)
                    var params = editText.layoutParams
                    params.width = 200
                    editText.layoutParams = params
                }
                if (data == null) data = ArrayList()
            }
            3->{
                //量表题出题
                holder as SingleHolder
                holder.itemView.number.text = (position+1).toString()

                var question = holder.questionStem.text
                var score = holder.valueEt.text
                holder.addSelection.setOnClickListener {
                    val editText = EditText(context)
                    holder.selectionsLinearLO.addView(editText)
                    var params = editText.layoutParams
                    params.width = 200
                    editText.layoutParams = params
                }
                if (data == null) data = ArrayList()
            }
            4->{
                //排序题出题
                holder as SingleHolder
                holder.itemView.number.text = (position+1).toString()

                var question = holder.questionStem.text
                var score = holder.valueEt.text
                holder.addSelection.setOnClickListener {
                    val editText = EditText(context)
                    holder.selectionsLinearLO.addView(editText)
                    var params = editText.layoutParams
                    params.width = 200
                    editText.layoutParams = params
                }
                if (data == null) data = ArrayList()
            }


        }





    }
    fun getDate() = data
    override fun getItemViewType(position: Int): Int {
        return data!![position].type!!
    }


}