🛒 ShopEZ – Full Stack E-Commerce Application

ShopEZ is a full-stack e-commerce web application built using Spring Boot for the backend and Angular for the frontend.
The project focuses on real-world backend concepts such as REST APIs, JWT-based authentication, role-based authorization, and database-driven business logic.

🚀 Features
👤 User Features

User registration & login (JWT authentication)

Browse products by category

View product details

Add/remove items from cart

Place orders

View order history

🛠️ Admin Features

Admin login with role-based access

Add, update, and delete products

Manage product categories

View all user orders

🧱 Tech Stack
Backend

Java

Spring Boot

Spring Security (JWT)

Spring Data JPA

Hibernate

MySQL

RESTful APIs

Frontend

Angular

TypeScript

HTML / CSS

Bootstrap

Tools & Others

Maven

Git & GitHub

Postman (API testing)

🔐 Authentication & Security

JWT-based authentication

Role-based authorization (USER, ADMIN)

Secure API endpoints using Spring Security

Password encryption using BCrypt

🗂️ Project Structure (Backend)
src/main/java/com/shopez
├── controller
├── service
├── repository
├── model
├── security
├── dto
└── config

🗄️ Database Design

User

Role

Product

Category

Cart

Order

OrderItem

(Relationships handled using JPA annotations)


ng serve
