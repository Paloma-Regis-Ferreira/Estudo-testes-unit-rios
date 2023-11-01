# Estudo-testes-unitarios

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

