package input.key.event

data class KeyPressedEvent(
    override val keyCode: Int,
    override val keyChar: Char,
    override val keyType: KeyType
) : KeyEvent