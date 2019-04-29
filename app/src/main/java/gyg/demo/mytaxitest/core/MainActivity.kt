package gyg.demo.mytaxitest.core

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection
import gyg.demo.mytaxitest.R
import gyg.demo.mytaxitest.data.TaxiService
import gyg.demo.mytaxitest.data.model.hasData
import gyg.demo.mytaxitest.providers.BaseScheduleProvider
import gyg.demo.mytaxitest.taxiList.data.Hamburg
import io.reactivex.Observable
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var service: TaxiService

    @Inject
    lateinit var scheduleProvider: BaseScheduleProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_main)

        Observable.just(Hamburg())
            .flatMap {
                service.getTaxis(
                    it.bound1.lat,
                    it.bound1.long,
                    it.bound2.lat,
                    it.bound2.long
                )
            }
            .subscribeOn(scheduleProvider.io())
            .observeOn(scheduleProvider.ui())
            .subscribe({
                Log.d("TaxiType has data", it.hasData().toString())
                Log.d("TaxiType data: ", it.list[4].toString())
            }, {
                it.printStackTrace()
                Log.d("TAXI_NO_DATA", it.localizedMessage)
            })

    }
}
