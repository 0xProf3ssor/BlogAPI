# BlogAPI

**BlogAPI** is a backend application for a blogging platform, built using Java and Spring Boot. This API supports user management, post creation, comment handling, and more.

## Features

- User registration and authentication (JWT-based authentication)
- CRUD operations for blog posts
- Add and manage post images
- Commenting system for posts
- Pagination support for retrieving users, posts, and comments

## Technologies Used

- **Java**: Programming language
- **Spring Boot**: Framework for building the backend API
- **ModelMapper**: For object mapping
- **Spring Security**: For authentication and authorization
- **JPA (Hibernate)**: For database interaction
- **MySQL/PostgreSQL**: Database (configurable)

## Installation

### Clone the repository

```bash
git clone https://github.com/0xProf3ssor/BlogAPI.git
```

### Navigate to the project directory

```bash
cd BlogAPI
```

### Build the project

```bash
mvn clean install
```

### Run the application

```bash
mvn spring-boot:run
```

## API Endpoints

### User Endpoints

- `POST /api/v1/register`: Register a new user
- `GET /api/v1/users`: Get all users with pagination
- `GET /api/v1/users/{id}`: Get a single user by ID
- `DELETE /api/v1/users/{id}`: Delete a user by ID

### Post Endpoints

- `GET /api/v1/posts`: Get all posts with pagination
- `POST /api/v1/posts`: Create a new post (with optional images)
- `PUT /api/v1/posts/{id}`: Update a post by ID
- `PUT /api/v1/posts/{id}/add-image`: Add images to a post
- `DELETE /api/v1/posts/{postId}/images/{imgId}`: Delete an image from a post
- `DELETE /api/v1/posts/{id}`: Delete a post by ID

### Comment Endpoints

- `POST /api/v1/posts/{postId}/comments`: Add a comment to a post
- `GET /api/v1/posts/{postId}/comments`: Get all comments for a post with pagination
- `PUT /api/v1/comments/{id}`: Update a comment by ID
- `DELETE /api/v1/comments/{id}`: Delete a comment by ID

### Authentication Endpoints

- `POST /api/v1/login`: Login and obtain a JWT token

## Contributing

To contribute to this project:

1. Fork the repository.
2. Create a new feature branch:

   ```bash
   git checkout -b feature-name
   ```

3. Make your changes and commit:

   ```bash
   git commit -m 'Add feature'
   ```

4. Push to your branch:

   ```bash
   git push origin feature-name
   ```

5. Open a Pull Request.
