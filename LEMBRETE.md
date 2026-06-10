# Lembrete - Alterações realizadas

## 1. Credenciais movidas para .env
- **`.env`** — criado com `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`
- **`application.properties`** — valores substituídos por placeholders `${DB_URL}`, `${DB_USERNAME}`, `${DB_PASSWORD}`
- **`BarbeariaApplication.java`** — adicionado método `loadDotenv()` que carrega o `.env` antes do Spring iniciar
- **`.gitignore`** — já tinha `*.env`, então o `.env` não é versionado

## 2. Endpoint de login
- **`AuthController.java`** — adicionado `POST /auth/login`
  - Recebe `ClienteDTO` (nome + senha)
  - Busca cliente por nome no banco
  - Verifica senha com `BCryptPasswordEncoder.matches()`
  - Retorna `200` com `{id, nome, telefone}` se ok, ou `401` se inválido
