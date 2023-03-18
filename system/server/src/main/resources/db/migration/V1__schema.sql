CREATE TABLE event_data (
    id INT PRIMARY KEY IDENTITY,
    hours VARCHAR,
    intervals INT,
    date_created TIMESTAMP NOT NULL DEFAULT (now() at time zone 'utc'),
    date_modified TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE offered_services (
    id INT PRIMARY KEY IDENTITY,
    name VARCHAR NOT NULL,
    price DECIMAL,
    date_created TIMESTAMP NOT NULL DEFAULT (now() at time zone 'utc'),
    date_modified TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE doctors (
    id UUID PRIMARY KEY IDENTITY,
    application_user_id UUID REFERENCES application_users(id),
    date_created TIMESTAMP NOT NULL DEFAULT (now() at time zone 'utc'),
    date_modified TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE cabinet (
    id INT PRIMARY KEY IDENTITY,
    name VARCHAR NOT NULL,
    city VARCHAR NOT NULL,
    address VARCHAR NOT NULL,
    post_code VARCHAR NOT NULL,
    mobile_phone VARCHAR NOT NULL
    doctor_id UUID REFERENCES doctors(id),
    date_created TIMESTAMP NOT NULL DEFAULT (now() at time zone 'utc'),
    date_modified TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE
)

CREATE TABLE schedules(
    id UUID PRIMARY KEY IDENTITY,
    cabinet_id UUID REFERENCES cabinets(id),
    date_created TIMESTAMP NOT NULL DEFAULT (now() at time zone 'utc'),
    date_modified TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE roles (
    id UUID PRIMARY KEY IDENTITY,
    name VARCHAR NOT NULL,
    application_user_id UUID REFERENCES application_users(id),
    date_created TIMESTAMP NOT NULL DEFAULT (now() at time zone 'utc'),
    date_modified TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE
);

