package org.mbrew.momiji.compile

import org.mbrew.momiji.codegen.CodeGenerator
import org.mbrew.momiji.sema.SemanticProcessor

class Compiler(
    val language: Language,
) {
    fun compile(src: SourceFile): Distribution = CompilerImpl.compile(src, language)
}

object CompilerImpl {
    fun compile(src: SourceFile, language: Language): Distribution {
        val ast = language.parser.parse(src)
        SemanticProcessor.process(ast)
        return CodeGenerator.generate(ast)
    }
}
