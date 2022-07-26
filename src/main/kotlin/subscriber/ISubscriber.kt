package subscriber

interface ISubscriber<T> {
    fun process(data: T)
}