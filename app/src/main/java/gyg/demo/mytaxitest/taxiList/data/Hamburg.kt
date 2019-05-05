package gyg.demo.mytaxitest.taxiList.data

import gyg.demo.mytaxitest.data.model.Coordinate
import gyg.demo.mytaxitest.data.model.Place

class Hamburg(
    override val bound1: Coordinate = Coordinate(53.694865, 9.757589),
    override val bound2: Coordinate = Coordinate(53.394655, 10.099891)
) : Place()
