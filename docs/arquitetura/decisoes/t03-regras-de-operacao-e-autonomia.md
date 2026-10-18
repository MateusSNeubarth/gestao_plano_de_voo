# T03 — Regras de operação e autonomia

As verificações implementam `VerificacaoDoPlano` e retornam `Problema`, conforme o contrato usado por `ValidadorDePlano`. Não alteram o plano, não associam regras à aeronave durante a validação e não criam reservas.

## Organização

- `FaixaDeAltitude` e `JanelaHoraria` são regras imutáveis que implementam `RegraDeOperacao`.
- `CatalogoDeRegrasPadrao` associa a classe de cada categoria às suas regras. O construtor com mapa permite configurar categorias e composições adicionais sem alterar as verificações. Uma categoria não cadastrada é rejeitada.
- `VerificacaoDeOperacao` verifica a faixa global de 25.000 a 35.000 pés, os incrementos de 1.000 e todas as regras da categoria. O intervalo usa `Trecho.chegada`, inclusive seu arredondamento para minutos. Permanência em solo não faz parte desse intervalo.
- `VerificacaoDeAutonomia` acumula distâncias, compara com `Aeronave.distanciaMaximaSemReabastecimento()` e só então reinicia o acumulador quando o trecho termina em pouso. Exatamente 85% é permitido.
- `RegrasDeOperacaoConfig` registra o catálogo e as duas verificações como beans; a lista de verificações já injetada no validador incorpora ambas. As novas classes de domínio não dependem do Spring ou da persistência.

A alternativa de selecionar categorias diretamente no validador foi descartada para manter a composição das regras no catálogo. O método existente `Aeronave.podeOperar` avalia as regras explicitamente associadas à instância; a validação do plano consulta o catálogo, pois os mapeadores de persistência não carregam essa lista. Sem regras associadas, o método da aeronave retorna falso.

## Limite das 06:00

Hipótese adotada nesta implementação, pendente de confirmação do grupo: o fim exclusivo exige chegada estritamente anterior às 06:00. Chegada exatamente às 06:00 é rejeitada. A alternativa seria tratar o intervalo de voo como `[entrada, saída)`, aceitando saída às 06:00. A decisão está isolada em `JanelaHoraria` e coberta por teste.

A janela exige entrada anterior à saída e ambas na mesma data. Assim, comparar apenas os horários não permite acidentalmente um trecho que atravesse a meia-noite.

## Verificação

`RegrasDeOperacaoTest` cobre os critérios de aceite, fronteiras de altitude e horário, autonomia inclusiva, reabastecimento, dados incompletos, extensão do catálogo e integração das verificações por configuração Spring sem banco. Também verifica que a validação preserva o estado e as reservas do plano.

Executar junto aos testes de domínio existentes:

```sh
./mvnw -Dtest=RegrasDeOperacaoTest,PlanoDeVooTest,TrechoTest test
```
