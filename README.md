# Movie Recommendation App

The Movie Recommendation App is a web application that provides personalized movie recommendations based on user preferences. The app utilizes a combination of a Laravel backend, an Angular frontend, and a Python machine learning script for recommendation generation.

---

## Current functionality

When the user opens the main page, they receive an ID which is saved for the session.
Using their ID, the user can rate several movies that are displayed on the page.
In the future, users can also view their movie ratings from the past and receive recommended movies based on their ratings.
---

## Features

[x] -> not finished yet

- User Registration: Users can create an account to access personalized movie recommendations. [x]
- User Authentication: Secure user authentication is implemented to protect user accounts. [x]
- Movie Search: Users can search for movies by title, genre, or other criteria. 
- Movie Ratings: Users can rate movies. 
- Past movie ratings: Users can see all rated movies. 
- Recommendation Engine: The app utilizes a machine learning algorithm to suggest movies based on user preferences and ratings.
- Genre Filtering: Users can filter movie recommendations based on specific genres.
- User Dashboard: Users have access to a personalized dashboard that displays their ratings, watched movies, and recommended movies.

## Technologies Used

- Laravel: A powerful PHP framework used for building the backend server-side logic and API endpoints.
- Angular: A TypeScript-based web application framework used for creating the interactive frontend user interface.
- SQLite: A relational database management system used to store movie data, user information, and ratings.
- Python: A programming language used for the machine learning script that generates movie recommendations.
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

The machine learning script is integrated into the Laravel backend and is invoked when a user requests movie recommendations.

---

## Contribution Guidelines

Contributions to the Movie Recommendation App are welcome!

## License

This project is licensed under the MIT License. Feel free to use, modify, and distribute the code as per the terms of the license.

## Contact

If you have any questions or suggestions regarding the Movie Recommendation App, please feel free to contact me at thomas.altenburger@hotmail.com.

---
