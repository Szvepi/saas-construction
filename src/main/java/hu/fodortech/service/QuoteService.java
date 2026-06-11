package hu.fodortech.service;

import hu.fodortech.config.SecurityUtils;
import hu.fodortech.dto.AiResponse;
import hu.fodortech.dto.CreateQuoteRequest;
import hu.fodortech.dto.QuoteResponse;
import hu.fodortech.entity.Client;
import hu.fodortech.entity.Quote;
import hu.fodortech.entity.QuoteStatus;
import hu.fodortech.repository.ClientRepository;
import hu.fodortech.repository.QuoteRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuoteService {

    private final QuoteRepository quoteRepository;
    private final ClientRepository clientRepository;
    private final SecurityUtils securityUtils;

    public QuoteService(QuoteRepository quoteRepository, ClientRepository clientRepository, SecurityUtils securityUtils) {
        this.quoteRepository = quoteRepository;
        this.clientRepository = clientRepository;
        this.securityUtils = securityUtils;
    }

    public QuoteResponse createQuote(CreateQuoteRequest request) {
        String companyId = securityUtils.getCurrentUserCompanyId();

        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        // Ensure the client belongs to the authenticated user's company
        if (!companyId.equals(client.getCompanyId())) {
            throw new RuntimeException("Access denied: Client does not belong to your company");
        }

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
        String companyId = securityUtils.getCurrentUserCompanyId();

        return quoteRepository.findAll()
                .stream()
                .filter(quote -> companyId.equals(quote.getClient().getCompanyId()))
                .map(QuoteResponse::fromQuote)
                .collect(Collectors.toList());
    }

    public Quote updateStatus(UUID id, QuoteStatus status) {
        String companyId = securityUtils.getCurrentUserCompanyId();

        Quote quote = quoteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quote not found"));

        // Ensure the quote belongs to the authenticated user's company
        if (!companyId.equals(quote.getClient().getCompanyId())) {
            throw new RuntimeException("Access denied: Quote does not belong to your company");
        }

        quote.setStatus(status);
        return quoteRepository.save(quote);
    }

    public QuoteResponse createQuoteFromAI(AiResponse aiResponse, Long clientId) {
        String companyId = securityUtils.getCurrentUserCompanyId();

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        // Ensure the client belongs to the authenticated user's company
        if (!companyId.equals(client.getCompanyId())) {
            throw new RuntimeException("Access denied: Client does not belong to your company");
        }

        BigDecimal totalPrice = aiResponse.getItems().stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, (a, b) -> a.add(b));

        Quote quote = new Quote();
        quote.setTitle(aiResponse.getDescription());
        quote.setTotalPrice(totalPrice);
        quote.setEstimatedCost(totalPrice); // Assuming estimated cost equals total for simplicity
        quote.setStatus(QuoteStatus.DRAFT);
        quote.setClient(client);

        Quote savedQuote = quoteRepository.save(quote);
        return QuoteResponse.fromQuote(savedQuote);
    }
}
