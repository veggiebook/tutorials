package com.tutorialspoint;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class StreamTutorial {
	static int counter = 0;

	public static void generate() {
		Stream<String> streamGenerated = Stream.generate(() -> "element ").limit(10);
		streamGenerated.forEach(System.out::println);
	}

	public static void iterate() {
		Stream<Integer> streamIterated = Stream.iterate(40, n -> n - 2).limit(20);
		streamIterated.forEach(System.out::println);
	}

	public static void primitiveStream() {
		IntStream intStream = IntStream.range(1, 5);
		LongStream longStream = LongStream.rangeClosed(1, 3);
		Random random = new Random();
		DoubleStream doubleStream = random.doubles(3);
		intStream.forEach(System.out::println);
		longStream.forEach(System.out::println);
		doubleStream.forEach(System.out::println);
	}

	public static void streamFile() throws IOException {
		Path path = Paths.get("C:\\Users\\benling.cai\\Documents\\worknotes\\campus.xml");
		Stream<String> streamOfStrings = Files.lines(path);
		Stream<String> streamWithCharset = Files.lines(path, Charset.forName("UTF-8"));
		streamOfStrings.forEach(System.out::println);
		streamWithCharset.forEach(System.out::println);
		streamOfStrings.close();
		streamWithCharset.close();
	}

	private static void wasCalled() {
		counter++;
	}

	public static void pipeline() {
// intermediate operations which reduce the size of the stream should be placed before operations 
//		which are applying to each element. So, keep such methods as skip(), filter(), distinct()
//		at the top of your stream pipeline.
		List<String> list = Arrays.asList("abc", "abc2", "abc22", "abc222");
		counter = 0;
		Stream<String> stream = list.stream().filter(element -> {
			wasCalled();
			return element.contains("2");
		});
		System.out.println("counter called times: " + counter);

		Optional<String> stream2 = list.stream().filter(element -> {
			System.out.println("checking on " + element);
//			System.out.println("filter() was called");
			return element.contains("2");
		}).filter(elem -> {
			System.out.println("filter 22 was called");
			return elem.contains("22");
		}).map(element -> {
			System.out.println("map() was called");
			return element.toUpperCase();
		}).findAny();
		System.out.println(stream2);
	}

	public static void testReduce() {
		OptionalInt reduced = IntStream.range(1, 5).reduce((a, b) ->{
			System.out.println(a + "  " + b);
			return a + b;});
		System.out.println(reduced);
	}

	@SuppressWarnings("unchecked")
	private static void println(Stream s) {
		s.forEach(System.out::println);
	}

	public static void main(String[] args) throws Exception {
		testReduce();
	}

}
