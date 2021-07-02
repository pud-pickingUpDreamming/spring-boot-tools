drop table if exists user_info;
CREATE TABLE user_info
(
    `id` UInt64,
    `user_name` String,
    `password` String,
    `phone` String,
    `email` String,
    `create_time` Date
)
ENGINE = MergeTree
ORDER BY id
SETTINGS index_granularity = 8192;

drop table if exists dict;
CREATE TABLE dict
(
    `id` UInt64,
    `type` String,
    `key` String,
    `value` String,
    `create_time` Date
)
ENGINE = MergeTree
ORDER BY id
SETTINGS index_granularity = 8192;

