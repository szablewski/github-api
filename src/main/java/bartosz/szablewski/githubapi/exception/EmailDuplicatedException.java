package bartosz.szablewski.githubapi.exception;

import lombok.Getter;

@Getter
public class EmailDuplicatedException extends RuntimeException {

    private String email;

    public EmailDuplicatedException(String message) {
        super(message);
    }

    public EmailDuplicatedException(String message, String email) {
        super(message);
        this.email = email;
    }
}
