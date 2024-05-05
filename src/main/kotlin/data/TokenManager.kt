package data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.File


object TokenManager {
    private const val FILE_NAME = ".jira"
    private val tokenFlow = MutableStateFlow<String?>(
        value = readToken()
    )
    suspend fun writeToken(token: String) {
        val file = File(FILE_NAME)
        file.writeText(token)
        tokenFlow.emit(token)
    }

    fun getTokenFlow(): Flow<String?> {
        return tokenFlow
    }

    private fun readToken(): String? {
        val file = File(FILE_NAME)
        return if (file.exists()) {
            file.readText()
        } else {
            null
        }
    }
}