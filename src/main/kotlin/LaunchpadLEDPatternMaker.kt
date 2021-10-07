import input.midi.MidiInputListener
import input.midi.events.note.MidiNoteOnEvent
import input.midi.events.option.MidiOptionsButtonOnEvent
import launchpad.Color
import launchpad.NoteButton
import launchpad.OptionButton


class LaunchpadLEDPatternMaker(name: String) {
    private var recording = false
    private val inputListener = MidiInputListener(name)
    private val activeButtons = mutableMapOf<NoteButton, Color>()
    private var buttonLastPressed: NoteButton? = null

    val currentLightUpData: LaunchpadCanvasData
        get() {
            if (!recording) throw Exception("bruh are you redarted?")
            return LaunchpadCanvasData(activeButtons.map { LightUp(it.key, it.value) })
        }

    init {
        inputListener.on<MidiNoteOnEvent>(this) { event ->
            if (recording) {
                val button = enumValues<NoteButton>().first { it.num == event.note }

                if (activeButtons[button] != null)
                    activeButtons.remove(button)
                else
                    activeButtons[button] = Color.WHITE

                buttonLastPressed = button
            }
        }

        inputListener.on<MidiOptionsButtonOnEvent>(this) { event ->
            if (buttonLastPressed == null) return@on

            val button = enumValues<OptionButton>().first { it.num == event.button }
            val currentColor = activeButtons[buttonLastPressed!!] ?: throw Exception("what the fuck")

            when (button) {
                OptionButton.UP ->
                    activeButtons[buttonLastPressed!!] = enumValues<Color>().first { it.num == (currentColor.num + 16).toByte() }

                OptionButton.DOWN ->
                    activeButtons[buttonLastPressed!!] = enumValues<Color>().first { it.num == (currentColor.num - 16).toByte() }

                OptionButton.RIGHT ->
                    activeButtons[buttonLastPressed!!] = enumValues<Color>().first { it.num == (currentColor.num + 1).toByte() }

                OptionButton.LEFT ->
                    activeButtons[buttonLastPressed!!] = enumValues<Color>().first { it.num == (currentColor.num - 1).toByte() }

                else -> return@on
            }
        }
    }


    fun startRecording() {
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
}