# 🎯 Prova Técnica - Framework de Automação QA

## 📋 Sobre o Projeto

Framework completo de automação de testes desenvolvido para demonstração de habilidades em QA Automation, incluindo:
- ✅ Automação UI com Selenium WebDriver + Page Object Model
- ✅ Automação de API REST com RestAssured
- ✅ Testes de integração com PostgreSQL
- ✅ Aplicação Mock completa (Node.js + Express)
- ✅ Relatório visual personalizado para apresentação
- ✅ 100% de cobertura de testes (6/6 testes passando)

## 🛠️ Tecnologias Utilizadas

### Backend & Testes
- **Java 17** (Eclipse Adoptium JDK)
- **Maven 3.9.9** - Gerenciamento de dependências e build
- **Selenium WebDriver 4.15.0** - Automação de testes UI
- **RestAssured 5.4.0** - Automação de testes de API REST
- **TestNG 7.8.0** - Framework de testes e relatórios
- **WebDriverManager 5.6.2** - Gerenciamento automático de drivers
- **Log4j2 2.21.1** - Sistema de logging

### Mock Application
- **Node.js 18+** com Express 4.18.2
- **PostgreSQL 17** - Banco de dados relacional
- **JDBC PostgreSQL Driver 42.7.0**

### Padrões & Arquitetura
- **Page Object Model** - Organização de testes UI
- **Data-Driven Testing** - Configurações via properties
- **Separation of Concerns** - Estrutura modular

## 📂 Estrutura do Projeto

```
prova-tecnica-qa-automation/
├── src/
│   ├── main/java/com/qa/
│   │   ├── config/              # Configuration (config.properties loader)
│   │   ├── database/            # DatabaseHelper (JDBC PostgreSQL)
│   │   └── utils/               # Utilitários gerais
│   └── test/
│       ├── java/com/qa/
│       │   ├── ui/
│       │   │   ├── pages/       # Page Objects (LoginPage, DashboardPage)
│       │   │   └── tests/       # UI Tests (LoginUITest, AsyncLoadingTest)
│       │   ├── api/tests/       # API Tests (LoginApiTest)
│       │   └── database/tests/  # Database Tests (DatabaseValidationTest)
│       └── resources/
│           ├── config.properties # Configurações centralizadas
│           └── log4j2.xml       # Logging configuration
├── mock-app/                    # 🚀 Aplicação Mock Node.js
│   ├── server.js                # Express server com autenticação
│   ├── public/
│   │   ├── login.html           # Página de login
│   │   └── dashboard.html       # Dashboard com painéis de usuário
│   ├── setup-database.sql       # Schema e dados de teste PostgreSQL
│   └── package.json
├── target/
│   └── surefire-reports/        # Relatórios TestNG
├── generate-report.html         # 🎨 Relatório visual customizado
├── APRESENTACAO.md              # 📋 Guia de apresentação
└── pom.xml
```

## 🚀 Como Executar

### Pré-requisitos

1. ✅ **Java 17** instalado ([Eclipse Adoptium](https://adoptium.net/))
2. ✅ **Maven 3.9+** instalado
3. ✅ **PostgreSQL 17** instalado e rodando
4. ✅ **Node.js 18+** instalado
5. ✅ **Google Chrome** (versão recente)

### 🔧 Setup Completo (Passo a Passo)

#### 1. Configurar Variáveis de Ambiente (Windows)

```powershell
# Adicionar JAVA_HOME
[Environment]::SetEnvironmentVariable("JAVA_HOME", "C:\Program Files\Eclipse Adoptium\jdk-17.0.17", "User")

# Adicionar Maven e Java ao PATH
$currentPath = [Environment]::GetEnvironmentVariable("Path", "User")
$newPath = "C:\Users\gabriel\Maven\apache-maven-3.9.9\bin;$env:JAVA_HOME\bin;$currentPath"
[Environment]::SetEnvironmentVariable("Path", $newPath, "User")
```

#### 2. Configurar PostgreSQL

```powershell
# Criar database
& "C:\Program Files\PostgreSQL\17\bin\psql.exe" -U postgres -c "CREATE DATABASE prova_tecnica_qa;"

# Executar schema e dados de teste
cd mock-app
& "C:\Program Files\PostgreSQL\17\bin\psql.exe" -U postgres -d prova_tecnica_qa -f setup-database.sql
```

#### 3. Iniciar Mock Application

```powershell
# Instalar dependências do Node.js
cd mock-app
npm install

# Iniciar servidor (localhost:8080)
npm start
```

> 💡 **Importante**: Mantenha o servidor rodando em um terminal separado

#### 4. Executar Testes

```powershell
# Em outro terminal, na raiz do projeto
mvn clean test
```

### 🎯 Executando Testes Específicos

#### Todos os testes (6 testes):
```bash
mvn clean test
```

#### Apenas testes UI (3 testes):
```bash
mvn test -Dtest=LoginUITest,AsyncLoadingTest
```

#### Apenas testes de API (2 testes):
```bash
mvn test -Dtest=LoginApiTest
```

#### Apenas validação de Database (1 teste):
```bash
mvn test -Dtest=DatabaseValidationTest
```

### 📊 Visualizar Relatórios

#### Relatório Visual Customizado (Recomendado para Apresentação)
```powershell
start generate-report.html
```
- ✨ Design moderno e responsivo
- 📈 Visualização de 100% de taxa de sucesso
- 🎨 Layout otimizado para demonstração
- 🛠️ Mostra stack tecnológica completa

#### Relatório TestNG Padrão
```powershell
start target\surefire-reports\index.html
```

## 📊 Cobertura de Testes (6/6 - 100%)

### ✅ Testes UI com Selenium (3 testes)
| Teste | Descrição | Status |
|-------|-----------|--------|
| `testLoginWithValidAdminCredentials` | Login ADMIN + verificação painel admin | ✅ PASS |
| `testLoginWithValidRegularUserCredentials` | Login USER regular | ✅ PASS |
| `testAsyncDashboardLoading` | Validação de carregamento assíncrono | ✅ PASS |

### ✅ Testes API com RestAssured (2 testes)
| Teste | Endpoint | Response | Status |
|-------|----------|----------|--------|
| `testApiLoginWithValidCredentials` | POST /api/login | 200 OK | ✅ PASS |
| `testApiLoginWithInvalidCredentials` | POST /api/login | 401 Unauthorized | ✅ PASS |

### ✅ Validações de Database (1 teste)
| Teste | Descrição | Status |
|-------|-----------|--------|
| `testUserBlocking` | Verificação de bloqueio de usuário no PostgreSQL | ✅ PASS |

## 🎨 Aplicação Mock

A aplicação mock simula um sistema real de autenticação com:

### Funcionalidades Implementadas
- 🔐 Sistema de login com autenticação
- 👥 Controle de acesso baseado em roles (ADMIN/USER/VISITOR)
- 🚫 Bloqueio automático após 3 tentativas falhas
- 📊 Dashboard com painéis específicos por perfil
- 💾 Persistência em PostgreSQL (usuários, auditoria, sessões)
- 🔄 API REST para integração

### Endpoints Disponíveis
- `GET /login` - Página de login
- `GET /dashboard` - Dashboard do sistema
- `POST /api/login` - Autenticação via API
- `POST /api/reset-attempts` - Reset de tentativas (para testes)

### Usuários de Teste
| Username | Password | Role | Bloqueado |
|----------|----------|------|-----------|
| admin | admin123 | ADMIN | ❌ |
| user | user123 | USER | ❌ |
| visitor | visitor123 | VISITOR | ❌ |
| blocked_user | blocked123 | USER | ✅ |

## 🎯 Destaques Técnicos

### Page Object Model
```java
// Exemplo: LoginPage.java
public class LoginPage {
    private WebDriver driver;
    
    @FindBy(id = "username")
    private WebElement usernameField;
    
    @FindBy(css = "button[type='submit']")
    private WebElement loginButton;
    
    public void login(String username, String password) {
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();
    }
}
```

### RestAssured API Testing
```java
// Exemplo: LoginApiTest.java
@Test
public void testApiLoginWithValidCredentials() {
    given()
        .contentType(ContentType.JSON)
        .body("{\"username\":\"admin\",\"password\":\"admin123\"}")
    .when()
        .post("/api/login")
    .then()
        .statusCode(200)
        .body("success", equalTo(true));
}
```

### Database Validation
```java
// Exemplo: DatabaseHelper.java
public boolean isUserBlocked(String username) throws SQLException {
    String query = "SELECT bloqueado FROM usuarios WHERE username = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        return rs.next() && rs.getBoolean("bloqueado");
    }
}
```

## 📋 Configuração (config.properties)

```properties
# Database
db.url=jdbc:postgresql://localhost:5432/prova_tecnica_qa
db.username=postgres
db.password=postgres

# Application URLs
app.base.url=http://localhost:8080
api.base.url=http://localhost:8080

# Browser
browser=chrome
implicit.wait=10
explicit.wait=15
```

## 🎓 Para Apresentação

1. **Iniciar Mock App**: `npm start` (em `mock-app/`)
2. **Executar Testes**: `mvn clean test`
3. **Abrir Relatório**: `start generate-report.html`
4. **Demonstrar**: Mostrar 100% de taxa de sucesso e stack tecnológica

Consulte **[APRESENTACAO.md](APRESENTACAO.md)** para roteiro completo de apresentação.

## 🛠️ Troubleshooting

### Porta 8080 em uso
```powershell
# Verificar processo usando porta 8080
netstat -ano | findstr :8080

# Matar processo (substitua <PID>)
taskkill /PID <PID> /F
```

### ChromeDriver incompatível
- WebDriverManager baixa automaticamente a versão correta
- Verifique logs em `target/surefire-reports`

### Database connection refused
```powershell
# Verificar se PostgreSQL está rodando
Get-Service -Name postgresql*

# Verificar se database existe
& "C:\Program Files\PostgreSQL\17\bin\psql.exe" -U postgres -l
```

## 👨‍💻 Autor

**Gabriel**
- GitHub: [@gabrielgblbel](https://github.com/gabrielgblbel)

## 🏆 Resultados

- ✅ **6/6 testes passando** (100% de taxa de sucesso)
- ✅ **0 erros** de compilação ou runtime
- ✅ **Cobertura completa**: UI, API e Database
- ✅ **Tempo médio de execução**: ~17 segundos
- ✅ **Aplicação mock funcional** com autenticação real
- ✅ **Relatório visual profissional** para apresentação

## 📄 Arquivos Importantes

- `pom.xml` - Dependências Maven e configuração de build
- `config.properties` - Configurações centralizadas
- `generate-report.html` - Relatório visual customizado
- `APRESENTACAO.md` - Guia de apresentação para entrevista
- `mock-app/server.js` - Servidor Express com API REST
- `mock-app/setup-database.sql` - Schema PostgreSQL

---

**Projeto desenvolvido para demonstração de competências em QA Automation**  
**Versão**: 2.0.0  
**Data**: 21/11/2025  
**Status**: ✅ Pronto para Produção
