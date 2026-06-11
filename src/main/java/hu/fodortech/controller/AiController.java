package hu.fodortech.controller;

import hu.fodortech.dto.AiRequest;
import hu.fodortech.dto.AiResponse;
import hu.fodortech.service.AiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@Tag(name = "AI Services", description = "APIs for AI-powered features")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/generate-quote")
    @Operation(summary = "Generate quote with AI", description = "Uses OpenAI to generate construction quote content in JSON format")
    public ResponseEntity<AiResponse> generateQuote(@RequestBody AiRequest request) {
        AiResponse response = aiService.generateQuote(request);
        return ResponseEntity.ok(response);
    }
}
