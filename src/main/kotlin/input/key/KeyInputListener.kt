package input.key

import eventbus.EventBus
import input.key.event.*
import java.awt.event.KeyListener
import kotlin.reflect.KClass


class KeyInputListener: KeyListener {
    private val eventBus = EventBus<KeyEvent>()


    override fun keyPressed(e: java.awt.event.KeyEvent?) {
        if (e != null) eventBus.publish(KeyPressedEvent(e.keyCode, e.keyChar, findKeyType(e.keyCode)))
    }

    override fun keyReleased(e: java.awt.event.KeyEvent?) {
        if (e != null) eventBus.publish(KeyReleasedEvent(e.keyCode, e.keyChar, findKeyType(e.keyCode)))
    }

    override fun keyTyped(e: java.awt.event.KeyEvent?) {
        if (e != null) eventBus.publish(KeyTypedEvent(e.keyCode, e.keyChar, findKeyType(e.keyCode)))
    }


    fun <E: KeyEvent> on(type: KClass<E>, tag: Any, handler: (E) -> Unit) = eventBus.on(type, tag, handler)
    inline fun <reified E: KeyEvent> on(tag: Any, noinline handle: (E) -> Unit) = on(E::class, tag, handle)

    fun removeEvent(tag: Any) {
        eventBus.remove(tag)
    }


    companion object {
        private fun findKeyType(keyCode: Int): KeyType {
            return when (keyCode) {
                8 -> KeyType.BACKSPACE
                10 -> KeyType.ENTER
                16 -> KeyType.SHIFT
                17 -> KeyType.CONTROL
                18 -> KeyType.ALT
                27 -> KeyType.ESCAPE
                in 65..90 -> KeyType.CHARACTER
                in 112..123 -> KeyType.FUNCTION
                else -> KeyType.UNKNOWN
            }
        }
    }
}