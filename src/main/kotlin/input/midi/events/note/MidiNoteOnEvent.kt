package input.midi.events.note

import input.midi.events.MidiMessageSentEvent

data class MidiNoteOnEvent(
    override val message: ByteArray,
    override val note: Byte,
    val strength: Byte,
    override val length: Int,
    override val status: Int
): MidiMessageSentEvent, MidiNoteActionEvent
