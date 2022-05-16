package kr.goldenmine.imageverifier

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import kotlin.math.max

class VerifierImageGrayScaled(
    private val ratio: Float = 0.99F,
    private val n: Int = 2,
) : IVerifier {
    override val name: String
        get() = "image_grayscaled"

    override fun verify(file: File, image: BufferedImage): Boolean {
        return getGrayRatio(image) > ratio
    }

    fun getGrayRatio(bufferedImage: BufferedImage): Float {
        val pixels = bufferedImage.width * bufferedImage.height
        var graypixels = 0
        for(x in 0 until bufferedImage.width) {
            for(y in 0 until bufferedImage.height) {
                if(isGray(Color(bufferedImage.getRGB(x, y)), n)) {
                    graypixels++
                }
            }
        }

        return graypixels.toFloat() / pixels
    }

    fun isGray(color: Color, n: Int): Boolean {
        val r = color.red
        val g = color.green
        val b = color.blue

        val r2 = r / 255.0
        val g2 = g / 255.0
        val b2 = b / 255.0

        val k = 1 - max(max(r2, g2), b2)
        val c = (1 - r2 - k) / (1 - k)
        val m = (1 - g2 - k) / (1 - k)
        val y = (1 - b2 - k) / (1 - k)

        return (c + m + y) * 100 < n
    }

}