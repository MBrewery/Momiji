package org.mbrew.momiji.ast

interface Type : AstNode {
}

enum class SpecialType : Type {
    // Void will be translated to Unit if possible
    Top, Bottom, Never, Void, Unit, Inferred, Unknown,
    String,
    ;
}

enum class PrimitiveType : Type {
    Byte, Short, Int, Long, Float, Double, Char, Boolean;
}

class ArrayType(
    val elementType: Type
) : Type

class NamedType(
    val name: String
) : Type

class FullQualifiedType(
    val name: String
) : Type


