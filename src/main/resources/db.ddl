CREATE TABLE t_group (
  group_id CHAR(36) PRIMARY KEY,
  name     VARCHAR(100)
);

CREATE TABLE t_user (
  user_id  CHAR(36) PRIMARY KEY,
  name     VARCHAR(100),
  group_id CHAR(36),
  FOREIGN KEY (group_id)
  REFERENCES t_group (group_id)
);