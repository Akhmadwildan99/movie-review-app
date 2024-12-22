CREATE TABLE IF NOT EXISTS movie_reviews (
    id BIGSERIAL PRIMARY KEY  NOT NULL,
    content TEXT,
    rating int ,
    created_at TIMESTAMP WITHOUT TIME ZONE ,
    updated_at TIMESTAMP WITHOUT TIME ZONE ,
    user_id BIGINT,
    movie_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (movie_id) REFERENCES movies(id)
);