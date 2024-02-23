package org.mbrew.momiji.compile

import org.mbrew.momiji.ast.File

interface Parser {
    fun parse(src: SourceFile): File
}
