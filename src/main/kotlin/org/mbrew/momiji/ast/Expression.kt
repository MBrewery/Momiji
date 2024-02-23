package org.mbrew.momiji.ast

import org.mbrew.momiji.sema.Symbol
import org.mbrew.momiji.sema.PrimitiveTypeDesc
import org.mbrew.momiji.sema.TypeDesc

sealed class Expr(
    var type: TypeDesc = TypeDesc.Unknown
) : AstNode

data object NothingExpr : Expr(PrimitiveTypeDesc.Unit)

/**
 * Supported binary operators
 * Compiler default implemented operator overloading.
 * + - * / %
 * && ||
 * << >> >>> & | ^
 * == != ( equals, !equals in java)
 * < <= > >=
 * === !== ( == != in java )
 */
enum class BinaryOperator : AstNode {
    Add, Sub, Mul, Div, Mod,
    And, Or,
    Shl, Shr, UShr, BitAnd, BitOr, BitXor,
    Eq, Neq,
    Lt, Leq, Gt, Geq,
    RefEq, RefNeq,
    ;
}

class BinaryExpr(val left: Expr, val right: Expr, val operator: BinaryOperator) : Expr()

/**
 * Supported unary operators
 * + -
 * !
 * ~
 * ++ --
 */
enum class UnaryOperator : AstNode {
    Pos, Neg,
    Not,
    BitNot,
    PrefixInc, PrefixDec, PostfixInc, PostfixDec
}

class UnaryExpr(val operand: Expr, val operator: UnaryOperator) : Expr()

class TernaryExpr(val condition: Expr, val thenExpr: Expr, val elseExpr: Expr) : Expr()

// 'instanceof' in java
class IsExpr(val left: Expr, var right: Symbol) : Expr(PrimitiveTypeDesc.Boolean)

// explicit type cast in java
class AsExpr(val left: Expr, var right: Symbol) : Expr()

class NewExpr(var klass: Symbol, val args: List<Expr>) : Expr()

class VarExpr(var varName: Symbol) : Expr()

/*
 * note: in backend, if receiver is null & callee is resolved to a type,
 * it'll be replaced to a constructor call.
 */
class CallExpr(var receiver: Expr, val callee: Symbol, val args: List<Expr>) : Expr()

sealed class AssignableExpr : Expr()

class FieldAccessExpr(val receiver: Expr, val varName: Symbol) : AssignableExpr()

// array index access is one-arg version
class KeyAccessExpr(val receiver: Expr, val args: List<Expr>) : AssignableExpr()

class AssignExpr(val lVal: AssignableExpr, val value: Expr) : AssignableExpr()

sealed class LiteralExpr(type: TypeDesc) : Expr(type)
class IntLiteral(val value: Int) : LiteralExpr(PrimitiveTypeDesc.Int)
class LongLiteral(val value: Long) : LiteralExpr(PrimitiveTypeDesc.Long)
class FloatLiteral(val value: Float) : LiteralExpr(PrimitiveTypeDesc.Float)
class DoubleLiteral(val value: Double) : LiteralExpr(PrimitiveTypeDesc.Double)
class CharLiteral(val value: String) : LiteralExpr(PrimitiveTypeDesc.Char)
class BooleanLiteral private constructor(val value: Boolean) : LiteralExpr(PrimitiveTypeDesc.Boolean) {
    companion object {
        @JvmStatic
        val True = BooleanLiteral(true)

        @JvmStatic
        val False = BooleanLiteral(false)
    }
}

class StringLiteral(val value: String) : LiteralExpr(TODO())
data object NullLiteral : LiteralExpr(TypeDesc.Bottom)



