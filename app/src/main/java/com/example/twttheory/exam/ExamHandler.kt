package com.example.twttheory.exam

import com.example.twttheory.service.RefreshState
import com.example.twttheory.service.awaitAndHandle
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody

fun generateRequestBody(requestDataMap: Map<String, String>): Map<String, RequestBody> {
    val requestBodyMap: MutableMap<String, RequestBody> = HashMap()
    for (key: String in requestDataMap.keys) {
        val requestBody: RequestBody =
            RequestBody.create(
                MediaType.parse("multipart/form-data"),
                requestDataMap[key].orEmpty()
            )
        requestBodyMap[key] = requestBody
    }
    return requestBodyMap
}

//测试用
fun testFun(
    callback: suspend (RefreshState<String>) -> Unit = {}
) {
    GlobalScope.launch(Dispatchers.Main) {
        val test1 = listOf(0, 1, 2)
        val test2 = listOf(3, 4, 5)
        val params = mapOf(
            "test1" to test1,
            "test2" to test2,
            "test3" to mapOf(
                "test3_1" to test1,
                "test3_2" to test2
            )
        )
        ExamService.test(
            RequestBody.create(
                MediaType.parse("application/json;charset=UTF-8"),
                Gson().toJson(params)
            )
        ).awaitAndHandle {
            callback(RefreshState.Failure(it))
        }?.data?.let {
            callback(RefreshState.Success(it.test3.toString()))
        }
    }
}

//创建新任务
fun createExam(
    paper_type: Int,
    paper_name: String,
    paper_hint: String,
    number: Int,
    is_random: Boolean,
    paper_question: List<PostQuestion>,
    start_time: String,
    end_time: String,
    last_time: Int,
    password: String,
    times: Int,
    callback: suspend (RefreshState<Unit>) -> Unit = {}
) {
    GlobalScope.launch(Dispatchers.Main) {
        val params = mapOf(
            "paper_type" to paper_type,
            "paper_name" to paper_name,
            "paper_hint" to paper_hint,
            "number" to number,
            "is_random" to is_random,
            "paper_question" to paper_question,
            "start_time" to start_time,
            "end_time" to end_time,
            "last_time" to last_time,
            "password" to password,
            "times" to times
        )
        ExamService.createTask(
            RequestBody.create(
                MediaType.parse("application/json;charset=UTF-8"),
                Gson().toJson(params)
            )
        ).awaitAndHandle {
            callback(RefreshState.Failure(it))
        }?.data?.let {
            if (it) callback(RefreshState.Success(Unit))
            else callback(RefreshState.Error(Unit))
        }
    }
}

//暂停任务
fun pauseExam(
    paper_id: Int,
    callback: suspend (RefreshState<Unit>) -> Unit = {}
) {
    GlobalScope.launch(Dispatchers.Main) {
        ExamService.pauseTask(
            RequestBody.create(
                MediaType.parse("application/json;charset=UTF-8"),
                Gson().toJson(mapOf("paper_id" to paper_id))
            )
        ).awaitAndHandle {
            callback(RefreshState.Failure(it))
        }?.data?.let {
            if (it) callback(RefreshState.Success(Unit))
            else callback(RefreshState.Error(Unit))
        }
    }
}

//修改任务限制
fun changeLimit(
    paper_id: Int,
    paper_name: String,
    paper_hint: String,
    start_time: String,
    end_time: String,
    last_time: String, //toString()来解决Int与Long的不统一
    password: String,
    times: Int,
    number: Int, //答卷者做题数量（“题库抽题”）
    is_random: Boolean,
    callback: suspend (RefreshState<Unit>) -> Unit = {}
) {
    GlobalScope.launch(Dispatchers.Main) {
        val params = mapOf(
            "paper_id" to paper_id,
            "paper_name" to paper_name,
            "paper_hint" to paper_hint,
            "start_time" to start_time,
            "end_time" to end_time,
            "last_time" to last_time,
            "password" to password,
            "times" to times,
            "number" to number,
            "is_random" to is_random
        )
        ExamService.changeLimit(
            RequestBody.create(
                MediaType.parse("application/json;charset=UTF-8"),
                Gson().toJson(params)
            )
        ).awaitAndHandle {
            callback(RefreshState.Failure(it))
        }?.data?.let {
            if (it) callback(RefreshState.Success(Unit))
            else callback(RefreshState.Error(Unit))
        }
    }
}

//下载问卷
fun downloadExam(
    paper_id: Int,
    callback: suspend (RefreshState<String>) -> Unit = {}
) {
    GlobalScope.launch(Dispatchers.Main) {
        ExamService.downloadTask(
            RequestBody.create(
                MediaType.parse("application/json;charset=UTF-8"),
                Gson().toJson(mapOf("paper_id" to paper_id))
            )
        ).awaitAndHandle {
            callback(RefreshState.Failure(it))
        }?.data?.let {
            callback(RefreshState.Success(it))
        }
    }
}

//停止并删除问卷
fun deleteExam(
    paper_id: Int,
    callback: suspend (RefreshState<Unit>) -> Unit = {}
) {
    GlobalScope.launch(Dispatchers.Main) {
        ExamService.deleteTask(
            RequestBody.create(
                MediaType.parse("application/json;charset=UTF-8"),
                Gson().toJson(mapOf("paper_id" to paper_id))
            )
        ).awaitAndHandle {
            callback(RefreshState.Failure(it))
        }?.data?.let {
            if (it) callback(RefreshState.Success(Unit))
            else callback(RefreshState.Error(Unit))
        }
    }
}

//获取修改题目
fun getChangeExam(
    paper_id: Int,
    callback: suspend (RefreshState<Unit>) -> Unit = {}
) {
    GlobalScope.launch(Dispatchers.Main) {
        ExamService.getChangeTask(
            RequestBody.create(
                MediaType.parse("application/json;charset=UTF-8"),
                Gson().toJson(mapOf("paper_id" to paper_id))
            )
        ).awaitAndHandle {
            callback(RefreshState.Failure(it))
        }?.data?.let {
            ExamModel.updateChangedQuestion(it.question)
            callback(RefreshState.Success(Unit))
        }
    }
}

//提交修改
fun postChangeExam(
    question: List<ChangedQuestion>,
    paper_id: Int,
    callback: suspend (RefreshState<Unit>) -> Unit = {}
) {
    GlobalScope.launch(Dispatchers.Main) {
        ExamService.postChangeTask(
            RequestBody.create(
                MediaType.parse("application/json;charset=UTF-8"),
                Gson().toJson(mapOf("question" to question, "paper_id" to paper_id))
            )
        ).awaitAndHandle {
            callback(RefreshState.Failure(it))
        }?.data?.let {
            if (it) callback(RefreshState.Success(Unit))
            else callback(RefreshState.Error(Unit))
        }
    }
}


//获取我发布的
fun getPostedExam(
    callback: suspend (RefreshState<Unit>) -> Unit = {}
) {
    GlobalScope.launch(Dispatchers.Main) {
        ExamService.getMyPosted().awaitAndHandle {
            callback(RefreshState.Failure(it))
        }?.data?.let {
            ExamModel.updatePosted(it)
            callback(RefreshState.Success(Unit))
        }
    }
}

//获取和我相关的
fun getRelatedExam(
    callback: suspend (RefreshState<Unit>) -> Unit = {}
) {
    GlobalScope.launch(Dispatchers.Main) {
        ExamService.getMyRelated().awaitAndHandle {
            callback(RefreshState.Failure(it))
        }?.data?.let {
            ExamModel.updateRelated(it)
            callback(RefreshState.Success(Unit))
        }
    }
}

//答题时获取题目  (没有url，启动不起来，所以暂时注释了)
fun getExam(
    paper_id: Int,
    password: String,
    callback: suspend (RefreshState<Unit>) -> Unit = {}
) {
    GlobalScope.launch(Dispatchers.Main) {
        ExamService.getTask(
            RequestBody.create(
                MediaType.parse("application/json;charset=UTF-8"),
                Gson().toJson(mapOf("paper_id" to paper_id, "password" to password))
            )
        ).awaitAndHandle {
            callback(RefreshState.Failure(it))
        }?.data?.let {
            ExamModel.updateQuestions(it.paper_question)
            callback(RefreshState.Success(Unit))
        }
    }
}

//交卷
fun solveExam(
    paper_id: Int,
    submit_id: Int,
    my_answer: List<String>,
    callback: suspend (RefreshState<Unit>) -> Unit = {}
) {
    GlobalScope.launch(Dispatchers.Main) {
        val params = mapOf(
            "paper_id" to paper_id,
            "submit_id" to submit_id,
            "my_answer" to my_answer
        )
        ExamService.solveTask(
            RequestBody.create(
                MediaType.parse("application/json;charset=UTF-8"),
                Gson().toJson(params)
            )
        ).awaitAndHandle {
            callback(RefreshState.Failure(it))
        }?.data?.let {
            if (it) callback(RefreshState.Success(Unit))
            else callback(RefreshState.Error(Unit))
        }
    }
}

//答题者查看成绩
fun getScore(paper_id: Int, callback: suspend (RefreshState<Unit>) -> Unit = {}) {
    GlobalScope.launch(Dispatchers.Main) {
        ExamService.catScore(
            RequestBody.create(
                MediaType.parse("application/json;charset=UTF-8"),
                Gson().toJson(mapOf("paper_id" to paper_id))
            )
        ).awaitAndHandle {
            callback(RefreshState.Failure(it))
        }?.data?.let {
            ExamModel.updateScoreList(it)
            callback(RefreshState.Success(Unit))
        }
    }
}

//简易登录
fun login(userId: Int, callback: suspend (RefreshState<Unit>) -> Unit = {}) {
    GlobalScope.launch(Dispatchers.Main) {
        ExamService.login(
            RequestBody.create(
                MediaType.parse("application/json;charset=UTF-8"),
                Gson().toJson(mapOf("userId" to userId))
            )
        ).awaitAndHandle {
            callback(RefreshState.Failure(it))
        }?.data?.let {
            if (it) callback(RefreshState.Success(Unit))
            else callback(RefreshState.Error(Unit))
        }
    }
}