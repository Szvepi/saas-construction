package hu.fodortech.dto;

public class AiRequest {

    private String input;

    public AiRequest() {
    }

    public AiRequest(String input) {
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }
}
