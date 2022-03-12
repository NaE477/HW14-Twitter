CREATE TABLE IF NOT EXISTS users(
    user_id serial primary key ,
    firstname varchar(50),
    lastname varchar(50),
    username varchar(50),
    password varchar(50),
    email varchar(50)
);

CREATE TABLE IF NOT EXISTS twits(
    twit_id serial primary key ,
    twit_content varchar(280),
    user_id integer,
    twit_date timestamp,

    foreign key (user_id) references users(user_id)
);

CREATE TABLE IF NOT EXISTS comments(
    comment_id serial primary key ,
    comment_content varchar(280),
    twit_id integer,
    user_id integer,
    comment_date timestamp,

    foreign key (twit_id) references twits(twit_id),
    foreign key (user_id) references users(user_id)
);