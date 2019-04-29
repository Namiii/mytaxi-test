package gyg.demo.mytaxitest.data

import androidx.annotation.Keep
import gyg.demo.mytaxitest.data.model.TaxiList
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface TaxiService {
    @Keep
    @GET(".")
    fun getTaxis(
        @Query("p1Lat") bound1Lat: Float,
        @Query("p1Lon") bound1Lon: Float,
        @Query("p2Lat") bound2Lat: Float,
        @Query("p2Lon") bound2Lon: Float
    ): Observable<TaxiList>
}