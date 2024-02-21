package org.mbrew.momiji.compile

import org.mbrew.momiji.codegen.CodeGenerator
import org.mbrew.momiji.codegen.JvmCodeGenerator

data class Target(
    val name: String,
    val generator: CodeGenerator,
) {
    companion object {
        @JvmStatic
        val JVM = Target("jvm", JvmCodeGenerator)

    }
}
