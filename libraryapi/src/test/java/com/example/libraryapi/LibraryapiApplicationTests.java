package com.example.libraryapi;

import com.example.libraryapi.repository.AutorRepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LibraryapiApplicationTests {
	@Autowired
	AutorRepositoryTest autorRepositoryTest;

	//@Test
	void contextLoads() {
	}
/*
	@Test
	void repoTest(){
		autorRepositoryTest.gerAutor();

	}

 */
}
