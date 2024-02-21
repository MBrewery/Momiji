package org.mbrew.momiji.compile

class Distribution (
    val sourceName: String,
    val language: Language,
    /**
     * bytecode of the classes
     */
    val classes: Array<ByteArray>,
)