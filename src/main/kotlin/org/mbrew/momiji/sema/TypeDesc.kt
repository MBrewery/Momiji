package org.mbrew.momiji.sema

import org.mbrew.momiji.ast.Modifier
import java.lang.reflect.TypeVariable

sealed class TypeDesc(
    val fullName: String,
) {
    data object Unknown : TypeDesc("<unknown>")
    data object Inferred : TypeDesc("<inferred>")
    data object Never : TypeDesc("<never>")
    data object Top : TypeDesc("<top>")
    data object Bottom : TypeDesc("<bottom>")
}

class ClassDesc(
    fullName: String,
    val modifiers: Int,
    val superClass: TypeDesc? = Object,
    val interfaces: List<TypeDesc> = emptyList(),
    val genericParameters: List<GenericDesc> = emptyList(),
    val outerClass: TypeDesc? = null,
) : TypeDesc(fullName) {
    companion object {
        @JvmStatic
        fun fromClass(klass: Class<*>): ClassDesc {
            when {
                klass.isArray -> ArrayDesc(fromClass(klass.componentType))
                klass.isPrimitive -> PrimitiveTypeDesc.find(klass)
                else -> ClassDesc(
                    klass.name,
                    klass.modifiers,
                    fromClass(klass.superclass),
                    klass.interfaces.map(::fromClass),
                    klass.typeParameters.map { GenericDesc.fromTypeParam(it) },
                    fromClass(klass.enclosingClass),
                )
            }
            TODO()
        }

        @JvmStatic
        val Object = fromClass(Any::class.java)
    }
}

class ArrayDesc(
    val elementType: TypeDesc,
) : TypeDesc("${elementType.fullName}[]")

class InvokableDesc(
    val returnType: TypeDesc,
    val parameters: List<TypeDesc>,
) : TypeDesc("<invokable>")

class PackageDesc(
    val path: String,
) : TypeDesc(path)

enum class GenericBoundType {
    EXTENDS, SUPER, NONE,
}

class GenericDesc(
    val name: String,
    val boundType: GenericBoundType = GenericBoundType.NONE,
) {
    companion object {
        fun fromTypeParam(tv: TypeVariable<out Class<*>>): GenericDesc {
            return GenericDesc(tv.name)
        }
    }
}

class PrimitiveTypeDesc private constructor(
    name: String,
    val jvmName: String,
    val jvmClass: Class<*>,
    val descriptor: String,
) : TypeDesc(name) {
    companion object {
        @JvmStatic
        fun find(klass: Class<*>) = when (klass) {
            Void.TYPE -> Unit
            java.lang.Boolean.TYPE -> Boolean
            java.lang.Byte.TYPE -> Byte
            java.lang.Short.TYPE -> Short
            java.lang.Integer.TYPE -> Int
            java.lang.Long.TYPE -> Long
            java.lang.Float.TYPE -> Float
            java.lang.Double.TYPE -> Double
            java.lang.Character.TYPE -> Char
            else -> throw IllegalArgumentException("Not a primitive type: $klass")
        }

        @JvmStatic
        val Unit = PrimitiveTypeDesc("Unit", "void", Void.TYPE, "V")

        @JvmStatic
        val Boolean = PrimitiveTypeDesc("Boolean", "boolean", java.lang.Boolean.TYPE, "Z")

        @JvmStatic
        val Byte = PrimitiveTypeDesc("Byte", "byte", java.lang.Byte.TYPE, "B")

        @JvmStatic
        val Short = PrimitiveTypeDesc("Short", "short", java.lang.Short.TYPE, "S")

        @JvmStatic
        val Int = PrimitiveTypeDesc("Int", "int", java.lang.Integer.TYPE, "I")

        @JvmStatic
        val Long = PrimitiveTypeDesc("Long", "long", java.lang.Long.TYPE, "J")

        @JvmStatic
        val Float = PrimitiveTypeDesc("Float", "float", java.lang.Float.TYPE, "F")

        @JvmStatic
        val Double = PrimitiveTypeDesc("Double", "double", java.lang.Double.TYPE, "D")

        @JvmStatic
        val Char = PrimitiveTypeDesc("Char", "char", java.lang.Character.TYPE, "C")
    }
}
