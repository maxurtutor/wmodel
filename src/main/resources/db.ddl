CREATE TABLE t_group (
  group_id INT PRIMARY KEY,
  name     VARCHAR(100)
);

CREATE TABLE t_user (
  user_id  INT PRIMARY KEY AUTO_INCREMENT,
  name     VARCHAR(100),
  group_id INT,
  FOREIGN KEY (group_id)
  REFERENCES t_group (group_id)
);