package org.mbrew.momiji.sema

import org.mbrew.momiji.ast.*

object Binder {
    fun bind(node: File, st: SymbolTable) = with(node) {
        val st = SymbolTable(st)

        for (i in imports) {
            bind(i, st)
        }

        for (i in classes) {
            bind(i, st)
        }
    }

    fun bind(node: Import, st: SymbolTable) {
        when (node) {
            is Import.ImportAll -> {
                TODO()
            }
            is Import.Single -> st[node.alias ?: node.klass] = TODO()
            is Import.Static -> st[node.alias ?: node.field] = TODO()
        }
    }

    fun bind(node: ClassDecl, st: SymbolTable) = with(node) {
    }

    fun bind(node: MethodDecl) {


        bind(node.body)
    }

    fun bind(node: FieldDecl) {

    }

    fun bind(node: Stmt) {
        when (node) {
            NothingStmt -> return
            is Block -> node.stmts.forEach(::bind)
            is BreakStmt -> bind(node)
            is ContinueStmt -> bind(node)
            is ExprStmt -> bind(node.expr)
            is ForEachStmt -> bind(node)
            is ForRangeStmt -> bind(node)
            is ForStmt -> bind(node)
            is IfStmt -> bind(node)
            is ReturnStmt -> bind(node.expr)
            is SwitchStmt -> TODO()
        }
    }

    fun bind(node: Expr) {
        when (node) {
            NullLiteral, NothingExpr,
            is BooleanLiteral, is CharLiteral, is FloatLiteral, is DoubleLiteral,
            is IntLiteral, is LongLiteral, is StringLiteral -> return

            is AsExpr -> bind(node)
            is AssignExpr -> bind(node)
            is FieldAccessExpr -> bind(node)
            is KeyAccessExpr -> bind(node)
            is BinaryExpr ->  bind(node)
            is CallExpr ->  bind(node)
            is IsExpr ->  bind(node)
            is NewExpr -> bind(node)
            is TernaryExpr -> bind(node)
            is UnaryExpr -> bind(node)
            is VarExpr -> bind(node)
        }
    }
}

object CommonSymbolKeys {
    const val THIS = "this"
}

class SymbolTable(
    private val parent: SymbolTable? = null,
    val allowOverride: Boolean = false,
) {
    private val symTab = mutableMapOf<String, Symbol>()

    fun get(name: String): Symbol? = symTab[name] ?: parent?.get(name)

    operator fun set(name: String, symbol: Symbol) {
        symTab[name] = symbol
    }
}
