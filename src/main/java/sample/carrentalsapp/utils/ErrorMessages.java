package sample.carrentalsapp.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorMessages {

    // LoginException
    INVALID_USERNAME_OR_PASSWORD("Invalid username or password!"),



    // AdminException
    WRONG_COMPLETE_FIELDS("You must complete all fields!"),

    // CustomerException
    INVALID_SERIAL_NUMBER("Invalid serial number, try again!"),
    INVALID_RENTED_STATUS("Vehicle is not rented, try again!");


    private String message;
}
