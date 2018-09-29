create table user_subscriptions(
  chanel_id integer not null references usr,
  subscriber_id integer not null references usr,
  primary key (chanel_id, subscriber_id)
)