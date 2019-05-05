package gyg.demo.mytaxitest.core

import androidx.lifecycle.ViewModel
import gyg.demo.mytaxitest.OpenClassOnDebug
import io.reactivex.disposables.CompositeDisposable

@OpenClassOnDebug
abstract class BaseViewModel : ViewModel() {

    val disposable = CompositeDisposable()

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}
