package models

case class Pokemon(nr: Int, name: String, type_one: String, type_two: String,
                   total: Int, hp: Int, attack: Int, defense: Int, specialAttack: Int,
                   specialDefense: Int, speed: Int, generation: Int, legendary: Boolean)
