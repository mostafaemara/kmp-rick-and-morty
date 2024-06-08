import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import model.Character
import model.RickAndMortyResponse

class RickAndMortyApi {
    private val baseUrl="https://rickandmortyapi.com/api"
    
    private val httpClient= HttpClient(){
        install(ContentNegotiation){
            json()
            
        }
    }
    
  suspend  fun getCharacter(page:Int=1): RickAndMortyResponse<Character> {
        return httpClient.get("$baseUrl/character"){
            url {
                parameters.append("page",page.toString())
            }
        }.body()
    }
}