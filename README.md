# Service Manager API

A Spring Boot application for managing Swisscom services, resources, and owners.

## Project Structure
## API Endpoints

### Service Controller (`/api/v1/service`)
- `POST /` - Create a new service
- `GET /paginated` - Get paginated list of services
- `GET /` - Get all services
- `GET /{id}` - Get service by ID
- `DELETE /{id}` - Delete service
- `POST /registerFull` - Register complete service with resources and owners

### Resource Controller (`/api/v1/resource`)
- `POST /` - Create a new resource
- `GET /by-service/{serviceId}/paginated` - Get paginated resources by service ID
- `GET /by-service/{serviceId}` - Get all resources by service ID
- `DELETE /{id}` - Delete resource

### Owner Controller (`/api/v1/owner`)
- `POST /` - Create a new owner
- `GET /by-resource/{resourceId}/paginated` - Get paginated owners by resource ID
- `GET /by-resource/{id}` - Get all owners by resource ID
- `GET /{id}` - Get owner by ID
- `PUT /` - Update owner
- `DELETE /{id}` - Delete owner

## Current Optimizations
- Pagination support for large datasets
- CORS configuration for cross-origin requests
- Response Entity handling for proper HTTP status codes
- Validation using Jakarta Validation
- Lombok for reducing boilerplate code

## Potential Improvements
1. **Documentation**
    - Add Swagger/OpenAPI documentation
    - Add detailed API documentation

2. **Security**
    - Implement authentication and authorization
    - Add JWT token support

3. **Code Quality**
    - Implement AOP for logging and monitoring
    - Add more unit and integration tests
    - Implement request/response DTOs

4. **Deployment**
    - Add Docker support
    - Create Kubernetes configurations
    - Add CI/CD pipeline

5. **Monitoring**
    - Add health check endpoints
    - Implement metrics collection
    - Add logging framework

## Requirements
- Java 17 or higher
- Maven 3.6+
- MongoDB (running on default port 27017)

## Running the Application

1. Clone the repository:
```bash
git clone <repository-url>
```
2. Navigate to the project directory:
```bash 
cd service-manager-api
```
3. Give Permission and run script to run application:
```bash
chmod +x run-app.sh
./run-app.sh
````
4. Optional run using Docker Compose
````bash
docker-compose up --build
````
