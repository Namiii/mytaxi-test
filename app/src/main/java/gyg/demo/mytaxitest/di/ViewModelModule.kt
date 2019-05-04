package gyg.demo.mytaxitest.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import gyg.demo.mytaxitest.taxiList.TaxiListViewModel
import gyg.demo.mytaxitest.taxiMap.TaxiMapViewModel

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(TaxiListViewModel::class)
    abstract fun bindTaxiListViewModel(viewModel: TaxiListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TaxiMapViewModel::class)
    abstract fun bindTaxiMapViewModel(viewModel: TaxiMapViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: CustomViewModelFactory): ViewModelProvider.Factory
}
