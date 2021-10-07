import input.key.KeyInputListener
import input.key.event.KeyEvent
import util.Tick
import window.Window
import kotlin.reflect.KClass

class Engine(ticksPerSecond: Int) {
    private val inputListener = KeyInputListener()
    private val window = Window("Launchpad Control", inputListener)

    var ticksPerSecond: Int = ticksPerSecond
        set(value) {
            field = value
            optimalTime = 1.0 / value
        }

    private var optimalTime = 1.0 / this.ticksPerSecond

    private var stop = false

    fun <E : KeyEvent> on(type: KClass<E>, tag: Any, handler: (E) -> Unit) = inputListener.on(type, tag, handler)
    inline fun <reified E : KeyEvent> on(tag: Any, noinline handle: (E) -> Unit) = on(E::class, tag, handle)


    fun run(updateBlock: (tick: Tick) -> Unit) {
        var time = 0f
        var tickDelta: Float
        var tickCounter = 0
        var lastTpsTime = 0.0
        var lastLoopTime: Double = System.nanoTime() / 1_000_000_000.0

        while (!stop && window.isActive) {
            // Frames calculation logic
            //------------------------------------------------------------------------------------------
            val nowD: Double = System.nanoTime() / 1_000_000_000.0
            val updateLength: Float = (nowD - lastLoopTime).toFloat()
            time += updateLength
            lastLoopTime = nowD
            tickDelta = (updateLength / optimalTime).toFloat()
            lastTpsTime += updateLength
            tickCounter++
            if (lastTpsTime >= 1) {
                lastTpsTime = 0.0
                tickCounter = 0
            }
            //------------------------------------------------------------------------------------------
            // Frames calculation logic


            updateBlock(Tick(tickDelta, updateLength, time))


            // util.Tick limiting
            //------------------------------------------------------------------------------------------
            try {
                Thread.sleep(((lastLoopTime - nowD + optimalTime) * 1000.0).toLong())
            } catch (ignored: Exception) {
            }
            //------------------------------------------------------------------------------------------
            // util.Tick limiting
        }

        window.dispose()
    }


    fun requestStop() {
        stop = true
    }
}