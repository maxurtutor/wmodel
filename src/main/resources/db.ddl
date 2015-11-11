CREATE TABLE t_group (
  group_id    CHAR(36) PRIMARY KEY,
  name        VARCHAR(100) UNIQUE,
  update_date DATETIME NOT NULL
);

CREATE TABLE t_user (
  user_id  CHAR(36) PRIMARY KEY,
  name     VARCHAR(100) UNIQUE,
  group_id CHAR(36) NOT NULL,
  FOREIGN KEY (group_id)
  REFERENCES t_group (group_id)
);