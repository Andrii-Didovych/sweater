insert into usr (id, username, password, active, activation_code, photo)
    values (1, 'admin', '$2a$08$o189hMuWb0kBCc6nXDzfZuQhKz5i08oRZUFPsKHomyttBx9zCsAS6', true, '5aa4030d-e902-4f61-acd1-c926b0680581', 'default-photo-of-user.png');

insert into user_role (user_id, roles)
    values (1, 'USER'), (1, 'ADMIN');

