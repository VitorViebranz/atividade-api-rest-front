# 📚 Biblioteca MVC com Spring Boot + Thymeleaf

Aplicação Spring Boot para o gerenciamento de biblioteca com interface web MVC (Thymeleaf), permitindo controlar **usuários**, **livros** e **empréstimos**.

---

## 🖥️ Páginas MVC

- `/` → CRUD de usuários
- `/livros` → CRUD de livros
- `/emprestimos` → empréstimos e devoluções
- `/relatorios` → relatório de empréstimos

---

## 🚀 Objetivo

O projeto tem como objetivo simular o funcionamento básico de uma biblioteca:

- Cadastro de usuários  
- Cadastro de livros  
- Controle de empréstimos e devoluções  

---

## 🧩 Diagrama de Classes (Entidades)

### 👤 Usuario

| Campo | Tipo |
|------|------|
| id | Long |
| nome | String |
| email | String |

---

### 📖 Livro

| Campo | Tipo |
|------|------|
| id | Long |
| titulo | String |
| autor | String |
| isbn | String |
| disponivel | Boolean |

---

### 🔄 Emprestimo

| Campo | Tipo |
|------|------|
| id | Long |
| usuario | Usuario |
| livro | Livro |
| dataEmprestimo | LocalDate |
| dataDevolucao | LocalDate |

---

### 🔗 Relacionamentos

| Origem | Relação | Destino |
|--------|--------|--------|
| Usuario | 1:N | Emprestimo |
| Livro | 1:N | Emprestimo |

---

## 📘 Endpoints

### 👤 Usuários

| Método | Endpoint | Entrada | Retorno | Descrição |
|--------|---------|--------|---------|----------|
| GET | `/api/usuarios` | — | [{<br>&nbsp;&nbsp;"id": 1,<br>&nbsp;&nbsp;"nome": "João Silva",<br>&nbsp;&nbsp;"email": "joao.silva@email.com"<br>}] | Lista todos os usuários |
| GET | `/api/usuarios/{id}` | — | {<br>&nbsp;&nbsp;"id": 2,<br>&nbsp;&nbsp;"nome": "Maria Oliveira",<br>&nbsp;&nbsp;"email": "maria.oliveira@email.com"<br>} | Busca usuário por ID |
| PUT | `/api/usuarios` | {<br>&nbsp;&nbsp;"id": 1,<br>&nbsp;&nbsp;"nome": "João Silva Atualizado",<br>&nbsp;&nbsp;"email": "joao.silva@email.com"<br>} | {<br>&nbsp;&nbsp;"id": 1,<br>&nbsp;&nbsp;"nome": "João Silva Atualizado",<br>&nbsp;&nbsp;"email": "joao.silva@email.com"<br>} | Atualiza usuário |

---

### 📖 Livros

| Método | Endpoint | Entrada | Retorno | Descrição |
|--------|---------|--------|---------|----------|
| GET | `/api/livros` | — | [{<br>&nbsp;&nbsp;"id": 1,<br>&nbsp;&nbsp;"titulo": "Clean Code",<br>&nbsp;&nbsp;"autor": "Robert C. Martin",<br>&nbsp;&nbsp;"isbn": "9780132350884",<br>&nbsp;&nbsp;"disponivel": true<br>}] | Lista todos os livros |
| POST | `/api/livros` | {<br>&nbsp;&nbsp;"titulo": "Domain-Driven Design",<br>&nbsp;&nbsp;"autor": "Eric Evans",<br>&nbsp;&nbsp;"isbn": "9780321125217"<br>} | {<br>&nbsp;&nbsp;"id": 2,<br>&nbsp;&nbsp;"titulo": "Domain-Driven Design",<br>&nbsp;&nbsp;"autor": "Eric Evans",<br>&nbsp;&nbsp;"isbn": "9780321125217",<br>&nbsp;&nbsp;"disponivel": true<br>} | Cadastra novo livro |

---

### 🔄 Empréstimos

| Método | Endpoint | Entrada | Retorno | Descrição |
|--------|---------|--------|---------|----------|
| GET | `/api/emprestimos` | — | [{<br>&nbsp;&nbsp;"id": 3,<br>&nbsp;&nbsp;"dataEmprestimo": "2026-04-25",<br>&nbsp;&nbsp;"dataDevolucao": null,<br>&nbsp;&nbsp;"livro": {<br>&nbsp;&nbsp;&nbsp;&nbsp;"id": 1,<br>&nbsp;&nbsp;&nbsp;&nbsp;"titulo": "teste Livro",<br>&nbsp;&nbsp;&nbsp;&nbsp;"autor": "2312",<br>&nbsp;&nbsp;&nbsp;&nbsp;"isbn": "31231",<br>&nbsp;&nbsp;&nbsp;&nbsp;"disponivel": false<br>&nbsp;&nbsp;},<br>&nbsp;&nbsp;"usuario": {<br>&nbsp;&nbsp;&nbsp;&nbsp;"id": 2,<br>&nbsp;&nbsp;&nbsp;&nbsp;"nome": "Fulano",<br>&nbsp;&nbsp;&nbsp;&nbsp;"email": "FulanoTeste@gmail.com"<br>&nbsp;&nbsp;}<br>}] | Lista todos os empréstimos |
| POST | `/api/emprestimos` | {<br>&nbsp;&nbsp;"usuarioId": 2,<br>&nbsp;&nbsp;"livroId": 1<br>} | {<br>&nbsp;&nbsp;"id": 3,<br>&nbsp;&nbsp;"dataEmprestimo": "2026-04-25",<br>&nbsp;&nbsp;"dataDevolucao": null,<br>&nbsp;&nbsp;"livro": {<br>&nbsp;&nbsp;&nbsp;&nbsp;"id": 1,<br>&nbsp;&nbsp;&nbsp;&nbsp;"titulo": "teste Livro",<br>&nbsp;&nbsp;&nbsp;&nbsp;"autor": "2312",<br>&nbsp;&nbsp;&nbsp;&nbsp;"isbn": "31231",<br>&nbsp;&nbsp;&nbsp;&nbsp;"disponivel": false<br>&nbsp;&nbsp;},<br>&nbsp;&nbsp;"usuario": {<br>&nbsp;&nbsp;&nbsp;&nbsp;"id": 2,<br>&nbsp;&nbsp;&nbsp;&nbsp;"nome": "Fulano",<br>&nbsp;&nbsp;&nbsp;&nbsp;"email": "FulanoTeste@gmail.com"<br>&nbsp;&nbsp;}<br>} | Realiza empréstimo |
| PATCH | `/api/emprestimos/{id}/devolver` | — | {<br>&nbsp;&nbsp;"id": 3,<br>&nbsp;&nbsp;"dataEmprestimo": "2026-04-25",<br>&nbsp;&nbsp;"dataDevolucao": "2026-04-30",<br>&nbsp;&nbsp;"livro": {<br>&nbsp;&nbsp;&nbsp;&nbsp;"id": 1,<br>&nbsp;&nbsp;&nbsp;&nbsp;"titulo": "teste Livro",<br>&nbsp;&nbsp;&nbsp;&nbsp;"disponivel": true<br>&nbsp;&nbsp;}<br>} | Registra devolução |

---

Feito por: Vitor Domingos, Kauê Dylon e Luiz Felipe

---
