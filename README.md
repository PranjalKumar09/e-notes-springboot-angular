# E-Notes Application

E-Notes is a secure, scalable, and feature-rich digital note-taking application designed to streamline the process of creating, organizing, and managing notes. Built using Spring Boot for the backend and Angular/React for the frontend, it incorporates modern technologies for authentication, caching, scheduling, and performance optimization.

---

## 🚀 Features  
- **Secure Authentication**: OAuth2 login for enhanced security.  
- **Aspect-Oriented Programming (AOP)**: Modular logging and security handling.  
- **Performance Optimization**: Caching with Spring Boot for faster response times.  
- **Task Scheduling**: Automated processes using Spring Scheduler.  
- **API Documentation**: Interactive Swagger API documentation.  
- **Testing and QA**: JUnit, Mockito, and JMeter integration for testing and performance analysis.  
- **Containerization**: Docker support for streamlined deployment.  
- **Deployment**: Fully deployable on AWS.  

---

## 🛠️ Technology Stack

### Backend
- **Spring Boot**: REST API, JPA, Actuator, AOP, Security, Caching  
- **Security**: OAuth2 and Spring Security  
- **Testing**: JUnit, Mockito, SonarQube, JMeter  
- **API Documentation**: Swagger  
- **Containerization**: Docker  

### Frontend
- **Framework**: Angular or React  
- **Design**: Figma  

### Database
- **MySQL**: Reliable and efficient relational database.  

### Tools
- **Development**: Spring Tool Suite (STS), Visual Studio Code  
- **Version Control**: GitHub  
- **Design**: Figma  

### Deployment
- **Server**: AWS  

---

## 📂 Project Structure  

```
E-Notes/
├── backend/
│   ├── src/main/java/com/enotes/  # Backend code
│   ├── src/test/java/com/enotes/  # Test cases
│   ├── resources/                 # Configuration files
│   └── Dockerfile                 # Docker configuration for backend
├── frontend/
│   ├── src/                       # Frontend code
│   ├── public/                    # Static files
│   └── package.json               # Node dependencies
├── docker-compose.yml             # Container orchestration
├── README.md                      # Project documentation
└── .gitignore                     # Ignored files and directories
```

---

## 🔧 Installation and Setup  

### Prerequisites  
- **Java 17+**  
- **Node.js 16+ and npm**  
- **MySQL Server**  
- **Docker**  

### Backend Setup  
1. Clone the repository:  
   ```bash
   git clone https://github.com/your-username/e-notes.git
   cd e-notes/backend
   ```
2. Configure MySQL credentials in `application.properties`.  
3. Build and run the application:  
   ```bash
   ./mvnw spring-boot:run
   ```

### Frontend Setup  
1. Navigate to the frontend directory:  
   ```bash
   cd e-notes/frontend
   ```
2. Install dependencies:  
   ```bash
   npm install
   ```
3. Start the development server:  
   ```bash
   npm start
   ```

### Docker Setup  
1. Build and start the containers:  
   ```bash
   docker-compose up --build
   ```

---

## 📘 API Documentation  
The API is documented using Swagger. Once the backend is running, visit:  
`http://localhost:8080/swagger-ui.html`

---

## 🧪 Testing  
- **Unit Tests**: Run JUnit tests using Maven:  
  ```bash
  ./mvnw test
  ```  
- **Performance Testing**: Use JMeter scripts for load testing.

---

## 🌐 Deployment  
1. Build the Docker images for the backend and frontend.  
2. Push the images to a container registry (e.g., AWS ECR).  
3. Deploy the containers on AWS ECS or EC2.  

---

## 🤝 Contribution  
1. Fork the repository.  
2. Create a feature branch:  
   ```bash
   git checkout -b https://github.com/PranjalKumar09/e-notes-springboot-angular
   ```
3. Commit your changes and open a pull request.  

---

## 📜 License  
This project is licensed under the [MIT License](LICENSE).

---

## 📧 Contact  
For any queries or support, reach out to **[coderkumarshukla@gmail.com](mailto:coderkumarshukla@gmail.com)**.

