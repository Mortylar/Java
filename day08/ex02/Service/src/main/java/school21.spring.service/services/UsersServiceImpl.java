package school21.spring.service.services;

import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;

@Component("UsersService")
public class UsersServiceImpl implements UsersService {

    private static final Long DEFAULT_ID = 1L;

    private UsersRepository repository;

    @Autowired
    public UsersServiceImpl(@Qualifier("UsersRepository")
                            UsersRepository repository) {
        this.repository = repository;
    }

    @Override
    public String signUp(String email) {
        if (repository.findByEmail(email).isPresent()) {
            throw new RuntimeException(
                String.format("User with email %s already registered", email));
        }
        repository.save(new User(DEFAULT_ID, email));
        return generateTemporaryPassword();
    }

    public String generateTemporaryPassword() {
        final int length = 12;
        int downBorder = (int)'0';
        int upBorder = (int)'z';
        Random random = new Random();

        return (random.ints(downBorder, upBorder + 1)
                    .filter(i -> isLetter(i))
                    .limit(length)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint,
                             StringBuilder::append)
                    .toString());
    }

    private boolean isLetter(int symbol) {
        final int firstDownBorder = (int)':';
        final int firstUpBorder = (int)'?';
        final int secondDownBorder = (int)'[';
        final int secondUpBorder = (int)'`';
        return ((symbol < firstDownBorder) || (symbol > firstUpBorder)) &&
            ((symbol < secondDownBorder) || (symbol > secondUpBorder));
    }
}
