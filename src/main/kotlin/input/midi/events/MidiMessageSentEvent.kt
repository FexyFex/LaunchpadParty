package input.midi.events


interface MidiMessageSentEvent: MidiEvent {
    val message: ByteArray
    val length: Int
    val status: Int
}