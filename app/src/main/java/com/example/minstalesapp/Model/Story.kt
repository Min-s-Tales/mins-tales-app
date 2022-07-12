package com.example.minstalesapp.Model

data class Story(
    var id: Int,
    var title: String,
    var display: String,
    var description: String,
    var url_folder: String,
    var url_icon: String,
    var price: Float,
    var author: String,
    var nb_download: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Story

        if (id != other.id) return false
        if (title != other.title) return false
        if (display != other.display) return false
        if (description != other.description) return false
        if (url_folder != other.url_folder) return false
        if (url_icon != other.url_icon) return false
        if (price != other.price) return false
        if (author != other.author) return false
        if (nb_download != other.nb_download) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + title.hashCode()
        result = 31 * result + display.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + url_folder.hashCode()
        result = 31 * result + url_icon.hashCode()
        result = 31 * result + price.hashCode()
        result = 31 * result + author.hashCode()
        result = 31 * result + nb_download
        return result
    }
}
