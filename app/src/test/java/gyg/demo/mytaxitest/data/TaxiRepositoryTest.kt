package gyg.demo.mytaxitest.data

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import gyg.demo.mytaxitest.data.model.Coordinate
import gyg.demo.mytaxitest.data.model.Place
import gyg.demo.mytaxitest.data.model.TaxiList
import gyg.demo.mytaxitest.data.model.TaxiType
import gyg.demo.mytaxitest.di.CustomSerializerDeserializer
import gyg.demo.mytaxitest.taxiList.data.Hamburg
import io.reactivex.observers.TestObserver
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@RunWith(JUnit4::class)
class TaxiRepositoryTest {

    private val mockWebServer = MockWebServer()

    private lateinit var taxiRepository: TaxiRepository
    private lateinit var taxiService: TaxiService

    private val defaultPlace =
        Place(
            Coordinate(0f, 0f),
            Coordinate(0f, 0f)
        )

    @Before
    fun setup() {
        //set up for MockWebServer to work correctly
        val builder = GsonBuilder()
        builder.registerTypeAdapter(TaxiType::class.java, CustomSerializerDeserializer())
        val gson = builder.create()

        val retrofit = Retrofit.Builder()
            .baseUrl(TEST_BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        taxiService = retrofit.create(TaxiService::class.java)
        taxiRepository = TaxiRepository(taxiService)
        mockWebServer.start(MOCK_WEB_SERVER_PORT)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }


    @Test
    fun getListOfTaxis() {
        val testObserver = TestObserver<TaxiList>()

        val testResource = "/taxis.txt".getTestResource()
        mockWebServer.enqueue(MockResponse().setBody(testResource).setResponseCode(200))

        taxiRepository.getInitTaxis().subscribe(testObserver)

        testObserver.run {
            assertEquals(30, values()[0].list.size)
            assertEquals(911174, values()[0].list[0].id)
        }

        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
    }

    @Test
    fun getEmptyList() {
        val testObserver = TestObserver<TaxiList>()

        val test = "/taxis_empty.txt".getTestResource()
        mockWebServer.enqueue(MockResponse().setBody(test).setResponseCode(200))

        taxiRepository.getTaxis(defaultPlace).subscribe(testObserver)

        testObserver.run {
            assertEquals(0, values()[0].list.size)
        }

        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
    }

    @Test
    fun getError() {
        val testObserver = TestObserver<TaxiList>()

        mockWebServer.enqueue(MockResponse().setResponseCode(403))

        taxiRepository.getTaxis(defaultPlace).subscribe(testObserver)

        testObserver.run {
            assertEquals("HTTP 403 Client Error", errors()[0].message)
        }

        testObserver.assertNoValues()
        assertEquals(1, testObserver.errorCount())
    }

    @Test
    fun checkTaxiListUrl() {
        val testResource = "/taxis.txt".getTestResource()
        mockWebServer.enqueue(MockResponse().setBody(testResource).setResponseCode(200))

        val hamburg = Hamburg()
        taxiRepository.getTaxis(hamburg).subscribe()

        val request = mockWebServer.takeRequest()
        assertEquals(
            "/?p1Lat=${hamburg.bound1.lat}" +
                    "&p1Lon=${hamburg.bound1.long}" +
                    "&p2Lat=${hamburg.bound2.lat}" +
                    "&p2Lon=${hamburg.bound2.long}"
            , request.path
        )
    }

    @Test
    fun checkCacheExists() {
        val testResource = "/taxis.txt".getTestResource()
        mockWebServer.enqueue(MockResponse().setBody(testResource).setResponseCode(200))

        taxiRepository.getInitTaxis().subscribe()

        assertTrue(taxiRepository.hasCachedTaxiList())
    }

    @Test
    fun checkCacheNotExist() {
        val testResource = "/taxis_empty.txt".getTestResource()
        mockWebServer.enqueue(MockResponse().setBody(testResource).setResponseCode(200))

        taxiRepository.getTaxis(defaultPlace).subscribe()

        assertFalse(taxiRepository.hasCachedTaxiList())
    }

    @Test
    fun getCacheTaxiList() {
        val testResource = "/taxis.txt".getTestResource()
        mockWebServer.enqueue(MockResponse().setBody(testResource).setResponseCode(200))

        taxiRepository.getInitTaxis().subscribe()
        val data = taxiRepository.getCachedTaxiList()

        assertEquals(30, data.list.size)
        assertEquals(911174, data.list[0].id)
    }

    companion object {
        private const val TEST_BASE_URL = "http://localhost:8080"
        private const val MOCK_WEB_SERVER_PORT = 8080
    }

    private fun String.getTestResource(): String? {
        return this.javaClass::class.java.getResource(this)?.readText()
    }
}
