package io.github.aifiltration.ai

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.chat.chatCompletionRequest
import io.github.aifiltration.usedNames
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.text.Normalizer

@OptIn(BetaOpenAI::class)
suspend fun chatCompletionRequest(
	chatCompletionMessages: List<ChatCompletionMessage> = emptyList(),
) = openAiClient.chatCompletion(chatCompletionRequest {
	maxTokens = 40
	model = modelId

	val defaultMessage = ChatMessage(
		role = ChatRole.System,
		content = defaultPrompt.format(
			Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
			usedNames[AI_ID]
		),
	)

	val promptMessages = mutableListOf(defaultMessage)
	promptMessages += defaultMessages.map {
		ChatMessage(
			role = ChatRole.User,
			content = it.content,
			name = it.author
		)
	}

	promptMessages += ChatMessage(
		role = ChatRole.System,
		content = "Début de la vraie conversation:",
	)

	promptMessages += chatCompletionMessages.map {
		ChatMessage(
			role = if (it.fromAI) ChatRole.Assistant else ChatRole.User,
			content = it.content,
			name = it.author,
		)
	}

	messages = promptMessages.map {
		ChatMessage(
			role = it.role,
			content = if (it.role == ChatRole.User) "${it.name}: ${it.content}" else it.content,
			name = it.name?.removeNonSpacingMarks()?.removeBlankCharacters(),
		)
	}
}).choices.first().message?.content?.replace(Regex("^.*: "), "")

suspend fun models() = openAiClient.models()

private fun String.removeNonSpacingMarks() =
	Normalizer.normalize(this, Normalizer.Form.NFD).replace("\\p{Mn}+".toRegex(), "")

private fun String.removeBlankCharacters() = replace("\\s".toRegex(), "")
