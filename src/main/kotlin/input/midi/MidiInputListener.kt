package input.midi

import eventbus.EventBus
import input.midi.events.*
import input.midi.events.note.MidiNoteOffEvent
import input.midi.events.note.MidiNoteOnEvent
import input.midi.message.note.MidiNoteOffMessage
import input.midi.message.note.MidiNoteOnMessage
import midi.MidiDetector
import midi.MidiDeviceNotFoundException
import javax.sound.midi.MidiMessage
import javax.sound.midi.Receiver
import kotlin.reflect.KClass


class MidiInputListener(private val deviceName: String) {
    private val inputListener = MidiInputReceiverListener()
    private val device = MidiDetector.findMidiTransmitterDeviceByName(deviceName) ?: throw MidiDeviceNotFoundException(deviceName)

    init {
        device.transmitter.receiver = inputListener
    }

    fun open(): MidiInputListener {
        device.open()
        println("Listener device $deviceName opened")
        return this
    }

    fun <E: MidiEvent> on(type: KClass<E>, tag: Any, handler: (E) -> Unit) = inputListener.on(type, tag, handler)
    inline fun <reified E: MidiEvent> on(tag: Any, noinline handle: (E) -> Unit) = on(E::class, tag, handle)

    fun removeEvent(tag: Any) {
        inputListener.removeEvent(tag)
    }

    fun close() {
        device.close()
        println("Listener device $deviceName closed")
    }


    private class MidiInputReceiverListener: Receiver {
        val eventBus = EventBus<MidiEvent>()


        override fun send(message: MidiMessage?, timeStamp: Long) {
            if (message == null) return
            val dMsg = dissectMessage(message.message)

            val messageEvent = when (dMsg) {
                is MidiNoteOnMessage -> MidiNoteOnEvent(message.message, dMsg.key, dMsg.strength, message.length, message.status)
                is MidiNoteOffMessage -> MidiNoteOffEvent(message.message, dMsg.key, message.length, message.status)
                //is MidiOptionsButtonOnMessage ->
                else -> MidiUnknownMessageSentEvent(message.message, message.length, message.status)
            }

            eventBus.publish(messageEvent)
        }

        fun <E: MidiEvent> on(type: KClass<E>, tag: Any, handler: (E) -> Unit) = eventBus.on(type, tag, handler)
        inline fun <reified E: MidiEvent> on(tag: Any, noinline handle: (E) -> Unit) {
            eventBus.on(E::class, tag, handle)
        }

        fun removeEvent(tag: Any) {
            eventBus.remove(tag)
        }

        override fun close() {}
    }
}