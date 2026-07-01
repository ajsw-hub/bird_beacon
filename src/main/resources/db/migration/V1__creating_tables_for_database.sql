CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100),
    email VARCHAR(100),
    enabled BOOLEAN
);

INSERT INTO users (username, email, enabled) VALUES
('super_cooper','super@cooper.com', TRUE),
('test','test@test.com', TRUE);

CREATE TABLE posts(
    id BIGSERIAL PRIMARY KEY,
    content VARCHAR(300),
    posterId BIGINT NOT NUll REFERENCES users(id) ON UPDATE CASCADE
);