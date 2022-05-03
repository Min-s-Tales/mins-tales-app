package com.example.minstalesapp.Model

data class Story(
    var id : Int,
    var title : String,
    var description : String,
    var urlContentStory : Int,
    var icon : String,
    var price : Float,
    var nbDownload : Int,
    var tags : Array<String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Story

        if (id != other.id) return false
        if (title != other.title) return false
        if (description != other.description) return false
        if (urlContentStory != other.urlContentStory) return false
        if (icon != other.icon) return false
        if (price != other.price) return false
        if (nbDownload != other.nbDownload) return false
        if (!tags.contentEquals(other.tags)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + title.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + urlContentStory
        result = 31 * result + icon.hashCode()
        result = 31 * result + price.hashCode()
        result = 31 * result + nbDownload
        result = 31 * result + tags.contentHashCode()
        return result
    }
}