package input.midi.events.option

data class MidiOptionsButtonOnEvent(
    override val message: ByteArray,
    override val button: Byte,
    val param: Byte,
    override val length: Int,
    override val status: Int
): MidiOptionsButtonActionEvent