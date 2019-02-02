package com.github.insanusmokrassar.HtmlParser

import kotlinx.serialization.json.JSON
import java.io.FileInputStream
import java.io.InputStream
import java.nio.charset.Charset

internal fun openInputStream(path: String): InputStream {
    return ClassLoader.getSystemResourceAsStream(path) ?: FileInputStream(path)
}

internal fun readSettings(path: String): Settings = try {
    JSON.nonstrict.parse(
        Settings.serializer(),
        openInputStream(path).readBytes().toString(Charset.defaultCharset())
    )
} catch (e: Exception) {
    Settings()
}
