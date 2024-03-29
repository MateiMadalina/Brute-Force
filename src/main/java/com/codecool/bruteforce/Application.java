package com.codecool.bruteforce;

import com.codecool.bruteforce.authentication.AuthenticationService;
import com.codecool.bruteforce.authentication.AuthenticationServiceImpl;
import com.codecool.bruteforce.logger.ConsoleLogger;
import com.codecool.bruteforce.logger.Logger;
import com.codecool.bruteforce.passwords.breaker.PasswordBreakerImpl;
import com.codecool.bruteforce.passwords.generator.PasswordGenerator;
import com.codecool.bruteforce.passwords.generator.PasswordGeneratorImpl;
import com.codecool.bruteforce.passwords.model.AsciiTableRange;
import com.codecool.bruteforce.users.generator.UserGenerator;
import com.codecool.bruteforce.users.generator.UserGeneratorImpl;
import com.codecool.bruteforce.users.model.User;
import com.codecool.bruteforce.users.repository.CrackedUsers;
import com.codecool.bruteforce.users.repository.UserRepository;
import com.codecool.bruteforce.users.repository.UserRepositoryImpl;

import java.util.List;

public class Application {

    private static Logger logger = new ConsoleLogger();

    private static final AsciiTableRange lowercaseChars = new AsciiTableRange(97, 122);
    private static final AsciiTableRange uppercaseChars = new AsciiTableRange(65, 90);
    private static final AsciiTableRange numbers = new AsciiTableRange(48, 57);

    public static void main(String[] args) {

        String dbFile = "src/main/resources/Users.db";
        String dbFileCracked = "src/main/resources/CrackedUsers.db";
        UserRepository userRepository = new UserRepositoryImpl(dbFile, logger);
        CrackedUsers crackedUsers = new CrackedUsers(dbFileCracked,logger);
        crackedUsers.deleteAll();


        userRepository.deleteAll();

       List<PasswordGenerator> passwordGenerators = createPasswordGenerators();

       UserGenerator userGenerator = new UserGeneratorImpl(logger, passwordGenerators);
       int userCount = 10;
       int maxPwLength = 2;



       addUsersToDb(userCount, maxPwLength, userGenerator, userRepository);
        System.out.println(userRepository.getAll());

       logger.logInfo(String.format("Database initialized with %d users; maximum password length: %d%n", userCount, maxPwLength));

        AuthenticationService authenticationService = new AuthenticationServiceImpl(userRepository);
        breakUsers(userCount, maxPwLength, authenticationService);

    }

    private static void addUsersToDb(int count, int maxPwLength, UserGenerator userGenerator,
                                     UserRepository userRepository)
    {
        List<User> users = userGenerator.generate(count,maxPwLength);
        for (User user: users) {
            userRepository.add(user.userName(),user.password());
        }
    }

    private static List<PasswordGenerator> createPasswordGenerators() {
        var lowercasePwGen = new PasswordGeneratorImpl(lowercaseChars);
        var uppercasePwGen = new PasswordGeneratorImpl(lowercaseChars, uppercaseChars);
        PasswordGenerator numbersPwGen = new PasswordGeneratorImpl(numbers,lowercaseChars, uppercaseChars);
        return List.of(lowercasePwGen, uppercasePwGen, numbersPwGen);
    }

    private static void breakUsers(int userCount, int maxPwLength, AuthenticationService authenticationService) {
        var passwordBreaker = new PasswordBreakerImpl();
        String dbFileCracked = "src/main/resources/CrackedUsers.db";
        CrackedUsers crackedUsers = new CrackedUsers(dbFileCracked,logger);
        crackedUsers.deleteAll();
        logger.logInfo("Initiating password breaker...\n");

        for (int i = 1; i <= userCount; i++) {
            String user = "user" + i;
            for (int j = 1; j <= maxPwLength; j++) {
                logger.logInfo(String.format("Trying to break %s with all possible password combinations with length = %d...%n", user, j));

                // start measuring time
                long startTime = System.currentTimeMillis();

                // Get all pw combinations
                List<String> pwCombinations = passwordBreaker.getCombinations(j);
                boolean broken = false;

                for (String pw : pwCombinations) {
                    // Try to authenticate the current user with pw
                    // If successful, stop measuring time, and print the pw and the elapsed time to the console, then go to next user
                    if(authenticationService.authenticate(user,pw)){
                        broken = true;
                        long endTime = System.currentTimeMillis();
                        long elapsedTime = endTime - startTime;
                        logger.logInfo("Time used for breaking the passworld: " + elapsedTime);
                        logger.logInfo("Broken password is: " + pw);
                        crackedUsers.add(elapsedTime,pw);
                        break;
                    }
                }

                if (broken) {
                    break;
                }
            }
        }
    }

}
