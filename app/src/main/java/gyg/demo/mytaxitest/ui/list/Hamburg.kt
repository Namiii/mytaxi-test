package gyg.demo.mytaxitest.ui.list

import gyg.demo.mytaxitest.data.model.Place
import gyg.demo.mytaxitest.data.model.Coordinate

class Hamburg(
    override val bound1: Coordinate = Coordinate(53.694865f, 9.757589f),
    override val bound2: Coordinate = Coordinate(53.394655f, 10.099891f)
) : Place()