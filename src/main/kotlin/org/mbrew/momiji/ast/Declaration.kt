package org.mbrew.momiji.ast

class File(
    val inPackage: Symbol,
    val imports: List<Import>,
    val classes: List<ClassDecl>,
) : AstNode

sealed interface Import : AstNode {
    class Single(
        val klass: Symbol
    ) : Import
    class ImportAll(
        val fromPackage: Symbol
    ) : Import
    class Static(
        val klass: Symbol,
        val field: Symbol
    ) : Import
}

class ClassDecl(
    val packageName: Symbol,
    val name: Symbol,
    val modifiers: List<Modifier> = listOf(Modifier.Public, Modifier.Final),

    val superClass: Type = SpecialType.Top,
    val interfaces: List<Type> = emptyList(),
    val typeParams: List<TypeParam> = emptyList(),

    val fields: List<Field> = emptyList(),
    val methods: List<Method> = emptyList(),
    val constructors: List<Method> = emptyList(),
    val staticBlock: Stmt = NothingStmt,

    val innerClasses: List<ClassDecl> = emptyList(),
) : AstNode

class TypeParam(
    val name: Symbol,
    val upperBounds: List<Type>,
    val lowerBounds: List<Type>,
) : AstNode

class Field(
    val name: Symbol,
    val type: Type,
    val initExpr: Expr?
) : AstNode

class Method(
    val name: Symbol,
    val modifiers: List<Modifier> = listOf(Modifier.Public, Modifier.Final),
    val params: List<Param>,
    val returnType: Type = SpecialType.Unit,
    val body: Stmt
) : AstNode

class Param(
    val name: Symbol,
    val type: Type
) : AstNode

/**
 * represent a modifier exists in jvm class spec
 * customized modifier should be translated to some of these
 */
enum class Modifier : AstNode {
    // access modifiers
    Public,
    Private,
    Protected,
    // PackagePrivate, if no access modifier is specified, it is package-private in java

    // class only
    Interface,
    Annotation,
    Enum, // manual mark on fields is forbidden. fields in enum implicitly marked by compiler

    // fields only
    Volatile,
    Transient,

    // methods only
    Bridge,
    Native,
    Synchronized,
    // StrictFP, // no one uses this
    Varargs,

    // else
    Abstract,
    Final,
    Static,
    Synthetic,

    // special, sugary modifiers for the compiler
    Sealed,
    Inline,
    Operator,
    Override, // only for kotlin override check, will produce error if not overridden
    Data,
    ;

}
