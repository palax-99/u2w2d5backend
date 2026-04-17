package antoninopalazzolo.u2w2d5backend.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
//annotazione per classe di configurazione
public class CloudinaryConfig {

    @Bean
    public Cloudinary getImageUploader(
            @Value("${cloudinary.name}") String cloudName,
            // @Value legge il valore da application.properties
            // che a sua volta lo prende da env.properties
            // quindi le credenziali non sono mai scritte nel codice!
            @Value("${cloudinary.apikey}") String apiKey,
            @Value("${cloudinary.secret}") String apiSecret) {

        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", cloudName);
        // le chiavi "cloud_name", "api_key", "api_secret"
        // si scrivono esattamente cosi
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);

        return new Cloudinary(config);
    }
}
