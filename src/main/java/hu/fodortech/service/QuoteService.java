package hu.fodortech.service;

import hu.fodortech.dto.CreateQuoteRequest;
import hu.fodortech.dto.QuoteResponse;
import hu.fodortech.entity.Client;
import hu.fodortech.entity.Quote;
import hu.fodortech.entity.QuoteStatus;
import hu.fodortech.repository.ClientRepository;
import hu.fodortech.repository.QuoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuoteService {

    private final QuoteRepository quoteRepository;
    private final ClientRepository clientRepository;

    public QuoteService(QuoteRepository quoteRepository, ClientRepository clientRepository) {
        this.quoteRepository = quoteRepository;
        this.clientRepository = clientRepository;
    }

    public QuoteResponse createQuote(CreateQuoteRequest request) {
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Quote quote = new Quote();
        quote.setTitle(request.getTitle());
        quote.setTotalPrice(request.getTotalPrice());
        quote.setEstimatedCost(request.getEstimatedCost());
        quote.setStatus(QuoteStatus.DRAFT);
        quote.setClient(client);

        Quote savedQuote = quoteRepository.save(quote);
        return QuoteResponse.fromQuote(savedQuote);
    }

    public List<QuoteResponse> getAllQuotes() {
        return quoteRepository.findAll()
                .stream()
                .map(QuoteResponse::fromQuote)
                .collect(Collectors.toList());
    }

    public Quote updateStatus(UUID id, QuoteStatus status) {
        Quote quote = quoteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quote not found"));
        quote.setStatus(status);
        return quoteRepository.save(quote);
    }
}
