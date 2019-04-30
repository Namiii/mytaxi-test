package gyg.demo.mytaxitest.taxiList

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import gyg.demo.mytaxitest.core.BaseFragment
import gyg.demo.mytaxitest.data.ResultWrapper

class TaxiListFragment : BaseFragment() {

    var viewModel: TaxiListViewModel? = null

    var a = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (viewModel == null) {
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(TaxiListViewModel::class.java)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel?.let { vm ->
            vm.getInitTaxis()
            vm.data.observe(this, Observer {
                a = when (it) {
                    is ResultWrapper.Success -> {
                        true

                    }
                    is ResultWrapper.Failure -> {
                        false
                    }
                }
            })
        }

    }
}