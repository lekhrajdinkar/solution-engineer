CREATE DATABASE retailappdb;
-- \c retailappdb;
CREATE SCHEMA retailapp;

-- ====================================
-- Create user : lekhraj_r
-- ====================================
CREATE USER lekhraj_r WITH PASSWORD 'lekhraj#21';
-- Grants
GRANT CONNECT ON DATABASE retailappdb TO lekhraj_r;
GRANT USAGE ON SCHEMA retailapp TO lekhraj_r;
GRANT SELECT ON ALL TABLES IN SCHEMA retailapp TO lekhraj_r;
-- To ensure future tables are accessible
ALTER DEFAULT PRIVILEGES IN SCHEMA retailapp GRANT SELECT ON TABLES TO lekhraj_r;

-- ====================================
-- Create user : lekhraj_rwx
-- ====================================
CREATE USER lekhraj_rwx WITH PASSWORD 'lekhraj#21';
-- Grants
GRANT CONNECT ON DATABASE retailappdb TO lekhraj_rwx;
GRANT ALL PRIVILEGES ON SCHEMA retailapp TO lekhraj_rwx;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA retailapp TO lekhraj_rwx;
-- To ensure future tables are accessible
ALTER DEFAULT PRIVILEGES IN SCHEMA retailapp GRANT ALL PRIVILEGES ON TABLES TO lekhraj_rwx;