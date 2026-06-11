package hu.fodortech.dto;

import hu.fodortech.entity.Client;

public class ClientResponse {

    private Long id;

    private String name;

    private String phone;

    private String email;

    public ClientResponse() {
    }

    public ClientResponse(Long id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public static ClientResponse fromClient(Client client) {
        return new ClientResponse(
            client.getId(),
            client.getName(),
            client.getPhone(),
            client.getEmail()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
