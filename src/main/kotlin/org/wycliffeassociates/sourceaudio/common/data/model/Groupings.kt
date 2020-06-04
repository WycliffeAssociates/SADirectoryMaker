package org.wycliffeassociates.sourceaudio.common.data.model

enum class Groupings(val grouping: String) {
    BOOK("book"),
    CHAPTER("chapter"),
    VERSE("verse"),
    CHUNK("chunk");

    companion object {
        fun isSupported(grouping: String): Boolean = values().any { it.name == grouping.toUpperCase() }
    }
}