package kr.goldenmine.imageverifier

import java.awt.image.BufferedImage
import java.io.File
import kotlin.math.max

class VerifierImageRatio(private val ratio: Double = 3.5) : IVerifier {
    override val name: String
        get() = "image_ratio"
    override fun verify(file: File, image: BufferedImage): Boolean {
        return max(image.width.toDouble() / image.height, image.height.toDouble() / image.width) > ratio
    }
}