package dependencyInjection

import subscriber.ISubscriber

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

    fun <T> addSubscriber(type: Class<T>, subscriber: ISubscriber<T>, identifier: String? = null): Boolean

    fun <T> removeSubscriber(type: Class<T>, identifier: String? = null): Boolean

    fun <T> publish(type: Class<T>, data: T, identifier: String? = null)

    fun unloadDependencies()
}