package org.mbrew.momiji.ast

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
    val flags: ClassFlags = ClassFlags(),

    // null = not specified, "java.lang.Object" = explicit extends Object
    var superClass: String? = null,
    val interfaces: MutableList<String> = mutableListOf(),
    val typeParams: List<TypeParam> = mutableListOf(),

    val fields: MutableList<FieldDecl> = mutableListOf(),
    val methods: MutableList<MethodDecl> = mutableListOf(),

    // static field init should be put here
    val initBlocks: MutableList<Stmt> = mutableListOf(),
    val staticBlocks: MutableList<Stmt> = mutableListOf(),

    val outerClass: String? = null,
    val innerClasses: MutableList<ClassDecl> = mutableListOf(),
) : AstNode

class TypeParam(
    val name: String,
    val upperBounds: List<String>,
    val lowerBounds: List<String>
) : AstNode

class FieldDecl(
    val name: String,
    val flags: FieldFlags,
    val type: String,
) : AstNode

class MethodDecl(
    val name: String,
    val flags: MethodFlags,
    val returnType: String,
    val params: List<String>,
    val body: Stmt
) : AstNode

class Param(
    val name: String,
    val type: String
) : AstNode

/**
 * represent a modifier exists in jvm class spec
 * customized modifier should be translated to some of these
 */
enum class Modifier(
    val onClass: Int,
    val onMethod: Int,
    val onField: Int,
) : AstNode {
    // access modifiers
    Public(0x0001, 0x0001, 0x0001),
    Private(-1, 0x0002, 0x0002),
    Protected(-1, 0x0004, 0x0004),
    // PackagePrivate, if no access modifier is specified, it is package-private in java

    // class only
    Interface(0x0200, -1, -1),
    Annotation(0x2000, -1, -1),
    Enum(0x4000, -1, 0x4000),

    // fields only
    Volatile(-1, -1, 0x0040),
    Transient(-1, -1, 0x0080),

    // methods only
    Bridge(-1, 0x0040, -1),
    Native(-1, 0x0100, -1),
    Synchronized(-1, 0x0020, -1),

    // StrictFP, // no one uses this
    Varargs(-1, 0x0080, -1),

    // else
    Abstract(0x0400, 0x0400, -1),
    Final(0x0010, 0x0010, 0x0010),
    Static(-1, 0x0008, 0x0008),
    Synthetic(0x1000, 0x1000, 0x1000),
    ;

}
