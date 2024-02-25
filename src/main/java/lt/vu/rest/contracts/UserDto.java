package lt.vu.rest.contracts;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mybatis.model.User;

@Getter @Setter
public class UserDto {
    private String email;
    private String name;
    private String surname;


    public UserDto(String email, String name, String surname) {
        this.email = email;
        this.name = name;
        this.surname = surname;
    }

    public static UserDto toUserDto(User user){
        if(user == null) return null;
        return new UserDto(user.getEmail(), user.getName(), user.getSurname());
    }

}
