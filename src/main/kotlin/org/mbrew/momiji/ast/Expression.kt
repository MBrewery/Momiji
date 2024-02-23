package org.mbrew.momiji.ast

sealed class Expr(
    var type: Type = SpecialType.Never
) : AstNode

data object NothingExpr : Expr(SpecialType.Unit)

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
class IsExpr(val left: Expr, var right: Symbol) : Expr(PrimitiveType.Boolean)

// explicit type cast in java
class AsExpr(val left: Expr, var right: Symbol) : Expr()

class NewExpr(val klass: Type, val args: List<Expr>) : Expr()

class VarExpr(val varName: Symbol) : Expr()

class CallExpr(val receiver: Expr, val methodName: Symbol, val args: List<Expr>) : Expr()

sealed class AssignableExpr : Expr()

class FieldAccessExpr(val receiver: Expr, val varName: Symbol) : AssignableExpr()

// array index access is one-arg version
class KeyAccessExpr(val receiver: Expr, val args: List<Expr>) : AssignableExpr()

class AssignExpr(val lVal: AssignableExpr, val value: Expr) : AssignableExpr()

sealed class LiteralExpr(type: Type) : Expr(type)
class IntLiteral(val value: Int) : LiteralExpr(PrimitiveType.Int)
class LongLiteral(val value: Long) : LiteralExpr(PrimitiveType.Long)
class FloatLiteral(val value: Float) : LiteralExpr(PrimitiveType.Float)
class DoubleLiteral(val value: Double) : LiteralExpr(PrimitiveType.Double)
class CharLiteral(val value: String) : LiteralExpr(PrimitiveType.Char)
class BooleanLiteral private constructor(val value: Boolean) : LiteralExpr(PrimitiveType.Boolean) {
    companion object {
        @JvmStatic
        val True = BooleanLiteral(true)

        @JvmStatic
        val False = BooleanLiteral(false)
    }
}

class StringLiteral(val value: String) : LiteralExpr(SpecialType.String)
data object NullLiteral : LiteralExpr(SpecialType.Bottom)



