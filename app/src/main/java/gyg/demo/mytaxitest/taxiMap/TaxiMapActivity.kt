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
import gyg.demo.mytaxitest.core.makeLongToast
import gyg.demo.mytaxitest.data.ResultWrapper
import gyg.demo.mytaxitest.databinding.ActivityTaxiMapBinding
import gyg.demo.mytaxitest.taxiList.data.Hamburg
import gyg.demo.mytaxitest.taxiMap.TaxiMapActivity.Companion.TAXI_ID_DATA_NAME
import gyg.demo.mytaxitest.taxiMap.data.TaxiMapState
import javax.inject.Inject

class TaxiMapActivity : BaseActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityTaxiMapBinding
    private var viewModel: TaxiMapViewModel? = null
    private lateinit var state: TaxiMapState

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

        val taxiId = intent.getIntExtra(TAXI_ID_DATA_NAME, 0)
        state = if (taxiId == 0) {
            TaxiMapState.ListState
        } else {
            TaxiMapState.SingleState(taxiId)
        }

        binding.mapSearchButton.setOnClickListener {
            searchOnMapBounds()
        }

        viewModel?.let { vm ->
            vm.listData.observe(this, Observer {
                when (it) {
                    is ResultWrapper.Success -> {
                        mapManager.loadTaxis(it.value.list)
                    }
                    is ResultWrapper.Failure -> {
                        makeLongToast(it.error.localizedMessage)
                    }
                }

                binding.hideSearchButton = false
            })

            vm.singleData.observe(this, Observer {
                when (it) {
                    is ResultWrapper.Success -> {
                        mapManager.loadTaxi(it.value)
                    }
                    is ResultWrapper.Failure -> {
                        makeLongToast(it.error.localizedMessage)
                    }
                }
                binding.hideSearchButton = false
            })
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mapManager.init(googleMap, Hamburg())
        displayDataOnMap()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun displayDataOnMap() {
        when (state) {

            is TaxiMapState.SingleState -> showSelectedTaxiOnMap((state as TaxiMapState.SingleState).id)
            is TaxiMapState.ListState -> searchOnMapBounds()
        }

        binding.hideSearchButton = true
    }

    private fun showSelectedTaxiOnMap(taxiId: Int) {
        viewModel?.getSelectedTaxi(taxiId)
    }

    private fun searchOnMapBounds() {
        val bounds = mapManager.getMapBounds()
        viewModel?.getTaxisAtBounds(
            bounds.northeast.latitude,
            bounds.northeast.longitude,
            bounds.southwest.latitude,
            bounds.southwest.longitude
        )
    }

    companion object {
        const val TAXI_ID_DATA_NAME = "taxi_id"
    }
}

fun Context.startTaxiMapActivity() {
    val intent = Intent(this, TaxiMapActivity::class.java)
    startActivity(intent)
}

fun Context.startTaxiMapActivity(taxiId: Int) {
    val intent = Intent(this, TaxiMapActivity::class.java)
    intent.putExtra(TAXI_ID_DATA_NAME, taxiId)
    startActivity(intent)
}
