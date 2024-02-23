package org.mbrew.momiji.transform

import org.mbrew.momiji.ast.File

interface Transformer {
    fun transform(src: File): File
}
