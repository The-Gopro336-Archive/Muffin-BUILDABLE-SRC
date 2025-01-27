package me.han.muffin.client.utils.color

import me.han.muffin.client.Muffin
import me.han.muffin.client.utils.render.AnimationUtils
import me.han.muffin.client.utils.render.RenderUtils
import java.awt.Color

data class Colour(
    var r: Int = 255,
    var g: Int = 255,
    var b: Int = 255,
    var a: Int = 255
) {

    constructor(color: Color) : this(color.red, color.green, color.blue, color.alpha)

    fun clientColour(alpha: Int): Colour {
        return Colour(Muffin.getInstance().fontManager.publicRed, Muffin.getInstance().fontManager.publicGreen, Muffin.getInstance().fontManager.publicBlue, alpha)
    }

    val brightness get() = intArrayOf(r, g, b).maxOrNull()!!.toFloat() / 255f

    val averageBrightness get() = (intArrayOf(r, g, b).average() / 255.0).toFloat()

    fun multiply(multiplier: Float): Colour {
        return Colour((r * multiplier).toInt().coerceIn(0, 255), (g * multiplier).toInt().coerceIn(0, 255), (b * multiplier).toInt().coerceIn(0, 255), a)
    }

    fun mix(other: Colour): Colour {
        return Colour((r + other.r) / 2 + (g + other.g) / 2, (b + other.b) / 2, (a + other.a) / 2)
    }

    fun interpolate(prev: Colour, deltaTime: Double, length: Double): Colour {
        return Colour(
            AnimationUtils.exponent(deltaTime, length, prev.r.toDouble(), r.toDouble()).toInt().coerceIn(0, 255),
            AnimationUtils.exponent(deltaTime, length, prev.g.toDouble(), g.toDouble()).toInt().coerceIn(0, 255),
            AnimationUtils.exponent(deltaTime, length, prev.b.toDouble(), b.toDouble()).toInt().coerceIn(0, 255),
            AnimationUtils.exponent(deltaTime, length, prev.a.toDouble(), a.toDouble()).toInt().coerceIn(0, 255)
        )
    }

    fun setGLColor() {
        RenderUtils.glColor(this.r, this.g, this.b, this.a)
    }

    fun toHex(): Int {
        return (r shl 16) + (g shl 8) + (b shl 0) + (a shl 24)
       //  return 0xff shl 24 or (r and 0xff shl 16) or (g and 0xff shl 8) or (b and 0xff)
    }

    fun getRGB(): Int = a and 0xFF shl 24 or (r and 0xFF shl 16) or (g and 0xFF shl 8) or (b and 0xFF shl 0)

    fun clone(): Colour {
        return Colour(r, g, b, a)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Colour

        if (r != other.r) return false
        if (g != other.g) return false
        if (b != other.b) return false
        if (a != other.a) return false

        return true
    }

    override fun hashCode(): Int {
        var result = r
        result = 31 * result + g
        result = 31 * result + b
        result = 31 * result + a
        return result
    }

}