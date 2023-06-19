package io.github.aifiltration

fun removeDuplicateConsecutiveWords(input: String, minDuplicates: Int = 3): List<String> {
	val words = input.split(Regex("\\s+"))
	val duplicates = mutableListOf<String>()
	var previousWord = ""
	var duplicateCount = 0
	for (word in words) {
		if (word == previousWord) {
			duplicateCount++
		} else {
			if (duplicateCount >= minDuplicates) {
				duplicates.add(previousWord)
			}
			duplicateCount = 1
			previousWord = word
		}
	}
	if (duplicateCount >= minDuplicates) {
		duplicates.add(previousWord)
	}
	return duplicates.distinct().filter { it.length > 1 }
}

fun List<Item>.removeDuplicateConsecutiveWords(minDuplicates: Int = 3) =
	filter { removeDuplicateConsecutiveWords(it.content, minDuplicates).isEmpty() }
