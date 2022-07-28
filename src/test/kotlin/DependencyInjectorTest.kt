import com.kotlinizer.dependencyInjection.IDependencyInjector
import com.kotlinizer.dependencyInjection.Injector
import com.kotlinizer.dependencyInjection.publishToInjector
import com.kotlinizer.dependencyInjection.registerToInjector
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.kotlinizer.subscriber.ISubscriber
import kotlin.test.*

class DependencyInjectorTest {

    lateinit var injector: IDependencyInjector
    private val testName = Name(
        firstName = FIRST_NAME,
        lastName = LAST_NAME,
        phoneNumber = PHONE_NUMBER
    )

    companion object {
        private const val OWNER_NAME = "owner_name"
        private const val FIRST_NAME = "kotlinizer"
        private const val LAST_NAME = "kotlin-ftw"
        private const val PHONE_NUMBER = 5555555555L
    }

    @BeforeEach
    fun setup() {
        // Given an Injection Implementation.
        injector = Injector.instance

        // Given an object registered into the dependency injector.
        injector.register(
            type = Name::class.java,
            provider = testName,
            identifier = OWNER_NAME
        )
    }

    @AfterEach
    fun tearDown() {
        injector.unloadDependencies()
    }

    @Test
    fun testInitialization() {
        // Given an Injector Instance.
        assertNotNull(injector)

        // Given an object from an Injector.
        val testName = injector.resolve(Name::class.java, OWNER_NAME)

        // Assert the sample object is not null.
        assertNotNull(testName)

        // Assert the Content Matches.
        assertEquals(FIRST_NAME, testName.firstName, "First Name did not match.")
        assertEquals(LAST_NAME, testName.lastName, "Last Name did not match.")
        assertEquals(PHONE_NUMBER, testName.phoneNumber, "Phone Number did not match.")

        // Ensure reference is equal to original object.
        assertSame(testName, this.testName)
    }

    @Test
    fun testUnregisterDependency() {
        // Given an Injector Instance.
        assertNotNull(injector)

        // Given an object from an Injector.
        val testName = injector.resolve(Name::class.java, OWNER_NAME)

        // Assert the sample object is not null.
        assertNotNull(testName)

        // Ensure the object was successfully unregistered from the dependency injector.
        assertTrue(injector.unregister(Name::class.java, OWNER_NAME))

        // Re-resolve the dependency to ensure not present.
        val unregistered = injector.resolve(Name::class.java, OWNER_NAME)

        // Assert this object is now null after unregistering.
        assertNull(unregistered, "Resolved dependency is not null.")
    }

    @Test
    fun testExtensionFunction() {
        // Given an Injector Instance.
        assertNotNull(injector)

        val name = Name(
            firstName = "Mike",
            lastName = "Reynolds",
            phoneNumber = PHONE_NUMBER
        )

        name.registerToInjector(FIRST_NAME + LAST_NAME)

        val resolvedDependency = injector.resolve(Name::class.java, FIRST_NAME + LAST_NAME)

        assertNotNull(resolvedDependency, "Dependency was unexpectedly null.")

        assertSame(name, resolvedDependency, "Object was not the same.")

        name.publishToInjector()
    }

    @Test
    fun testPublisher() {
        // Given an Injector Instance.
        assertNotNull(injector)

        injector.addSubscriber(Name::class.java, TestSubscriber())
        injector.addSubscriber(Long::class.java, TestSubscriber2())

        injector.publish(Name::class.java, testName)
    }

    private data class Name(
        val firstName: String,
        val lastName: String,
        val phoneNumber: Long
    )

    private class TestSubscriber : ISubscriber<Name> {
        override fun process(data: Name) {
            assertIs<Name>(value = data, message = "Item not instance of Name.")
        }
    }

    private class TestSubscriber2 : ISubscriber<Long> {
        override fun process(data: Long) {
            assertIs<Long>(value = data, message = "Item not instance of Long.")
        }
    }
}