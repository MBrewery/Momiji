package org.mbrew.momiji.ast

import org.mbrew.momiji.sema.Symbol
import org.mbrew.momiji.sema.ClassDesc
import org.mbrew.momiji.sema.SymbolTable
import org.mbrew.momiji.sema.TypeRefSymbol

class File(
    val inPackage: String,
    val imports: List<Import>,
    val classes: List<ClassDecl>,
) : AstNode

sealed interface Import : AstNode {
    class Single(
        val klass: String,
        val alias: String? = null,
    ) : Import
    class ImportAll(
        val fromPackage: String
    ) : Import
    class Static(
        val klass: String,
        val field: String,
        val alias: String? = null
    ) : Import
}

class ClassDecl(
    val packageName: String,
    val name: String,
    val modifiers: MutableList<Modifier> = mutableListOf(Modifier.Public, Modifier.Final),

    val superClass: Symbol = TypeRefSymbol(ClassDesc.Object),
    val interfaces: MutableList<Symbol> = mutableListOf(),
    val typeParams: MutableList<TypeParam> = mutableListOf(),

    val fields: MutableList<FieldDecl> = mutableListOf(),
    val methods: MutableList<MethodDecl> = mutableListOf(),
    val constructors: MutableList<ConstructorDecl> = mutableListOf(),

    // dsl should process init order
    val initBlocks: MutableList<Stmt> = mutableListOf(),
    val staticBlocks: MutableList<Stmt> = mutableListOf(),

    val innerClasses: MutableList<ClassDecl> = mutableListOf(),
) : AstNode {
    lateinit var thisType: ClassDesc
}

class TypeParam(
    val name: Symbol,
    val upperBounds: List<Symbol>,
    val lowerBounds: List<Symbol>
) : AstNode

class FieldDecl(
    val name: Symbol,
    val type: Symbol,
    val modifiers: List<Modifier>,
) : AstNode

class MethodDecl(
    val name: Symbol,
    val modifiers: List<Modifier>,
    val params: List<Param>,
    val returnType: Symbol,
    val body: Stmt
) : AstNode {
    val symbolTable: SymbolTable = SymbolTable()
}

class ConstructorDecl(
    val modifiers: List<Modifier>,
    val params: List<Param>,
    val body: Stmt
) : AstNode {
    val symbolTable: SymbolTable = SymbolTable()
}

class Param(
    val name: Symbol,
    val type: Symbol
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
