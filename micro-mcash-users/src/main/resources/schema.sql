CREATE TABLE IF NOT EXISTS web_user(
  user_id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(100) NOT NULL,
  password varchar(255) NOT NULL,
  created datetime NOT NULL,
  updated datetime NOT NULL,
  email varchar(255) NOT NULL,
  PRIMARY KEY (user_id),
  UNIQUE KEY username_UNIQUE (username),
  UNIQUE KEY email_UNIQUE (email)
);