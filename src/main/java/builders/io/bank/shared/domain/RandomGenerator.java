package builders.io.bank.shared.domain;

import org.springframework.stereotype.Service;

import java.util.Random;
@Service
public final class RandomGenerator{
    private final Random random = new Random();

    private RandomGenerator() {}
    public String generateRandomHexString(int size) {
        StringBuilder stringBuilder = new StringBuilder();
        while (stringBuilder.length() < size) {
            stringBuilder.append(Integer.toHexString(this.random.nextInt()));
        }
        return stringBuilder.substring(0, size).toUpperCase();
    }
}
