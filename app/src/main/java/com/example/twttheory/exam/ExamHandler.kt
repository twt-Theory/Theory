package com.example.twttheory.exam

import com.example.twttheory.service.RefreshState
import com.example.twttheory.service.awaitAndHandle
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

fun pause(
    callback: suspend (RefreshState<Unit>) -> Unit = {}, paper_id: Int
) {
    GlobalScope.launch(Dispatchers.Main) {
        ExamService.pauseTask(RequestBody.create(
            MediaType.parse("multipart/form-data"),
            paper_id.toString()
        )).awaitAndHandle {
            callback(RefreshState.Failure(it))
        }?.data?.let {
            callback(RefreshState.Success(Unit))
        }
    }
}