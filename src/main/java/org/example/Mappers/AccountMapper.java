package org.example.Mappers;

import org.example.DTOs.AccountDTO;
import org.example.Entities.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountMapper {
    @Mapping(target = "username", source = "accountDto.username")
    @Mapping(target = "email", source = "accountDto.email")
    @Mapping(target = "password", source = "accountDto.password")
    @Mapping(target = "id", source = "accountId")
    Account convertDtoToEntity(AccountDTO accountDto, Long accountId);

    @Mapping(target = "username", source = "account.username")
    @Mapping(target = "email", source = "account.email")
    @Mapping(target = "password", source = "account.password")
    AccountDTO convertEntityToDto(Account account);
}
