package academy.devojo.userservice.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserGetResponse {

    private String firstName;
    private String lastName;
    private String email;

}
