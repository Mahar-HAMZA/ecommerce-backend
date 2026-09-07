# 🛒 E-Commerce REST API Backend Service

![Java](https://img.shields.io/badge/Language-Java_17+-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Framework-Spring_Boot_3.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/ORM-Spring_Data_JPA-59666C?style=for-the-badge)
![Database](https://img.shields.io/badge/Database-MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Tools](https://img.shields.io/badge/API_Testing-Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
![Status](https://img.shields.io/badge/Project_Status-In_Progress_(~43%25)-yellow?style=for-the-badge)

---

## 📌 Project Overview

This is a production-ready, scalable **E-Commerce Backend REST API** built using **Java** and **Spring Boot**. The system follows industry-standard software design practices, featuring a **3-Tier Architecture**, **Package-by-Feature** modular organization, **DTO-based Request/Response mapping**, **Global Exception Handling**, and strict **Data Validation**.

---

## 🏗️ System Architecture & Layering

The project strictly follows a **3-Tier Separation of Concerns** model to keep business logic isolated from HTTP transport and database layers:

```text
       ┌────────────────────────┐
       │     Client / Postman   │
       └───────────┬────────────┘
                   │ HTTP Requests (JSON)
                   v
       ┌────────────────────────┐
       │   Controller Layer     │ ──> Handles Endpoints & DTO Validation
       └───────────┬────────────┘
                   │ Internal DTO Transfer
                   v
       ┌────────────────────────┐
       │    Service Layer       │ ──> Core Business Rules & Transactions
       └───────────┬────────────┘
                   │ Domain Entities
                   v
       ┌────────────────────────┐
       │    Repository Layer    │ ──> Spring Data JPA / Hibernate Queries
       └───────────┬────────────┘
                   │ SQL Execution
                   v
       ┌────────────────────────┐
       │     MySQL Database     │ ──> Relational Storage
       └────────────────────────┘
