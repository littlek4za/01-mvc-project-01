# Project Name
Simple Employee Management System

## Description
This system does the following:
* A simple log in system for employee
* A table to show current employee list
* Support Employee Registration
* Support Employee info Update and Delete (only by Admin)
* This is just a starter code and has abiliy to expand the functionality as a employee portal for future usage.

## 
* **Backend:** Java (Spring Boot)
* **Frontend:** HTML, Thymeleaf
* **Database:** MySQL
* **Tools:** MySQL Workbench, IntelliJ IDEA / Eclipse, Maven

## Prerequisites
Make sure the following are installed on your system:
* **Java 17+**
* **Maven 3.6+**
* **MySQL Server and Workbench**

---
## Database Setup
1. Start MySQL Workbench and create a connection to your local MySQL server.
2. Open the provided SQL script, included in `sql/`
3. Run the script to create the `employee_directory` database and relevant tables.

### Edit `application.properties` file
1. Update the database connection settings in the file located at:
`src/main/resources/application.properties`:
2. Use the sample below and replace the values with your own MySQL connection details:
```properties
spring.datasource.url=jdbc:mysql://<your_host>:<your_port>/employee_directory
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
```
 Replace:
* `<your_host>` with your MySQL host (e.g., localhost)
* `<your_port>` with your MySQL port (default is 3306)
* `your_db_username` and `your_db_password` with your actual MySQL credentials
---
## Running the application
1. Clone the Repository
```bash
git clone https://github.com/littlek4za/01-mvc-project-01.git
cd 01-mvc-project-01
```
2. Build the Project with Maven
```bash
mvn clean install
```
3. Run the Spring Boot Application
```bash
mvn spring-boot:run
```

---
## Accessing the Application
Once the app is running, open your browser and visit:
http://localhost:8080

### Log in Info
Default log in info:
1. Admin (ROLE = Admin, Manager, Employee)
* username=admin
* password=admin

2. User1 (ROLE = Manager, Employee)
* username=user1
* password=user1

3. User2 (ROLE = Employee)
* username=user2
* password=user2

4. User3 (ROLE = Employee)
* username=user3
* password=user3

| Role | REST Permissions | MVC Permissions |
|---|---|---|
| Admin | create, request, update, delete | create, request, update, delete |
| Manager | create, request, update | create, request, update |
| Employee | request | create, request |

---
## API Endpoints
This application exposes the following RESTful API endpoints to manage employees and their associated user accounts.


### <ins>Create Employee</ins>
`POST` `/api/employees`

**Usage**: Registers a new employee with user details.

**Request Body:**
```json
{
  "firstName": "Admin",
  "lastName": "Company",
  "email": "admin@testmail.com",
  "userDTO": {
    "userName": "admin",
    "password": "abc123",
    "enable": true,
    "roles": [
      "ROLE_EMPLOYEE",
      "ROLE_MANAGER",
      "ROLE_ADMIN"
    ],
    "password":"test123"
  }
}
```


### <ins>Get All Employees</ins>
`GET` `/api/employees`

**Usage** : Returns a list of all employees.


### <ins>Get Employee by ID</ins>
`GET` `/api/employees/{id}`

**Usage** : Fetch details of a specific employee by ID.


### <ins>Update Entire Employee</ins>
`PUT` `/api/employees`

**Usage** : Updates an existing employee. The full object must be provided.

**Note** : Password changes are not allowed through the API. Password can only be changed via the web page interface.

**Request Body:**

```json
{
  "id": 1,
  "firstName": "Admin",
  "lastName": "Company",
  "email": "admin@testmail.com",
  "userDTO": {
    "userName": "admin",
    "password": "abc123",
    "enable": true,
    "roles": [
      "ROLE_EMPLOYEE",
      "ROLE_MANAGER",
      "ROLE_ADMIN"
    ]
  }
}

```


### <ins>Patch Partial Employee Data</ins>
`PATCH` `/api/employees/{id}`

**Usage** : Updates only specific fields of the employee. You may provide only the fields you want to update.

**Note** : Password changes are not allowed through the API. Password can only be changed via the web page interface.
**Request Body:**

```json
{
  "firstName": "Admin",
  "lastName": "Company",
  "email": "admin@testmail.com",
  "userDTO": {
    "userName": "admin",
    "password": "abc123",
    "enable": true,
    "roles": [
      "ROLE_EMPLOYEE",
      "ROLE_MANAGER",
      "ROLE_ADMIN"
    ]
  }
}
```


### <ins>Delete Employee</ins>
`DELETE` `/api/employees/{id}`

**Usage** : Deletes an employee by ID.

**Note** : Admin user cannot be deleted.


