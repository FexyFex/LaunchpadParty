package input.midi.message.button


data class MidiOptionsButtonOnMessage(override val button: Byte, val param: Byte): MidiOptionsButtonMessage
