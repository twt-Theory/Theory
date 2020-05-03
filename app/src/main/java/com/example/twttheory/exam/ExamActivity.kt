package com.example.twttheory.exam

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.twttheory.R
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class ExamActivity : AppCompatActivity() {
    private var test_layout: LinearLayout? = null
    private val the_page: Page? = null
    //答案列表
    private var the_answer_list: ArrayList<Answer>? = null
    //问题列表
    private var the_quesition_list: ArrayList<Question>? = null
    //问题所在的View
    private var que_view: View? = null
    //答案所在的View
    private var ans_view: View? = null
    private var xInflater: LayoutInflater? = null
    private var page: Page? = null
    //下面这两个list是为了实现点击的时候改变图片，因为单选多选时情况不一样，为了方便控制
//存每个问题下的imageview
    private val imglist: ArrayList<ArrayList<ImageView>> = ArrayList<ArrayList<ImageView>>()
    //存每个答案的imageview
    private var imglist2: ArrayList<ImageView>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam)
        xInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        //假数据
        initDate()
        //提交按钮
        val button: Button = findViewById<View>(R.id.submit) as Button
        button.setOnClickListener{
            //显示提交后的页面
        }
    }

    private fun initDate() { //假数据
// TODO Auto-generated method stub
        val a_one = Answer()
        a_one.answerId = "0"
        a_one.answer_content = "男"
        a_one.ans_state = 0
        val a_two = Answer()
        a_two.answerId = "1"
        a_two.answer_content = "女"
        a_two.ans_state = 0
        val a_three = Answer()
        a_three.answerId = "3"
        a_three.answer_content = "是"
        a_three.ans_state = 0
        val a_four = Answer()
        a_four.answerId = "4"
        a_four.answer_content = "不是"
        a_four.ans_state = 0
        val a_three1 = Answer()
        a_three1.answerId = "3"
        a_three1.answer_content = "是"
        a_three1.ans_state = 0
        val a_four1 = Answer()
        a_four1.answerId = "4"
        a_four1.answer_content = "不是"
        a_four1.ans_state = 0
        val answers_one = ArrayList<Answer>()
        answers_one.add(a_one)
        answers_one.add(a_two)
        val answers_two = ArrayList<Answer>()
        answers_two.add(a_one)
        answers_two.add(a_two)
        answers_two.add(a_three)
        answers_two.add(a_four)
        val answers_three = ArrayList<Answer>()
        answers_three.add(a_one)
        answers_three.add(a_two)
        answers_three.add(a_three)
        answers_three.add(a_four)
        answers_three.add(a_three1)
        answers_three.add(a_four1)
        val q_one = Question()
        q_one.setQuesitionId("00")
        q_one.setType("0")
        q_one.setContent("1、您的性别：")
        q_one.setAnswers(answers_one)
        q_one.setQue_state(0)
        val q_two = Question()
        q_two.setQuesitionId("01")
        q_two.setType("1")
        q_two.setContent("2、请问这道题应该选什么？")
        q_two.setAnswers(answers_two)
        q_two.setQue_state(0)
        val q_three = Question()
        q_three.setQuesitionId("03")
        q_three.setType("1")
        q_three.setContent("3、请问前面那一题的答案是什么？")
        q_three.setAnswers(answers_three)
        q_three.setQue_state(0)
        val quesitions: ArrayList<Question> = ArrayList<Question>()
        quesitions.add(q_one)
        quesitions.add(q_two)
        quesitions.add(q_three)
        page = Page()
        page!!.pageId = "000"
        page!!.status = "0"
        page!!.title = "第一次调查问卷"
        page!!.quesitions = quesitions
        //加载布局
        initView(page!!)
    }

    private fun initView(page: Page) { // TODO Auto-generated method stub
//这是要把问题的动态布局加入的布局
        test_layout = findViewById<View>(R.id.lly_test) as LinearLayout
        val page_txt = findViewById<View>(R.id.txt_title) as TextView
        page_txt.text = page.title
        //获得问题即第二层的数据
        the_quesition_list = page.quesitions
        //根据第二层问题的多少，来动态加载布局
        for (i in 0 .. the_quesition_list!!.size-1) {
            que_view = xInflater!!.inflate(R.layout.layout_question,test_layout, false)
            val txt_que = que_view!!.findViewById(R.id.txt_question_item) as TextView
            //这是第三层布局要加入的地方
            val add_layout = que_view!!.findViewById(R.id.lly_answer) as LinearLayout
            //判断单选-多选来实现后面是*号还是*多选，
            if (the_quesition_list!!.get(i).type.equals("1")) {
                set(txt_que, the_quesition_list!!.get(i).content, 1)
            } else {
                set(txt_que, the_quesition_list!!.get(i).content, 0)
            }
            //获得答案即第三层数据
            the_answer_list = the_quesition_list!!.get(i).answers
            imglist2 = ArrayList<ImageView>()
            for (j in 0 until the_answer_list!!.size) {
                ans_view = xInflater!!.inflate(R.layout.layout_answer, null)
                val txt_ans = ans_view!!.findViewById(R.id.txt_answer_item) as TextView
                val image: ImageView = ans_view!!.findViewById(R.id.image) as ImageView
                val line_view: View = ans_view!!.findViewById(R.id.vw_line)
                if (j == the_answer_list!!.size - 1) { //最后一条答案下面不要线是指布局的问题
                    line_view.visibility = View.GONE
                }
                //判断单选多选加载不同选项图片
                if (the_quesition_list!!.get(i).type.equals("1")) {
                   // image.setBackgroundDrawable(resources.getDrawable(R.drawable.multiselect_false))
                } else {
                   // image.setBackgroundDrawable(resources.getDrawable(R.drawable.radio_false))
                }
                Log.e("---", "------$image")
                imglist2!!.add(image)
                txt_ans.text = the_answer_list!!.get(j).answer_content
                val lly_answer_size =
                    ans_view!!.findViewById(R.id.lly_answer_size) as LinearLayout
                lly_answer_size.setOnClickListener{
                    //之后再写
                }
                add_layout.addView(ans_view)
            }
            /*for(int r=0; r<imglist2.size();r++){
				Log.e("---", "imglist2--------"+imglist2.get(r));
			}*/imglist.add(imglist2!!)
            test_layout!!.addView(que_view)
        }
        /*for(int q=0;q<imglist.size();q++){
			for(int w=0;w<imglist.get(q).size();w++){
				Log.e("---", "共有------"+imglist.get(q).get(w));
			}
		}*/
    }

    private operator fun set(
        tv_test: TextView,
        content: String,
        type: Int
    ) { //为了加载问题后面的* 和*多选
// TODO Auto-generated method stub
        val w: String
        w = if (type == 1) {
            "$content*[多选题]"
        } else {
            "$content*"
        }
        val start = content.length
        val end = w.length
        val word: Spannable = SpannableString(w)
        word.setSpan(
            AbsoluteSizeSpan(25), start, end,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        word.setSpan(
            StyleSpan(Typeface.BOLD), start, end,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        word.setSpan(
                ForegroundColorSpan(Color.BLUE), start, end,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        tv_test.text = word
    }

    internal inner class answerItemOnClickListener(
        private val i: Int,
        private val j: Int,
        private val the_answer_lists: ArrayList<Answer>,
        private val txt: TextView
    ) :
        View.OnClickListener {
        //实现点击选项后改变选中状态以及对应图片
        override fun onClick(arg0: View?) { // TODO Auto-generated method stub
//判断当前问题是单选还是多选
/*Log.e("------", "选择了-----第"+i+"题");
			for(int q=0;q<imglist.size();q++){
				for(int w=0;w<imglist.get(q).size();w++){
//					Log.e("---", "共有------"+imglist.get(q).get(w));
				}
			}
			Log.e("----", "点击了---"+imglist.get(i).get(j));*/
            if (the_quesition_list!![i].getType().equals("1")) { //多选
                if (the_answer_lists[j].ans_state === 0) { //如果未被选中
                    txt.setTextColor(Color.parseColor("#EA5514"))
                    //imglist[i][j].setBackgroundDrawable(resources.getDrawable(R.drawable.multiselect_true))
                    the_answer_lists[j].ans_state = 1
                    the_quesition_list!![i].setQue_state(1)
                } else {
                    txt.setTextColor(Color.parseColor("#595757"))
                    //imglist[i][j].setBackgroundDrawable(resources.getDrawable(R.drawable.multiselect_false))
                    the_answer_lists[j].ans_state = 0
                    the_quesition_list!![i].setQue_state(1)
                }
            } else { //单选
                for (z in 0 until the_answer_lists.size) {
                    the_answer_lists[z].ans_state = 0
                    //imglist[i][z].setBackgroundDrawable(resources.getDrawable(R.drawable.radio_false))
                }
                if (the_answer_lists[j].ans_state === 0) { //如果当前未被选中
                    //imglist[i][j].setBackgroundDrawable(resources.getDrawable(R.drawable.radio_true))
                    the_answer_lists[j].ans_state = 1
                    the_quesition_list!![i].setQue_state(1)
                } else { //如果当前已被选中
                    the_answer_lists[j].ans_state = 1
                    the_quesition_list!![i].setQue_state(1)
                }
            }
            //判断当前选项是否选中
        }

    }

    internal inner class submitOnClickListener(private val page: Page) :
        View.OnClickListener {
        override fun onClick(arg0: View?) { // TODO Auto-generated method stub
//判断是否答完题
            var isState = true
            //最终要的json数组
            var jsonArray: JSONArray? = JSONArray()
            //点击提交的时候，先判断状态，如果有未答完的就提示，如果没有再把每条答案提交（包含问卷ID 问题ID 及答案ID）
//注：不用管是否是一个问题的答案，就以答案的个数为准来提交上述格式的数据
            for (i in 0 until the_quesition_list!!.size) {
                the_answer_list = the_quesition_list!![i].getAnswers()
                //判断是否有题没答完
                if (the_quesition_list!![i].getQue_state() === 0) {
                    Toast.makeText(
                        applicationContext,
                        "您第" + (i + 1) + "题没有答完",
                        Toast.LENGTH_LONG
                    ).show()
                    jsonArray = null
                    isState = false
                    break
                } else {
                    for (j in 0 until the_answer_list!!.size) {
                        if (the_answer_list!![j].ans_state === 1) {
                            val json = JSONObject()
                            try {
                                json.put("psychologicalId", page.pageId)
                                json.put("questionId", the_quesition_list!![i].getQuesitionId())
                                json.put("optionId", the_answer_list!![j].answerId)
                                jsonArray!!.put(json)
                            } catch (e: JSONException) { // TODO Auto-generated catch block
                                e.printStackTrace()
                            }
                        }
                    }
                }
            }
            if (isState) {
                if (jsonArray!!.length() > 0) {
                    Log.e("af", jsonArray.toString())
                    for (item in 0 until jsonArray.length()) {
                        var job: JSONObject
                        try {
                            job = jsonArray.getJSONObject(item)
                            Log.e("----", "pageId--------" + job["pageId"])
                            Log.e("----", "quesitionId--------" + job["quesitionId"])
                            Log.e("----", "answerId--------" + job["answerId"])
                        } catch (e: JSONException) { // TODO Auto-generated catch block
                            e.printStackTrace()
                        } // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                    }
                }
            }
        }

    }
}