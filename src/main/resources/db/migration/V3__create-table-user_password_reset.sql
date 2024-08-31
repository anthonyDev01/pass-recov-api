CREATE TABLE user_password_reset(
    id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    password_code VARCHAR(6) NOT NULL,
    password_expiry DATETIME NOT NULL,
    constraint fk_user_id FOREIGN KEY(user_id) REFERENCES usuarios(id),
    PRIMARY KEY(id)
);