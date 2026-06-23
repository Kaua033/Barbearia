# LEMBRETE - Docker Barbearia

## Imagens Docker
- `barbearia-app:latest` — primeira build
- `barbearia-app:v2-20260623` — segunda build (23/06/2026)

## Como rodar
```powershell
docker run -d --name barbearia-container --env-file .\.env -p 8080:8080 barbearia-app:v2-20260623
```

## Dockerfile
- Usa `-Dmaven.test.skip=true` (compila sem testes)
- Multi-stage: `eclipse-temurin:17-jdk-alpine` (build) → `eclipse-temurin:17-jre-alpine` (runtime)
- Expõe porta 8080

## Banco
- Conectado ao **Neon** (config no `.env`)
- `.env` é passado via `--env-file` na execução do container

## Comandos úteis
- `docker logs barbearia-container` — ver logs
- `docker stop barbearia-container` — parar
- `docker rm barbearia-container` — remover
- `docker build -t barbearia-app:v2-20260623 .` — buildar nova imagem
