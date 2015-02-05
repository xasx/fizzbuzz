package pkgname;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

/**
 * @author xasx
 * @since 1.0
 */
public class FizzBuzz {

    public interface FizzyBizzy {
        String getAndPrepare();
    }

    static final class StringFizzyBizzy implements FizzyBizzy {

        final String content;

        StringFizzyBizzy(String content) {
            this.content = content;
        }

        @Override
        public String getAndPrepare() {
            return content;
        }
    }

    static FizzyBizzy fizz = new StringFizzyBizzy("fizz");
    static FizzyBizzy buzz = new StringFizzyBizzy("buzz");
    static FizzyBizzy fizzbuzz = new StringFizzyBizzy("fizzbuzz");

    static final class IncrementingFizzyBizzy implements FizzyBizzy {

        final AtomicLong value;
        static long increment;

        IncrementingFizzyBizzy(long init) {
            this.value = new AtomicLong(init);
        }

        @Override
        public String getAndPrepare() {
            return String.valueOf(value.getAndAdd(increment));
        }
    }

    static FizzyBizzy n(long number) {
        return new IncrementingFizzyBizzy(number);
    }

    static final List<FizzyBizzy> fizzBuzzInit;

    static {
        fizzBuzzInit = ImmutableList
                .of(n(1), n(2), fizz, n(4), buzz, fizz, n(7), n(8), fizz, buzz, n(11), fizz, n(13), n(14), fizzbuzz);
        IncrementingFizzyBizzy.increment = fizzBuzzInit.size();
    }

    static void generateFizzBuzz(long howFar) {

        long counter = 0;
        for (FizzyBizzy fizzyBizzy : Iterables.cycle(fizzBuzzInit)) {
            if (++counter >= howFar) break;
            System.out.println(fizzyBizzy.getAndPrepare());
        }

    }

    public static void main(String[] args) {
        generateFizzBuzz(100);
    }
}
