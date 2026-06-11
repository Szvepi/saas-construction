package hu.fodortech.controller;

import hu.fodortech.dto.ClientResponse;
import hu.fodortech.dto.CreateClientRequest;
import hu.fodortech.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@Tag(name = "Client Management", description = "APIs for managing construction clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    @Operation(summary = "Create a new client", description = "Creates a new construction client")
    public ResponseEntity<ClientResponse> createClient(@RequestBody CreateClientRequest request) {
        ClientResponse response = clientService.createClient(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get all clients", description = "Retrieves a list of all construction clients")
    public ResponseEntity<List<ClientResponse>> getAllClients() {
        List<ClientResponse> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }
}
