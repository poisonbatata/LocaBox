# 📦 LocaBox
Sua caixa de ferramentas e equipamentos compartilhada. Conectando quem tem a quem precisa.

## 📝 Objetivo do Projeto
LocaBox é um marketplace P2P para aluguel de ferramentas (furadeiras, serras, martelos…) e equipamentos (camping, caixas de som, churrasqueira…) por curtos períodos. O objetivo é reduzir custo e desperdício: em vez de comprar algo para uso esporádico, o usuário encontra alguém que já tem e aluga de forma simples e sustentável.

Pacote base: `br.edu.iff.ccc.locabox`

---

## ⚙️ Tecnologias Utilizadas
- Java 17
- Spring Boot 3.5.x (Web, Thymeleaf, Data JPA, Validation, Actuator)
- H2 Database (modo arquivo) + H2 Console
- Maven Wrapper (mvnw)

Obs.: O conector MySQL está presente no POM para futura integração, mas o perfil de desenvolvimento atual usa H2 (arquivo local).

---

## ▶️ Como Executar (Desenvolvimento)

### Pré-requisitos
- Windows com JDK 17 instalado e no PATH (confira com `java -version`)
- Git instalado
- NÃO é necessário instalar Maven: o projeto já inclui o Maven Wrapper (`mvnw.cmd`)

### Passo a passo (Windows PowerShell)
```powershell
# 1) Clonar o repositório
git clone <URL-DO-SEU-REPO>.git
cd LocaBox\locabox

# 2) (Opcional) Verificar o Maven Wrapper
.\mvnw.cmd -v

# 3) Rodar a aplicação
.\mvnw.cmd spring-boot:run

# 4) Acessar no navegador
# Página inicial (home)
# ->
# http://localhost:8080/
# Rotas úteis:
# - http://localhost:8080/principal
# - http://localhost:8080/user/login
# - http://localhost:8080/user/signup
# - http://localhost:8080/api/v1 (ping da API)


Para Linux/macOS, troque `mvnw.cmd` por `./mvnw`.

---

## 🗄️ Banco de Dados (H2)
- Console H2: acesse `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:file:./data.exemplo`
- Usuário: `sa`
- Senha: `password`

Importante: em `application.properties`, a propriedade abaixo está definida para recriar o schema a cada inicialização (útil no desenvolvimento). Para manter dados entre execuções, troque para `update`.

```properties
spring.jpa.hibernate.ddl-auto=create  # use 'update' para preservar dados
```

Arquivo de configuração: `locabox/src/main/resources/application.properties`.

---

## 🌐 Principais Endpoints (UI e API)
- GET `/` → home (lista itens populares na landing page)
- GET `/principal` → página principal alternativa
- GET `/user/login` → tela de login
- POST `/user/login` → autenticação (simples, ainda sem checagem de senha robusta)
- GET `/user/signup` → cadastro de usuário
- POST `/user/signup` → efetiva cadastro
- GET `/user/{id}` → perfil do usuário
- GET `/api/v1` → endpoint de saúde/boas-vindas da API
- GET `/h2-console` → console de banco H2

---

## 🧭 Estrutura do Projeto (resumo)
- `locabox/` → módulo Spring Boot
	- `src/main/java/br/edu/iff/ccc/locabox/` → código-fonte (controllers, services, entities)
	- `src/main/resources/templates/` → páginas Thymeleaf (index, login, signup, etc.)
	- `src/main/resources/static/` → assets estáticos (imagens)
	- `src/main/resources/application.properties` → configurações (H2, JPA, console)

---

## 🧩 Diagramas e Wireframes
- Diagrama (asta): `docs/Class Diagram0.asta`
- Wireframes (HTML/PNG):
	- `docs/wireframes/index.html` | `docs/wireframes/index.png`
	- `docs/wireframes/login.html` | `docs/wireframes/login.png`
	- `docs/wireframes/signup.html` | `docs/wireframes/signup.png`
	- `docs/wireframes/search.html` | `docs/wireframes/search.png`
	- `docs/wireframes/register-tool.html` | `docs/wireframes/register-tool.png`
	- `docs/wireframes/tool.html` | `docs/wireframes/tool.png`
	- `docs/wireframes/user-profile.html` | `docs/wireframes/user-profile.png`
	- `docs/wireframes/pay.html` | `docs/wireframes/pay.png`

Abra os arquivos HTML localmente para navegar pelos protótipos de tela.

---

## 🧑‍🤝‍🧑 Histórias de Usuário (MVP)
- Como visitante, quero acessar a home para descobrir itens populares e entender o serviço.
- Como visitante, quero criar uma conta (signup) para poder alugar e anunciar ferramentas.
- Como usuário, quero entrar (login) para acessar meu perfil e ações restritas.
- Como usuário, quero ver meu perfil com informações básicas para gerenciar meus dados.
- Como usuário, quero pesquisar por ferramentas para encontrar itens perto de mim.
- Como anunciante, quero cadastrar uma ferramenta para disponibilizá-la para aluguel.
- Como locatário, quero ver detalhes de uma ferramenta para decidir se alugo.
- Como locatário, quero iniciar uma solicitação de aluguel de forma simples e segura.

---

## 🧪 Dicas e Solução de Problemas
- Java 17: se a versão estiver diferente, ajuste o JAVA_HOME e/ou PATH.
- Porta 8080 ocupada: altere `server.port` em `application.properties` (ex.: `server.port=8081`).
- H2 Console não conecta: confira o JDBC URL exatamente como acima.
- Erro de Maven no Windows: use `mvnw.cmd` (e não `mvnw`).

---

## 📬 Contato e Contribuição
Sinta-se à vontade para abrir issues/PRs com melhorias. Sugestões para telas, regras de negócio e integrações são bem-vindas.

—
LocaBox • 2025
