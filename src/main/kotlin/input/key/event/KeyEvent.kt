package input.key.event

import eventbus.Event


interface KeyEvent: Event {
    val keyCode: Int
    val keyChar: Char
    val keyType: KeyType
}