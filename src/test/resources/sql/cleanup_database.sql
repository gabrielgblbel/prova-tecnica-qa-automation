-- SQL script to cleanup test data from database tables

-- Deleting test users
DELETE FROM users WHERE username LIKE 'test_%';

-- Resetting login attempts
UPDATE users SET login_attempts = 0 WHERE login_attempts > 0;

-- Removing expired sessions
DELETE FROM sessions WHERE expiry_time < NOW();