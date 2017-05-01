package dao

import models.Pokemon


trait Repository {

  def addPokemon(nr: Int, pokemon: Pokemon): Unit

  def getPokemon(nr: Int): Option[Pokemon]

  def getAllPokemons(): List[Pokemon]
}
