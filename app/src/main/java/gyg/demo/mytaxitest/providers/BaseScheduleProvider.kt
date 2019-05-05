package gyg.demo.mytaxitest.providers

import io.reactivex.Scheduler
import org.jetbrains.annotations.NotNull

interface BaseScheduleProvider {

    @NotNull
    fun computation(): Scheduler

    @NotNull
    fun io(): Scheduler

    @NotNull
    fun ui(): Scheduler
}
