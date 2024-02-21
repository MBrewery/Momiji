package org.mbrew.momiji.compile

import org.mbrew.momiji.sema.SemanticProcessor

class Compiler(
    val language: Language,
    val target: Target = Target.JVM,
) : (String) -> Distribution {
    fun compile(src: String): Distribution = CompilerImpl.compile(src, language, target)

    override operator fun invoke(p1: String) = compile(p1)
}

object CompilerImpl {
    fun compile(src: String, language: Language, target: Target): Distribution {
        val ast = language.parser.parse(src)
        SemanticProcessor.process(ast)
        return target.generator.generate(ast)
    }
}
