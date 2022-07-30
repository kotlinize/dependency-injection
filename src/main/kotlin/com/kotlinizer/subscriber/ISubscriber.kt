package com.kotlinizer.subscriber

/**
 * Interface for a Subscriber to listen for published objects.
 */
interface ISubscriber<T> {

    /**
     * Function to process an object being published.
     *
     * @param data The data of a Published object to be processed.
     */
    fun process(data: T)
}