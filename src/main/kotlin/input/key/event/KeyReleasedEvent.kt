package input.key.event

class KeyReleasedEvent(
    override val keyCode: Int,
    override val keyChar: Char,
    override val keyType: KeyType
) : KeyEvent