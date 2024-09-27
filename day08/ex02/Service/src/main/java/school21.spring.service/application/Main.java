package school21.spring.service.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import school21.spring.service.beans.ApplicationConfig;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;

public class Main {

    public static void main(String[] args) {
        AbstractApplicationContext context =
            new AnnotationConfigApplicationContext(ApplicationConfig.class);
        UsersRepository usersRepository;
        usersRepository = context.getBean(UsersRepository.class);
        printList(usersRepository.findAll());
        context.close();
    }

    private static void printList(List<User> userList) {
        System.out.print(userList.stream()
                             .map(user -> String.valueOf(user))
                             .collect(Collectors.joining("\n", "\n", "\n")));
    }
}
