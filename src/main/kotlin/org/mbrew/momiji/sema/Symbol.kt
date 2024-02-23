package org.mbrew.momiji.sema

interface Symbol

object NothingSymbol : Symbol

class UnresolvedSymbol(
    val name: String
) : Symbol

class TypeRefSymbol(
    var type: TypeDesc
) : Symbol

class FieldRefSymbol(
    var type: TypeDesc,
    val fieldName: String
) : Symbol

class MethodRefSymbol(
    var type: TypeDesc,
    val methodName: String
) : Symbol

class LocalVarSymbol(
    val slot: Int,
    val type: TypeDesc
) : Symbol
