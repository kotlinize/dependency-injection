package com.kotlinizer.dependencyInjection

import com.kotlinizer.subscriber.ISubscriber

/**
 * The interface of the Dependency dependencyInjection.Injection System.
 */
interface IDependencyInjector {

    /**
     * Registers a dependency into the Dependency Injector.
     *
     * @param type The [Class] of the dependency to store.
     * @param provider The Value [T] to store.
     * @param identifier The [String] Identifier to store, if multiple items of the same type are to be stored.
     * @return The [Boolean] value whether the dependency was successfully stored.
     */
    fun <T> register(
        type: Class<T>,
        provider: T,
        identifier: String? = null
    ): Boolean

    /**
     * Unregisters a dependency from the Dependency Injector.
     *
     * @param type The [Class] of the dependency to remove.
     * @param identifier The [String] Identifier to remove, if multiples are currently stored.
     * @return The [Boolean] value whether the dependency was successfully unregistered.
     */
    fun <T> unregister(type: Class<T>, identifier: String? = null): Boolean

    /**
     * Resolves/Retrieves a single dependency from the provided criteria.
     *
     * @param type The [Class] of the dependency to return.
     * @param identifier The [String] Identifier to return, if multiples are currently stored.
     * @return The value [T] retrieved from the Dependency Injector.
     */
    fun <T> resolve(type: Class<T>, identifier: String? = null): T?

    /**
     * Adds a subscriber of a given Class Type.
     *
     * @param type The [Class] of the dependency to return.
     * @param subscriber The [ISubscriber] implementation, or `WHO` is subscribing/listening.
     * @param identifier The [String] Identifier to store, if multiple items of the same type are to be stored.
     * @return The [Boolean] value whether the subscriber was successfully added, or not.
     */
    fun <T> addSubscriber(type: Class<T>, subscriber: ISubscriber<T>, identifier: String? = null): Boolean

    /**
     * Removes a subscriber of a given Class Type.
     *
     * @param type The [Class] of the dependency to return.
     * @param identifier The [String] Identifier to remove, if multiple items of the same type are currently stored.
     * @return The [Boolean] value whether the subscriber was successfully removed, or not.
     */
    fun <T> removeSubscriber(type: Class<T>, identifier: String? = null): Boolean

    /**
     * Publishes data to known subscribers.
     *
     * @param type The [Class] of the data to be published.
     * @param identifier The [String] value of the subscriber to publish to.
     */
    fun <T> publish(type: Class<T>, data: T, identifier: String? = null)

    /**
     * Unloads all the dependencies currently stored in the Injector.
     */
    fun unloadDependencies()
}