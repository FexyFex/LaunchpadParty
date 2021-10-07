package window

import input.key.KeyInputListener
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel

class Window(title: String, keyInputListener: KeyInputListener): JFrame(title) {
    init {
        val panel = JPanel()
        val label = JLabel()
        panel.add(label)
        this.add(panel)
        this.addKeyListener(keyInputListener)
        this.setSize(200, 100)
        this.isVisible = true
        this.defaultCloseOperation = DISPOSE_ON_CLOSE
    }
}