package com.mobile.application.dtoConverter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.mobile.application.dto.UserDto;
import com.mobile.application.model.User;


@Component 
public class UserDtoAssembler {
	
	/** entityToDto Converter
	 * @param user
	 * @return
	 */
	public UserDto entityToDto(User user)
	{
		UserDto dto=new UserDto();
		dto.setId(user.getId());
		dto.setEmail(user.getEmail());
		dto.setPassword(user.getPassword());
		dto.setPhoneNumber(user.getPhoneNumber());
		dto.setRolename(user.getRolename());
		dto.setUserName(user.getUserName());
		
		return dto;
		
	}
	/**  entity page To Dto page converter
 	 * @param user
	 * @return
	 */
	public Page<UserDto> entityToDto(Page<User> user)
	{
		
		List<UserDto> list= user.stream().map(users -> entityToDto(users)).collect(Collectors.toList());
		Page <UserDto>pages = new PageImpl<>(list,user.getPageable(),user.getTotalPages());
		return pages;
	}
	
	/** dtoToEntity Converter
	 * @param dto
	 * @return
	 */
	public User dtoToEntity(UserDto dto)
	{
		User user=new User();
		user.setEmail(dto.getEmail());
		user.setId(dto.getId());
		user.setPassword(dto.getPassword());
		user.setPhoneNumber(dto.getPhoneNumber());
		user.setRolename(dto.getRolename());
		user.setUserName(dto.getUserName());	
		
		return user;
		
	}
	/** dto page To entity page converter
	 * @param dto
	 * @return
	 */
	public Page <User> dtoToEntity(Page<UserDto> dto)
	{	
		List<User> res= dto.stream().map(x -> dtoToEntity(x)).collect(Collectors.toList());
		
		Page <User>pages = new PageImpl<>(res, dto.getPageable(),dto.getTotalElements());
		return pages;
	}

	
}
