package com.giant_giraffe.controllers.common

import com.giant_giraffe.core.respondApiResult
import com.giant_giraffe.exceptions.NotFoundException
import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.io.File
import java.util.*

fun Route.fileController() {

    val projectRootPath = System.getProperty("user.dir")

    post("/upload") {
        val multipartData = call.receiveMultipart()
        var fileDescription = ""
        val fileNames = mutableListOf<String>()

        multipartData.forEachPart { part ->
            when (part) {
                is PartData.FormItem -> {
                    fileDescription = part.value
                }
                is PartData.FileItem -> {
                    val fileName = "${UUID.randomUUID()}_${part.originalFileName}"
                    val fileBytes = part.streamProvider().readBytes()
                    val filePath = arrayOf(projectRootPath, "upload", fileName).joinToString(File.separator)
                    val file = File(filePath)
                    file.writeBytes(fileBytes)
                    fileNames.add(fileName)
                }
                else -> {  }
            }
        }

        call.respondApiResult(result = fileNames)
    }

    get("/download/{fileName}") {
        val fileName = call.parameters["fileName"]!!

        val filePath = arrayOf(projectRootPath, "upload", fileName).joinToString(File.separator)
        val file = File(filePath)
        if (file.exists()) {
            call.respondFile(file)
        } else {
            throw NotFoundException()
        }
    }

}