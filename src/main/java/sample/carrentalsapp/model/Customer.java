package sample.carrentalsapp.model;

import lombok.*;

@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer {

    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private int vehicleId;
}
