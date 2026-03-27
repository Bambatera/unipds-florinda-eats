# Florinda Eats

Projeto de estudos do **Módulo 0** da pós-gradução UniPDS.

## Projeto base

O projeto base para criação deste projeto está no GitHub da [UniPDS](https://github.com/unipds-projetos) e foi disponibilizado no repositório [modulo0-introducao-a-java](https://github.com/unipds-projetos/modulo0-introducao-a-java).

## O que é este projeto?

Projeto para introdução ao Java 25, com conceitos básicos de Orientação a Objetos e estruturas de dados básicas como Objetos, Arrays, entrada de saída de dados e leitura de arquivos.

### Do que se trata?

Uma representação básica de automação do restaurante da Dona Florinda (Chaves), oferencendo um cardápio digital.

### Tecnologias utilizadas

O projeto é escrito em Java 25 com as novas estruturas de desenvolvimento oferecidas pela nova versão da linguagem.    
Inicialmente não possuía nenhuma biblioteca externa, mas posteriormente foi adicionada a biblioteca Google Gson (de forma manual) para manipulação de objetos JSON.

Foi "instalado" o _Gradle_ na versão 9.3.1 no projeto.

### Refatorações

- 24/03/2026:
    - Removida da classe `CardapioService.java` a extensão à classe `Cardapio.java`.
    - Criada relação de "dependência" entre essas classes.
- 25/03/2026: Instalação do Gradle, configuração de dependências, and mudança na estrutura de pacotes, adequando-se ao Gradle.
- 26/03/2026: Aubstituição de `ImpressoraCardapio.java` por `ImpressoraService.java`, provendo iteração no console, e aplicando formatação de dados no método toString em `ItemCardapio.java`.
- 27/03/2026:
    - Atualização da estrutura de pacote utilizando nomes no plural.
    - Melhorada a leitura de arquivo de dados
    - Implementada a classe `ItemCardapioRepository.java` para prover acesso a dados estáticos (arquivos JSON ou CSV).
