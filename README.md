# gestao_plano_de_voo

Sistema de gestão de planos de voo para o trabalho final (parte 1) da disciplina de Projeto e Arquitetura de Sistemas.

O serviço expõe uma API REST para cadastrar a malha aérea (aeroportos e aerovias), pilotos e aeronaves, elaborar planos de voo, validá-los contra as regras de operação, autonomia e disponibilidade, e aprovar, abandonar ou cancelar os planos, reservando e liberando altitudes nas aerovias e vagas nos aeroportos.

## Sumário

- [Tecnologias](#tecnologias)
- [Como executar](#como-executar)
- [Dados iniciais](#dados-iniciais)
- [Autenticação](#autenticação)
- [Endpoints](#endpoints)
- [Roteiro de exemplo](#roteiro-de-exemplo)
- [Documentação do projeto](#documentação-do-projeto)

## Tecnologias

- Java 25
- Spring Boot 4.1 (Web MVC, Data JPA, Security)
- Flyway para as migrations do banco
- PostgreSQL no ambiente de produção e H2 em memória no ambiente local
- springdoc-openapi (Swagger UI)
- Lombok

## Como executar

### Pré-requisitos

- JDK 25 instalado e disponível no `PATH`.

O Maven não precisa estar instalado: o projeto inclui o Maven Wrapper (`mvnw` / `mvnw.cmd`).

### Ambiente local (H2, sem configuração)

O perfil `local` usa um banco H2 em memória e não exige URL, usuário ou senha de banco. Ao subir, o Flyway cria o esquema e carrega os [dados iniciais](#dados-iniciais).

Linux / macOS:

```sh
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

Windows (PowerShell):

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=local"
```

A API fica disponível em `http://localhost:8080`.

Também é possível gerar o pacote e executá-lo diretamente:

```sh
./mvnw package
java -jar target/trabalhofinal-0.0.1-SNAPSHOT.jar --spring.profiles.active=local
```

> O banco H2 é em memória: todo dado criado durante a execução é perdido ao encerrar o serviço, e cada execução começa novamente com os dados iniciais.

### Ambiente de produção (PostgreSQL)

Sem perfil ativo, o serviço usa PostgreSQL e lê a conexão das variáveis de ambiente:

| Variável | Descrição |
| --- | --- |
| `DB_URL` | URL JDBC do banco, por exemplo `jdbc:postgresql://<host>:5432/<banco>` |
| `DB_USERNAME` | Usuário do banco |
| `DB_PASSWORD` | Senha do banco |

```sh
export DB_URL=... DB_USERNAME=... DB_PASSWORD=...
./mvnw spring-boot:run
```

As migrations são aplicadas automaticamente na inicialização.

### Testes

```sh
./mvnw test
```

Os testes usam o perfil `local` e não precisam de banco configurado.

## Dados iniciais

As migrations carregam os seguintes registros, disponíveis em qualquer ambiente:

**Aeroportos**

| Código | Nome | Vagas |
| --- | --- | --- |
| POA | Aeroporto Internacional Salgado Filho - Porto Alegre | 41 |
| FLN | Aeroporto Internacional de Florianópolis/Hercílio Luz | 16 |
| CWB | Aeroporto Internacional Afonso Pena - Curitiba | 26 |

**Aerovias**

| Id | Origem | Destino | Distância (km) |
| --- | --- | --- | --- |
| R1 | POA | FLN | 363 |
| R4 | FLN | CWB | 245 |
| R5 | CWB | FLN | 245 |
| R8 | FLN | POA | 363 |

**Aeronaves**

| Prefixo | Categoria | Velocidade de cruzeiro (km/h) | Autonomia (km) |
| --- | --- | --- | --- |
| PT-ABC | `PARTICULAR` | 300 | 1500 |
| PR-GOL | `COMERCIAL_DE_PASSAGEIRO` | 850 | 5000 |
| PR-CRG | `COMERCIAL_DE_CARGA` | 800 | 6000 |

**Pilotos**

| Id | Nome |
| --- | --- |
| 1 | Ana Souza |
| 2 | Carlos Lima |

## Autenticação

Todos os endpoints da API exigem autenticação HTTP Basic. Há dois usuários, definidos em memória:

| Usuário | Senha | Perfil | Permissões |
| --- | --- | --- | --- |
| `admin` | `admin123` | `ADMIN` | Todas as operações |
| `usuario` | `usuario123` | `USUARIO` | Todas, exceto criar ou alterar cadastros de aeroportos, aerovias, pilotos e aeronaves |

Requisições sem credenciais respondem `401`, e requisições sem permissão respondem `403`. As credenciais podem ser trocadas pelas variáveis `ADMIN_USERNAME`, `ADMIN_PASSWORD`, `USUARIO_USERNAME` e `USUARIO_PASSWORD`.

Exemplo:

```sh
curl -u usuario:usuario123 http://localhost:8080/api/aeroportos
```

### Swagger UI

A documentação interativa da API está em `http://localhost:8080/swagger-ui/index.html` (a especificação OpenAPI fica em `/v3/api-docs`). Essas rotas não exigem autenticação; ao chamar um endpoint pelo Swagger, o navegador solicita usuário e senha, e basta informar um dos usuários acima.

## Endpoints

Datas e horários usam o formato ISO `AAAA-MM-DDTHH:MM:SS`, por exemplo `2030-01-10T08:00:00`. Todos os corpos de requisição e resposta são JSON.

### Aeroportos

| Método | Rota | Perfil | Descrição |
| --- | --- | --- | --- |
| GET | `/api/aeroportos` | Qualquer | Lista os aeroportos |
| GET | `/api/aeroportos/{codigo}` | Qualquer | Consulta um aeroporto. `404` se não existir |
| POST | `/api/aeroportos` | ADMIN | Cadastra um aeroporto. `201`; `409` se o código já existir; `400` se os dados forem inválidos |

```json
{ "codigo": "GRU", "nome": "Aeroporto Internacional de Guarulhos", "qtdVagas": 50 }
```

O código tem 3 letras. As vagas são numeradas de 1 a `qtdVagas`.

### Aerovias

| Método | Rota | Perfil | Descrição |
| --- | --- | --- | --- |
| GET | `/api/aerovias` | Qualquer | Lista as aerovias |
| GET | `/api/aerovias/{id}` | Qualquer | Consulta uma aerovia |
| POST | `/api/aerovias` | ADMIN | Cadastra uma aerovia. `201` |

```json
{ "id": "R10", "codOrigem": "POA", "codDestino": "GRU", "distanciaKm": 850 }
```

A aerovia é orientada: o sentido inverso exige outra aerovia.

### Pilotos

| Método | Rota | Perfil | Descrição |
| --- | --- | --- | --- |
| GET | `/api/pilotos` | Qualquer | Lista os pilotos |
| GET | `/api/pilotos/{id}` | Qualquer | Consulta um piloto. `404` se não existir |
| POST | `/api/pilotos` | ADMIN | Cadastra um piloto. `201`, com o piloto criado e seu identificador |

```json
{ "nome": "Bruno Costa" }
```

### Aeronaves

| Método | Rota | Perfil | Descrição |
| --- | --- | --- | --- |
| GET | `/api/aeronaves` | Qualquer | Lista as aeronaves |
| GET | `/api/aeronaves/{prefixo}` | Qualquer | Consulta uma aeronave. `404` se não existir |
| POST | `/api/aeronaves` | ADMIN | Cadastra uma aeronave. `201`; `409` se o prefixo já existir; `422` se a categoria for desconhecida ou faltarem dados da categoria |

O campo `categoria` define os dados obrigatórios:

| Categoria | Campos próprios obrigatórios |
| --- | --- |
| `PARTICULAR` | `empresaResponsavelPelaManutencao` |
| `COMERCIAL_DE_PASSAGEIRO` | `companhiaAerea`, `capacidadeMaxPassageiro` |
| `COMERCIAL_DE_CARGA` | `companhiaAerea`, `capacidadeMaxCargaTn` |

```json
{
  "prefixo": "PR-AZU",
  "categoria": "COMERCIAL_DE_CARGA",
  "velocidadeDeCruzeiro": 800,
  "autonomia": 6000,
  "companhiaAerea": "Azul Cargo",
  "capacidadeMaxCargaTn": 30.0
}
```

A velocidade de cruzeiro é em km/h e a autonomia em km.

### Disponibilidade

Consultam a ocupação no momento da requisição e não reservam nada. O período é dividido em slots de uma hora iniciados na hora cheia; um recurso só aparece como disponível se estiver livre em todos os slots tocados pelo período. Apenas reservas de planos aprovados contam como ocupação.

| Método | Rota | Perfil | Descrição |
| --- | --- | --- | --- |
| GET | `/api/aerovias/{id}/altitudes-disponiveis?inicio=...&fim=...` | Qualquer | Altitudes livres na aerovia, entre 25.000 e 35.000 pés, de 1.000 em 1.000 |
| GET | `/api/aeroportos/{codigo}/vagas-disponiveis?inicio=...&fim=...` | Qualquer | Vagas livres no aeroporto |

As duas respondem `400` se o período for inválido (início igual ou posterior ao fim, ou parâmetro ausente) e `404` se a aerovia ou o aeroporto não existir.

```sh
curl -u usuario:usuario123 "http://localhost:8080/api/aerovias/R1/altitudes-disponiveis?inicio=2030-01-10T08:00:00&fim=2030-01-10T10:00:00"
```

```json
{
  "aeroviaId": "R1",
  "inicio": "2030-01-10T08:00:00",
  "fim": "2030-01-10T10:00:00",
  "altitudes": [25000, 26000, 27000, 28000, 29000, 30000, 31000, 32000, 33000, 34000, 35000]
}
```

### Planos de voo: elaboração e consulta

| Método | Rota | Perfil | Descrição |
| --- | --- | --- | --- |
| POST | `/api/planos` | Qualquer | Cria um plano em edição, com percurso vazio. `201`; `422` se piloto, aeronave ou aeroporto não existir ou faltar a partida |
| GET | `/api/planos/{id}` | Qualquer | Consulta um plano, com os horários calculados de cada trecho. `404` se não existir |
| GET | `/api/planos?situacao={situacao}` | Qualquer | Lista os planos em uma situação: `EDICAO`, `APROVADO`, `CANCELADO` ou `ABANDONADO`. `400` se a situação for desconhecida ou ausente |
| PUT | `/api/planos/{id}/percurso` | Qualquer | Substitui a sequência inteira de trechos |
| PUT | `/api/planos/{id}/percurso/trechos/{ordem}` | Qualquer | Altera um trecho já informado; `ordem` começa em 1 |

Criação do plano:

```json
{
  "pilotoId": 1,
  "aeronavePrefixo": "PR-GOL",
  "codOrigem": "POA",
  "codDestino": "CWB",
  "dataHoraPartida": "2030-01-10T08:00:00"
}
```

Definição do percurso (o corpo de `PUT .../trechos/{ordem}` é um único trecho, no mesmo formato):

```json
{
  "trechos": [
    {
      "aeroviaId": "R1",
      "altitude": 30000,
      "horarioEntradaNaAerovia": "2030-01-10T08:00:00",
      "acaoNaChegada": "POUSO",
      "numeroVagaPretendida": 2,
      "tempoPermanenciaMinutos": 30
    },
    {
      "aeroviaId": "R4",
      "altitude": 31000,
      "horarioEntradaNaAerovia": "2030-01-10T08:56:00",
      "acaoNaChegada": "POUSO",
      "numeroVagaPretendida": 5,
      "tempoPermanenciaMinutos": 60
    }
  ]
}
```

- `acaoNaChegada` é `POUSO` ou `SOBREVOO`. Em um pouso, informe a vaga pretendida no aeroporto de destino da aerovia e o tempo de permanência em minutos; em um sobrevoo, esses campos não se aplicam.
- Todo trecho precisa de aerovia, altitude, horário de entrada e ação na chegada.
- O percurso pode ficar incompleto durante a edição; a consistência só é cobrada na [validação](#planos-de-voo-ciclo-de-vida). Editar o percurso nunca cria reservas.

Erros das rotas de percurso: `404` se o plano não existir; `409` se o plano não estiver em edição; `422` se a aerovia não existir, a vaga não pertencer ao aeroporto de destino, faltar algum dado do trecho ou a ordem não existir.

Resposta de criação, consulta e edição:

```json
{
  "id": 1,
  "status": "EDICAO",
  "dataHoraPartida": "2030-01-10T08:00:00",
  "pilotoId": 1,
  "pilotoNome": "Ana Souza",
  "aeronavePrefixo": "PR-GOL",
  "codOrigem": "POA",
  "codDestino": "CWB",
  "trechos": [
    {
      "ordem": 1,
      "aeroviaId": "R1",
      "altitude": 30000,
      "horarioEntradaNaAerovia": "2030-01-10T08:00:00",
      "chegada": "2030-01-10T08:26:00",
      "acaoNaChegada": "POUSO",
      "numeroVagaPretendida": 2,
      "tempoPermanenciaMinutos": 30,
      "fimDaPermanencia": "2030-01-10T08:56:00"
    }
  ]
}
```

A `chegada` é calculada pela distância da aerovia e pela velocidade de cruzeiro da aeronave, arredondada para o minuto seguinte. O `fimDaPermanencia` (somente em pousos) é a chegada mais o tempo de permanência. O trecho seguinte deve entrar na aerovia exatamente na chegada do anterior (após um sobrevoo) ou no fim da permanência (após um pouso).

### Planos de voo: ciclo de vida

```
EDICAO ──aprovar──► APROVADO ──cancelar──► CANCELADO
   │
   └──abandonar──► ABANDONADO
```

| Método | Rota | Perfil | Descrição |
| --- | --- | --- | --- |
| POST | `/api/planos/{id}/validacao` | Qualquer | Verifica se o plano poderia ser aprovado agora. Não muda a situação nem cria reservas |
| POST | `/api/planos/{id}/aprovacao` | Qualquer | Revalida o plano, reserva altitudes e vagas e muda para `APROVADO` |
| POST | `/api/planos/{id}/abandono` | Qualquer | Muda um plano em edição para `ABANDONADO` |
| POST | `/api/planos/{id}/cancelamento` | Qualquer | Libera as reservas de um plano aprovado e muda para `CANCELADO`; exige que a partida ainda não tenha ocorrido |

Respostas:

| Situação | Status |
| --- | --- |
| Sucesso | `200` |
| Plano inexistente | `404` |
| Plano em situação que não permite a operação, ou partida já ocorrida no cancelamento | `409` |
| Aprovação de plano com problemas de validação (o corpo traz a lista de problemas) | `422` |
| Recurso reservado por outro plano durante a aprovação (nenhuma reserva parcial é mantida) | `409` |

Resposta da validação, e corpo do `422` da aprovação:

```json
{
  "valido": false,
  "problemas": [
    { "codigo": "HORARIO_ENTRADA_INVALIDO", "mensagem": "O horário de entrada do trecho 2 deve ser 2030-01-10T08:56." },
    { "codigo": "DESTINO_FINAL_SEM_POUSO", "mensagem": "O último trecho deve terminar com pouso." }
  ]
}
```

Resposta da aprovação, do abandono e do cancelamento:

```json
{
  "id": 1,
  "status": "APROVADO",
  "reservas": [
    { "id": 1, "tipo": "AEROVIA", "slot": "2030-01-10T08:00:00", "aeroviaId": "R1", "altitude": 30000 },
    { "id": 2, "tipo": "ESTACIONAMENTO", "slot": "2030-01-10T08:00:00", "codigoAeroporto": "FLN", "numeroVaga": 2 }
  ]
}
```

#### Regras verificadas na validação

| Regra | Códigos de problema |
| --- | --- |
| O percurso tem pelo menos um trecho e é contínuo da origem ao destino do plano | `PERCURSO_INVALIDO` |
| O primeiro trecho entra na partida; os seguintes, na chegada ou no fim da permanência do anterior | `HORARIO_ENTRADA_INVALIDO` |
| A chegada ao destino final ocorre no mesmo dia da partida | `CHEGADA_OUTRO_DIA` |
| O último trecho termina em pouso, e todo pouso tem vaga e permanência | `DESTINO_FINAL_SEM_POUSO`, `VAGA_INVALIDA`, `PERMANENCIA_INVALIDA` |
| A altitude está entre 25.000 e 35.000 pés, em múltiplos de 1.000 | `ALTITUDE_INVALIDA` |
| A categoria da aeronave permite a altitude e o horário do trecho | `OPERACAO_NAO_PERMITIDA` |
| A distância desde o último pouso não passa de 85% da autonomia (sobrevoos acumulam, pousos reabastecem) | `AUTONOMIA_INSUFICIENTE` |
| A altitude está livre na aerovia em todos os slots do trecho | `CONFLITO_EM_AEROVIA` |
| A vaga está livre em todos os slots da permanência | `VAGA_INDISPONIVEL` |

Regras de operação por categoria:

| Categoria | Restrição |
| --- | --- |
| `PARTICULAR` | Altitude entre 25.000 e 27.000 pés |
| `COMERCIAL_DE_PASSAGEIRO` | Altitude entre 28.000 e 35.000 pés |
| `COMERCIAL_DE_CARGA` | O voo do trecho deve ocorrer inteiramente entre 00:00 e 06:00, com a chegada antes das 06:00 |

## Roteiro de exemplo

Com o serviço rodando no perfil `local`, o roteiro abaixo cria um plano de Porto Alegre a Curitiba com escala em Florianópolis, aprova, consulta a ocupação e cancela.

```sh
API=http://localhost:8080/api
AUTH="usuario:usuario123"

curl -u $AUTH -H "Content-Type: application/json" -X POST $API/planos -d '{
  "pilotoId": 1, "aeronavePrefixo": "PR-GOL",
  "codOrigem": "POA", "codDestino": "CWB",
  "dataHoraPartida": "2030-01-10T08:00:00"
}'

curl -u $AUTH -H "Content-Type: application/json" -X PUT $API/planos/1/percurso -d '{
  "trechos": [
    { "aeroviaId": "R1", "altitude": 30000, "horarioEntradaNaAerovia": "2030-01-10T08:00:00",
      "acaoNaChegada": "POUSO", "numeroVagaPretendida": 2, "tempoPermanenciaMinutos": 30 },
    { "aeroviaId": "R4", "altitude": 31000, "horarioEntradaNaAerovia": "2030-01-10T08:56:00",
      "acaoNaChegada": "POUSO", "numeroVagaPretendida": 5, "tempoPermanenciaMinutos": 60 }
  ]
}'

curl -u $AUTH -X POST $API/planos/1/validacao
curl -u $AUTH -X POST $API/planos/1/aprovacao

curl -u $AUTH "$API/aerovias/R1/altitudes-disponiveis?inicio=2030-01-10T08:00:00&fim=2030-01-10T09:00:00"
curl -u $AUTH "$API/aeroportos/FLN/vagas-disponiveis?inicio=2030-01-10T08:00:00&fim=2030-01-10T09:00:00"

curl -u $AUTH -X POST $API/planos/1/cancelamento
```

Após a aprovação, a altitude 30.000 da R1 e a vaga 2 de FLN deixam de aparecer nas consultas de disponibilidade; após o cancelamento, voltam a aparecer.

## Documentação do projeto

- [Casos de uso](docs/dominio/use_cases.md)
- [Diagrama de domínio](docs/dominio/diagrama_de_dominio.png)
- Diagramas C4: [contexto](docs/arquitetura/c4/DiagramaContexto.png), [containers](docs/arquitetura/c4/DiagramaContainer.png) e [componentes](docs/arquitetura/c4/DiagramaComponentes.png)
