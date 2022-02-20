package sample.carrentalsapp.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SuccessMessages {

    //Login Exception
    SUCCESSFUL_AUTHENTICATION("You have successfully logged in!");
    //Admin Exception

    //Customer Exception

    private String message;
}
