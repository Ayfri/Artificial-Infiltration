package io.github.aifiltration

import java.awt.Color


private const val RANDOM_COLORS_COUNT = 15

var anonymousNames = listOf(
	"BatonDeClan",
	"ChatNoJutsu",
	"ClashShinigami",
	"DragonSucréAuSucre",
	"GigaGaillard",
	"GrosNezBouché",
	"HunterStrawHat",
	"LigueDeTitan",
	"LittleSinge",
	"LégumeCoupant",
	"MiniPouletto",
	"NekoGearSolid",
	"NekoPikaPika",
	"OnePixelPiece",
	"OverMontre",
	"PetitPoneyEnMousse",
	"PirateChauve",
	"PixelHunters",
	"PizzaChevreMiel",
	"PizzaTwoPiece",
	"PizzaXPizza",
	"PèteQuiRépète",
	"RamenBallZ",
	"RamenSaiyan",
	"ValoloRiposte"
)

val usedNames = mutableMapOf<Int, String>()

var anonymousColors = (0..RANDOM_COLORS_COUNT).map {
	Color.getHSBColor(
		1.0f / RANDOM_COLORS_COUNT * it,
		0.4f,
		0.9f
	)
}

val usedColors = mutableMapOf<Int, Color>()

fun getAnonymousName(id: Int) = usedNames.getOrPut(id) {
	anonymousNames.filter { it !in usedNames.values }.random()
}

fun getAnonymousColor(id: Int) = usedColors.getOrPut(id) {
	anonymousColors.filter { it.rgb !in usedColors.values.map { it.rgb } }.random()
}
