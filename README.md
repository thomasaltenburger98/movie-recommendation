# Movie Recommendation App

The Movie Recommendation App is a web application that provides personalized movie recommendations based on user preferences. The app utilizes a combination of a Spring Boot backend, an Angular frontend, and a Python machine learning script for recommendation generation.

---

## Current functionality

When the user opens the main page, they receive an ID which is saved for the session.
Using their ID, the user can rate several movies that are displayed on the page.
In the future, users can also view their movie ratings from the past and receive recommended movies based on their ratings.
---

## Features

- User Registration: Users can create an account to access personalized movie recommendations. 
- User Authentication: Secure user authentication is implemented to protect user accounts. 
- Movie Search: Users can search for movies by title
- Movie Ratings: Users can rate movies. 
- Past movie ratings: Users can see all rated movies. 
- Watch-List
- Recommendation Engine: The app utilizes a machine learning algorithm to suggest movies based on user preferences and ratings. (User-based collaborative filtering)

## Technologies Used

- Spring Boot for the main backend service (REST API)
- Angular: A TypeScript-based web application framework used for creating the interactive frontend user interface.
- Postgresql
- Python
- Pandas: A data manipulation library in Python used for reading and processing movie data.
- Scikit-learn: A machine learning library in Python used for calculating similarity scores and generating movie recommendations.

## Machine Learning Script

The Python machine learning script is responsible for generating movie recommendations based on user preferences and ratings. Here's an overview of its functionality:

1. Data Processing: The script uses the Pandas library to read movie ratings and movie data from CSV files.
2. Data Aggregation: It aggregates the movie ratings by calculating the mean rating and the number of ratings for each movie.
3. Filtering: It keeps only the movies with over 100 ratings to ensure reliable recommendations.
4. User-Item Matrix: The script constructs a user-item matrix, where each row represents a movie and each column represents a user, with ratings as the values.
5. Normalization: The user-item matrix is normalized by subtracting the mean rating of each movie from its ratings.
6. Similarity Calculation: The script calculates the similarity between items (movies) using the Pearson correlation coefficient.
7. Recommendation Generation: Based on a target user's watched movies, the script identifies similar movies and predicts their ratings using a weighted average of similarity scores.
8. Top Recommendations: The script generates a list of top recommended movies for the target user based on the predicted ratings.

The machine learning script communicates with the Spring Boot backend via an additional REST API. 

---
## Setup

A step by step series of examples that tell you how to get this environment running:

1. Requirements: Docker, Java (+ Maven), Node.js, npm
2. Backend: Run `mvn clean install` to build the backend and run the backend
3. Frontend: Run `npm install` to install the frontend dependencies _(Angular and its dependencies)_ and run the frontend with `npm start`

### Database Setup

This project uses PostgreSQL as its database. You can easily set up a PostgreSQL instance using Docker:

1. Install Docker on your machine if you haven't already. You can download it from [here](https://www.docker.com/products/docker-desktop).
2. Run the following command to pull the PostgreSQL image and run it as a Docker container:

```bash
docker run --name movierecommendationdb -p 5455:5432 -e POSTGRES_USER=postgresuser -e POSTGRES_PASSWORD=mysecretpassword POSTGRES_DB=movie-recommendation-db -d postgres
```
Replace "mysecretpassword" with the password you want to set for your PostgreSQL database.
The password should be set in the **application.properties** file in the Spring Boot backend.

```
spring.datasource.url=jdbc:postgresql://localhost:5432/movie-recommendation-db
spring.datasource.username=postgresuser
spring.datasource.password=mysecretpassword
```

### Machine Learning Service Setup

1. Install the required packages: `pip install flask pandas numpy scikit-learn`
2. Run the machine learning service: `python movie-recommender.py`
3. Ensure that the port 5000 is used for the machine learning service. If you want to change the port, you need to update the URL in the Spring Boot backend.

---

## Contribution Guidelines

Contributions to the Movie Recommendation App are welcome!

## License

This project is licensed under the MIT License. Feel free to use, modify, and distribute the code as per the terms of the license.

## Contact

If you have any questions or suggestions regarding the Movie Recommendation App, please feel free to contact me at thomas.altenburger@hotmail.com.

---
