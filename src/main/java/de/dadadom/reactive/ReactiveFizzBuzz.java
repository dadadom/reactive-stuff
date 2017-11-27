package de.dadadom.reactive;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

/**
 * Solve the FizzBuzz programming task with Reactive Streams.
 */
public class ReactiveFizzBuzz {

    public static void main(String[] args) {

        int maxNumber = 1;
        try {
            maxNumber = Integer.valueOf(args[0]);
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            System.err.println("A valid number must be given as first argument.");
            System.exit(1);
        } catch (NumberFormatException nfe) {
            System.err.println("Invalid first argument: " + args[0] + ", must be a valid number.");
            System.exit(1);
        }

        final Flux<Integer> numbers = Flux.range(1, maxNumber);

        final Flux<String> fizz = Flux.just("", "", "Fizz").repeat();
        final Flux<String> buzz = Flux.just("", "", "", "", "Buzz").repeat();

        numbers //
            .zipWith(fizz, Tuples::of) //
            .zipWith(buzz, (t, s) -> Tuples.of(t.getT1(), t.getT2() + s)) //
            .map(t -> "".equals(t.getT2()) ? Integer.toString(t.getT1()) : t.getT2()) //
            .subscribe(System.out::println);
    }
}
