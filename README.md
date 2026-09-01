# 🏨 Grand Horizon - Hotel Billing & Management System

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.3-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17%20%2F%2021-orange.svg)](https://www.oracle.com/java/)
[![Currency](https://img.shields.io/badge/Currency-INR%20(%E2%82%B9)-blue.svg)](#)
[![Docker](https://img.shields.io/badge/Docker-Multi--stage-blue.svg)](https://www.docker.com/)
[![GitHub Actions CI/CD](https://img.shields.io/badge/CI%2FCD-GitHub%20Actions-2088FF.svg)](https://github.com/features/actions)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

A containerized, production-ready **Hotel Billing and Management System** built with **Spring Boot 3**, **Spring Data JPA**, **Thymeleaf**, and **Docker**, fully localized with Indian Rupee (₹) currency. Automated with a **GitHub Actions CI/CD pipeline** that runs automated unit/integration tests with Maven, builds multi-stage Docker images, applies `latest` and Git commit SHA tags, and pushes images directly to Docker Hub upon every push to the `main` branch.

---

## 📑 Table of Contents

- [Key Features](#-key-features)
- [System Architecture](#-system-architecture)
- [Tech Stack](#-tech-stack)
- [Project Directory Structure](#-project-directory-structure)
- [Getting Started Locally](#-getting-started-locally)
- [Containerization with Docker](#-containerization-with-docker)
  - [Build and Run with Docker](#1-build-and-run-with-docker)
  - [Run with Docker Compose](#2-run-with-docker-compose-spring-boot--postgresql)
- [GitHub Actions CI/CD Pipeline](#-github-actions-cicd-pipeline)
- [REST API Reference](#-rest-api-reference)
- [License](#-license)

---

## 🌟 Key Features

### 🛎️ 1. Room & Guest Management
- Inventory of rooms across different tiers (**Standard Room ₹2,499**, **Deluxe Room ₹4,999**, **Executive Suite ₹8,999**, **Presidential Penthouse ₹19,999**).
- Real-time room status tracking: `AVAILABLE`, `OCCUPIED`, `CLEANING`, `MAINTENANCE`.
- Guest registration profiles with ID verification (Aadhaar Card, Passport, Driver License).

### 🧾 2. Dynamic Billing & Folio Engine (₹ INR)
- Automatic room charge calculation based on check-in and check-out dates.
- Itemized billing for hotel services (**Room Service Dining**, **Beverages**, **Laundry**, **Ayurvedic Spa**, **Airport Chauffeur Transfer**).
- Configurable **GST Tax** engine (default 12%).
- Promotional and loyalty **Discount percentage** deduction.
- Calculation: $\text{Net Total} = \text{Room Charge} + \text{Services} + \text{GST Tax} - \text{Discount}$.

### 📄 3. Printable / PDF-Ready Invoices
- Professional tax invoice template with hotel branding, itemized breakdown in Rupees (`₹`), tax details, payment mode, and authorized signature section.
- Native browser print styling (`@media print`) for 1-click PDF download or physical printing.

### 💳 4. Flexible Settlement & Payment Modes
- Instant checkout payment processing (`UPI`, `CREDIT_CARD`, `CASH`, `NET_BANKING`, `CORPORATE_BILLING`).
- Auto-releases room back to `AVAILABLE` status upon full bill settlement.

---

## 🚀 Getting Started Locally

```bash
# Clone the repository
git clone https://github.com/vinay0716yadav-ops/hotel-billing-system.git
cd hotel-billing-system

# Run tests and start application
mvn spring-boot:run
```

- **Web Dashboard**: [http://localhost:8080/](http://localhost:8080/)
- **Billing Center**: [http://localhost:8080/billing](http://localhost:8080/billing)
- **H2 Database Console**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console) (JDBC URL: `jdbc:h2:mem:hoteldb`, User: `sa`, Password: *blank*)
- **Health Actuator**: [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)

---

## 🐳 Containerization with Docker

```bash
# Build Docker image
docker build -t hotel-billing-system:latest .

# Run standalone container
docker run -d -p 8080:8080 --name hotel-billing-app hotel-billing-system:latest

# Or run with Docker Compose (with PostgreSQL)
docker-compose up -d --build
```

---

## 🔄 GitHub Actions CI/CD Pipeline

The repository contains a fully automated CI/CD pipeline in [`.github/workflows/ci-cd.yml`](.github/workflows/ci-cd.yml).

### Setting Up Docker Hub Credentials in GitHub Secrets

1. **Docker Hub Access Token**:
   - Go to [Docker Hub Account Security](https://hub.docker.com/) $\rightarrow$ **New Access Token** (Read, Write, Delete).
2. **Add GitHub Secrets**:
   - Go to repository **Settings** $\rightarrow$ **Secrets and variables** $\rightarrow$ **Actions** $\rightarrow$ **New repository secret**:
     - `DOCKERHUB_USERNAME`: Your Docker Hub username
     - `DOCKERHUB_TOKEN`: Your Docker Hub token
3. **Trigger Pipeline**:
   - Any push to `main` branch triggers the build, test, image tag (`latest`, `sha-<commit>`), and push to Docker Hub!

---

## 📡 Sample REST API Payload (`POST /api/v1/bills/generate`)

```json
{
  "bookingId": 1,
  "items": [
    {
      "itemName": "Gourmet Breakfast Buffet",
      "category": "ROOM_SERVICE",
      "quantity": 2,
      "unitPrice": 450.00
    },
    {
      "itemName": "Airport Luxury Chauffeur Transfer",
      "category": "TRANSPORT",
      "quantity": 1,
      "unitPrice": 1800.00
    }
  ],
  "customTaxPercentage": 12.0,
  "discountPercentage": 5.0,
  "paymentStatus": "PAID",
  "paymentMethod": "UPI",
  "paymentTransactionId": "UPI-8839201",
  "notes": "Settled via GooglePay UPI at check-out."
}
```

---

## 📄 License
This project is licensed under the MIT License.
