package org.mbrew.momiji.ast

class ClassFlags(
    @JvmField val isPublic: Boolean = false,
    isFinal: Boolean = false,
    isInterface: Boolean = false,
    isAbstract: Boolean = false,
    @JvmField val isSynthetic: Boolean = false,
    @JvmField val isAnnotation: Boolean = false,
    @JvmField val isEnum: Boolean = false,
) : AstNode {
    @JvmField val isFinal: Boolean
    @JvmField val isInterface: Boolean
    @JvmField val isAbstract: Boolean

    var sum: Int = 0
        private set

    init {
        var isInterface0 = isInterface
        var isFinal0 = isFinal
        var isAbstract0 = isAbstract
        if (isAnnotation) isInterface0 = true
        assert(isInterface0 xor isEnum) { "Class cannot be both interface and enum" }
        if (isEnum) isFinal0 = true
        if (isInterface0) isAbstract0 = true
        assert(isFinal0 xor isAbstract0) { "Class cannot be both final and abstract" }

        this.isInterface = isInterface0
        this.isFinal = isFinal0
        this.isAbstract = isAbstract0

        resum()
    }

    fun resum() {
        var result = 0

        result = result or SUPER // ACC_SUPER is always set
        if (isPublic) result = result or PUBLIC
        if (isFinal) result = result or FINAL
        else if (isAbstract) result = result or ABSTRACT
        if(isSynthetic) result = result or SYNTHETIC
        if(isAnnotation) result = result or ANNOTATION
        else if(isEnum) result = result or ENUM
        if(isInterface) result = result or INTERFACE

        sum = result
    }

    companion object {
        const val PUBLIC = 0x0001
        const val FINAL = 0x0010
        const val SUPER = 0x0020
        const val INTERFACE = 0x0200
        const val ABSTRACT = 0x0400
        const val SYNTHETIC = 0x1000
        const val ANNOTATION = 0x2000
        const val ENUM = 0x4000

        @JvmStatic
        val JavaDefault = ClassFlags()
        @JvmStatic
        val KotlinDefault = ClassFlags(isPublic = true, isFinal = true)
    }
}

class FieldFlags(
    @JvmField val isPublic: Boolean = false,
    @JvmField val isPrivate: Boolean = false,
    @JvmField val isProtected: Boolean = false,
    isStatic: Boolean = false,
    isFinal: Boolean = false,
    @JvmField val isVolatile: Boolean = false,
    @JvmField val isTransient: Boolean = false,
    @JvmField val isSynthetic: Boolean = false,
    @JvmField val isEnum: Boolean = false
) : AstNode {
    @JvmField val isStatic: Boolean
    @JvmField val isFinal: Boolean

    var sum: Int = 0
        private set

    init {
        assert(!(isPublic && isPrivate) && !(isPublic && isProtected) && !(isPrivate && isProtected)) { "Field cannot be both public, private and protected" }
        var isStatic0 = isStatic
        var isFinal0 = isFinal
        if (isEnum) {
            isStatic0 = true
            isFinal0 = true
        }
        assert(!(isFinal && isVolatile)) { "Field cannot be both final and volatile" }
        this.isStatic = isStatic0
        this.isFinal = isFinal0

        resum()
    }

    fun resum() {
        var result = 0

        if (isPublic) result = result or PUBLIC
        else if (isPrivate) result = result or PRIVATE
        else if (isProtected) result = result or PROTECTED
        if (isStatic) result = result or STATIC
        if (isFinal) result = result or FINAL
        if (isVolatile) result = result or VOLATILE
        if (isTransient) result = result or TRANSIENT
        if (isSynthetic) result = result or SYNTHETIC
        if (isEnum) result = result or ENUM

        sum = result
    }

    companion object {
        const val PUBLIC = 0x0001
        const val PRIVATE = 0x0002
        const val PROTECTED = 0x0004
        const val STATIC = 0x0008
        const val FINAL = 0x0010
        const val VOLATILE = 0x0040
        const val TRANSIENT = 0x0080
        const val SYNTHETIC = 0x1000
        const val ENUM = 0x4000

        @JvmStatic
        val JavaDefault = FieldFlags()
        @JvmStatic
        val KotlinDefault = FieldFlags(isPublic = true, isFinal = true)
    }
}

class MethodFlags(
    @JvmField val isPublic: Boolean = false,
    @JvmField val isPrivate: Boolean = false,
    @JvmField val isProtected: Boolean = false,
    @JvmField val isStatic: Boolean = false,
    @JvmField val isFinal: Boolean = false,
    @JvmField val isSynchronized: Boolean = false,
    @JvmField val isBridge: Boolean = false,
    @JvmField val isVarargs: Boolean = false,
    @JvmField val isNative: Boolean = false,
    @JvmField val isAbstract: Boolean = false,
    @JvmField val isStrictFP: Boolean = false,
    @JvmField val isSynthetic: Boolean = false
) : AstNode {

    var sum: Int = 0
        private set

    init {
        assert(!(isPublic && isPrivate) && !(isPublic && isProtected) && !(isPrivate && isProtected)) { "Method cannot be both public, private and protected" }
        assert(!(isFinal && isAbstract)) { "Method cannot be both final and abstract" }

        resum()
    }

    fun resum() {
        var result = 0

        if (isPublic) result = result or PUBLIC
        else if (isPrivate) result = result or PRIVATE
        else if (isProtected) result = result or PROTECTED
        if (isStatic) result = result or STATIC
        if (isFinal) result = result or FINAL
        if (isSynchronized) result = result or SYNCHRONIZED
        if (isBridge) result = result or BRIDGE
        if (isVarargs) result = result or VARARGS
        if (isNative) result = result or NATIVE
        if (isAbstract) result = result or ABSTRACT
        if (isStrictFP) result = result or STRICTFP
        if (isSynthetic) result = result or SYNTHETIC

        sum = result
    }

    companion object {
        const val PUBLIC = 0x0001
        const val PRIVATE = 0x0002
        const val PROTECTED = 0x0004
        const val STATIC = 0x0008
        const val FINAL = 0x0010
        const val SYNCHRONIZED = 0x0020
        const val BRIDGE = 0x0040
        const val VARARGS = 0x0080
        const val NATIVE = 0x0100
        const val ABSTRACT = 0x0400
        const val STRICTFP = 0x0800
        const val SYNTHETIC = 0x1000

        @JvmStatic
        val JavaDefault = MethodFlags()
        @JvmStatic
        val KotlinDefault = MethodFlags(isPublic = true, isFinal = true)
    }
}
