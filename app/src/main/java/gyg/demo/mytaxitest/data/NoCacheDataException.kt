package gyg.demo.mytaxitest.data

class NoCacheDataException : Throwable() {
    override val message: String?
        get() = "The selected taxi was not found in cache, go back and try another one!"
}