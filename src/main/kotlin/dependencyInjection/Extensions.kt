package dependencyInjection

/**
 * Assists in publishing any object to the dependency injector.
 *
 * @param injectorInstance The [IDependencyInjector] instance intended to be published to, if provided. If not provided,
 * the current instance will be used.
 * @param identifier The [String] value of the identifier that was used when registering a subscriber.
 */
fun Any.publishToInjector(injectorInstance: IDependencyInjector? = null, identifier: String? = null) {
    injectorInstance?.publish(type = this.javaClass, data = this, identifier = identifier)
        ?: Injector.instance.publish(type = this.javaClass, data = this, identifier = identifier)
}

/**
 * Assists in registering any object to the dependency injector.
 *
 * @param identifier The [String] value of the identifier used to link to this referenced object, if provided.
 */
fun Any.registerToInjector(identifier: String? = null) {
    Injector.instance.register(this.javaClass, this, identifier)
}
