package controllers

import com.google.inject.Inject
import models.Pokemon

import play.api._
import play.api.libs.json.Json
import play.api.mvc._
import services.PokemonService

class PokemonController @Inject() (val service: PokemonService) extends Controller{

  implicit val pokemenFormat = Json.format[Pokemon]

  def getAll = Action {
    Ok(Json.obj("pokemons" -> service.getAllPokemons()))
  }

  def getPokemon(id: Int) = Action {
    Ok(Json.obj("pokemon" -> service.getPokemon(id)))
  }

}
