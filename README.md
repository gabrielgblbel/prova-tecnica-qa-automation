# 🧩 Prova Técnica - Analista de Automação de Testes

## 📋 Sobre o Projeto

Este projeto contém a solução completa da prova técnica para Analista de Automação de Testes, incluindo:
- Automação UI com Selenium WebDriver
- Automação de API com RestAssured
- Testes de integração com PostgreSQL
- Análises teóricas e práticas de QA

## 🛠️ Tecnologias Utilizadas

- **Java 11+**
- **Maven** - Gerenciamento de dependências
- **Selenium WebDriver** - Automação UI
- **RestAssured** - Automação de API
- **JUnit 5** - Framework de testes
- **Cucumber** - BDD (Behavior Driven Development)
- **PostgreSQL** - Banco de dados
- **WebDriverManager** - Gerenciamento de drivers
- **Log4j2** - Logging

## 📂 Estrutura do Projeto

```
prova-tecnica-qa-automation/
├── src/
│   ├── main/java/com/qa/
│   │   ├── config/              # Configurações
│   │   ├── database/            # Helpers de banco de dados
│   │   └── utils/               # Utilitários gerais
│   └── test/
│       ├── java/com/qa/
│       │   ├── ui/              # Testes UI (Selenium)
│       │   ├── api/             # Testes API (RestAssured)
│       │   ├── integration/     # Testes de integração
│       │   └── database/        # Validações de BD
│       └── resources/
│           ├── config.properties
│           └── log4j2.xml
├── scripts/
│   ├── setup_database.sql
│   └── cleanup_database.sql
├── docs/
│   ├── cenarios-de-teste.md
│   ├── respostas-teoricas.md
│   └── analise-sql.md
└── pom.xml
```

## 🚀 Como Executar

### Pré-requisitos

1. **Java 11+** instalado
2. **Maven 3.6+** instalado
3. **PostgreSQL** instalado e rodando
4. **Git** instalado

### Configuração do Banco de Dados

1. Crie um banco de dados PostgreSQL:
```sql
CREATE DATABASE prova_tecnica_qa;
```

2. Execute o script de setup:
```bash
psql -U postgres -d prova_tecnica_qa -f scripts/setup_database.sql
```

### Configuração do Projeto

1. Clone o repositório:
```bash
git clone https://github.com/gabrielgblbel/prova-tecnica-qa-automation.git
cd prova-tecnica-qa-automation
```

2. Configure o arquivo `src/test/resources/config.properties`:
```properties
db.url=jdbc:postgresql://localhost:5432/prova_tecnica_qa
db.username=postgres
db.password=sua_senha

app.base.url=http://localhost:8080
api.base.url=http://localhost:8080/api

browser=chrome
```

3. Instale as dependências:
```bash
mvn clean install -DskipTests
```

### Executando os Testes

#### Todos os testes:
```bash
mvn clean test
```

#### Apenas testes UI:
```bash
mvn test -Dtest=LoginUITest
```

#### Apenas testes de API:
```bash
mvn test -Dtest=LoginApiTest
```

#### Apenas testes de Integração:
```bash
mvn test -Dtest=AsyncLoadingTest
```

#### Apenas validações de Banco:
```bash
mvn test -Dtest=DatabaseValidationTest
```

## 📊 Cobertura de Testes

- ✅ **Parte 0**: Teoria e Conceitos (documentado em `docs/respostas-teoricas.md`)
- ✅ **Parte A**: Análise e Planejamento (documentado em `docs/cenarios-de-teste.md`)
- ✅ **Parte B**: Automação UI com Selenium
- ✅ **Parte C**: Automação API com RestAssured
- ✅ **Parte D**: Integração e Esperas
- ✅ **Parte E**: SQL e PostgreSQL (documentado em `docs/analise-sql.md`)

## 🎯 Cenários Implementados

### Testes UI (Selenium)
1. ✅ Login válido com usuário USER
2. ✅ Login válido com usuário ADMIN
3. ✅ Login de perfil sem acesso (VISITOR)
4. ✅ Bloqueio após 3 tentativas inválidas
5. ✅ Validação de campos obrigatórios

### Testes API (RestAssured)
1. ✅ POST /api/login - 200 (credenciais válidas)
2. ✅ POST /api/login - 401 (credenciais inválidas)
3. ✅ POST /api/login - 403 (acesso negado)
4. ✅ POST /api/login - 423 (usuário bloqueado)

### Validações de Banco
1. ✅ Consulta de logins de administradores
2. ✅ Verificação de usuários bloqueados
3. ✅ Detecção de dados órfãos
4. ✅ Validação de integridade referencial

## 📝 Documentação

Toda a documentação detalhada está disponível em:

- **[Respostas Teóricas](docs/respostas-teoricas.md)** - Parte 0 da prova
- **[Cenários de Teste](docs/cenarios-de-teste.md)** - Parte A da prova
- **[Análise SQL](docs/analise-sql.md)** - Parte E da prova

## 👨‍💻 Autor

**Gabriel**
- GitHub: [@gabrielgblbel](https://github.com/gabrielgblbel)

## 📄 Licença

Este projeto foi desenvolvido como parte de uma prova técnica.

---

**Data de Entrega**: 13/11/2025  
**Versão**: 1.0.0
