# 🚀 Mock Application - Sistema de Autenticação

Aplicação web completa desenvolvida com **Node.js + Express** para simular um sistema real de autenticação, permitindo a execução dos testes automatizados.

## 📋 Sobre

Esta aplicação mock implementa:
- 🔐 Sistema de login com autenticação completa
- 👥 Controle de acesso baseado em roles (ADMIN, USER, VISITOR)
- 🚫 Bloqueio automático após 3 tentativas de login falhas
- 📊 Dashboard com carregamento assíncrono e painéis específicos por perfil
- 💾 Integração com PostgreSQL para persistência de dados
- 🔄 API REST para integração com testes automatizados
- 📝 Auditoria de logins e sessões de usuário

## 🛠️ Tecnologias

- **Node.js 18+**
- **Express 4.18.2** - Framework web
- **PostgreSQL 17** - Banco de dados (opcional, funciona em memória também)
- **Body-parser** - Parse de requisições JSON
- **CORS** - Habilitado para testes

## 🚀 Como Executar

### Pré-requisitos
- Node.js 18+ instalado
- PostgreSQL 17+ instalado e rodando
- Porta 8080 disponível

### 1️⃣ Instalar Dependências
```bash
npm install
```

### 2️⃣ Configurar PostgreSQL
```powershell
# Criar database
& "C:\Program Files\PostgreSQL\17\bin\psql.exe" -U postgres -c "CREATE DATABASE prova_tecnica_qa;"

# Executar schema e dados de teste
& "C:\Program Files\PostgreSQL\17\bin\psql.exe" -U postgres -d prova_tecnica_qa -f setup-database.sql
```

### 3️⃣ Iniciar o Servidor
```bash
npm start
```

✅ Servidor rodando em: **http://localhost:8080**

### Páginas Disponíveis
- `http://localhost:8080/login` - Página de login
- `http://localhost:8080/dashboard` - Dashboard (requer autenticação)

## 👤 Usuários de Teste

| Username | Password | Role | Bloqueado | Descrição |
|----------|----------|------|-----------|-----------|
| `admin` | `admin123` | ADMIN | ❌ | Acesso completo + painel administrativo |
| `user` | `user123` | USER | ❌ | Acesso regular ao sistema |
| `visitor` | `visitor123` | VISITOR | ❌ | Sem permissão de acesso (retorna 403) |
| `blocked_user` | `blocked123` | USER | ✅ | Usuário previamente bloqueado (retorna 423) |

**Nota**: Após 3 tentativas de login com senha incorreta, qualquer usuário será bloqueado automaticamente.

## 🔌 API Endpoints

### 🔐 POST `/api/login`
Autenticação de usuário

**Request:**
```json
{
  "username": "admin",
  "password": "admin123"
}
```

**Responses:**

| Status | Descrição | Body Example |
|--------|-----------|--------------|
| **200 OK** | Login bem-sucedido | `{"success": true, "message": "Login successful", "role": "ADMIN"}` |
| **400 Bad Request** | Campos obrigatórios ausentes | `{"success": false, "message": "Username and password are required"}` |
| **401 Unauthorized** | Credenciais inválidas | `{"success": false, "message": "Invalid credentials"}` |
| **403 Forbidden** | Acesso negado (role VISITOR) | `{"success": false, "message": "Access denied"}` |
| **423 Locked** | Usuário bloqueado | `{"success": false, "message": "User is blocked"}` |

### 🔄 POST `/api/reset-attempts`
Reset de tentativas de login (helper para testes)

**Request:**
```json
{
  "username": "admin"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Login attempts reset successfully"
}
```

**Uso**: Permite resetar o contador de tentativas de um usuário durante a execução dos testes automatizados.

## 📋 Funcionalidades Implementadas

### Frontend
- ✅ **Página de Login** (`public/login.html`)
  - Formulário com validação client-side
  - Mensagens de erro/sucesso dinâmicas
  - Armazenamento de role no sessionStorage
  - Redirecionamento automático baseado em autenticação

- ✅ **Dashboard** (`public/dashboard.html`)
  - Carregamento assíncrono simulado (2 segundos)
  - Spinner de loading durante carregamento
  - Painel administrativo visível apenas para ADMIN
  - Layout responsivo e moderno

### Backend (server.js)
- ✅ **Sistema de Autenticação Completo**
  - Validação de credenciais contra banco de dados
  - Controle de tentativas de login (máx. 3)
  - Bloqueio automático de usuários
  - Gerenciamento de roles (ADMIN/USER/VISITOR)

- ✅ **Integração com PostgreSQL**
  - Tabela `usuarios` - Dados de usuários e bloqueios
  - Tabela `auditoria_login` - Histórico de tentativas
  - Tabela `sessoes` - Controle de sessões ativas
  - Queries preparadas para segurança

- ✅ **API REST**
  - Endpoints documentados e testáveis
  - Respostas padronizadas em JSON
  - CORS habilitado para integração
  - Status codes HTTP corretos

### Recursos de Segurança
- 🔒 Validação de campos obrigatórios
- 🔒 Proteção contra força bruta (bloqueio após 3 tentativas)
- 🔒 Controle de acesso baseado em roles
- 🔒 Prepared statements (prevenção SQL Injection)

## 🧪 Executar Testes Automatizados

Certifique-se de que o servidor mock está **rodando** antes de executar os testes:

```bash
# Terminal 1: Manter servidor rodando
npm start

# Terminal 2: Executar testes
cd ..
mvn clean test
```

### Testes que Dependem desta Aplicação

| Classe de Teste | Quantidade | O que Valida |
|----------------|------------|--------------|
| `LoginUITest` | 2 testes | Login UI com Selenium (admin e user) |
| `AsyncLoadingTest` | 1 teste | Carregamento assíncrono do dashboard |
| `LoginApiTest` | 2 testes | API REST (200 OK e 401 Unauthorized) |
| `DatabaseValidationTest` | 1 teste | Bloqueio de usuário no PostgreSQL |
| **TOTAL** | **6 testes** | **100% de cobertura** |

## 📁 Estrutura de Arquivos

```
mock-app/
├── server.js              # Servidor Express principal
├── package.json           # Dependências Node.js
├── setup-database.sql     # Schema PostgreSQL + dados de teste
├── public/
│   ├── login.html         # Página de login
│   └── dashboard.html     # Dashboard com painéis
└── README.md              # Esta documentação
```

## 🔧 Troubleshooting

### Porta 8080 já está em uso
```powershell
# Verificar processo
netstat -ano | findstr :8080

# Matar processo (substitua <PID>)
taskkill /PID <PID> /F
```

### Erro ao conectar no PostgreSQL
```powershell
# Verificar se PostgreSQL está rodando
Get-Service -Name postgresql*

# Testar conexão
& "C:\Program Files\PostgreSQL\17\bin\psql.exe" -U postgres -c "SELECT version();"
```

### Database não existe
```powershell
# Recriar database
& "C:\Program Files\PostgreSQL\17\bin\psql.exe" -U postgres -c "DROP DATABASE IF EXISTS prova_tecnica_qa;" -c "CREATE DATABASE prova_tecnica_qa;"
& "C:\Program Files\PostgreSQL\17\bin\psql.exe" -U postgres -d prova_tecnica_qa -f setup-database.sql
```

## 📊 Schema do Banco de Dados

### Tabela: `usuarios`
| Coluna | Tipo | Descrição |
|--------|------|-----------|
| id | SERIAL PRIMARY KEY | ID único do usuário |
| username | VARCHAR(50) UNIQUE | Nome de usuário |
| password | VARCHAR(100) | Senha (plaintext para demo) |
| role | VARCHAR(20) | ADMIN, USER ou VISITOR |
| bloqueado | BOOLEAN | Status de bloqueio |
| tentativas_login | INTEGER | Contador de tentativas |
| data_criacao | TIMESTAMP | Data de criação |

### Tabela: `auditoria_login`
Registra todas as tentativas de login (sucesso e falha)

### Tabela: `sessoes`
Gerencia sessões ativas de usuários autenticados

---

**Desenvolvido para suportar testes automatizados QA**  
**Versão**: 2.0  
**Compatível com**: Java 17, Selenium 4.15, RestAssured 5.4
