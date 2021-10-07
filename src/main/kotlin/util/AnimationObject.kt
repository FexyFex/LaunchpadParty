package util

import animation.Animation
import animation.KeyFrame


interface AnimationObject: TickObject {
    var currentAnimation: Animation?
    var animationTime: Float
    val currentKeyFrame: KeyFrame?
        get() {
            if (currentAnimation == null) return null

            var frame = currentAnimation!!.keyFrames.first()

            for ((index, keyFrame) in currentAnimation!!.keyFrames.withIndex()) {
                val nextFrame: KeyFrame = try {
                    currentAnimation!!.keyFrames[index + 1]
                } catch (e: IndexOutOfBoundsException) {
                    return currentAnimation!!.keyFrames.last()
                }

                if (keyFrame.timeStamp < animationTime && nextFrame.timeStamp > animationTime) {
                    frame = keyFrame
                    break
                }
            }

            return frame
        }


    fun startAnimation(animation: Animation)
    fun stopAnimation()

    override fun tick(tick: Tick) {
        super.tick(tick)
        if (currentAnimation != null)
            animationTime += tick.updateLength
    }
}