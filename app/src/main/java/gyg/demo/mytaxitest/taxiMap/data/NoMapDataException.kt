package gyg.demo.mytaxitest.taxiMap.data

class NoMapDataException: Throwable() {
    override val message: String?
        get() = "Could not find any taxis within the given bound, try searching elsewhere!"
}
