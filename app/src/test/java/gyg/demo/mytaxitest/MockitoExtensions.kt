package gyg.demo.mytaxitest

import com.nhaarman.mockito_kotlin.createinstance.nonNullProvider
import org.mockito.Mockito

inline fun <reified T: Any> any() = Mockito.any(T::class.java) ?: nonNullProvider().createInstance(T::class)
