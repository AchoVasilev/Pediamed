package server.DAL.valueObjects;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import server.utils.guards.Guard;

import java.io.Serializable;
import java.util.regex.Pattern;

@Embeddable
@Getter
public class Email implements Serializable {
    private static final String MAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private final String email;

    public Email(String email) {
        this.email = this.validate(email);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || this.getClass() != obj.getClass())
            return false;
        var that = (Email) obj;
        return this.email.equals(that.email);
    }

    private String validate(String email) {
        if (!patternMatches(email)) {
            throw new IllegalArgumentException("Invalid email");
        }

        return Guard.Against.EmptyOrBlank(email);
    }

    private boolean patternMatches(String email) {
       return Pattern.compile(MAIL_REGEX)
                .matcher(email)
                .matches();
    }
}
