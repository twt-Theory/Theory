package com.example.twttheory.exam

import com.example.twttheory.service.CommonBody
import com.example.twttheory.service.ServiceFactory
import kotlinx.coroutines.Deferred
import okhttp3.RequestBody
import retrofit2.http.*

interface ExamService {
    //创建新任务
    @Multipart
    @POST("")
    fun createTask(
        @PartMap
        requestBodyMap: Map<String, RequestBody>
    ): Deferred<CommonBody<Boolean>>

    //暂停任务
    @Multipart
    @POST("")
    fun pauseTask(
        @Part("paper_id")
        paper_id: RequestBody
    ): Deferred<CommonBody<Boolean>>

    //修改任务限制
    @Multipart
    @POST("")
    fun changeTask(
        @PartMap
        requestBodyMap: Map<String, RequestBody>
    ): Deferred<CommonBody<Boolean>>

    //下载问卷
    @Multipart
    @POST("")
    fun downloadTask(
        @Part("paper_id")
        paper_id: RequestBody
    ): Deferred<CommonBody<String>>

    //删除问卷
    @Multipart
    @POST("")
    fun deleteTask(
        @Part("paper_id")
        paper_id: RequestBody
    ): Deferred<CommonBody<Boolean>>

    //获取我发布的
    @POST("")
    fun getMyPosted(): Deferred<CommonBody<List<Posted>>>

    //获取和我相关的
    @POST("")
    fun getMyRelated(): Deferred<CommonBody<List<Related>>>

    //答题
    @Multipart
    @POST("")
    fun getTask(
        @Part("paper_id")
        paper_id: RequestBody,
        @Part("password")
        password: RequestBody
    ): Deferred<CommonBody<Questions>>

    //交卷
    @Multipart
    @POST("")
    fun solveTask(
        @Part("answer")
        answer: RequestBody
    ): Deferred<CommonBody<Boolean>>

    companion object : ExamService by ServiceFactory()
}

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
    val paper_type: Int
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
    val paper_question: List<GetQuestion>
)

//答题时获取的单个题目
data class GetQuestion(
    val question: String,
    val answer: List<String>,
    val right_id: String,
    val type: Int,
    val is_necessary: Boolean,
    val score: Int,
    val need_question: Int,
    val need_answer: Int,
    val max_select: Int,
    val min_select: Int
)

//创建新任务时的每一道题
data class PostQuestion(
    var question: String?,
    var answer: List<String>?,
    var right_id: String?,
    var type: Int?,
    var is_necessary: Boolean?,
    var is_random: Boolean?,
    var score: Int?,
    var need_question: Int?,
    var need_answer: Int?,
    var max_select: Int?,
    var min_select: Int?
)