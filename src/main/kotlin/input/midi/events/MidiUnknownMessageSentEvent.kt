package input.midi.events

data class MidiUnknownMessageSentEvent(
    override val message: ByteArray,
    override val length: Int,
    override val status: Int
): MidiMessageSentEvent
