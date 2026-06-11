package hu.fodortech.service;

import hu.fodortech.config.SecurityUtils;
import hu.fodortech.dto.ClientResponse;
import hu.fodortech.dto.CreateClientRequest;
import hu.fodortech.entity.Client;
import hu.fodortech.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final SecurityUtils securityUtils;

    public ClientService(ClientRepository clientRepository, SecurityUtils securityUtils) {
        this.clientRepository = clientRepository;
        this.securityUtils = securityUtils;
    }

    public ClientResponse createClient(CreateClientRequest req) {
        String companyId = securityUtils.getCurrentUserCompanyId();

        Client client = new Client();
        client.setName(req.getName());
        client.setPhone(req.getPhone());
        client.setEmail(req.getEmail());
        client.setCompanyId(companyId);

        Client saved = clientRepository.save(client);
        return ClientResponse.fromClient(saved);
    }

    public List<ClientResponse> getAllClients() {
        String companyId = securityUtils.getCurrentUserCompanyId();

        List<Client> clients = clientRepository.findAll();
        return clients.stream()
            .filter(client -> companyId.equals(client.getCompanyId()))
            .map(ClientResponse::fromClient)
            .collect(Collectors.toList());
    }
}
