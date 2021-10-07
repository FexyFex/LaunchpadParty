package equalizer

import LightUp
import LaunchpadCanvasData
import animation.Animation
import animation.KeyFrame
import launchpad.Color
import launchpad.LaunchpadLEDControl
import launchpad.NoteButton
import util.AnimationObject
import util.Tick


class Equalizer(launchpadName: String): AnimationObject {
    override var clock: Float = 0f
    private val control = LaunchpadLEDControl(launchpadName).open()
    override var currentAnimation: Animation? = null
    override var animationTime: Float = 0f


    override fun startAnimation(animation: Animation) {
        currentAnimation = animation
        animationTime = 0f
    }

    override fun stopAnimation() {
        currentAnimation = null
        animationTime = 0f
    }

    override fun tick(tick: Tick) {
        super.tick(tick)
        control.resetAllLED()
        if (currentAnimation != null) {

            if (animationTime > currentAnimation!!.animationLength) {
                stopAnimation()
                return
            }

            currentKeyFrame!!.lightUpData.lightUps.forEach {
                control.lightLED(it.button, it.color)
            }
        }
    }


    fun shutdown() {
        control.close()
    }


    companion object {
        val pulseAnimation = Animation(4f,
            listOf(
                KeyFrame(
                    0f, LaunchpadCanvasData(
                        listOf(
                            LightUp(NoteButton.R1C1, Color.RED),
                            LightUp(NoteButton.R1C2, Color.RED),
                            LightUp(NoteButton.R1C3, Color.RED),
                        )
                    )
                ),
                KeyFrame(
                    2f, LaunchpadCanvasData(
                        listOf(
                            LightUp(NoteButton.R2C1, Color.RED),
                            LightUp(NoteButton.R2C2, Color.RED),
                            LightUp(NoteButton.R2C3, Color.RED),
                        )
                    )
                )
            )
        )
    }
}