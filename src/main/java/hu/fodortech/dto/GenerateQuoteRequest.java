package hu.fodortech.dto;

public class GenerateQuoteRequest {

    private String input;

    private Long clientId;

    public GenerateQuoteRequest() {
    }

    public GenerateQuoteRequest(String input, Long clientId) {
        this.input = input;
        this.clientId = clientId;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
