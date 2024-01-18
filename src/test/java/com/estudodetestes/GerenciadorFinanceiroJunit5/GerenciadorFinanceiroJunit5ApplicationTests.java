package com.estudodetestes.GerenciadorFinanceiroJunit5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GerenciadorFinanceiroJunit5ApplicationTests {

	private Integer cont = 0;

	@Test
	void contextLoads() {
	cont ++;
		System.out.println(cont);
	}

	@Test
	void  contador2(){
		cont = cont + 1;
		System.out.println(cont);
	}

	@Test
	void  contador(){
		cont = cont + 1;
		System.out.println(cont);
	}

	@Test
	void  contador3(){
		cont = cont + 1;
		System.out.println(cont);
	}

	@Test
	void  contador4(){
		cont = cont + 1;
		System.out.println(cont = cont++);
	}

	@ParameterizedTest
	@ValueSource(strings = {"Teste1", "Teste2", "teste3"})
	void deveTestarStrings(String param){
		System.out.println(param);
		Assertions.assertNotNull(param);
	}

	@ParameterizedTest
	@CsvSource(value = {
			"1, 2, 3",
			"5, 20, 25",
			"6, 8, 14"
	})
	void deveRetornarSoma(int value1, int value2, int result){
		int resultado = value1 + value2;
		System.out.println(resultado);
		System.out.println(result);
		Assertions.assertEquals(result, resultado);
	}
}
