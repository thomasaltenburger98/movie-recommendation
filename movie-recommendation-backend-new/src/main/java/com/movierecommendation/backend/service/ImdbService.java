package com.movierecommendation.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movierecommendation.backend.helpers.TmdbConfig;
import com.movierecommendation.backend.model.FilmDetail;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ImdbService {

    //private static final String IMDB_API_URL = "http://www.omdbapi.com/?t={title}&apikey={apikey}";
    private static final String IMDB_API_URL = "https://api.themoviedb.org/3/movie/{id}";
    private static final String TMDB_CONFIG_URL = "https://api.themoviedb.org/3/configuration";
    //TODO get from env file
    private static final String BEARER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyYTIxZjVmMGU1Njc1OGYwNWQ2ZDA0OWI1ZDQxN2Y3ZSIsInN1YiI6IjY2NWM5N2ZmMTMzNGJlMTNmODQ4MTliYiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.KJxxGaKVuQbdIi_m3Sg7TSf-65CN8Vj8kp4VYp91COY";

    /*public Map<String, Object> getFilmInfoFromImdb(String filmTitle) {
        RestTemplate restTemplate = new RestTemplate();
        String apiKey = "your_api_key"; // replace with your actual API key

        ResponseEntity<Map> response = restTemplate.getForEntity(IMDB_API_URL, Map.class, filmTitle, apiKey);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to fetch data from IMDB for film: " + filmTitle);
        }
    }*/

    public TmdbConfig getTmdbConfig() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(BEARER_TOKEN);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<TmdbConfig> response = restTemplate.exchange(TMDB_CONFIG_URL, HttpMethod.GET, entity, TmdbConfig.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to fetch TMDB configuration");
        }
    }

    public FilmDetail getFilmInfoFromTmdb(Long tmdbId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(BEARER_TOKEN);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<FilmDetail> response = restTemplate.exchange(IMDB_API_URL, HttpMethod.GET, entity, FilmDetail.class, tmdbId);
        FilmDetail filmDetail;

        if (response.getStatusCode().is2xxSuccessful()) {
            filmDetail = response.getBody();
        } else {
            throw new RuntimeException("Failed to fetch film info from TMDB for film tmdbId: " + tmdbId);
        }
        filmDetail = addCreditsToFilmDetail(tmdbId, filmDetail);
        return filmDetail;
    }

    private FilmDetail addCreditsToFilmDetail(Long tmdbId, FilmDetail filmDetail) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(BEARER_TOKEN);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<FilmDetail.Credits> response = restTemplate.exchange(IMDB_API_URL + "/credits", HttpMethod.GET, entity, FilmDetail.Credits.class, tmdbId);
        FilmDetail.Credits credits;

        if (response.getStatusCode().is2xxSuccessful()) {
            credits = response.getBody();
        } else {
            throw new RuntimeException("Failed to fetch credits from TMDB for film tmdbId: " + tmdbId);
        }
        filmDetail.setCredits(credits);
        return filmDetail;
    }

    /*public FilmDetail getFilmInfoFromTmdb(int id) {
        String jsonData = "{\"adult\":false,\"backdrop_path\":\"/lxD5ak7BOoinRNehOCA85CQ8ubr.jpg\",\"belongs_to_collection\":{\"id\":10194,\"name\":\"Toy Story Collection\",\"poster_path\":\"/7G9915LfUQ2lVfwMEEhDsn3kT4B.jpg\",\"backdrop_path\":\"/9FBwqcd9IRruEDUrTdcaafOMKUq.jpg\"},\"budget\":30000000,\"genres\":[{\"id\":16,\"name\":\"Animation\"},{\"id\":12,\"name\":\"Adventure\"},{\"id\":10751,\"name\":\"Family\"},{\"id\":35,\"name\":\"Comedy\"}],\"homepage\":\"http://toystory.disney.com/toy-story\",\"id\":862,\"imdb_id\":\"tt0114709\",\"origin_country\":[\"US\"],\"original_language\":\"en\",\"original_title\":\"Toy Story\",\"overview\":\"Led by Woody, Andy's toys live happily in his room until Andy's birthday brings Buzz Lightyear onto the scene. Afraid of losing his place in Andy's heart, Woody plots against Buzz. But when circumstances separate Buzz and Woody from their owner, the duo eventually learns to put aside their differences.\",\"popularity\":260.527,\"poster_path\":\"/uXDfjJbdP4ijW5hWSBrPrlKpxab.jpg\",\"production_companies\":[{\"id\":3,\"logo_path\":\"/1TjvGVDMYsj6JBxOAkUHpPEwLf7.png\",\"name\":\"Pixar\",\"origin_country\":\"US\"}],\"production_countries\":[{\"iso_3166_1\":\"US\",\"name\":\"United States of America\"}],\"release_date\":\"1995-10-30\",\"revenue\":394436586,\"runtime\":81,\"spoken_languages\":[{\"english_name\":\"English\",\"iso_639_1\":\"en\",\"name\":\"English\"}],\"status\":\"Released\",\"tagline\":\"Hang on for the comedy that goes to infinity and beyond!\",\"title\":\"Toy Story\",\"video\":false,\"vote_average\":7.971,\"vote_count\":17907,\"credits\":{\"cast\":[{\"adult\":false,\"gender\":2,\"id\":31,\"known_for_department\":\"Acting\",\"name\":\"Tom Hanks\",\"original_name\":\"Tom Hanks\",\"popularity\":78.039,\"profile_path\":\"/mKr8PN8sn80LzVaZMg8L52kmakm.jpg\",\"cast_id\":14,\"character\":\"Woody (voice)\",\"credit_id\":\"52fe4284c3a36847f8024f95\",\"order\":0},{\"adult\":false,\"gender\":2,\"id\":12898,\"known_for_department\":\"Acting\",\"name\":\"Tim Allen\",\"original_name\":\"Tim Allen\",\"popularity\":24.313,\"profile_path\":\"/PGLz0YLg4eB49BA6QxzHF5czxX.jpg\",\"cast_id\":15,\"character\":\"Buzz Lightyear (voice)\",\"credit_id\":\"52fe4284c3a36847f8024f99\",\"order\":1},{\"adult\":false,\"gender\":2,\"id\":7167,\"known_for_department\":\"Acting\",\"name\":\"Don Rickles\",\"original_name\":\"Don Rickles\",\"popularity\":19.684,\"profile_path\":\"/iJLQV4dcbTUgxlWJakjDldzlMXS.jpg\",\"cast_id\":16,\"character\":\"Mr. Potato Head (voice)\",\"credit_id\":\"52fe4284c3a36847f8024f9d\",\"order\":2}],\"crew\":[{\"adult\":false,\"gender\":2,\"id\":8,\"known_for_department\":\"Directing\",\"name\":\"Lee Unkrich\",\"original_name\":\"Lee Unkrich\",\"popularity\":6.125,\"profile_path\":\"/crb297utC6W4HSstOe5djDPTwEN.jpg\",\"credit_id\":\"52fe4284c3a36847f8024f8b\",\"department\":\"Editing\",\"job\":\"Editor\"}]}}";

        try {
            ObjectMapper mapper = new ObjectMapper();
            FilmDetail filmDetail = mapper.readValue(jsonData, FilmDetail.class);
            return filmDetail;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON data", e);
        }
    }*/
}