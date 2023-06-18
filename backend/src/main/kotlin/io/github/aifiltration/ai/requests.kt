package io.github.aifiltration.ai

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.chat.chatCompletionRequest
import io.github.aifiltration.LOGGER

@OptIn(BetaOpenAI::class)
suspend fun chatCompletionRequest(
	chatCompletionMessages: List<ChatCompletionMessage> = emptyList(),
) = openAiClient.chatCompletion(chatCompletionRequest {
	maxTokens = 40
	model = modelId

	val defaultMessage = ChatMessage(
		role = ChatRole.System,
		content = defaultPrompt,
	)

	val promptMessages = mutableListOf(defaultMessage)
	promptMessages += defaultMessages.map {
		ChatMessage(
			role = ChatRole.User,
			content = it.content,
			name = it.author.replace(Regex("\\s"), "_")
		)
	}

	promptMessages += ChatMessage(
		role = ChatRole.System,
		content = "Starting conversation:",
	)

	promptMessages += chatCompletionMessages.map {
		ChatMessage(
			role = if (it.fromAI) ChatRole.Assistant else ChatRole.User,
			content = it.content,
			name = it.author.replace(Regex("\\s"), "_"),
		)
	}

	LOGGER.info(
		"Prompt messages: ${promptMessages.joinToString("\n") { "${it.name}: ${it.content}" }}"
	)

	messages = promptMessages.map {
		ChatMessage(
			role = it.role,
			content = "${it.name}: ${it.content}",
			name = it.name,
		)
	}
}).choices.first().message
