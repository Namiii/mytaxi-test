package gyg.demo.mytaxitest.taxiList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gyg.demo.mytaxitest.R
import gyg.demo.mytaxitest.core.BaseFragment
import gyg.demo.mytaxitest.data.ResultWrapper
import gyg.demo.mytaxitest.data.model.Taxi
import gyg.demo.mytaxitest.databinding.TaxiListFragmentBinding

class TaxiListFragment : BaseFragment() {

    private lateinit var binding: TaxiListFragmentBinding

    var listener: TaxiListFragmentInteractionListener? = null
    var viewModel: TaxiListViewModel? = null

    private val adapter = TaxiListAdapter(object : OnItemClickListener<Taxi> {
        override fun onClick(item: Taxi) {
            listener?.taxiSelected(item.id)
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (viewModel == null) {
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(TaxiListViewModel::class.java)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.taxi_list_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.let {
            it.title = getString(R.string.list_title)
            it.setDisplayHomeAsUpEnabled(false)
        }
        setHasOptionsMenu(false)

        binding.taxiListRecyclerView.adapter = adapter
        binding.taxiListRecyclerView.layoutManager =
            LinearLayoutManager(
                context,
                RecyclerView.VERTICAL,
                false
            )
        binding.taxiListRecyclerView.addItemDecoration(MarginItemDecoration(context!!))

        viewModel?.let { vm ->
            vm.data.observe(this, Observer {
                when (it) {
                    is ResultWrapper.Success -> {
                        binding.taxiListSwipeLayout.isRefreshing = false
                        adapter.updateItems(it.value.list)
                        binding.hasData = true

                    }
                    is ResultWrapper.Failure -> {
                        binding.taxiListSwipeLayout.isRefreshing = false
                        binding.hasData = false
                    }
                }
            })
            getData()
        }

        binding.taxiListSwipeLayout.setOnRefreshListener {
            getData()
        }
    }

    private fun getData() {
        viewModel?.getInitTaxis()
        binding.taxiListSwipeLayout.isRefreshing = true
        binding.hasData = true
    }
}

interface TaxiListFragmentInteractionListener {
    fun taxiSelected(id: Int)
}
