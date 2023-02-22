package server.DAL.valueObjects;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.utils.guards.Guard;

import java.util.regex.Pattern;

@Embeddable
@Getter
@NoArgsConstructor
public class MobilePhone implements ValueObject{
    private static final String PHONE_REGEX = "^([+]?359)|0?(|-| )[7-9][789]\\d(|-| )\\d{3}(|-| )\\d{3}$";
    private String mobilePhone;
    public MobilePhone(String mobilePhone) {
        this.mobilePhone = this.validate(mobilePhone);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || this.getClass() != obj.getClass())
            return false;
        var that = (MobilePhone) obj;
        return this.mobilePhone.equals(that.mobilePhone);
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
