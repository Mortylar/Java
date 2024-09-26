package school21.spring.service.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;

public class Main {

    private static final String[] BEAN_NAMES = {
        "SpringUsersRepositoryJdbc", "HikariUsersRepositoryJdbc",
        "SpringUsersRepositoryJdbcTemplate",
        "HikariUsersRepositoryJdbcTemplate"};

    public static void main(String[] args) {
        AbstractApplicationContext context =
            new ClassPathXmlApplicationContext("context.xml");
        UsersRepository usersRepository;
        for (int i = 0; i < BEAN_NAMES.length; ++i) {
            String name = BEAN_NAMES[i];
            usersRepository = context.getBean(name, UsersRepository.class);
            System.out.printf("\nResult for bean %s:\n", name);
            printList(usersRepository.findAll());
        }
        context.close();
    }

    private static void printList(List<User> userList) {
        System.out.print(userList.stream()
                             .map(user -> String.valueOf(user))
                             .collect(Collectors.joining("\n", "\n", "\n")));
    }
}
