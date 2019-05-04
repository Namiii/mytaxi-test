package gyg.demo.mytaxitest.data.model

fun TaxiList.hasData(): Boolean =
    !list.isNullOrEmpty()
