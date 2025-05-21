# 📝 ToDoList – Smart Task Management with Secure Authentication

Welcome to **ToDoList**, a modern Spring Boot-based web application built to help users **organize, manage, and secure** their tasks with simplicity and flexibility. Whether you're a student, professional, or tech enthusiast, this platform offers a streamlined user experience with strong authentication features and future-proof architecture.

---

## 🚀 Project Highlights

✅ **Built with Java + Spring Boot**  
✅ **JWT-Based Authentication and Authorization**  
✅ **OAuth2 Social Login Integration (Google & GitHub)**  
✅ **Secure Refresh Token Storage in Database**  
✅ **Optional Email Verification for New Users**  
✅ **Responsive and Minimalistic Web Interface (Thymeleaf)**  
✅ **Production-ready with PostgreSQL & H2 support**  
✅ **Clean API Documentation with SwaggerUI/OpenAPI**

---

## 🎯 Objectives

This project was developed as part of a final university assignment to demonstrate:
- Practical implementation of secure authentication methods in Java.
- Clean software architecture using modern Spring technologies.
- Real-world full-stack features such as user onboarding and authorization.
- Clean Git-based version control and documented development flow.

---

## 🧩 Core Features

### 👤 User Registration & Login
- Traditional email/password registration.
- Secure password hashing using `BCryptPasswordEncoder`.
- Optional email verification with secure token links.

### 🔐 JWT Authentication
- Access and Refresh token generation.
- Custom token lifetime configuration.
- Secure token parsing, validation, and username extraction.
- Refresh tokens are stored and revoked securely in the database.

### 🌐 Social Login
- Sign in via Google or GitHub using Spring Security OAuth2.
- Automatic user creation on first login.
- Custom user principal mapping for consistent user experience.

### 📦 API Endpoints (Protected & Public)
- `/api/auth/register`, `/api/auth/login`, `/api/auth/refresh`
- `/api/tasks` – Protected task CRUD operations.
- Swagger documentation available at `/swagger-ui/index.html`

---

## 🛡️ Security Enhancements

- **Stateless JWT session model** (no HTTP sessions).
- **Refresh Token Revocation** on logout or token expiry.
- **@PreAuthorize annotations** for role-based access control.
- Optional features for future:
    - 2FA via Email/SMS
    - Rate limiting per IP
    - IP blacklisting/whitelisting

---

## 📚 Tech Stack

| Layer | Technology |
|-------|-|
| Backend | Java 21, Spring Boot 3 |
| Security | Spring Security, JWT, OAuth2 |
| Persistence | PostgreSQL (prod), Spring Data JPA |
| Dev Tools | SwaggerUI,Git |
| Build Tool | Maven |
| Testing | JUnit 5, Mockito |

