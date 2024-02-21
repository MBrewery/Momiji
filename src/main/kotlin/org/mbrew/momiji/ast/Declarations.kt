package org.mbrew.momiji.ast

class VarDecl(
    val name: String,
    val type: String,
    val value: Expression
) : AstNode
