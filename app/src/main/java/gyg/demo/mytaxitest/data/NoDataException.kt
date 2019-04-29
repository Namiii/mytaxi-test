package gyg.demo.mytaxitest.data

class NoDataException : Throwable() {
    override val message: String?
        get() = "The taxi list was empty!"

}
