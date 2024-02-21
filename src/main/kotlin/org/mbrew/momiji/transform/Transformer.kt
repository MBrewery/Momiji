package org.mbrew.momiji.transform

import org.mbrew.momiji.ast.Module

interface Transformer {
    fun transform(src: Module): Module
}
