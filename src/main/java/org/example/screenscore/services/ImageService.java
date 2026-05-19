package org.example.screenscore.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.screenscore.models.Type;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ImageService {
    private final String apikey = "85a531ec";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public String download(String title, Type type) throws URISyntaxException, JsonProcessingException {
        if(type == Type.Anime) {
            return downloadFromJikan(title);
        }
        return downloadFromOMDb(title, type);
    }

    private String fetchOMDb(String title, Type type) throws URISyntaxException {
        String url = "https://www.omdbapi.com/?t=";
        String encodedTitle = title.replaceAll(" ", "%20");

        String OMDbType = switch (type) {
            case Movie -> "movie";
            case Anime -> "N/A";
            case Series -> "series";
        };

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI(url + encodedTitle + "&apikey=" + apikey + "&type=" + OMDbType))
                .GET()
                .build();

        HttpResponse<String> response = null;

        try {
            response = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return response.body();
    }

    private String downloadFromOMDb(String title, Type type) throws URISyntaxException, JsonProcessingException {
        String body = fetchOMDb(title, type);
        JsonNode root = mapper.readTree(body);
        String response = root.get("Response").asText();

        if(response.equals("True")){
            try{
                String url = root.get("Poster").asText();

                if(url.equals("N/A"))
                    return "N/A";

                String fileName = root.get("imdbID").asText();
                String format = url.lastIndexOf(".") == -1 ? ".jpg" : url.substring(url.lastIndexOf("."));

                Path dirPath = Paths.get("images");
                if(!Files.exists(dirPath)){
                    Files.createDirectories(dirPath);
                }


                Path filePath = dirPath.resolve(fileName + format);
                String imgPath = filePath.toString();

                if(Files.exists(filePath)){
                    return imgPath;
                }

                try(InputStream in = URI.create(url).toURL().openStream()){
                    Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);
                }

                return imgPath;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "N/A";
    }

    private String fetchJikan(String title) throws URISyntaxException {
        String url = "https://api.jikan.moe/v4/anime?q=";
        String encodedTitle = title.replaceAll(" ", "%20");

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI(url + encodedTitle ))
                .GET()
                .build();

        HttpResponse<String> response = null;

        try {
            response = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return response.body();
    }

    private String downloadFromJikan(String title) throws URISyntaxException, JsonProcessingException {
        String body = fetchJikan(title);
        JsonNode root = mapper.readTree(body);

        try{
            JsonNode data = root.get("data");

            if(data.isArray() && !data.isEmpty()){
                JsonNode anime = data.get(0);

                String fileName = anime.path("mal_id").asText() + ".jpg";

                String urlAnime = anime.path("images")
                        .path("jpg")
                        .path("image_url").asText();

                Path dirPath = Paths.get("images");
                if(!Files.exists(dirPath)){
                    Files.createDirectories(dirPath);
                }

                Path filePath = dirPath.resolve(fileName);
                String imgPath = filePath.toString();

                if(Files.exists(filePath)){
                    return imgPath;
                }

                try(InputStream in = URI.create(urlAnime).toURL().openStream()){
                    Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);
                }

                return imgPath;
            }
        }catch(IOException e){
            e.printStackTrace();
        }

        return "N/A";
    }
}
