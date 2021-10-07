import equalizer.Equalizer
import input.key.event.KeyPressedEvent
import input.key.event.KeyType


fun main() {
    val engine = Engine(15)
    val equalizer = Equalizer("Launchpad MK2")

    engine.on<KeyPressedEvent>(1) {
        if (it.keyType == KeyType.ESCAPE) engine.requestStop()
        if (it.keyChar == 'p') equalizer.startAnimation(Equalizer.pulseAnimation)
    }

    engine.run { tick ->
        equalizer.tick(tick)
    }

    equalizer.shutdown()
}