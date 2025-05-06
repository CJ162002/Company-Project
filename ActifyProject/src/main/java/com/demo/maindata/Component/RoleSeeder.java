package com.demo.maindata.Component;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import com.demo.maindata.Repository.RoleRepository;
import com.demo.maindata.entity.RoleEntity;
import com.demo.maindata.entity.RoleName;

@Component
public class RoleSeeder implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepo;

    @Override
    public void run(String... args) {
        try {
            if (roleRepo.findByName(RoleName.ADMIN).isEmpty()) {
                RoleEntity admin = new RoleEntity();
                admin.setName(RoleName.ADMIN);
                roleRepo.save(admin);
            }
            if (roleRepo.findByName(RoleName.MANAGER).isEmpty()) {
                RoleEntity manager = new RoleEntity();
                manager.setName(RoleName.MANAGER);
                roleRepo.save(manager);
            }
            if (roleRepo.findByName(RoleName.USER).isEmpty()) {
                RoleEntity user = new RoleEntity();
                user.setName(RoleName.USER);
                roleRepo.save(user);
            }
            System.out.println("✅ Roles initialized successfully!");

        } catch (Exception e) {
            System.err.println("🔥 Error while seeding roles: " + e.getMessage());
        }
    }
}
