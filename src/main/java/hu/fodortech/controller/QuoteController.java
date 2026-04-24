package hu.fodortech.controller;

import hu.fodortech.dto.CreateQuoteRequest;
import hu.fodortech.dto.QuoteResponse;
import hu.fodortech.entity.QuoteStatus;
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

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
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
}
