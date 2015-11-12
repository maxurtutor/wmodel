CREATE TABLE t_group (
  group_id INT PRIMARY KEY AUTO_INCREMENT,
  name     VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE t_user (
  user_id  INT PRIMARY KEY AUTO_INCREMENT,
  group_id INT          NOT NULL,
  name     VARCHAR(100) NOT NULL UNIQUE,
  FOREIGN KEY (group_id)
  REFERENCES t_group (group_id)
);


