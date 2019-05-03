package gyg.demo.mytaxitest.taxiMap

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import gyg.demo.mytaxitest.R
import gyg.demo.mytaxitest.core.BaseActivity
import gyg.demo.mytaxitest.data.ResultWrapper
import gyg.demo.mytaxitest.databinding.ActivityTaxiMapBinding
import gyg.demo.mytaxitest.taxiList.data.Hamburg
import javax.inject.Inject

class TaxiMapActivity : BaseActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityTaxiMapBinding
    private var viewModel: TaxiMapViewModel? = null

    @Inject
    lateinit var mapManager: MapManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_taxi_map)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (viewModel == null) {
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(TaxiMapViewModel::class.java)
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel?.let { vm ->
            vm.listData.observe(this, Observer {
                when (it) {

                    is ResultWrapper.Success -> {
                        mapManager.loadTaxis(it.value.list)
                        binding.hideSearchButton = false
                    }
                    is ResultWrapper.Failure -> {
                    }
                }
            })
        }

        binding.mapSearchButton.setOnClickListener {
            searchOnMapBounds()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mapManager.init(googleMap, Hamburg())
        searchOnMapBounds()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun searchOnMapBounds() {
        val bounds = mapManager.getMapBounds()
        viewModel?.getTaxisAtBounds(
            bounds.northeast.latitude,
            bounds.northeast.longitude,
            bounds.southwest.latitude,
            bounds.southwest.longitude
        )

        binding.hideSearchButton = true
    }
}

fun Context.startTaxiMapActivity() {
    val intent = Intent(this, TaxiMapActivity::class.java)
    startActivity(intent)
}
