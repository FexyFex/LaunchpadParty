package input.midi.message.button

import input.midi.message.DissectedMidiMessage


interface MidiOptionsButtonMessage: DissectedMidiMessage {
    val button: Byte
}