package gyg.demo.mytaxitest.taxiList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import gyg.demo.mytaxitest.R
import gyg.demo.mytaxitest.data.model.Taxi
import gyg.demo.mytaxitest.databinding.TaxiListItemLayoutBinding

class TaxiListAdapter(
    private val clickListener: OnItemClickListener<Taxi>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = listOf<Taxi>()

    fun updateItems(items: List<Taxi>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<TaxiListItemLayoutBinding>(
            LayoutInflater.from(parent.context),
            R.layout.taxi_list_item_layout,
            parent,
            false
        )
        binding.clickListener = clickListener
        return TaxiViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        (holder as TaxiViewHolder).bind(item)
    }

    class TaxiViewHolder(private val binding: TaxiListItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(taxi: Taxi) {
            binding.taxi = taxi
        }
    }
}

interface OnItemClickListener<Vehicle> {
    fun onClick(item: Vehicle)
}