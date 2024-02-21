package org.mbrew.momiji.codegen

import org.mbrew.momiji.ast.Module
import org.mbrew.momiji.compile.Distribution

interface CodeGenerator {
    fun generate(module: Module): Distribution
}
