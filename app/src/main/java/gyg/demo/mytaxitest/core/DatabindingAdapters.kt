package gyg.demo.mytaxitest.core

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import gyg.demo.mytaxitest.R
import gyg.demo.mytaxitest.data.model.TaxiType
import gyg.demo.mytaxitest.data.model.Vehicle

@BindingAdapter("setTaxiIcon")
fun setTaxiIcon(view: ImageView, vehicle: Vehicle?) {
    vehicle?.let {
        view.setImageResource(
            when (vehicle.type) {
                TaxiType.NormalTaxi -> R.drawable.ic_vehicle_normal
                TaxiType.PoolingTaxi -> R.drawable.ic_vehicle_pooling
            }
        )
    }
}

@BindingAdapter("setTaxiType")
fun setTaxiType(view: TextView, vehicle: Vehicle?) {
    vehicle?.let {
        val taxiTypeText = when (vehicle.type) {
            is TaxiType.NormalTaxi -> {
                R.string.normal_taxi_type
            }
            is TaxiType.PoolingTaxi -> {
                R.string.pooling_taxi_type
            }
        }
        view.setText(taxiTypeText)
    }
}

@BindingAdapter("goneIf")
fun goneIf(view: View, gone: Boolean) {
    view.visibility = if (gone) View.GONE else View.VISIBLE
}
