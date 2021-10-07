package input.midi.events.option

import input.midi.events.MidiMessageSentEvent

interface MidiOptionsButtonActionEvent: MidiMessageSentEvent {
    val button: Byte
}