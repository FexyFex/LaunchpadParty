package util

interface TickObject {
    var clock: Float

    fun tick(tick: Tick) {
        clock += tick.updateLength
    }
}