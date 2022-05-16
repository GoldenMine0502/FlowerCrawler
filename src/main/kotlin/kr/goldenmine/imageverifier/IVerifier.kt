package kr.goldenmine.imageverifier

import java.awt.image.BufferedImage
import java.io.File

interface IVerifier {
    fun verify(file: File, image: BufferedImage): Boolean
}