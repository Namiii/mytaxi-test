package gyg.demo.mytaxitest.data

class NoCacheData : Throwable() {
    override val message: String?
        get() = "The selected taxi was not found in cache!"
}