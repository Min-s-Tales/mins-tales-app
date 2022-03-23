package com.example.minstalesapp

import io.paperdb.Paper
import java.io.File

open class PaperUtils {
    private val talesLocalStorage = Paper.book().read<MutableList<File>>("tales", mutableListOf<File>())

    open fun deleteZIPFile(file: File) : Boolean{
        val fileLocalStorage = Paper.book().read<MutableList<File>>("tales", mutableListOf<File>())
        val isDeleted = fileLocalStorage!!.remove(file)
        Paper.book().write("products", fileLocalStorage)
        return isDeleted

    }

    open fun getZIPFiles(): MutableList<File>? {
        return Paper.book().read<MutableList<File>>("tales", mutableListOf<File>())
    }

    open fun addZIPFile(file: File): Boolean {
        val isAdded = talesLocalStorage!!.add(file)
        Paper.book().write("products", talesLocalStorage)
        return isAdded
    }

    open fun replaceIfExists(fileName:String):Boolean {
        var isExisting = false
        val talesInStorage = this.getZIPFiles()
        talesInStorage?.forEach {
            if (it.name == fileName){
                isExisting = true
                Paper.book().write("tales", talesInStorage)
                return true
            }
        }
        return isExisting
    }
}