package hu.fodortech.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.fodortech.dto.AiRequest;
import hu.fodortech.dto.AiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class AiService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.model:gpt-4o-mini}")
    private String model;

    @Autowired
    public AiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.openai.com/v1").build();
        this.objectMapper = new ObjectMapper();
    }

    public AiResponse generateQuote(AiRequest request) {
        String prompt = "Te egy tapasztalt magyar építőipari kivitelező vagy. Készíts egy ajánlatot az alábbi munkára magyarul: " + request.getInput() +
                ". Válasz formátuma JSON: {\"description\": \"leírás\", \"items\": [{\"name\": \"tétel\", \"quantity\": 1, \"unitPrice\": 100.0, \"unit\": \"m2\"}]}";

        Map<String, Object> requestBody = Map.of(
                "model", model,
                "messages", List.of(Map.of("role", "user", "content", prompt))
        );

        JsonNode response = webClient.post()
                .uri("/chat/completions")
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        String content = response.path("choices").get(0).path("message").path("content").asText();

        try {
            AiResponse aiResponse = objectMapper.readValue(content, AiResponse.class);
            return aiResponse;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse AI response", e);
        }
    }
}
