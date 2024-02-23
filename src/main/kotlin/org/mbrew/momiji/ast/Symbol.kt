package org.mbrew.momiji.ast

interface Symbol

object NothingSymbol : Symbol

class UnresolvedSymbol(
    val name: String
) : Symbol




