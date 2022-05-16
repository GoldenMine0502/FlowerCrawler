package kr.goldenmine.imageverifier

import java.awt.image.BufferedImage
import java.io.File

interface IVerifier {
    val name: String
    fun verify(file: File, image: BufferedImage): Boolean
}