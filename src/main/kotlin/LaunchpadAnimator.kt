import animation.Animation
import launchpad.LaunchpadLEDControl
import util.AnimationObject
import util.Tick


class LaunchpadAnimator(launchpadName: String): AnimationObject {
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
}