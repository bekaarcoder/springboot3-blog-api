package com.blitzstriker.blogapi;

import com.blitzstriker.blogapi.entity.Role;
import com.blitzstriker.blogapi.repository.RoleRepository;
import com.blitzstriker.blogapi.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class BlogApiApplication implements CommandLineRunner {
    private final RoleRepository roleRepository;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(BlogApiApplication.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            var role1 = new Role();
            role1.setId(AppConstants.ROLE_ADMIN);
            role1.setName("ROLE_ADMIN");

            var role2 = new Role();
            role2.setId(AppConstants.ROLE_USER);
            role2.setName("ROLE_USER");

            List<Role> roles = List.of(role1, role2);
            roleRepository.saveAll(roles);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
