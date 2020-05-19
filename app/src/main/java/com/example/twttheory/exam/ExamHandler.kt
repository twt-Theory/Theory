package com.example.twttheory.exam

import com.example.twttheory.service.RefreshState
import com.example.twttheory.service.awaitAndHandle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody

//TODO: 暂停任务接口->任务应该有是否暂停的属性
//TODO: 获取我发布的/和我相关的 无Post参数？
//TODO：下载操作、获取发布/相关后的操作、答题操作

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
    callback: suspend (RefreshState<Unit>) -> Unit = {},
    paper_type: Int,
    paper_name: String,
    paper_hint: String,
    paper_question: List<PostQuestion>,
    start_time: String,
    end_time: String,
    last_time: Int,
    password: String,
    times: Int
) {
    GlobalScope.launch(Dispatchers.Main) {
        val params = mapOf(
            "paper_type" to paper_type.toString(),
            "paper_name" to paper_name,
            "paper_hint" to paper_hint,
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
            callback(RefreshState.Success(Unit))
        }
    }
}

//暂停任务
fun pauseExam(
    callback: suspend (RefreshState<Unit>) -> Unit = {}, paper_id: Int
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
            callback(RefreshState.Success(Unit))
        }
    }
}

//修改任务限制
fun changeExam(
    callback: suspend (RefreshState<Unit>) -> Unit = {},
    paper_id: Int,
    paper_name: String,
    paper_hint: String,
    start_time: String,
    end_time: String,
    last_time: Int,
    password: String,
    times: Int
) {
    GlobalScope.launch(Dispatchers.Main) {
        val params = mapOf(
            "paper_id" to paper_id.toString(),
            "paper_name" to paper_name,
            "paper_hint" to paper_hint,
            "start_time" to start_time,
            "end_time" to end_time,
            "last_time" to last_time.toString(),
            "password" to password,
            "times" to times.toString()
        )
        ExamService.changeTask(generateRequestBody(params)).awaitAndHandle {
            callback(RefreshState.Failure(it))
        }?.data?.let {
            callback(RefreshState.Success(Unit))
        }
    }
}

//下载问卷
fun downloadExam(
    callback: suspend (RefreshState<Unit>) -> Unit = {}, paper_id: Int
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
            //TODO: here!!!!!!!!!!!!!!!!!
            callback(RefreshState.Success(Unit))
        }
    }
}

//停止并删除问卷
fun deleteExam(
    callback: suspend (RefreshState<Unit>) -> Unit = {}, paper_id: Int
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
            callback(RefreshState.Success(Unit))
        }
    }
}

//获取我发布的
fun getPostedExam(
    callback: suspend (RefreshState<Unit>) -> Unit = {}
) {
    GlobalScope.launch(Dispatchers.Main) {
        ExamService.getMyPosted(
            mapOf()
        ).awaitAndHandle {
            callback(RefreshState.Failure(it))
        }?.data?.let {
            //TODO: here!!!!!!!!!!!!!!!!!
            callback(RefreshState.Success(Unit))
        }
    }
}

//获取和我相关的
fun getRelatedExam(
    callback: suspend (RefreshState<Unit>) -> Unit = {}
) {
    GlobalScope.launch(Dispatchers.Main) {
        ExamService.getMyRelated(
            mapOf()
        ).awaitAndHandle {
            callback(RefreshState.Failure(it))
        }?.data?.let {
            //TODO: here!!!!!!!!!!!!!!!!!
            callback(RefreshState.Success(Unit))
        }
    }
}

//答题时获取题目
fun getExam(
    callback: suspend (RefreshState<Unit>) -> Unit = {},
    paper_id: Int,
    password: String
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
            //TODO: here!!!!!!!!!!!!!!!!!
            callback(RefreshState.Success(Unit))
        }
    }
}

//交卷
fun solveExam(
    callback: suspend (RefreshState<Unit>) -> Unit = {},
    answer: List<String>
) {
    GlobalScope.launch(Dispatchers.Main) {
        ExamService.solveTask(
            RequestBody.create(
                MediaType.parse("multipart/form-data"), answer.toString()
            )
        ).awaitAndHandle {
            callback(RefreshState.Failure(it))
        }?.data?.let {
            //TODO: here!!!!!!!!!!!!!!!!!
            callback(RefreshState.Success(Unit))
        }
    }
}