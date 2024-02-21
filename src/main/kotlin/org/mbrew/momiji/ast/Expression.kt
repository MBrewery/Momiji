package org.mbrew.momiji.ast

import org.mbrew.momiji.symbol.TypeDescriptor

sealed class Expression : AstNode {
    var type: TypeDescriptor = TypeDescriptor.Unknown
}

/**
 * Supported binary operators
 * + - * / %
 * && ||
 * << >> >>> & | ^
 * == !=
 * < <= > >=
 * === !==
 */
enum class BinaryOperator {
    Add, Sub, Mul, Div, Mod,
    And, Or,
    Shl, Shr, UShr, BitAnd, BitOr, BitXor,
    Eq, Neq,
    Lt, Leq, Gt, Geq,
    RefEq, RefNeq,
    ;
}

class BinaryExpression(val left: Expression, val right: Expression, val operator: BinaryOperator) : Expression()

/**
 * Supported unary operators
 * + -
 * !
 * ~
 * ++ --
 */
enum class UnaryOperator {
    Pos, Neg,
    Not,
    BitNot,
    Inc, Dec,
}

class UnaryExpression(val operand: Expression, val operator: UnaryOperator) : Expression()

class VarAccessExpression(val varName: String) : Expression()

