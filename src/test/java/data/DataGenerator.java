package data;

import com.github.javafaker.Faker;
import lombok.Data;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@UtilityClass
public class DataGenerator {


    public static String getPlanningDate(int cnt) {
        return LocalDate.now().plusDays(cnt).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @UtilityClass
    public static class Registration {
        public static UserInfo getUserInfo(String locale) {
            Faker faker = new Faker(new Locale(locale));
            return new UserInfo(faker.address().cityName(),
                    faker.name().fullName(),
                    faker.phoneNumber().phoneNumber());
        }
    }

    @Data
    public static class UserInfo {
        private final String city;
        private final String name;
        private final String phone;
    }
}
