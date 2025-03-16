# IRCTC - Train Ticket Booking System 🚆🎫

**Technologies Used:**
- **Java** ☕
- **Hibernate** 🛠️
- **Spring Boot** 🚀
- **JWT (JSON Web Token)** 🔐
- **PostgreSQL** 🗃️
- **MongoDB** 🗄️
- **Swagger** 📝

### Overview
The **IRCTC Train Ticket Booking System** is a real-time platform for booking train tickets, checking seat availability, and managing bookings. It includes features such as ticket booking, cancellations, user management, and an admin dashboard. The system uses a modern tech stack to provide a secure, efficient, and user-friendly experience. 🚆💻

### Features
- **Real-time Seat Availability**: Check available seats for trains in real-time. ⏱️
- **JWT Authentication**: Secure login and role-based authorization for different user types (admin, user). 🔒
- **REST APIs**: Built using Spring Boot, allowing ticket booking, cancellations, and user management. 🔌
- **Pagination, Search, and Filters**: Efficient train and ticket lookup with pagination and filtering. 🔍
- **Admin Dashboard**: Manage train schedules, seat availability, and view bookings. 🛠️
- **Swagger**: API documentation for ease of integration and testing. 📑

### Installation ⚙️

#### Prerequisites:
- Java 11 or higher ☕
- PostgreSQL 🗃️
- MongoDB 🗄️
- Maven ⚙️

#### Steps:
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/irctc-train-ticket-booking.git
   cd irctc-train-ticket-booking
   ```
2. Create a PostgreSQL database and configure application.properties with your database credentials.
3. Run MongoDB locally or configure the MongoDB connection in application.properties.
4. Build and run the application:
```
 mvn clean install
 mvn spring-boot:run
```
5. Access the application at http://localhost:8080.

