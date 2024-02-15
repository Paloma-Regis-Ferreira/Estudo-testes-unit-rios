# Estudo-testes-unitarios

# Sumário
1. [O que são testes unitários](#o-que-são-testes-unitários)
2. [Princípio F.I.R.S.T](#princípio-first)
3. [Asserts no JUnit](#asserts-no-junit)
   - [Asserts Básicos](#asserts-básicos)
   - [Asserts com Objetos](#asserts-com-objetos)
   - [Asserts com Float](#asserts-com-float)
   - [Falha vs. Erro](#falha-vs-erro)
   - [Tratamento de Exceções](#tratamento-de-exceções)
4. [Ciclo de Vida das Variáveis](#ciclo-de-vida-das-variáveis)
5. [Parameterized](#parameterized)
   - [@ValueSource](#valuesource)
   - [@CsvSource](#csvsource)
6. [Dummy Classes](#dummy-classes)
   - [Utilidades](#utilidades)
   - [Dummy Classes vs. Mocks](#dummy-classes-vs-mocks)


# O que são testes unitários

Um **teste** é um procedimento ou processo no qual um sistema, componente ou função é avaliado para verificar se ele funciona conforme o esperado. Essa avaliação envolve a comparação das expectativas com os resultados reais, o que é fundamental para a validação de um sistema.

Os testes desempenham um papel crucial na identificação de erros, falhas ou comportamentos indesejados em um sistema. Eles permitem que esses problemas sejam identificados e corrigidos antes que o sistema seja usado em um ambiente de produção, contribuindo para a confiabilidade e a qualidade do software.

Um **teste unitário** é um tipo específico de teste de software que se concentra na avaliação individual de pequenas unidades de código, como funções, métodos ou classes. Essas unidades de código são isoladas de outras partes do sistema durante o teste. O objetivo principal de um teste unitário é garantir que cada unidade de código funcione conforme o esperado, produzindo os resultados corretos para entradas específicas.

# Principio F.I.R.S.T

O princípio F.I.R.S.T. é uma abordagem fundamental para garantir a eficácia e a qualidade dos testes unitários. Essa sigla representa cinco características essenciais que orientam a criação de testes unitários de alta qualidade:

- **Fast (Rápido)**: Os testes devem ser executados rapidamente, permitindo uma verificação frequente do código sem consumir muito tempo.

- **Isolated (Isolado)**: Cada teste deve ser independente e não depender dos resultados de outros testes, ou seja, um primeiro teste não deve criar um cenário para o segundo. Isso garante que os testes não interfiram uns nos outros.

- **Repeatable (Repetível)**: Os testes devem produzir resultados consistentes em cada execução, assegurando a reprodutibilidade. Isso significa que, por exemplo, ao trabalhar com datas, o mesmo teste executado repetidamente não deve resultar em diferentes dias da semana; ele deve sempre produzir o mesmo resultado, evitando que, por exemplo, um teste gere uma quinta-feira em uma execução e um sábado em outra.

- **Self-validating (Autovalidação)**: Os resultados dos testes devem ser automaticamente verificáveis, eliminando a necessidade de verificação manual.

- **T (Timely ou Thorough)**: A letra "T" na sigla F.I.R.S.T. pode ser interpretada de duas maneiras, dependendo da fonte. Alguns a associam a "Timely" (Oportuno), enfatizando o momento do desenvolvimento do teste. Outras fontes a associam a "Thorough" (Abrangente), destacando a necessidade de testes com cenários abrangentes. A interpretação exata pode variar, mas ambas as abordagens visam aprimorar a qualidade dos testes unitários
  - **Timely (Oportuno)**: O princípio "Timely" refere-se ao momento em que os testes são realizados, enfatizando que eles devem ser feitos, por exemplo, assim que cada função desse módulo é implementada. Isso permite a detecção precoce de problemas de lógica ou cálculos incorretos no início do processo de desenvolvimento, facilitando correções imediatas.
  - **Thorough (Abrangente)**: Esse princípio destaca a necessidade de testes abrangentes que cubram todas as situações possíveis, não se restringindo apenas ao cenário ideal. Isso garante que os testes sejam capazes de identificar problemas em uma variedade de condições e cenários, tornando o código mais robusto e confiável.

# Asserts no JUnit

## Asserts Básicos

As assertivas são declarações adicionadas aos testes e desempenham um papel fundamental: verificar se o código do seu teste se comporta conforme o esperado, ou seja, se as condições esperadas são verdadeiras. Elas ajudam a determinar se as condições definidas estão sendo atendidas, permitindo assim a validação da funcionalidade do software. Essas assertivas realizam a **autovalidação (Self-validating)**, o que significa que verificam automaticamente se as condições são verdadeiras ou falsas e relatam o resultado sem a necessidade de intervenção manual. Abaixo, apresentamos uma lista de assertivas com explicações breves para ajudar a entender o que cada uma faz

- `assertTrue(x == y)`: Esta assertiva verifica se a expressão `x` é igual a `y` e retorna verdadeira (true). Ela é útil para verificar se duas variáveis são iguais.

- `assertFalse(x == y)`: Semelhante à anterior, mas verifica se a expressão `x` é diferente de `y`, retornando verdadeira (true) se forem diferentes.

- `assertEquals(esperadoPrimeiro, recebidoDepois)`: Verifica se o valor `esperadoPrimeiro` é igual ao valor `recebidoDepois`. Esta assertiva é comumente usada para testar resultados de funções ou métodos.

    - **Importância da Ordem no `assertEquals`**: Inverter a ordem dos parâmetros no `assertEquals` pode levar a interpretações incorretas dos resultados dos testes. Certifique-se de que a ordem esteja correta para que a comparação seja feita de forma precisa.

- `assertNotEquals(esperadoPrimeiro, recebidoDepois)`: Esta assertiva verifica se o valor `esperadoPrimeiro` é diferente do valor `recebidoDepois`.

- `assertFalse(casa.equalsIgnoreCase(Casa))`: Essa assertiva envolve funções de string e verifica se a string `casa` não é igual à string `Casa`, ignorando diferenças de maiúsculas e minúsculas.

- `assertNull(objeto)`: Verifica se o objeto é nulo (null). É útil para garantir que uma variável ou referência aponte para um objeto nulo.

- `assertNotNull(objeto)`: Ao contrário da anterior, esta assertiva verifica se o objeto não é nulo.

## Asserts com Objetos

- `assertSame(objeto1, objeto2)`: Verifica se as duas referências `objeto1` e `objeto2` apontam para a mesma instância de objeto. É útil para verificar se duas variáveis referem-se ao mesmo objeto.

- `assertEquals(objet1, objeto2)`: Esta assertiva verifica se os objetos `objet1` e `objeto2` são iguais de acordo com a implementação dos métodos `equals`. Sem a implementação adequada desse método na classe do objeto, eles não serão considerados iguais.

    - **Exemplo de `assertSame` que retorna true**:

      ```java
      import static org.junit.Assert.*;
      
      @Test
      public void testSameObjectTrue() {
          String str1 = new String("Hello");
          String str2 = str1;
      
          assertSame("Essas duas referências apontam para o mesmo objeto", str1, str2);
      }

    - **Exemplo de `assertSame` que retorna false**:

      ```java
      import static org.junit.Assert.*;
      
      @Test
      public void testSameObjectFalse() {
          String str1 = new String("Hello");
          String str2 = new String("Hello");
      
          assertSame("Essas duas referências apontam para objetos diferentes", str1, str2);
      }
    
    - **Exemplo de `assertEquals` que retorna true**:
   
       ```java
       import static org.junit.Assert.*;
   
       @Test
       public void testObjectEquality() {
           Person person1 = new Person("Alice", 30);
           Person person2 = new Person("Alice", 30);
    
        assertEquals(person1, person2);

           
    - **Exemplo de `assertEquals` que retorna false**:
   
       ```java
       import static org.junit.Assert.*;
   
       @Test
       public void testObjectEquality() {
           Person person1 = new Person("Alice", 30);
           Person person2 = new Person("Ana", 30);
    
        assertEquals(person1, person2);
       ```


## Asserts com float
- `assertEquals(esperadoPrimeiro, recebidoDepois, 0,01)`: O valor 0,01 é um delta de comparação, se o valor recebido como resposta for muito grande em casas decimais ele pode ser acrescido e aceitará o encurtamento desse valor, por exemplo:
    - Supondo que o valor recebido seja 3,33336:
- `assertEquals(3,14, valorRecebido, 0,01)
- `assertEquals(3,13, valorRecebido, 0,01)


## Falha vs.Erro
Uma **falha** em um teste ocorre quando uma assertiva espera um valor específico, mas o resultado obtido não corresponde ao esperado. Isso pode acontecer quando o código testado não produz o resultado previsto. Para lidar com essa situação, as assertivas geralmente lançam uma exceção chamada **'AssertionFailedError'** quando a condição não é atendida que é **tratada** pelo próprio framework do Junit.

Um **erro** em um teste ocorre quando o teste lança uma exceção não esperada. Isso geralmente acontece quando o código testado gera uma exceção **não tratada** durante a execução do teste, e o teste não foi projetado para lidar com essa exceção.

É importante distinguir entre **falhas** e **erros**, pois as **falhas** indicam que o código não está funcionando conforme o esperado, enquanto os **erros** indicam problemas no próprio teste, como exceções **não tratadas**. Compreender essa diferença é fundamental para diagnosticar e corrigir problemas em seus testes de forma eficaz.


## Tratamento de exceções
Quando tratamos exceções, ou seja, dizemos ao código de teste que estamos esperando uma determinada exceção ao executar uma ação, o teste deve funcionar. Se a exceção esperada não for lançada o teste falhará ou, se for lançada uma outra exceção que não é a que estamos tratando no teste, ocorrerá um erro. Portanto, ao tratar exceções dentro do teste, devemos ser o mais específicos possível sobre qual exceção estamos tratando.

   
       ```java
       import static org.junit.Assert.*;
   
      @Test
      public void deveLancarExcecaoComDivisaoPorZero_blocoTryCatch(){
          try {
              float resultado = 10 / 0;
              Assertions.fail("Deveria ter lançado exceção na linha anterior");
          }catch (ArithmeticException e){
              Assertions.assertEquals("/ by zero", e.getMessage());
          }
      }
      ```

       ```java
       import static org.junit.Assert.*;
       
       @Test
       public void deveLancarExcecaoComDivisaoPorZero_Lambda(){
           ArithmeticException exception = assertThrows(ArithmeticException.class, () -> {
               float resultado = 10/0;
           });
           Assertions.assertEquals("/ by zero", exception.getMessage());
       }
      ```
      
## Ciclo de Vida das Variáveis

Variáveis globais são declaradas fora de métodos e são acessíveis em todo o escopo da classe, enquanto as locais são declaradas dentro de métodos e são acessíveis apenas dentro desse método.

Se uma variável global for estática, ela pode ter seu valor compartilhado entre diferentes testes, o que pode levar a resultados inesperados se não for gerenciada adequadamente. Quando não estática, o JUnit considera o valor inicial dela para cada teste executado, descartando quaisquer alterações feitas por testes anteriores.

### Ordem de execução
Ao utilizar atributos de classe estaticos podemos ter problemas com a ordem de execução dos testes, um primeiro teste afetara o resultado de um segundo teste e se necessitar de uma ordem de execução especifica, isso causará erro. 
É possível garantir a ordem de execução de testes pelo junit das seguintes formas
Junit4 : Nomenclatura dos testes em ordem alfabetica, normalmente colocando um prefixo teste1_nomeDoTeste, teste2_nomeDoTeste...
Junit5: O Junit5 tem uma notação de classe @TestMethodOrder que necessita de um parametro MethodOrderer. Podendo ser ordem alfabetica como o junit4 ou por exemplo, o OrderAnnotation que permite colocar os testes em ordem numerica acrescentando a anotação @Order(1) por exemplo.

Apesar de possível, não é recomendado realizar testes dessa forma. De acordo com o principio FIRST cada teste deve ser individual e não criar cenários para outros testes, sendo eles individualmente responsaveis por criar seus proprios cenarios

### Anotações Before e After no JUnit

No JUnit, as anotações `@Before` e `@After` (no JUnit 4) e `@BeforeEach` e `@AfterEach` (no JUnit 5) são usadas para configurar o estado dos testes antes e depois de cada execução. Essas anotações garantem que o ambiente de teste esteja configurado corretamente e que quaisquer recursos necessários estejam disponíveis durante a execução do teste.

As anotações `@BeforeClass` e `@AfterClass` (no JUnit 4) e `@BeforeAll` e `@AfterAll` (no JUnit 5) são executadas apenas uma vez antes e depois de todos os testes na classe de teste, respectivamente. O método anotado com `@BeforeClass` ou `@BeforeAll` deve ser definido como estático, pois é chamado antes da criação de qualquer instância da classe de teste.


## Parameterized

O `@Parameterized` é uma funcionalidade do JUnit que permite a execução de testes parametrizados. Isso significa que você pode escrever um único teste e executá-lo várias vezes com diferentes conjuntos de dados de entrada. Isso é útil quando você deseja testar uma determinada funcionalidade com diferentes valores de entrada para garantir que ela funcione corretamente em várias situações.

Para utilizar o @Parameterized, é necessário criar uma classe de teste convencional, onde o método de teste deve ser anotado com `@Parameterized`. Em seguida, os dados de entrada devem ser fornecidos utilizando um método ou um arquivo CSV, sendo acompanhados pelas anotações `@ValueSource` ou `@CsvSource`.

Por exemplo, suponha que você queira testar uma classe `Calculator` que possui um método `add(int a, int b)` usando diferentes pares de números.

### @ValueSource

A anotação `@ValueSource` é usada para fornecer um único valor por parâmetro do método de teste parametrizado. Por exemplo, se você estiver testando um método que aceita um único parâmetro do tipo `int`, `String`, ou outros tipos básicos, você pode usar `@ValueSource`.

#### Exemplo de Uso:

      ```java
      @ParameterizedTest
      @ValueSource(ints = { 1, 2, 3 })
      public void testSomeMethod(int value) {
          // Implementação do teste
      }
      ```

### @CsvSource

A anotação `@CsvSource` é usada quando você precisa fornecer múltiplos valores por parâmetro do método de teste parametrizado, mas esses valores estão relacionados de forma estruturada, como em uma linha CSV (valores separados por vírgula). Por exemplo, se você estiver testando um método que aceita dois ou mais parâmetros, você pode usar `@CsvSource`.

#### Exemplo de Uso:

      ```java
      @ParameterizedTest
      @CsvSource({ "1, 2, 3", "2, 3, 5", "5, 5, 10" })
      public void testAdd(int a, int b, int expectedResult) {
          // Implementação do teste
      }
      ```

Neste exemplo, o método testAdd será executado três vezes, uma vez para cada linha fornecida pelo @CsvSource, onde cada linha contém três valores que serão atribuídos aos parâmetros a, b e expectedResult.

Portanto, a principal diferença entre @ValueSource e @CsvSource é a maneira como os valores são fornecidos: @ValueSource é usado para fornecer um único valor por parâmetro, enquanto @CsvSource é usado para fornecer múltiplos valores estruturados por parâmetro.
Portanto, a principal diferença entre @ValueSource e @CsvSource é a maneira como os valores são fornecidos: @ValueSource é usado para fornecer um único valor por parâmetro, enquanto @CsvSource é usado para fornecer múltiplos valores estruturados por parâmetro.

## Dummy Classes

As Dummy Classes, ou classes dummy, são importantes em testes de software, especialmente em testes de unidade. Elas são usadas para preencher requisitos de dependências durante os testes, sem fornecer implementação real.

### Utilidades:

- **Simulação de Dependências**: Substituir dependências irrelevantes para simplificar o ambiente de teste.
- **Satisfazer Assinaturas de Métodos**: Fornecer implementações vazias para interfaces ou classes abstratas.
- **Redução de Complexidade**: Simplificar o código de teste, tornando-o mais direto.
- **Performance**: Melhorar a eficiência dos testes em cenários onde criar instâncias reais é custoso.

É importante garantir que as Dummy Classes sejam apropriadas para o contexto específico do teste, para evitar falsos resultados nos testes. Por exemplo, se uma dependência está sendo usada ativamente durante o teste e sua implementação real é necessária para o teste ser válido, usar uma Dummy Class pode comprometer a validade do teste.

## Dummy Classes vs. Mocks

As Dummy Classes e Mocks são abordagens diferentes para lidar com dependências em testes de unidade.

### Diferenças:

- **Propósito**:
  - Dummy Classes: Preenchem requisitos de assinatura de métodos ou representam dependências irrelevantes.
  - Mocks: Simulam o comportamento de dependências reais, configurados com expectativas de uso durante o teste.

- **Complexidade**:
  - Dummy Classes: Simples, com implementações mínimas.
  - Mocks: Mais complexos, podem simular comportamentos específicos e ser verificados durante o teste.

- **Flexibilidade**:
  - Dummy Classes: Menos flexíveis, oferecem implementações básicas.
  - Mocks: Altamente flexíveis, podem ser configurados para comportamentos específicos.

- **Interação com os testes**:
  - Dummy Classes: Não são verificadas diretamente durante o teste, usadas para preencher lacunas de dependências.
  - Mocks: São interagidos e verificados durante o teste.

Em resumo, Dummy Classes são utilizadas para simplificar testes, enquanto Mocks simulam comportamentos específicos de dependências reais.

