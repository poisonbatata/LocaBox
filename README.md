# 📦 LocaBox
*Sua caixa de ferramentas e equipamentos compartilhada. Conectando quem tem a quem precisa.*

## 📝 Sobre o Projeto
LocaBox é um marketplace P2P para aluguel de ferramentas (furadeiras, serras, martelos…) e equipamentos (camping, caixas de som, churrasqueira…) por curtos períodos.

O objetivo é reduzir custo e desperdício: em vez de comprar algo para uso esporádico, o usuário encontra alguém que já tem e aluga de forma simples e sustentável.

---

## ⚙️ Tecnologias Utilizadas
- Java 17
- Spring Boot
- Thymeleaf
- H2 Database
- Maven Wrapper

Pacote base: `br.edu.iff.ccc.locabox`

---

## ▶️ Como Executar (dev)

### Pré-requisitos
- JDK 17 instalado e configurado (`java -version`)
- Maven wrapper (já incluso no projeto)
- (Opcional) IDE: IntelliJ / Eclipse / VS Code + Extensões Java

### Passos
```bash
# 1) clonar
git clone <seu-repo-github>.git
cd <seu-repo-github>

# 2) rodar
./mvnw spring-boot:run

# 3) acessar
http://localhost:8080/principal
