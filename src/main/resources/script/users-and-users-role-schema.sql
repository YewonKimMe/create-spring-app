USE `create-spring-app`;

CREATE TABLE users
(
    id              BIGINT       NOT NULL,
    user_id         VARCHAR(60)  NOT NULL,
    password        VARCHAR(100) NOT NULL,
    second_password VARCHAR(100) NOT NULL,
    email           VARCHAR(100) NOT NULL,
    created_at      datetime     NOT NULL,
    credit          INT          NOT NULL,
    is_verified     BIT(1)       NOT NULL,
    is_blocked      BIT(1)       NOT NULL,
    is_expired      BIT(1)       NOT NULL,
    is_enabled      BIT(1)       NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE users_role
(
    role_id   BIGINT       NOT NULL,
    role_name VARCHAR(255) NOT NULL,
    user_id   BIGINT       NOT NULL,
    CONSTRAINT pk_users_role PRIMARY KEY (role_id)
);

ALTER TABLE users_role
    ADD CONSTRAINT FK_USERS_ROLE_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);