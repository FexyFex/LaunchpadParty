package input.midi.message.note

data class MidiNoteOnMessage(override val key: Byte, val strength: Byte): MidiNoteMessage
