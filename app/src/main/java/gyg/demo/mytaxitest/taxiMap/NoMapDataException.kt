package gyg.demo.mytaxitest.taxiMap

class NoMapDataException: Throwable() {
    override val message: String?
        get() = "Could not find any taxis within the given bound, try searching elsewhere!"
}