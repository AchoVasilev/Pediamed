package server.domain.valueObjects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import server.infrastructure.utils.guards.Guard;

import javax.persistence.Embeddable;
import java.util.regex.Pattern;

@Embeddable
@Getter
@NoArgsConstructor
public class PhoneNumber implements ValueObject{
    private static final String PHONE_REGEX = "^([+]?359)|0?(|-| )[7-9][789]\\d(|-| )\\d{3}(|-| )\\d{3}$";
    private String phoneNumber;
    public PhoneNumber(String phoneNumber) {
        this.phoneNumber = this.validate(phoneNumber);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || this.getClass() != obj.getClass())
            return false;
        var that = (PhoneNumber) obj;
        return this.phoneNumber.equals(that.phoneNumber);
    }

    private String validate(String mobilePhone) {
        if (!patternMatches(mobilePhone)) {
            throw new IllegalArgumentException("Invalid email");
        }

        return Guard.Against.EmptyOrBlank(mobilePhone);
    }

    private boolean patternMatches(String email) {
        return Pattern.compile(PHONE_REGEX)
                .matcher(email)
                .matches();
    }
}
