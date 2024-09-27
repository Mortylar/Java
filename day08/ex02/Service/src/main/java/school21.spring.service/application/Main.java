package school21.spring.service.application;

import java.io.Closeable;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import school21.spring.service.config.ApplicationConfig;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.services.UsersService;

public class Main {

    public static void main(String[] args) {
        AbstractApplicationContext context =
            new AnnotationConfigApplicationContext(ApplicationConfig.class);
        UsersService service = (UsersService)context.getBean("UsersService");
        try {
            System.out.printf("\nPassword = %s\n", service.signUp("Aboba"));
        } catch (Exception e) {
            System.out.printf(e.getMessage());
        }
        UsersRepository usersRepository;
        usersRepository =
            (UsersRepository)context.getBean("UsersRepositoryTemplate");
        printList(usersRepository.findAll());
        context.close();
        // System.exit(0);
    }

    private static void printList(List<User> userList) {
        System.out.print(userList.stream()
                             .map(user -> String.valueOf(user))
                             .collect(Collectors.joining("\n", "\n", "\n")));
    }
}
