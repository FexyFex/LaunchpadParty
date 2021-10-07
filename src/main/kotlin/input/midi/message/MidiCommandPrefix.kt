package input.midi.message

enum class MidiCommandPrefix(val value: Byte) {
    NOTE_ACTION(0x90.toByte()),
    OPTIONBUTTON_ACTION((-80).toByte()),
    UNKNOWN(0x00)
}