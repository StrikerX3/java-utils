package com.ivan.utils.lazy;

import java.util.Arrays;

public class TestLazy {
	public static void main(final String[] args) {
		testLazyInit();
		System.out.println();
		testLazyInitArray1();
		System.out.println();
		testLazyInitArray2();
	}

	private static void testLazyInit() {
		final LazyInit<Object> init = new LazyInit<Object>() {
			@Override
			protected Object initValue() {
				System.out.println("initialized");
				return new Object();
			}
		};

		System.out.println("init created");
		System.out.println("got object: " + init.get());
		System.out.println("got object: " + init.get());
	}

	private static void testLazyInitArray1() {
		final LazyInitArray<Object> initArr = new LazyInitArray<Object>(Object.class, 4) {
			@Override
			protected Object initValue(final int index) {
				System.out.println("initialized " + index);
				return new Object() {
					@Override
					public String toString() {
						return super.toString() + " - " + index;
					}
				};
			}
		};
		System.out.println("initArr1 created");

		for (int i = 0; i < 2; i++) {
			System.out.println(i + " - got object: " + initArr.get(i));
		}
		for (int i = 1; i < 3; i++) {
			System.out.println(i + " - got object: " + initArr.get(i));
		}

		System.out.println(Arrays.toString(initArr.get()));
	}

	private static void testLazyInitArray2() {
		final LazyInitArray<Object> initArr = new LazyInitArray<Object>(Object.class, 3) {
			@Override
			protected Object initValue(final int index) {
				System.out.println("initialized " + index);
				return new Object() {
					@Override
					public String toString() {
						return super.toString() + " - " + index;
					}
				};
			}
		};
		System.out.println("initArr2 created");

		System.out.println(Arrays.toString(initArr.get()));

		for (int i = 0; i < 3; i++) {
			System.out.println(i + " - got object: " + initArr.get(i));
		}
	}
}
