package gyg.demo.mytaxitest.providers

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers


class ImmediateScheduleProviders : BaseScheduleProvider {
    override fun computation(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun io(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun ui(): Scheduler {
        return Schedulers.trampoline()
    }
}
