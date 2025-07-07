# 🍿 Popcorn Pal

Welcome to **Popcorn Pal**, a modern movie review and rating platform built to connect movie enthusiasts with their favorite films. Powered by **Spring Boot** on the backend and integrated with **The Movie Database (TMDB) API**, Popcorn Pal allows users to browse trending, popular, top-rated, and upcoming movies, add films to a personal watchlist, and share reviews with sentiment analysis. With secure user authentication via **JWT**, Popcorn Pal provides a seamless and engaging experience for cinephiles.

---

## 📖 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [Setup and Installation](#-setup-and-installation)
- [API Endpoints](#-api-endpoints)
- [Database Schema](#-database-schema)
- [Authentication](#-authentication)
- [Sentiment Analysis](#-sentiment-analysis)
- [Contributing](#-contributing)
- [License](#-license)

---

## 🎥 Features

- **Movie Discovery**: Browse trending, now playing, popular, top-rated, and upcoming movies using the TMDB API.
- **Search Functionality**: Search for movies with pagination support for a streamlined experience.
- **User Management**: Register, log in, and manage user profiles with secure JWT-based authentication.
- **Watchlist**: Add or remove movies from a personalized watchlist, enriched with movie details like title and poster.
- **Reviews with Sentiment Analysis**: Write, view, and delete movie reviews, with sentiment analysis powered by an n8n webhook.
- **Secure APIs**: Protected endpoints requiring authentication for user-specific actions.
- **CORS Support**: Configured to allow seamless communication with a React frontend running on `http://localhost:5173`.

---

## 🛠 Tech Stack

- **Backend**: Spring Boot 3.3.3 (Java 21)
- **Database**: PostgreSQL
- **Authentication**: Spring Security with JWT (JSON Web Tokens)
- **API Client**: RestTemplate for TMDB API integration
- **ORM**: Spring Data JPA with Hibernate
- **Logging**: SLF4J with Logback
- **Dependencies**:
  - `spring-boot-starter-web`
  - `spring-boot-starter-data-jpa`
  - `spring-boot-starter-security`
  - `jjwt` (JWT handling)
  - `postgresql` (Database driver)
  - `lombok` (Code simplification)
  - `spring-boot-starter-validation` (Input validation)

---

## 📁 Project Structure

The backend is organized into packages for clarity and maintainability:

```
com.edwin.Popcorn_Pal
├── config
│   ├── JwtAuthenticationFilter.java  # JWT token validation filter
│   ├── SecurityConfig.java           # Spring Security configuration
│   └── WebConfig.java                # CORS configuration for frontend
├── controller
│   ├── movieController.java          # Handles movie-related API requests
│   ├── ReviewController.java         # Manages review CRUD operations
│   ├── userController.java           # User registration, login, and profile
│   └── WatchlistController.java      # Watchlist management
├── model
│   ├── Review.java                   # Review entity
│   ├── User.java                     # User entity
│   └── Watchlist.java                # Watchlist entity
├── repository
│   ├── ReviewRepository.java         # JPA repository for reviews
│   ├── UserRepository.java           # JPA repository for users
│   └── WatchlistRepository.java      # JPA repository for watchlist
├── service
│   ├── movieService.java             # Movie data fetching from TMDB
│   ├── ReviewService.java            # Review management with sentiment analysis
│   ├── userService.java              # User authentication and management
│   └── WatchlistService.java         # Watchlist operations
├── PopcornPalApplication.java        # Main Spring Boot application
├── pom.xml                           # Maven dependencies
└── application.properties            # Configuration properties
```

---

## 🚀 Setup and Installation

### Prerequisites
- Java 21
- Maven 3.8+
- PostgreSQL 13+
- TMDB API Key (get one from [TMDB](https://www.themoviedb.org/))
- n8n instance for sentiment analysis (optional, defaults to "Neutral" if unavailable)

### Steps
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/<your-username>/Popcorn-Pal.git
   cd Popcorn-Pal
   ```

2. **Configure the Database**:
   - Create a PostgreSQL database named `popcorn_pal`.
   - Update `application.properties` with your database credentials:
     ```properties
     spring.datasource.url=jdbc:postgresql://localhost:5432/popcorn_pal
     spring.datasource.username=your-username
     spring.datasource.password=your-password
     ```

3. **Add TMDB API Key**:
   - Add your TMDB API key to `application.properties`:
     ```properties
     tmdb.api.key=your-tmdb-api-key
     ```

4. **Build and Run**:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

5. **Access the API**:
   - The backend runs on `http://localhost:8080`.
   - Test endpoints using tools like Postman or integrate with a frontend (e.g., React on `http://localhost:5173`).

---

## 🛠 API Endpoints

### User Management
| Endpoint                | Method | Description                       | Authentication |
|-------------------------|--------|-----------------------------------|----------------|
| `/api/users/register`   | POST   | Register a new user               | None           |
| `/api/users/login`      | POST   | Log in and receive JWT token      | None           |
| `/api/users/me`         | GET    | Get current user details          | JWT            |
| `/api/users/{username}` | GET    | Get user by username              | None           |
| `/api/users/{userId}`   | DELETE | Delete a user by ID               | None           |

### Movie Endpoints
| Endpoint                         | Method | Description                          | Authentication |
|----------------------------------|--------|--------------------------------------|----------------|
| `/api/movies/trending`           | GET    | Get trending movies                 | None           |
| `/api/movies/details/{movieId}`  | GET    | Get movie details by TMDB ID        | None           |
| `/api/movies/now-playing`        | GET    | Get now playing movies              | None           |
| `/api/movies/popular`            | GET    | Get popular movies                  | None           |
| `/api/movies/top-rated`          | GET    | Get top-rated movies                | None           |
| `/api/movies/upcoming`           | GET    | Get upcoming movies                 | None           |
| `/api/movies/search?query={q}`   | GET    | Search movies by query with paging  | None           |

### Review Endpoints
| Endpoint                         | Method | Description                          | Authentication |
|----------------------------------|--------|--------------------------------------|----------------|
| `/api/reviews`                   | POST   | Add a new review                    | JWT            |
| `/api/reviews/movie/{movieId}`   | GET    | Get reviews for a movie (paged)     | None           |
| `/api/reviews/{reviewId}`        | DELETE | Delete a review by ID               | JWT            |

### Watchlist Endpoints
| Endpoint                         | Method | Description                          | Authentication |
|----------------------------------|--------|--------------------------------------|----------------|
| `/api/watchlist`                 | POST   | Add movie to watchlist              | JWT            |
| `/api/watchlist`                 | GET    | Get user's watchlist                | JWT            |
| `/api/watchlist/check/{movieId}` | GET    | Check if movie is in watchlist      | JWT            |
| `/api/watchlist/{watchlistId}`   | DELETE | Remove movie from watchlist         | JWT            |

---

## 🗄 Database Schema

The application uses PostgreSQL with three main tables:

### `users`
| Column       | Type         | Description                     |
|--------------|--------------|---------------------------------|
| `userId`     | UUID         | Primary key                     |
| `name`       | VARCHAR      | User's full name                |
| `username`   | VARCHAR      | Unique username                 |
| `email`      | VARCHAR      | Unique email address            |
| `password`   | VARCHAR      | Hashed password (BCrypt)        |
| `createdAt`  | TIMESTAMP    | Account creation timestamp      |

### `reviews`
| Column         | Type         | Description                     |
|----------------|--------------|---------------------------------|
| `reviewId`     | UUID         | Primary key                     |
| `tmdb_movie_id`| BIGINT       | TMDB movie ID                   |
| `username`     | VARCHAR      | Reviewing user's username       |
| `rating`       | DOUBLE       | Rating (e.g., 7.5)              |
| `review_text`  | VARCHAR(1000)| Review content                  |
| `created_at`   | TIMESTAMP    | Review creation timestamp       |
| `tag`          | VARCHAR      | Sentiment (Positive/Negative/Neutral) |

### `watchlist`
| Column         | Type         | Description                     |
|----------------|--------------|---------------------------------|
| `watchlistId`  | UUID         | Primary key                     |
| `username`     | VARCHAR      | User's username                 |
| `tmdb_movie_id`| BIGINT       | TMDB movie ID                   |
| `added_at`     | TIMESTAMP    | Timestamp when added            |

---

## 🔒 Authentication

Popcorn Pal uses **JWT-based authentication**:
- **Registration**: Users register with a username, email, and password (hashed with BCrypt).
- **Login**: Successful login returns a JWT token, valid for 24 hours.
- **Protected Endpoints**: Endpoints like `/api/users/me`, `/api/watchlist`, and `/api/reviews` require a valid JWT in the `Authorization` header (`Bearer <token>`).
- **Security Filter**: `JwtAuthenticationFilter` validates tokens and sets the security context for authenticated requests.

> **Note**: The JWT secret key in `userService.java` (`SECRET_KEY`) should be replaced with a secure, environment-specific key in production.

---

## 😊 Sentiment Analysis

- **Review Sentiment**: When a user submits a review, the `ReviewService` sends the review text to an n8n webhook (`http://localhost:5678/webhook-test/...`) for sentiment analysis.
- **Response**: The webhook returns a sentiment tag ("Positive", "Negative", or "Neutral"), which is stored in the `tag` column of the `reviews` table.
- **Fallback**: If the webhook fails, the sentiment defaults to "Neutral".

---

## 🤝 Contributing

Contributions are welcome! To contribute:
1. Fork the repository.
2. Create a feature branch (`git checkout -b feature/YourFeature`).
3. Commit your changes (`git commit -m 'Add YourFeature'`).
4. Push to the branch (`git push origin feature/YourFeature`).
5. Open a Pull Request.

Please ensure your code follows the project's coding style and includes relevant tests.

---

## 📜 License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

**Popcorn Pal** is your go-to platform for movie exploration and community-driven reviews. Start building your watchlist and sharing your thoughts today! 🎬