package hu.fodortech.controller;

import hu.fodortech.dto.AiRequest;
import hu.fodortech.dto.AiResponse;
import hu.fodortech.dto.CreateQuoteRequest;
import hu.fodortech.dto.GenerateQuoteRequest;
import hu.fodortech.dto.QuoteResponse;
import hu.fodortech.entity.QuoteStatus;
import hu.fodortech.service.AiService;
import hu.fodortech.service.QuoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/quotes")
@Tag(name = "Quote Management", description = "APIs for managing construction quotes")
public class QuoteController {

    private final QuoteService quoteService;
    private final AiService aiService;

    public QuoteController(QuoteService quoteService, AiService aiService) {
        this.quoteService = quoteService;
        this.aiService = aiService;
    }

    @PostMapping
    @Operation(summary = "Create a new quote", description = "Creates a new construction quote for a client")
    public ResponseEntity<QuoteResponse> createQuote(@RequestBody CreateQuoteRequest request) {
        QuoteResponse createdQuote = quoteService.createQuote(request);
        return ResponseEntity.ok(createdQuote);
    }

    @GetMapping
    @Operation(summary = "Get all quotes", description = "Retrieves all construction quotes")
    public ResponseEntity<List<QuoteResponse>> getAllQuotes() {
        List<QuoteResponse> quotes = quoteService.getAllQuotes();
        return ResponseEntity.ok(quotes);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update quote status", description = "Updates the status of a specific quote")
    public ResponseEntity<QuoteResponse> updateStatus(@PathVariable UUID id, @RequestBody QuoteStatus status) {
        quoteService.updateStatus(id, status);
        List<QuoteResponse> quotes = quoteService.getAllQuotes();
        QuoteResponse updated = quotes.stream()
                .filter(q -> q.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Quote not found"));
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/generate")
    @Operation(summary = "Generate quote with AI", description = "Generates a construction quote using AI based on input and client")
    public ResponseEntity<QuoteResponse> generateQuote(@RequestBody GenerateQuoteRequest request) {
        AiRequest aiRequest = new AiRequest(request.getInput());
        AiResponse aiResponse = aiService.generateQuote(aiRequest);
        QuoteResponse quoteResponse = quoteService.createQuoteFromAI(aiResponse, request.getClientId());
        return ResponseEntity.ok(quoteResponse);
    }
}
