# Data processing
import pandas as pd
import numpy as np
import scipy.stats
# Visualization
import seaborn as sns
# Similarity
from sklearn.metrics.pairwise import cosine_similarity

# Read in data
ratings=pd.read_csv('ml-latest-small/ratings.csv')
# Take a look at the data
print(ratings.head())


# Read in data
movies = pd.read_csv('ml-latest-small/movies.csv')
# Take a look at the data
print(movies.head())