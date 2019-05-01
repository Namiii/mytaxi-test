package gyg.demo.mytaxitest.util

import android.annotation.SuppressLint
import android.view.View
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher


fun nthChildOf(matcher: Matcher<View>, position: Int): Matcher<View> {
    return object : TypeSafeMatcher<View>() {
        var currentPosition: Int = 0
        var hashCode: Int = 0

        @SuppressLint("DefaultLocale")
        override fun describeTo(description: Description) {
            description.appendText(String.format("child in position: %d ", position))
            matcher.describeTo(description)
        }

        override fun matchesSafely(view: View): Boolean {
            if (matcher.matches(view) && currentPosition++ == position) {
                hashCode = view.hashCode()
            }
            return view.hashCode() == hashCode
        }
    }
}
