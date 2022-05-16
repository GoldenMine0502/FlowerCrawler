package kr.goldenmine.imageverifier

import java.awt.image.BufferedImage
import java.io.File

class VerifierImageSmall: IVerifier {
    override val name: String
        get() = "image_small"
    override fun verify(file: File, image: BufferedImage): Boolean {
        return image.width < 80 || image.height < 80
    }
}