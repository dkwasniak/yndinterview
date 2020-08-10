package com.damiankwasniak.data

import okhttp3.MediaType
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.ResponseBody
import retrofit2.Response

fun <T> createSuccessResponse(code: Int, response: T):
        Response<T> {
    return Response.success(response, createRawResponseForCode(code))
}

fun <T> createErrorResponse(code: Int): Response<T> {
    return Response.error(code, ResponseBody.create(MediaType.parse(""), "Error"))
}

fun createRawResponseForCode(code: Int): okhttp3.Response {
    return okhttp3.Response.Builder()
        .code(code)
        .message("")
        .protocol(Protocol.HTTP_1_1)
        .request(
            Request.Builder()
                .url("http://example.com")
                .build())
        .build()
}