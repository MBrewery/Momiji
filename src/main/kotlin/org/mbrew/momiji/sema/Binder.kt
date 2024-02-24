package org.mbrew.momiji.sema

import org.mbrew.momiji.ast.*

object Binder {
    fun bind(node: File, st: Scope) {
        with(node) {
            val moduleSt = ChildScope(st)

            for (i in imports) {
                bind(i, moduleSt)
            }

            for (i in classes) {
                bind(i, moduleSt)
            }
        }
    }

    fun bind(node: Import, st: Scope) {
        when (node) {
            is Import.ImportAll -> {
                TODO()
            }

            is Import.Single -> st.defineType(node.alias ?: node.klass, ClassDesc.fromClass(Class.forName(node.klass)))
            is Import.Static -> st.defineVar(
                node.alias ?: node.field,
                FieldRefSymbol(ClassDesc.fromClass(Class.forName(node.klass)), node.field)
            )
        }
    }

    fun bind(node: ClassDecl, st: Scope) {
        with(node) {
            val classSt = ChildScope(st)

            when {
                flags.isEnum -> {
                    assert(superClass == null) { "Enum class cannot have super class" }
                    superClass = "java.lang.Enum"
                }
                flags.isAnnotation -> {
                    assert(superClass == null) { "Annotation class cannot have super class" }
                    superClass = "java.lang.Object"
                    interfaces += "java.lang.annotation.Annotation"
                }
                flags.isInterface -> {
                    assert(superClass == null) { "Interface class cannot have super class" }
                    superClass = "java.lang.Object"
                }
                else -> superClass = superClass ?: "java.lang.Object"
            }


            val thisType = ClassDesc(
                "$packageName.$name", flags.sum,
                st.resolveType(superClass!!),
                interfaces.map(st::resolveType),
                emptyList(),
                outerClass?.let { st.resolveType(outerClass) },
                )
            st.defineType(name, thisType)

            for (i in fields) {
                bind(i, classSt)
            }

            for (i in methods) {
                bind(i, classSt)
            }

            for (i in innerClasses) {
                bind(i, classSt)
            }
        }
    }

    fun bind(node: MethodDecl, st: Scope) {


        bind(node.body)
    }

    fun bind(node: FieldDecl, st: Scope) {

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
            is BinaryExpr -> bind(node)
            is CallExpr -> bind(node)
            is IsExpr -> bind(node)
            is NewExpr -> bind(node)
            is TernaryExpr -> bind(node)
            is UnaryExpr -> bind(node)
            is VarExpr -> bind(node)
        }
    }
}
