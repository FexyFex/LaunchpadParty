import input.key.event.KeyPressedEvent
import input.key.event.KeyType
import launchpad.LaunchpadLEDControl


fun main() {
    val launchpadName = "Launchpad MK2"
    val engine = Engine(15)
    val control = LaunchpadLEDControl(launchpadName)
    val patternMaker = LaunchpadLEDPatternMaker(launchpadName)

    engine.on<KeyPressedEvent>(1) {
        if (it.keyType == KeyType.ESCAPE) engine.requestStop()
        if (it.keyType == KeyType.ENTER) patternMaker.startRecording()
        if (it.keyType == KeyType.BACKSPACE) patternMaker.finish()
    }

    engine.run { tick ->
        control.resetAllLED()
        patternMaker.currentLightUpData?.lightUps?.forEach {
            control.lightLED(it.button, it.color)
        }
    }

    patternMaker.close()
}