package org.mbrew.momiji.compile

import org.mbrew.momiji.ast.Module

interface Parser {
    fun parse(src: String): Module
}
