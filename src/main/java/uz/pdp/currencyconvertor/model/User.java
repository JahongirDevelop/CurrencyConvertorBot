package uz.pdp.currencyconvertor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class User {
    private Long chatId;
    private String firstName;
    private String lastName;
    private String username;
    private String phoneNumber;
    private UserState state;
}
