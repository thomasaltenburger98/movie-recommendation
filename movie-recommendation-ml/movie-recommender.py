from flask import Flask, jsonify, request
import pandas as pd
import numpy as np
from sklearn.metrics.pairwise import cosine_similarity

class MovieRecommender:
    def __init__(self, ratings_file, movies_file):
        self.ratings = pd.read_csv(ratings_file)
        self.movies = pd.read_csv(movies_file)
        self.df = pd.merge(self.ratings, self.movies, on='movieId', how='inner')
        self._prepare_data()

    def _prepare_data(self):
        agg_ratings = self.df.groupby('title').agg(mean_rating=('rating', 'mean'), number_of_ratings=('rating', 'count')).reset_index()
        agg_ratings_GT100 = agg_ratings[agg_ratings['number_of_ratings'] > 100]
        self.df_GT100 = pd.merge(self.df, agg_ratings_GT100[['title']], on='title', how='inner')
        self.matrix = self.df_GT100.pivot_table(index='title', columns='userId', values='rating')
        self.matrix_norm = self.matrix.subtract(self.matrix.mean(axis=1), axis=0)
        self.item_similarity = self.matrix_norm.T.corr()

    def recommend(self, picked_userid=1, number_of_similar_items=5, number_of_recommendations=3):
        picked_userid_unwatched = pd.DataFrame(self.matrix_norm[picked_userid].isna()).reset_index()
        picked_userid_unwatched = picked_userid_unwatched[picked_userid_unwatched[picked_userid] == True]['title'].values.tolist()
        picked_userid_watched = pd.DataFrame(self.matrix_norm[picked_userid].dropna(axis=0, how='all').sort_values(ascending=False)).reset_index().rename(columns={picked_userid: 'rating'})
        rating_prediction = {}

        for picked_movie in picked_userid_unwatched:
            picked_movie_similarity_score = self.item_similarity[[picked_movie]].reset_index().rename(columns={picked_movie: 'similarity_score'})
            picked_userid_watched_similarity = pd.merge(left=picked_userid_watched, right=picked_movie_similarity_score, on='title', how='inner').sort_values('similarity_score', ascending=False)[:number_of_similar_items]
            predicted_rating = round(np.average(picked_userid_watched_similarity['rating'], weights=picked_userid_watched_similarity['similarity_score']), 6)
            rating_prediction[picked_movie] = predicted_rating

        return sorted(rating_prediction.items(), key=lambda x: x[1], reverse=True)[:number_of_recommendations]

app = Flask(__name__)
recommender = MovieRecommender('ml-latest-small/ratings.csv', 'ml-latest-small/movies.csv')

@app.route('/recommend', methods=['GET'])
def recommend():
    user_id = int(request.args.get('user_id', 1))
    num_similar_items = 10000
    num_recommendations = int(request.args.get('num_recommendations', 20))
    recommendations = recommender.recommend(picked_userid=user_id, number_of_similar_items=num_similar_items, number_of_recommendations=num_recommendations)
    return jsonify(recommendations)

if __name__ == '__main__':
    app.run(debug=True)
