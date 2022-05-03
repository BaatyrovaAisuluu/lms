package kg.peaksoft.peaksoftlmsbb4.annotations.phoneNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {

    @Override
    public boolean isValid(String phoneNumber,
                           ConstraintValidatorContext constraintValidatorContext) {

        if (phoneNumber.length() == 10 || phoneNumber.length() == 13) {
            if (phoneNumber.charAt(0) == '+') {
                phoneNumber = phoneNumber.substring(1);
            }

            if (phoneNumber.matches("[0-9]*")) {
                return phoneNumber.length() == 10 || phoneNumber.length() == 12;
            }
        }

        return false;
    }
}
