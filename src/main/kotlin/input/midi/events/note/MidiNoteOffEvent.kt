package input.midi.events.note

import input.midi.events.MidiMessageSentEvent

data class MidiNoteOffEvent(
    override val message: ByteArray,
    override val note: Byte,
    override val length: Int,
    override val status: Int
): MidiMessageSentEvent, MidiNoteActionEvent
