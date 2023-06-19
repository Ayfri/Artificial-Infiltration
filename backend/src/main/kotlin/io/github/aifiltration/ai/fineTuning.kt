package io.github.aifiltration.ai

import com.aallam.openai.api.file.FileId
import com.aallam.openai.api.file.Purpose
import com.aallam.openai.api.file.fileSource
import com.aallam.openai.api.file.fileUpload
import com.aallam.openai.api.finetune.fineTuneRequest
import io.github.aifiltration.env
import okio.source
import java.io.File
import java.nio.charset.StandardCharsets

const val fineTuningFileId = "data"
val fineTuningFile = env("FINE_TUNING_FILE")

val fineTuningFileContent = File(fineTuningFile).readText(StandardCharsets.UTF_8)


suspend fun uploadFile() = openAiClient.file(
	request = fileUpload {
		file = fileSource {
			name = fineTuningFileId
			source = fineTuningFileContent.byteInputStream().source()
		}
		purpose = Purpose("fine-tune")
	}
)

suspend fun files() = openAiClient.files()

suspend fun deleteFile() = runCatching { openAiClient.delete(FileId(fineTuningFileId)) }

suspend fun deleteAllFiles() = files().forEach { openAiClient.delete(it.id) }

suspend fun fineTuning(file: FileId) = openAiClient.fineTune(fineTuneRequest {
	model = modelId
	trainingFile = file
})
