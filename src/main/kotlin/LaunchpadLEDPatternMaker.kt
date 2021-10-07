import input.midi.MidiInputListener
import input.midi.events.note.MidiNoteOnEvent
import input.midi.events.option.MidiOptionsButtonOnEvent
import launchpad.Color
import launchpad.OptionButton


class LaunchpadLEDPatternMaker(name: String) {
    private var recording = false
    private val inputListener = MidiInputListener(name).open()
    private val activeButtons = mutableMapOf<Byte, Byte>()
    private var buttonLastPressed: Byte? = null

    val currentLightUpData: LaunchpadCanvasData?
        get() {
            if (!recording) return null
            return LaunchpadCanvasData(activeButtons.map { LightUp(it.key, it.value) })
        }

    init {
        inputListener.on<MidiNoteOnEvent>(this) { event ->
            if (recording) {
                if (activeButtons[event.note] != null)
                    activeButtons.remove(event.note)
                else
                    activeButtons[event.note] = Color.WHITE.num

                buttonLastPressed = event.note
            }
        }

        inputListener.on<MidiOptionsButtonOnEvent>(this) { event ->
            if (buttonLastPressed == null) return@on

            val button = enumValues<OptionButton>().first { it.num == event.button }
            val currentColor = activeButtons[buttonLastPressed!!] ?: throw Exception("what the fuck")

            when (button) {
                OptionButton.UP -> {
                    var target = currentColor + 16
                    if (target > 127) target = 0 + (target - 127)
                    activeButtons[buttonLastPressed!!] = target.toByte()
                }

                OptionButton.DOWN -> {
                    var target = currentColor - 16
                    if (target < 0) target += 127
                    activeButtons[buttonLastPressed!!] = target.toByte()
                }

                OptionButton.RIGHT -> {
                    var target = currentColor + 1
                    if (target > 127) target = 0
                    activeButtons[buttonLastPressed!!] = target.toByte()
                }

                OptionButton.LEFT -> {
                    var target = currentColor - 1
                    if (target < 0) target = 127
                    activeButtons[buttonLastPressed!!] = target.toByte()
                }

                else -> return@on
            }
        }
    }


    fun startRecording() {
        if (!recording)
            recording = true
    }

    fun finish(): LaunchpadCanvasData {
        if (!recording) throw Exception("bruh are you redarted?")
        val data = LaunchpadCanvasData(activeButtons.map { LightUp(it.key, it.value) })
        recording = false
        activeButtons.clear()
        buttonLastPressed = null
        return data
    }


    fun close() = inputListener.close()
}