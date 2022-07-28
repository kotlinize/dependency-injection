package com.kotlinizer.subscriber

interface ISubscriber<T> {
    fun process(data: T)
}