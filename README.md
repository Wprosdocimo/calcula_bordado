# Projeto Sem nome

Aplicativo android para calculo de valor de bordado partindo da entrada de valores pelo usuário de quantidade de cores e numero de pontos e apresentando uma estimativa de custos e valor minimo que deve ser cobrado.

## Telas

### Inicial

Campos de entrada:

- Tamanho do bastidor;
- Quantidade de pontos da matriz;
- Quantidade de cores do bordado;
- Quantidade de bordados por peça;

![Tela Inicial](TelaInicial.jpg)

### Tela de resultado

- Custo calculado;
- Preço sugerido;
- Tempo de produção;

![Tela de resultado](Resultado.jpg)

### Tela de configuração

- Tempo de bordado
  - Velocidade da maquina
  - tempo de troca de cor
  - tempo de preparação

- Lucro desejado

- Custos de material:
  - Linha bordado
    - Custo do cone
    - Quantidade de linha por cone
    - Consumo de linha por 1000 pontos (6.5)
  - Linha bobina
    - Custo do cone
    - Quantidade de linha por cone
    - Consumo de linha por 1000 pontos (2.5)
  - Entretela
    - Custo (do metro)
    - largura
    - comprimento (sempre 1 metro?)

- Mão de obra (valor da hora trabalhada):
  - Numero de horas trabalhadas (por dia)
  - Numero de dias trabalhados (por mês)
  - Previsão de ganho (Salário, Calculado ou informado)

- Custos fixos:
  - Aluguel
  - Luz
  - Agua
  - Telefone / Internet
  - Manutenção (valor anual)

![Tela de configurações](Configuracoes.jpg)

### Tela CRUD Bastidores

> Em definição

## Tecnologias

- Android:
  - Kotlin
  - SQLite
  - ROOM lib

Ferramentas:

- Android Studio
  - versão minima 4.4
  - versão 10 do android;

## Próximos Passos

- ~~Definir tela de resultado do cálculo;~~
- ~~Acessar dados do Database para popular os editTexts da tela de configuração;~~
- ~~Acessar os dados do Database para fazer o cálculo na tela inicial;~~
- ~~Salvar as alterações de configuração na base de dados.~~
- ~~Configurar singleton na base de dados;~~
- ~~Criar tabela de bastidores no banco;~~
- ~~Configurar viewmodel na aplicação;~~
- ~~Configurar LiveData para configurações;~~
- ~~Criar viewmodel e livedata para bastidores;~~
- ~~Definir o layout da tela de gestão dos bastidores;~~
  - ~~ListView com float button;~~
  - ~~AlertDialog (para adição);~~
  - ~~Definir tela de adição de bastidor;~~

----

- Separar grupos de configuração com CardView;
- Configurar inserção/edição/deleção de bastidores;

----
Ideias de melhoria:

- Ajustar cálculo da mão de obra com os custos da legislação vigente (INSS, férias, 13º) (Validar); [ddauriol](https://www.twitch.tv/ddauriol)
- Levar em consideração a depreciação da maquina nos custos fixos; [ddauriol](https://www.twitch.tv/ddauriol)
