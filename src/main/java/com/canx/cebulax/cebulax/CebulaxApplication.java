package com.canx.cebulax.cebulax;

import com.canx.cebulax.cebulax.dto.UserDTO;
import com.canx.cebulax.cebulax.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CebulaxApplication implements CommandLineRunner {

	private final UserService userService;

	public CebulaxApplication(UserService userService) {
		this.userService = userService;
	}

	public static void main(String[] args) {
		SpringApplication.run(CebulaxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		UserDTO userDTO = new UserDTO();
		userDTO.setName("kamil");
		userDTO.setName("roman");

		long id = userService.createUser(userDTO);

		System.out.println(id);

	}
}
