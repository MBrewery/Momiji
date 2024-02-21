package org.mbrew.momiji.ast

class Module(
    val name: String,
    val imports: List<Import>,
    val varDecl: List<VarDecl>
) : AstNode

class Import(
    val name: String
) : AstNode
