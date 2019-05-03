package gyg.demo.mytaxitest.taxiMap

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import gyg.demo.mytaxitest.R
import gyg.demo.mytaxitest.core.BaseActivity
import gyg.demo.mytaxitest.data.ResultWrapper
import gyg.demo.mytaxitest.taxiList.data.Hamburg
import javax.inject.Inject

class TaxiMapActivity : BaseActivity(), OnMapReadyCallback {

    var viewModel: TaxiMapViewModel? = null

    private lateinit var mMap: GoogleMap

    @Inject
    lateinit var mapManager: MapManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_taxi_map)

        if (viewModel == null) {
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(TaxiMapViewModel::class.java)
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel?.let { vm ->
            vm.listData.observe(this, Observer {
                when (it) {

                    is ResultWrapper.Success -> mapManager.loadTaxis(it.value.list)
                    is ResultWrapper.Failure -> {
                    }
                }
            })
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val hamburg = mapManager.getPlaceCenter(Hamburg())
        mMap.addMarker(MarkerOptions().position(hamburg).title("Marker in Hamburg"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hamburg, 12f))

        mapManager.init(mMap)
        val bounds = mapManager.getMapBounds()
        viewModel?.getTaxisAtBounds(
            bounds.northeast.latitude,
            bounds.northeast.longitude,
            bounds.southwest.latitude,
            bounds.southwest.longitude
        )

    }
}

fun Context.startTaxiMapActivity() {
    val intent = Intent(this, TaxiMapActivity::class.java)
    startActivity(intent)
}
