package gyg.demo.mytaxitest

import androidx.test.espresso.IdlingRegistry
import com.jakewharton.espresso.OkHttp3IdlingResource
import okhttp3.OkHttpClient

abstract class IdlingResources {
    companion object {
        fun registerOkHttp(client: OkHttpClient) {
            IdlingRegistry.getInstance().register(
                OkHttp3IdlingResource.create(
                    "okhttp", client
                )
            )
        }
    }
}