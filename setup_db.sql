-- Set up DB

DROP TABLE user_profile CASCADE CONSTRAINTS;
DROP TABLE group_profile CASCADE CONSTRAINTS;
DROP TABLE message CASCADE CONSTRAINTS;
DROP TABLE friendship CASCADE CONSTRAINTS;
DROP TABLE user_group CASCADE CONSTRAINTS;


CREATE TABLE user_profile (
	full_name varchar2(32),
	email varchar2(32),
	dob   TIMESTAMP,
	last_login TIMESTAMP,
	CONSTRAINT uname_user_pk PRIMARY KEY(email)
);

CREATE TABLE group_profile (
	gname varchar2(32),
	description varchar2(70),
	member_limit number(10),
	CONSTRAINT gname_u_pk PRIMARY KEY(gname)
);

CREATE TABLE message (
	sender_email varchar2(32),	
	recipient_email varchar2(32),
	subject varchar2(50),
	text_body varchar2(100),
	date_sent TIMESTAMP,
	CONSTRAINT sender_fk FOREIGN KEY(sender_email) REFERENCES user_profile(email),
	CONSTRAINT recipient_fk FOREIGN KEY(recipient_email) REFERENCES user_profile(email)
);

CREATE TABLE friendship ( 
	sender_email varchar2(32),
	accepter_email varchar2(32),
	status number(1), 
	date_established TIMESTAMP,
	CONSTRAINT a_fk FOREIGN KEY (sender_email) REFERENCES user_profile(email),
	CONSTRAINT b_fk FOREIGN KEY (accepter_email) REFERENCES user_profile(email)	
);

CREATE TABLE user_group (
	group_profile varchar2(32),
	email_profile varchar2(32),
	CONSTRAINT user_fk FOREIGN KEY(email_profile) REFERENCES user_profile(email)
);

commit; 

	 
