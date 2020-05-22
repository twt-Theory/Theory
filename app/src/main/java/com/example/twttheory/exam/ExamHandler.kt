package com.example.twttheory.exam

import com.example.twttheory.service.RefreshState
import com.example.twttheory.service.awaitAndHandle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody

//TODO：Model完善（更新recyclerView）

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
            "paper_type" to paper_type.toString(),
            "paper_name" to paper_name,
            "paper_hint" to paper_hint,
            "number" to number.toString(),
            "is_random" to is_random.toString(),
            "paper_question" to paper_question.toString(),
            "start_time" to start_time,
            "end_time" to end_time,
            "last_time" to last_time.toString(),
            "password" to password,
            "times" to times.toString()
        )
        ExamService.createTask(generateRequestBody(params)).awaitAndHandle {
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
                MediaType.parse("multipart/form-data"),
                paper_id.toString()
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
    last_time: String,
    password: String,
    times: String,
    callback: suspend (RefreshState<Unit>) -> Unit = {}
) {
    GlobalScope.launch(Dispatchers.Main) {
        val params = mapOf(
            "paper_id" to paper_id.toString(),
            "paper_name" to paper_name,
            "paper_hint" to paper_hint,
            "start_time" to start_time,
            "end_time" to end_time,
            "last_time" to last_time,
            "password" to password,
            "times" to times
        )
        ExamService.changeLimit(generateRequestBody(params)).awaitAndHandle {
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
    callback: suspend (RefreshState<Unit>) -> Unit = {}
) {
    GlobalScope.launch(Dispatchers.Main) {
        ExamService.downloadTask(
            RequestBody.create(
                MediaType.parse("multipart/form-data"),
                paper_id.toString()
            )
        ).awaitAndHandle {
            callback(RefreshState.Failure(it))
        }?.data?.let {
            //具体下载操作还没实现!!!!!!!!!!!!!
            callback(RefreshState.Success(Unit))
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
                MediaType.parse("multipart/form-data"),
                paper_id.toString()
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
                MediaType.parse("multipart/form-data"),
                paper_id.toString()
            )
        ).awaitAndHandle {
            callback(RefreshState.Failure(it))
        }?.data?.let {
            ExamModel.updateChangedQuestion(it)
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
        val params = mapOf(
            "question" to question.toString(),
            "paper_id" to paper_id.toString()
        )
        ExamService.postChangeTask(generateRequestBody(params)).awaitAndHandle {
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

//答题时获取题目
fun getExam(
    paper_id: Int,
    password: String,
    callback: suspend (RefreshState<Unit>) -> Unit = {}
) {
    GlobalScope.launch(Dispatchers.Main) {
        ExamService.getTask(
            RequestBody.create(
                MediaType.parse("multipart/form-data"), paper_id.toString()
            ),
            RequestBody.create(
                MediaType.parse("multipart/form-data"), password
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
    paper_question: List<HandleQuestion>,
    callback: suspend (RefreshState<Unit>) -> Unit = {}
) {
    GlobalScope.launch(Dispatchers.Main) {
        val params = mapOf(
            "paper_id" to paper_id.toString(),
            "submit_id" to submit_id.toString(),
            "paper_question" to paper_question.toString()
        )
        ExamService.solveTask(generateRequestBody(params)).awaitAndHandle {
            callback(RefreshState.Failure(it))
        }?.data?.let {
            if (it) callback(RefreshState.Success(Unit))
            else callback(RefreshState.Error(Unit))
        }
    }
}