package input.midi

import input.midi.message.DissectedMidiMessage
import input.midi.message.MidiCommandPrefix
import input.midi.message.button.MidiOptionsButtonMessage
import input.midi.message.button.MidiOptionsButtonOffMessage
import input.midi.message.button.MidiOptionsButtonOnMessage
import input.midi.message.note.MidiNoteMessage
import input.midi.message.note.MidiNoteOffMessage
import input.midi.message.note.MidiNoteOnMessage


private const val off: Byte = 0

fun dissectMessage(message: ByteArray): DissectedMidiMessage? {
    val command = enumValues<MidiCommandPrefix>().find { it.value == message[0] } ?: MidiCommandPrefix.UNKNOWN
    val instructions = ByteArray(message.size - 1) { message[it + 1] }

    return when (command) {
        MidiCommandPrefix.NOTE_ACTION -> generateNoteActionMessage(instructions)
        MidiCommandPrefix.OPTIONBUTTON_ACTION -> generateOptionsButtonActionMessage(instructions)
        MidiCommandPrefix.UNKNOWN -> null
    }
}


private fun generateNoteActionMessage(instructions: ByteArray): MidiNoteMessage {
    return if (instructions[1] == off)
        MidiNoteOffMessage(instructions[0])
    else
        MidiNoteOnMessage(instructions[0], instructions[1])
}

private fun generateOptionsButtonActionMessage(instructions: ByteArray): MidiOptionsButtonMessage {
    return if (instructions[1] == off)
        MidiOptionsButtonOffMessage(instructions[0])
    else
        MidiOptionsButtonOnMessage(instructions[0], instructions[1])
}