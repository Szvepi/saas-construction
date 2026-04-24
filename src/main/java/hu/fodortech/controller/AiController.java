package hu.fodortech.controller;

import hu.fodortech.service.AiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@Tag(name = "AI Services", description = "APIs for AI-powered features")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/generate-quote")
    @Operation(summary = "Generate quote with AI", description = "Uses OpenAI to generate construction quote content")
    public ResponseEntity<String> generateQuote(@RequestBody Map<String, String> request) {
        String prompt = request.get("prompt");
        if (prompt == null || prompt.isEmpty()) {
            return ResponseEntity.badRequest().body("Prompt is required");
        }
        String response = aiService.generateQuote(prompt);
        return ResponseEntity.ok(response);
    }
}
