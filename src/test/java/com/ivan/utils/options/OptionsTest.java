package com.ivan.utils.options;

public class OptionsTest {
	private static final class Options {
		public static final Option<String> NAME = Option.createOptional();
		public static final Option<Integer> MAX = Option.createRequired(5);
		public static final Option<Boolean> ENABLED = Option.createRequired(true);
	}

	private static final OptionSet options = new OptionSet();

	public static void main(final String[] args) {
		System.out.println(options.get(Options.NAME));
		options.set(Options.NAME, "name");
		System.out.println(options.get(Options.NAME));
		options.reset(Options.NAME);
		System.out.println(options.get(Options.NAME));

		options.set(Options.MAX, 15);
		options.set(Options.ENABLED, false);

		System.out.println();
		System.out.println(options.get(Options.NAME));
		System.out.println(options.get(Options.MAX));
		System.out.println(options.get(Options.ENABLED));

		options.reset();
		System.out.println();
		System.out.println(options.get(Options.NAME));
		System.out.println(options.get(Options.MAX));
		System.out.println(options.get(Options.ENABLED));

		System.out.println();
		options.set(Options.NAME, null);
		try {
			options.set(Options.MAX, null);
		} catch (final IllegalArgumentException e) {
			System.out.println("max cannot be null");
		}
		try {
			options.set(Options.ENABLED, null);
		} catch (final IllegalArgumentException e) {
			System.out.println("enabled cannot be null");
		}

		System.out.println();
		System.out.println(options.get(Options.NAME));
		System.out.println(options.get(Options.MAX));
		System.out.println(options.get(Options.ENABLED));
	}
}
