package input.midi.message.note

import input.midi.message.DissectedMidiMessage


interface MidiNoteMessage: DissectedMidiMessage {
    val key: Byte
}
