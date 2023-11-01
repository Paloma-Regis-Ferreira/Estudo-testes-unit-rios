# Estudo-testes-unitarios

# Sumário
1. [O que são testes unitários](#o-que-são-testes-unitários)
2. [Princípio F.I.R.S.T](#princípio-first)
3. [Asserts no JUnit](#asserts-no-junit)
   
   3.1. [Asserts Básicos](#asserts-básicos)
   3.2. [Asserts com Objetos](#asserts-com-objetos)

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

No JUnit, as assertivas desempenham um papel fundamental para verificar se o código do seu teste se comporta conforme o esperado. Elas ajudam a determinar se as condições definidas estão sendo atendidas, permitindo assim a validação da funcionalidade do software. Essas assertivas realizam a **autovalidação (Self-validating)**, o que significa que verificam automaticamente se as condições são verdadeiras ou falsas e relatarão o resultado sem a necessidade de intervenção manual. Abaixo, apresentamos uma lista de assertivas com explicações superficiais para ajudar a entender o que cada uma faz.

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
