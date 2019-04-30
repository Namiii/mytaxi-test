package gyg.demo.mytaxitest.core

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun Disposable.addTo(disposable: CompositeDisposable): Disposable {
    disposable.add(this)
    return this
}