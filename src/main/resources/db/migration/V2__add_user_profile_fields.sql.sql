ALTER TABLE users
ADD COLUMN profilePicture TEXT NOT NULL DEFAULT 'default-profile-pic.jpg',
ADD COLUMN bio VARCHAR(500),
ADD COLUMN dateOfBirth DATE;
