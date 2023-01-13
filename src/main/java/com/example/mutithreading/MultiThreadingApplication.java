package com.example.mutithreading;

import com.example.mutithreading.service.ThreadService;
import com.example.mutithreading.tasks.runnable.RunnableExp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class MultiThreadingApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MultiThreadingApplication.class, args);
	}


	@Autowired
	private ThreadService threadService;

	@Override
	public void run(String... args) {
		//Example 1 - Thread/Runnable
		//threadService.example1();

		// Example 2 - Synchronization with Join
		//threadService.example2();

		// Example 3 - State in Threads
		//threadService.example3Simple();
		//threadService.example3Mid();

		// Example 4 - isAlive()
		//threadService.example4();

		// Example 5 - priority
		//threadService.example5();

		// Example 6 - daemons & Thread groups
		//threadService.example6Simple();
		//threadService.example6Mid();

		// Example 7 - Wep Page Download
		//threadService.example7Simple();
		//threadService.example7Mid();

		// Example 8 - Interrupt Threads
		//threadService.example8();

		// Example 9 - Synchronization
		//threadService.example9(true);

		// Example 10 - Synchronization in Shared Resources
		//threadService.example10(true);

	}

}
