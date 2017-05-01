package services

import com.amazonaws.services.sqs.model.Message
import com.google.inject.Inject
import connections.S3ConnectorImpl
import dao.InMemRepository
import models.Pokemon
import play.api.Logger

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


trait PokemonService {
  def handleNewFile(message: Message)
  def addPokemons(list: List[String]): Future[Boolean]
  def addPokemon(pokemon: Pokemon): Future[Boolean]
  def getPokemon(key: Int): Option[Pokemon]
  def getAllPokemons(): List[Pokemon]
}

class PokemonServiceImpl @Inject() (repo: InMemRepository, s3con: S3ConnectorImpl) extends PokemonService{


  override def handleNewFile(message: Message): Unit = {
    Logger.info("Handling message")
    val elements = s3con.downloadFile()
    addPokemons(elements)
  }

  def addPokemons(list: List[String]): Future[Boolean] = {
    list.foreach(p => {
      val elements = p.split(",")
      val nr = elements(0).toInt
      val name = elements(1)
      val type_one = elements(2)
      val type_two = elements(3)
      val total = elements(4).toInt
      val hp = elements(5).toInt
      val attack = elements(6).toInt
      val defense = elements(7).toInt
      val specialAttack = elements(8).toInt
      val specialDefense = elements(9).toInt
      val speed = elements(10).toInt
      val generation = elements(11).toInt
      val legendary = elements(12).toBoolean
      val pokemon = Pokemon(nr, name, type_one, type_two, total, hp,
        attack, defense, specialAttack, specialDefense, speed, generation, legendary)
      repo.addPokemon(nr, pokemon)
    })
    Future(true)
  }

  override def addPokemon(pokemon: Pokemon): Future[Boolean] = {
    repo.addPokemon(pokemon.nr, pokemon)
    Future(true)
  }

  override def getPokemon(key: Int): Option[Pokemon] = {
    repo.getPokemon(key)
  }

  def getAllPokemons(): List[Pokemon] = {
    repo.getAllPokemons()
  }

}
