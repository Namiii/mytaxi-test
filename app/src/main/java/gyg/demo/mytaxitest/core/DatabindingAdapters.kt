package gyg.demo.mytaxitest.core

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import gyg.demo.mytaxitest.R
import gyg.demo.mytaxitest.data.model.TaxiType
import gyg.demo.mytaxitest.data.model.Taxi

@BindingAdapter("setTaxiIcon")
fun setTaxiIcon(view: ImageView, taxi: Taxi?) {
    taxi?.let {
        view.setImageResource(
            when (taxi.type) {
                TaxiType.NormalTaxi -> R.drawable.ic_vehicle_normal
                TaxiType.PoolingTaxi -> R.drawable.ic_vehicle_pooling
            }
        )
    }
}

@BindingAdapter("setTaxiType")
fun setTaxiType(view: TextView, taxi: Taxi?) {
    taxi?.let {
        val taxiTypeText = when (taxi.type) {
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
