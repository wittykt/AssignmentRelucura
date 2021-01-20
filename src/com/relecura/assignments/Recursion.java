package com.relecura.assignments;

import java.util.Iterator;

public class Recursion {
	public static void main(String[] args) {
		loop(5);
		System.out.println("-----------");
		recloop(5);
	}
	
	public static void loop(int count) {
		System.out.println("Dayumm!!");
		for (int i = 0; i < count; i++) {
			System.out.println("Hie!!");
		}
		System.out.println("Awesome!!");
	} 
	
	public static void recloop(int count) {
		System.out.println("Dayumm!!");
		if(count>0) {
			recloop(count-1);
		}
		System.out.println("Hi");
		System.out.println("Awesome!!");
	} 
}
