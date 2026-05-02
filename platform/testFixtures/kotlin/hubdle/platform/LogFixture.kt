package hubdle.platform

import com.javiersc.kotlin.stdlib.AnsiColor.Foreground.Cyan
import com.javiersc.kotlin.stdlib.AnsiColor.Foreground.Yellow
import com.javiersc.kotlin.stdlib.ansiColor

object LogFixture {

    fun lifecycle(message: () -> String): String {
        val label = "[Hubdle]".ansiColor(Cyan)
        val content = message().ansiColor(Yellow)
        return "$label $content"
    }
}
