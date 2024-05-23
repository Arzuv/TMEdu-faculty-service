-- V1__Create_user_table.sql
CREATE TABLE IF NOT EXISTS faculty (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    created_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by    varchar(50) not null,
    modified_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_by   varchar(50) not null
);
