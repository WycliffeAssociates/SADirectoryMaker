package org.wycliffeassociates.sourceaudio.directorymaker.common.data.model

enum class Groupings(val grouping: String) {
    BOOK("book"),
    CHAPTER("chapter"),
    VERSE("verse"),
    CHUNK("chunk");

    companion object {
        fun isSupportedGrouping(grouping: String): Boolean = values().any { it.name == grouping.toUpperCase() }
    }
}