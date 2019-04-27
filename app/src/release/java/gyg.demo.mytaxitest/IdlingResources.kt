package gyg.demo.mytaxitest

import okhttp3.OkHttpClient

abstract class IdlingResources {
    companion object {
        fun registerOkHttp(client: OkHttpClient) {}
    }
}