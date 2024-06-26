package com.chirag047.tuber.Common

public sealed class ResponseType<T>(val data: T? = null, val errorMsg: String? = null) {
    public class Sucess<T>(data: T? = null) : ResponseType<T>(data = data)
    public class Error<T>(errorMsg: String) : ResponseType<T>(errorMsg = errorMsg)
    public class Loading<T>() : ResponseType<T>()

}