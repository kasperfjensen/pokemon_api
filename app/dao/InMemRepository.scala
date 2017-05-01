package dao


import com.google.inject.Singleton
import models.Pokemon
import play.api.Logger

import scala.collection.mutable

@Singleton
class InMemRepository extends Repository{

  val repo = new mutable.HashMap[Int, Pokemon]

  override def addPokemon(nr: Int, pokemon: Pokemon): Unit = {
    repo.put(nr, pokemon)
  }

  override def getPokemon(nr: Int): Option[Pokemon] = {
    Logger.info(repo.values.toString())
    repo.get(nr)
  }

  def getAllPokemons(): List[Pokemon] = {
    repo.values.toList
  }
}
