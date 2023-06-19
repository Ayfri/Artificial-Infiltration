package io.github.aifiltration.ai

import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import io.github.aifiltration.env

val modelId = ModelId("gpt-4-0613")

val openAiClient = OpenAI(
	token = env("OPENAI_API_KEY"),
)

val defaultPrompt = env("DEFAULT_PROMPT")
