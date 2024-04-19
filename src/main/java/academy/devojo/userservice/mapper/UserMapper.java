package academy.devojo.userservice.mapper;

import academy.devojo.userservice.domain.User;
import academy.devojo.userservice.request.UserPostRequest;
import academy.devojo.userservice.request.UserPutRequest;
import academy.devojo.userservice.response.UserGetResponse;
import academy.devojo.userservice.response.UserPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserGetResponse toUserGetResponse(User user);
    List<UserGetResponse> toUserGetResponseList(List<User> users);

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(100_000))")
    User toUser(UserPostRequest request);
    User toUser(UserPutRequest response);
    UserPostResponse toUserPostResponse(User user);


}
