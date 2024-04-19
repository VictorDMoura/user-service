package academy.devojo.userservice.mapper;

import academy.devojo.userservice.domain.User;
import academy.devojo.userservice.response.UserGetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserGetResponse toUserGetResponse(User user);
    List<UserGetResponse> toUserGetResponseList(List<User> users);


}
