-- V1__Create_user_table.sql
CREATE TABLE IF NOT EXISTS discipline (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    faculty_id INTEGER,
    created_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by    varchar(50) not null,
    modified_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_by   varchar(50) not null,
    constraint FK_faculty_id foreign key (faculty_id)
    REFERENCES faculty(id),
    );
