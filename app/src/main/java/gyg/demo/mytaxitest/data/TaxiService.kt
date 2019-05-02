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
        @Query("p1Lat") bound1Lat: Double,
        @Query("p1Lon") bound1Lon: Double,
        @Query("p2Lat") bound2Lat: Double,
        @Query("p2Lon") bound2Lon: Double
    ): Observable<TaxiList>
}