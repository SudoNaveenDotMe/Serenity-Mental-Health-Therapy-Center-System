# Supermarket System - Layered Architecture

A JavaFX application demonstrating a layered architecture for a simple supermarket management system. This project is developed as part of the curriculum for the **Graduate Diploma in Software Engineering (GDSE)** course for the GDSE-74 batch at IJSE.

---

## 📋 Features

* **Customer Management:** Add, update, delete, and view customer details.
* **Item Management:** Manage product inventory. *(Planned)*
* **Order Processing:** Create and manage customer orders. *(Planned)*

---

## 🏛️ Architecture

This project strictly follows a layered architecture to separate concerns and improve maintainability.

* **Controller Layer:** Manages the UI (JavaFX) and user interactions.
* **Service (BO) Layer:** Handles all business logic and orchestrates data flow.
* **DAO Layer:** Responsible for all data access operations, abstracting the database from the rest of the application.
* **DTO Layer:** Data Transfer Objects used to carry data between layers.
* **Factory Pattern:** A `DAOFactory` is used to provide the necessary DAO implementations to the service layer, promoting loose coupling.

---

## 🛠️ Technologies Used

* **Java / JavaFX:** For the core application and user interface.
* **JDBC:** For database connectivity.
* **MySQL:** As the backend database.
* **Git & GitHub:** For version control.

---

## 🚀 How to Run

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/shamodhas-ijse-teaching/supermarket-layered-architecture-ad1-gdse-74.git](https://github.com/shamodhas-ijse-teaching/supermarket-layered-architecture-ad1-gdse-74.git)
    ```
2.  **Database Setup:** Import the provided `.sql` file into your MySQL database to create the necessary tables.
3.  **Configure DB Connection:** Update the database username and password in the configuration file.
4.  **Build & Run:** Open the project in your IDE (e.g., IntelliJ IDEA) and run the `AppInitializer.java` file.
