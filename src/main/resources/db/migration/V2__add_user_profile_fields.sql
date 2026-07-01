ALTER TABLE users
ADD COLUMN profilepicture TEXT NOT NULL DEFAULT 'default-profile-pic.jpg',
ADD COLUMN bio VARCHAR(500),
ADD COLUMN dateofbirth DATE;