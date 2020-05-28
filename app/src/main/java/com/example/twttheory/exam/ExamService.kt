package com.example.twttheory.exam

import com.example.twttheory.service.CommonBody
import com.example.twttheory.service.ServiceFactory
import kotlinx.coroutines.Deferred
import okhttp3.RequestBody
import retrofit2.http.*
import java.util.*

interface ExamService {
    //    测试用
    @Headers("Content-type:application/json;charset=UTF-8")
    @POST("test")
    fun test(
        @Body
        requestBody: RequestBody
    ): Deferred<CommonBody<Data>>

//    @JvmSuppressWildcards
//    @FormUrlEncoded
//    @POST("test")
//    fun test(
//        @FieldMap map: Map<String, String>
//    ): Deferred<Test1>

    //创建新任务
    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("createPaper")
    fun createTask(
        @FieldMap map: Map<String, Objects>
    ): Deferred<CommonBody<Boolean>>

    //暂停任务
    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("")
    fun pauseTask(
        @Field("paper_id")
        paper_id: Int
    ): Deferred<CommonBody<Boolean>>

    //修改任务限制
    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("alterPaper")
    fun changeLimit(
        @FieldMap map: Map<String, String>
    ): Deferred<CommonBody<Boolean>>

    //下载问卷
    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("")
    fun downloadTask(
        @Field("paper_id")
        paper_id: Int
    ): Deferred<CommonBody<String>>

    //删除问卷
    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("")
    fun deleteTask(
        @Field("paper_id")
        paper_id: Int
    ): Deferred<CommonBody<Boolean>>

    //获取修改题目
    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("toAlterQuestion")
    fun getChangeTask(
        @Field("paper_id")
        paper_id: Int
    ): Deferred<CommonBody<ChangedQuestions>>

    //提交修改
    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("AlterQuestion")
    fun postChangeTask(
        @FieldMap map: Map<String, String>
    ): Deferred<CommonBody<Boolean>>

    //获取我发布的
    @GET("getMine")
    fun getMyPosted(): Deferred<CommonBody<List<Posted>>>

    //获取和我相关的
    @GET("getAvailable")
    fun getMyRelated(): Deferred<CommonBody<List<Related>>>

    //答题时获取题目
    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("getPaper")
    fun getTask(
        @Field("paper_id")
        paper_id: Int,
        @Field("password")
        password: String
    ): Deferred<CommonBody<Questions>>

    //交卷
    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("postPaper")
    fun solveTask(
        @FieldMap map: Map<String, String>
    ): Deferred<CommonBody<Boolean>>

    //答卷者查看成绩
    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("catScore")
    fun catScore(
        @Field("paper_id")
        paper_id: Int
    ): Deferred<CommonBody<List<PaperInfo>>>

    companion object : ExamService by ServiceFactory()
}

data class Data(
    val `0`: Int,
    val `1`: Int,
    val `2`: Int,
    val test3: Test3
)

data class Test3(
    val test3_1: List<Int>,
    val test3_2: List<Int>
)

//获取修改题目
data class ChangedQuestions(
    val question: List<ChangedQuestion>
)

data class ChangedQuestion(
    val question_id: Int,
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

//"我发布的"问卷
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

//"和我相关的"问卷
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

//答题时获取的所有题目
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

//创建新任务时的每一道题
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

//交卷时的每道题
data class HandleQuestion(
    val question_id: Int,
    val answer: String
)

//做题者的问卷信息
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
    val score: Int
)