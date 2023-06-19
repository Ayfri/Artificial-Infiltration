package io.github.aifiltration

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
data class Item(val author: String, val content: String)

val allowedUsernames = setOf(
	"ayfri",
	"bahsiik",
	"anta",
	"nimajjj",
	"kheir",
	"lunasphys",
	"jaepasdargent",
	"dxvy"
)

fun getFullPath(relativePath: String): String {
	val path = File("data/src/main/resources/").absolutePath
	return "$path\\$relativePath"
}

fun main() {
	val dataString = File(getFullPath("data.json")).readText()
	val data = Json.decodeFromString<List<Item>>(dataString)

	val filteredData = data
		.filter { it.author.lowercase() in allowedUsernames }
		.filter { it.content.length < 140 }
		.removeDuplicateConsecutiveWords(7)
		.filter { it.content.replace(Regex("\\s{4,}"), " ").length > 20 }
		.also { println("Filtered data size: ${it.size}") }
		.take(100)
	// 1000 = 0.7€ par partie + 0.6€ pour envoyer le fine-tuning
	// 250 = 0.175€ par partie + 0.15€ pour envoyer le fine-tuning
	// 100 = 0.07€ par partie + 0.06€ pour envoyer le fine-tuning

	val groupedDataDebug = filteredData
		.groupBy { it.author }
		.mapValues { it.value.size }

	val sortedData = groupedDataDebug.entries.sortedByDescending { it.value }
	println(sortedData.joinToString("\n", "[\n", "\n]") { "\t${it.key}: ${it.value}" })

	@Serializable
	data class GroupedItem(val prompt: String, val completion: String)

	var groupedData = filteredData.chunked(15).map { messages ->
		GroupedItem(
			prompt = "Exemple de conversation:",
			completion = messages.joinToString("\n") { "${it.author}: ${it.content}" }
		)
	}

	groupedData = filteredData.map { message ->
		GroupedItem(
			prompt = "Exemple de message de ${message.author}:",
			completion = message.content
		)
	}

	val jsonLines = groupedData.joinToString("\n") { Json.encodeToString(it) }
	val resultingFile = File(getFullPath("data-small.jsonl"))
	resultingFile.writeText(jsonLines)

	val stats = resultingFile.length()
	println("Original data length: ${data.size}")
	println("Filtered data length: ${filteredData.size}")
	println("Size of the new file: ${stats / 1000}kb")
}
