//package burundi.ilucky.Init;
//
//import burundi.ilucky.model.User;
//import burundi.ilucky.repository.UserRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//@Component
//public class AdminInitializer implements CommandLineRunner {
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public AdminInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    public void run(String... args) {
//        if (userRepository.findByUsername("admin").isEmpty()) {
//            User admin = new User();
//            admin.setUsername("admin");
//            admin.setPassword(passwordEncoder.encode("admin123"));
//            admin.setEmail("admin@example.com");
//            admin.setRole(UserRole.ADMIN);
//            userRepository.save(admin);
//            System.out.println("✅ Default admin account created!");
//        }
//    }
//}
//
