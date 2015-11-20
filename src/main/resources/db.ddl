CREATE TABLE t_group (
  group_id CHAR(36) PRIMARY KEY,
  name     VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE t_user (
  user_id  CHAR(36) PRIMARY KEY,
  group_id CHAR(36)     NOT NULL,
  name     VARCHAR(100) NOT NULL UNIQUE,
  FOREIGN KEY (group_id)
  REFERENCES t_group (group_id)
);


