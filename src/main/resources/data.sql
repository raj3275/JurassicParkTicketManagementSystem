INSERT INTO tickets (userName, name, price, date, venue, pet_allowed, parking_allowed) VALUES
('Raj Patel','Rahul Dravid', 11.50, '18 Nov', 'Brampton', 'Yes', 'Yes'),
('Raj Patel', 'Bhoomi Bachani', 11.00, '19 Nov', 'Toronto', 'No', 'Yes'),
('Elizabeth Olsen', 'David Galivan', 11.00, '18 Nov', 'Brampton', 'No', 'Yes'),
('Elizabeth Olsen', 'Sara Trump', 10.50, '20 Nov', 'Toronto', 'Yes', 'No'),
('Chris Evans', 'John Walker', 11.00, '19 Nov', 'Toronto', 'No', 'No'),
('Chris Evans', 'Karen Lee', 11.50, '18 Nov', 'Toronto', 'No', 'No'),
('Dakshil Chaudhary', 'Layla Johnson', 11.50, '18 Nov', 'Brampton', 'No', 'No'),
('Dakshil Chaudhary', 'Matt Murdock', 10.50, '20 Nov', 'Brampton', 'Yes', 'Yes'),
('Karina Thakkar', 'Karan Thakkar', 11.00, '19 Nov', 'Brampton', 'No', 'Yes'),
('Karina Thakkar', 'Niyati Thakkar', 11.50, '18 Nov', 'Brampton', 'Yes', 'Yes');

insert into SEC_User (userName, encryptedPassword, ENABLED)
values ('Chris Evans', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);

insert into SEC_User (userName, encryptedPassword, ENABLED)
values ('Dakshil Chaudhary', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);

insert into SEC_User (userName, encryptedPassword, ENABLED)
values ('Elizabeth Olsen', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);

insert into SEC_User (userName, encryptedPassword, ENABLED)
values ('Jon', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);

insert into SEC_User (userName, encryptedPassword, ENABLED)
values ('Karina Thakkar', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);

insert into SEC_User (userName, encryptedPassword, ENABLED)
values ('Raj Patel', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);

insert into sec_role (roleName)
values ('ROLE_VENDOR');

insert into sec_role (roleName)
values ('ROLE_GUEST');
 
insert into user_role (userId, roleId)
values (1, 2);
 
insert into user_role (userId, roleId)
values (2, 2);

insert into user_role (userId, roleId)
values (3, 2);

insert into user_role (userId, roleId)
values (4, 1);

insert into user_role (userId, roleId)
values (5, 2);

insert into user_role (userId, roleId)
values (6, 2);