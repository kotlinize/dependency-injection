package com.kotlinizer.dependencyInjection

import com.kotlinizer.subscriber.ISubscriber

@Suppress("UNCHECKED_CAST")
class Injector private constructor() : IDependencyInjector {

    /**
     * The Registry of items held by the Dependency Injector.
     */
    private val registry = HashSet<RegistryItem<Any>>()

    private val subscriberRegistry = HashSet<RegistryItem<Any>>()

    companion object {
        private var injectorInstance: Injector? = null

        /**
         * Current [Injector] instance, if initialized. If not initialized, this will return a new [Injector] instance.
         */
        @get:Synchronized
        val instance: IDependencyInjector
            get() = injectorInstance?.resolve(IDependencyInjector::class.java)
                ?: injectorInstance
                ?: Injector().also { injectorInstance = it }
    }

    override fun <T> register(type: Class<T>, provider: T, identifier: String?): Boolean {
        return registry.add(
            RegistryItem(
                identifier = identifier,
                clazz = type,
                item = provider as Any
            )
        )
    }

    override fun <T> unregister(type: Class<T>, identifier: String?): Boolean {
        registry.first { it.clazz == type && it.identifier == identifier }.let {
            return registry.remove(it)
        }
    }

    override fun <T> resolve(type: Class<T>, identifier: String?): T? {
        return registry.find { identifier == it.identifier && type == it.clazz }?.item as? T
    }

    override fun <T> publish(type: Class<T>, data: T, identifier: String?) {
        subscriberRegistry.filter { it.clazz == type && it.identifier == identifier }.forEach {
            (it.item as ISubscriber<T>).process(data = data)
        }
    }

    override fun unloadDependencies() {
        registry.clear()
        subscriberRegistry.clear()
    }

    override fun <T> addSubscriber(type: Class<T>, subscriber: ISubscriber<T>, identifier: String?): Boolean {
        return subscriberRegistry.add(
            RegistryItem(
                identifier = identifier,
                clazz = type,
                item = subscriber as Any
            )
        )
    }

    override fun <T> removeSubscriber(type: Class<T>, identifier: String?): Boolean {
        subscriberRegistry.first { it.clazz == type && it.identifier == identifier }.let {
            return subscriberRegistry.remove(it)
        }
    }

    private data class RegistryItem<S>(
        val identifier: String?,
        val clazz: Class<*>,
        val item: S
    )
}