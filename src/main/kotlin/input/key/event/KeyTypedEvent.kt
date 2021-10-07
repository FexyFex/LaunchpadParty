package input.key.event

data class KeyTypedEvent(
    override val keyCode: Int,
    override val keyChar: Char,
    override val keyType: KeyType
) : KeyEvent