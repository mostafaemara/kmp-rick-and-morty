import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import model.Character
import model.Episode
import model.Location
import model.RickAndMortyResponse

class RickAndMortyApi {
    private val baseUrl="https://rickandmortyapi.com/api"
    
    private val httpClient= HttpClient(){
        install(ContentNegotiation){
            json()
            
        }
    }
    
    suspend  fun getCharacter(nextPage:String? =null): RickAndMortyResponse<List<Character>> {
        return httpClient.get(nextPage?:"$baseUrl/character"){

        }.body()
    }


    suspend fun  getLocations (page:Int=1):RickAndMortyResponse<List<Location>>
    {
        return httpClient.get("$baseUrl/location"){
            url {
                parameters.append("page",page.toString())
            }
        }.body()
    }

    suspend fun  getEpisodes (page:Int=1):RickAndMortyResponse<List<Episode>>
    {
        return httpClient.get("$baseUrl/episode"){
            url {
                parameters.append("page",page.toString())
            }
        }.body()
    }
}