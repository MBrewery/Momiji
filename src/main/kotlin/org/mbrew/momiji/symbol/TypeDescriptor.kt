package org.mbrew.momiji.symbol

sealed class TypeDescriptor(
    val name: String,
) {
    data object Unknown : TypeDescriptor("<Unknown>")
}
