package org.mbrew.momiji.sema

sealed class Scope {
    abstract fun resolveType(name: String): TypeDesc

    abstract fun resolveVar(name: String): Symbol

    abstract fun defineType(name: String, type: TypeDesc)

    abstract fun defineVar(name: String, symbol: Symbol)
}

class RootScope : Scope() {
    private val types = mutableMapOf<String, TypeDesc>()
    private val symbols = mutableMapOf<String, Symbol>()

    override fun resolveType(name: String): TypeDesc {
        return types[name] ?: throw IllegalArgumentException("Type $name not found")
    }

    override fun resolveVar(name: String): Symbol {
        return symbols[name] ?: throw IllegalArgumentException("Symbol $name not found")
    }

    override fun defineType(name: String, type: TypeDesc) {
        types[name] = type
    }

    override fun defineVar(name: String, symbol: Symbol) {
        symbols[name] = symbol
    }
}

class ChildScope(
    val parent: Scope
) : Scope() {
    private val types = mutableMapOf<String, TypeDesc>()
    private val symbols = mutableMapOf<String, Symbol>()

    override fun resolveType(name: String): TypeDesc {
        return types[name] ?: parent.resolveType(name)
    }

    override fun resolveVar(name: String): Symbol {
        return symbols[name] ?: parent.resolveVar(name)
    }

    override fun defineType(name: String, type: TypeDesc) {
        types[name] = type
    }

    override fun defineVar(name: String, symbol: Symbol) {
        symbols[name] = symbol
    }
}
