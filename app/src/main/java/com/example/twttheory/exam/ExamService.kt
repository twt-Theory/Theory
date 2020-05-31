package com.example.twttheory.exam

import com.example.twttheory.service.CommonBody
import com.example.twttheory.service.ServiceFactory
import kotlinx.coroutines.Deferred
import okhttp3.RequestBody
import retrofit2.http.*

interface ExamService {
    //测试用
    @Headers("Content-type:application/json;charset=UTF-8")
    @POST("test")
    fun test(
        @Body requestBody: RequestBody
    ): Deferred<CommonBody<Data>>

    //创建新任务
    @Headers("Content-type:application/json;charset=UTF-8")
    @POST("createPaper")
    fun createTask(
        @Body requestBody: RequestBody
    ): Deferred<CommonBody<Boolean>>

    //暂停任务（暂无）
    @Headers("Content-type:application/json;charset=UTF-8")
    @POST("")
    fun pauseTask(
        @Body requestBody: RequestBody
    ): Deferred<CommonBody<Boolean>>

    //修改任务限制
    @Headers("Content-type:application/json;charset=UTF-8")
    @POST("alterPaper")
    fun changeLimit(
        @Body requestBody: RequestBody
    ): Deferred<CommonBody<Boolean>>

    //下载问卷（暂无）
    @Headers("Content-type:application/json;charset=UTF-8")
    @POST("")
    fun downloadTask(
        @Body requestBody: RequestBody
    ): Deferred<CommonBody<String>>

    //删除问卷（暂无）
    @Headers("Content-type:application/json;charset=UTF-8")
    @POST("")
    fun deleteTask(
        @Body requestBody: RequestBody
    ): Deferred<CommonBody<Boolean>>

    //获取修改题目
    @Headers("Content-type:application/json;charset=UTF-8")
    @POST("toAlterQuestion")
    fun getChangeTask(
        @Body requestBody: RequestBody
    ): Deferred<CommonBody<ChangedQuestions>>

    //提交修改
    @Headers("Content-type:application/json;charset=UTF-8")
    @POST("AlterQuestion")
    fun postChangeTask(
        @Body requestBody: RequestBody
    ): Deferred<CommonBody<Boolean>>

    //获取我发布的
    @Headers("Content-type:application/json;charset=UTF-8")
    @POST("getMine")
    fun getMyPosted(): Deferred<CommonBody<List<Posted>>>

    //获取和我相关的
    @Headers("Content-type:application/json;charset=UTF-8")
    @POST("getAvailable")
    fun getMyRelated(): Deferred<CommonBody<List<Related>>>

    //答题时获取题目
    @Headers("Content-type:application/json;charset=UTF-8")
    @POST("getPaper")
    fun getTask(
        @Body requestBody: RequestBody
    ): Deferred<CommonBody<Questions>>

    //交卷
    @Headers("Content-type:application/json;charset=UTF-8")
    @POST("postPaper")
    fun solveTask(
        @Body requestBody: RequestBody
    ): Deferred<CommonBody<Boolean>>

    //答卷者查看成绩
    @Headers("Content-type:application/json;charset=UTF-8")
    @POST("catScore")
    fun catScore(
        @Body requestBody: RequestBody
    ): Deferred<CommonBody<List<PaperInfo>>>

    //简易登录
    @Headers("Content-type:application/json;charset=UTF-8")
    @POST("login")
    fun login(
        @Body requestBody: RequestBody
    ): Deferred<CommonBody<Boolean>>

    companion object : ExamService by ServiceFactory()
}

//测试用
data class Data(
    val `0`: Int,
    val `1`: Int,
    val `2`: Int,
    val test3: Test3
)

//测试用
data class Test3(
    val test3_1: List<Int>,
    val test3_2: List<Int>
)

//“获取修改题目”的Post与返回数据
data class ChangedQuestions(
    val question: List<ChangedQuestion>
)

//同上
data class ChangedQuestion(
    var question_id: Int,
    var question: String,
    var answer: List<String>,
    var right_id: String,
    var type: Int,
    var is_necessary: Boolean,
    var is_random: Boolean,
    var score: Int,
    var need_question: Int,
    var need_answer: Int,
    var max_select: Int,
    var min_select: Int
)

//“我发布的问卷”返回数据
data class Posted(
    val paper_id: Int,
    val paper_name: String,
    val paper_hint: String,
    val start_time: String,
    val end_time: String,
    val last_time: Int,
    val password: String,
    val times: Int,
    val number: Int,
    val paper_type: Int,
    val is_random: Boolean
)

//“和我相关的问卷”返回数据
data class Related(
    val paper_id: Int,
    val paper_name: String,
    val paper_hint: String,
    val start_time: String,
    val end_time: String,
    val last_time: Int,
    val times: Int,
    val number: Int,
    val paper_type: Int,
    val score: Int
)

//答题时获取的整张问卷
data class Questions(
    val paper_question: List<GetQuestion>,
    val submit_id: Int
)

//答题时获取的单个题目
data class GetQuestion(
    val question_id: Int,
    val question: String,
    val answer: List<String>,
    val type: Int,
    val is_necessary: Boolean,
    val is_random: Boolean,
    val score: Int,
    val need_question: Int,
    val need_answer: Int,
    val max_select: Int,
    val min_select: Int
)

//创建新任务时的每一道题（用于@Post）
data class PostQuestion(
    val question: String,
    val answer: List<String>,
    val right_id: String,
    val type: Int,
    val is_necessary: Boolean,
    val is_random: Boolean,
    val score: Int,
    val need_question: Int,
    val need_answer: Int,
    val max_select: Int,
    val min_select: Int
)

//“答卷者看成绩”返回数据
data class PaperInfo(
    val is_judged: Boolean,
    val total_Score: Int,
    val question: List<QuestionInfo>
)

//每道题的信息
data class QuestionInfo(
    val question: String,
    val options: String,
    val my_answer: String,
    val right_id: String,
    val type: Int,
    val score: Int)

