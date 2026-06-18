# 🏨 Hotel Management API

Uma API RESTful robusta desenvolvida em Java e Spring Boot para o gerenciamento completo das operações de um hotel. O sistema provê funcionalidades essenciais para o controle de hóspedes, quartos e reservas, permitindo que a recepção gerencie as entradas (check-ins) e saídas (check-outs) com eficiência.

## 🚀 Tecnologias Utilizadas

Este projeto foi construído utilizando as melhores práticas de desenvolvimento, com um stack moderno e focado em performance, manutenibilidade e escalabilidade:

* **Java 25**: Linguagem principal utilizando features modernas.
* **Spring Boot 4.x**: Framework base para a construção da aplicação (WebMVC, Data JPA, Validation).
* **MySQL 8.0**: Banco de dados relacional para persistência de dados.
* **Docker & Docker Compose**: Contêinerização do banco de dados para facilitar a configuração do ambiente de desenvolvimento local.
* **SpringDoc OpenAPI (Swagger)**: Documentação interativa e padronizada da API.
* **Gradle**: Ferramenta de automação de builds e gerenciamento de dependências.
* **JUnit & Spring Boot Test**: Testes automatizados (com banco H2 em memória).

## 🏛️ Arquitetura e Domínio de Negócios

A arquitetura do projeto foi desenhada visando baixo acoplamento e alta coesão, aplicando conceitos de **SOLID**, **Clean Code** e **Object-Oriented Programming (OOP)**. A aplicação é dividida em três domínios principais:

1. **Gestão de Hóspedes (Guests)**:
   * Cadastro de novos clientes com validação de dados (CPF, E-mail, Data de Nascimento).
   * Diretório pesquisável de clientes registrados.

2. **Gestão de Quartos (Rooms)**:
   * Controle de disponibilidade em tempo real (`AVAILABLE`, `OCCUPIED`, `MAINTENANCE`).
   * Visualização de especificações dos quartos (número de camas, categoria) e status de ocupação atual.

3. **Gestão de Reservas e Hospedagem (Reservations)**:
   * Orquestração de reservas vinculando Hóspedes e Quartos com datas de entrada e saída.
   * Controle de ciclo de vida da reserva e mudança de status durante `Check-in` e `Check-out`.
   * Acerto de contas financeiro considerando o valor total previsto da estadia.

## 📖 Documentação da API (Contrato)

A API foi projetada segundo os princípios **RESTful**. Abaixo os principais fluxos disponíveis.

### 🛏️ Quartos (`/api/rooms`)
| Método | Rota | Descrição |
|--------|------|-----------|
| `GET` | `/api/rooms` | Lista todos os quartos (suporta filtro `?status=AVAILABLE`). |
| `GET` | `/api/rooms/{id}` | Retorna detalhes completos do quarto, incluindo informações do hóspede atual (se ocupado). |

### 👥 Hóspedes (`/api/guests`)
| Método | Rota | Descrição |
|--------|------|-----------|
| `GET` | `/api/guests` | Busca hóspedes (suporta filtro `?name=Nome`). |
| `POST` | `/api/guests` | Cadastra um novo cliente (Requer nome, cpf, email, birth_date). |

### 📅 Reservas (`/api/reservations`)
| Método | Rota | Descrição |
|--------|------|-----------|
| `POST` | `/api/reservations` | Cria uma nova reserva, processando datas, quarto e hóspede. |
| `GET` | `/api/reservations` | Lista o histórico e as reservas ativas. |
| `POST` | `/api/reservations/{id}/checkin` | Realiza o check-in, alterando o status da reserva e ocupando o quarto. |
| `POST` | `/api/reservations/{id}/checkout` | Realiza o check-out, liberando o quarto. |

## 🛠️ Como Executar o Projeto Localmente

### Pré-requisitos
* Java 25 instalado (JDK).
* Docker e Docker Compose instalados.

### Passos para rodar a aplicação:

1. **Subir a infraestrutura (Banco de Dados)**:
   Na raiz do projeto, inicie o contêiner do MySQL:
   ```bash
   docker-compose up -d
   ```
   *Isto iniciará um servidor MySQL na porta 3306 e criará o banco `hotel_management` mapeado no script `init.sql`.*

2. **Rodar a API Spring Boot**:
   Utilize o wrapper do Gradle para iniciar a aplicação:
   * **Linux / macOS**:
     ```bash
     ./gradlew bootRun
     ```
   * **Windows**:
     ```bash
     gradlew.bat bootRun
     ```

3. **Acessar a Aplicação**:
   A API estará disponível por padrão em: `http://localhost:8080` e os endpoints a partir de `/api/`.

## 🧪 Testes

A aplicação inclui testes de integração e unitários rodando contra um banco de dados em memória (H2). Para executar a suíte de testes:
```bash
./gradlew test
```

## 📈 Próximos Passos e Evolução (Roadmap)
* Implementação de Spring Security e JWT para autenticação e autorização de recepcionistas e administradores.
* Paginação dos endpoints de listagem de hóspedes e reservas.
* Gestão e soma de consumo de produtos (Frigobar/Restaurante) durante a estadia do hóspede.

---
*Desenvolvido com o objetivo de construir uma solução backend escalável, focando em modelagem de domínio rica, boas práticas arquiteturais e resiliência de código.*
