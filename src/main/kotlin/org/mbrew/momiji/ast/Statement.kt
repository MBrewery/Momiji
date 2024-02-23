package org.mbrew.momiji.ast

interface Stmt : AstNode

object NothingStmt : Stmt

class Block(
    val stmts: MutableList<Stmt> = mutableListOf()
) : Stmt

class ExprStmt(
    val expr: Expr
) : Stmt

class BreakStmt(
    val label: Symbol = NothingSymbol
) : Stmt

class ContinueStmt(
    val label: Symbol = NothingSymbol
) : Stmt

class ReturnStmt(
    val expr: Expr = NothingExpr
) : Stmt

class IfStmt(
    val condition: Expr,
    val thenStmt: Stmt,
    val elseStmt: Stmt = NothingStmt
) : Stmt

class WhileStmt(
    val condition: Expr,
    val body: Stmt,
    val doWhile: Boolean = false,
)

class ForStmt(
    val init: Stmt,
    val condition: Expr,
    val update: Stmt,
    val body: Stmt
) : Stmt

class ForEachStmt(
    val varName: Symbol,
    val iterable: Expr,
    val body: Stmt
) : Stmt

class ForRangeStmt(
    val varName: Symbol,
    val start: Expr = IntLiteral(0),
    val end: Expr,
    val step: Expr = IntLiteral(1),
    val body: Stmt
) : Stmt

class SwitchStmt(
    val expr: Expr,
    val cases: List<Case>,
    val default: Stmt = NothingStmt
) : Stmt

class Case(
    val expr: Expr,
    val stmt: Stmt
) : AstNode

