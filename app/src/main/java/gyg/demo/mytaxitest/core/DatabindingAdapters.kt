package gyg.demo.mytaxitest.core

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import gyg.demo.mytaxitest.R
import gyg.demo.mytaxitest.data.model.TaxiType
import gyg.demo.mytaxitest.data.model.Vehicle

@BindingAdapter("setTaxiIcon")
fun setTaxiIcon(view: ImageView, vehicle: Vehicle?) {
    vehicle?.let {
        val colorFilterValue = when (vehicle.type) {
            is TaxiType.NormalTaxi -> {
                R.color.normal_taxi_icon_color
            }
            is TaxiType.PoolingTaxi -> {
                R.color.pooling_taxi_icon_color
            }
        }
        view.setColorFilter(colorFilterValue)
        view.setImageResource(R.drawable.ic_vehicle)
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
                R.string.normal_taxi_type
            }
        }
        view.setText(taxiTypeText)
    }
}