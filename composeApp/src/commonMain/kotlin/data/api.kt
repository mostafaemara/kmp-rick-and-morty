import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import model.Character
import model.CharacterStatus
import model.Episode
import model.Gender
import model.Location
import model.RickAndMortyResponse

class RickAndMortyApi {
    private val baseUrl = "https://rickandmortyapi.com/api"

    private val httpClient = HttpClient() {
        install(ContentNegotiation) {
            json()

        }
    }

    suspend fun getCharacters(
        nextPage: String? = null,
        gender: Gender? = null,
        status: CharacterStatus? = null,
        name: String? = null
    ): RickAndMortyResponse<List<Character>> {
        return httpClient.get(nextPage ?: "$baseUrl/character") {
            url {
                if (gender != null) {
                    parameters.append("gender", gender.name)
                }
                if (status != null) {
                    parameters.append("status", status.name)
                }
                if (name != null) {
                    parameters.append("name", name)
                }

            }

        }.body()
    }

    suspend fun getCharacter(id: Int): Character {
        return httpClient.get("$baseUrl/character/$id") {
            url {


            }

        }.body()
    }

    suspend fun getLocations(page: Int = 1): RickAndMortyResponse<List<Location>> {
        return httpClient.get("$baseUrl/location") {
            url {
                parameters.append("page", page.toString())
            }
        }.body()
    }

    suspend fun getEpisodes(page: Int = 1): RickAndMortyResponse<List<Episode>> {
        return httpClient.get("$baseUrl/episode") {
            url {
                parameters.append("page", page.toString())
            }
        }.body()
    }
}