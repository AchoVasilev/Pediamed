CREATE TABLE event_data (
    id INT PRIMARY KEY,
    hours VARCHAR,
    intervals INT,
    date_created TIMESTAMP NOT NULL DEFAULT (now() at time zone 'utc'),
    date_modified TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE offered_services (
    id INT PRIMARY KEY,
    name VARCHAR NOT NULL,
    price DECIMAL,
    date_created TIMESTAMP NOT NULL DEFAULT (now() at time zone 'utc'),
    date_modified TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE appointment_causes (
    id INT PRIMARY KEY,
    name VARCHAR NOT NULL,
    date_created TIMESTAMP NOT NULL DEFAULT (now() at time zone 'utc'),
    date_modified TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE application_users (
    id UUID PRIMARY KEY,
    email VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    first_name VARCHAR NOT NULL,
    middle_name VARCHAR NOT NULL,
    last_name VARCHAR NOT NULL,
    mobile_phone VARCHAR NOT NULL,
    date_created TIMESTAMP NOT NULL DEFAULT (now() at time zone 'utc'),
    date_modified TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE roles (
    id UUID PRIMARY KEY,
    name VARCHAR NOT NULL,
    application_user_id UUID REFERENCES application_users(id),
    date_created TIMESTAMP NOT NULL DEFAULT (now() at time zone 'utc'),
    date_modified TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE doctors (
    id UUID PRIMARY KEY,
    application_user_id UUID REFERENCES application_users(id),
    date_created TIMESTAMP NOT NULL DEFAULT (now() at time zone 'utc'),
    date_modified TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE parents (
    id UUID PRIMARY KEY,
    application_user_id UUID REFERENCES application_users(id),
    date_created TIMESTAMP NOT NULL DEFAULT (now() at time zone 'utc'),
    date_modified TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE patients (
    id UUID PRIMARY KEY,
    first_name VARCHAR NOT NULL,
    last_name VARCHAR NOT NULL,
    age INT,
    birth_date DATE NOT NULL,
    parent_id UUID REFERENCES parents(id),
    date_created TIMESTAMP NOT NULL DEFAULT (now() at time zone 'utc'),
    date_modified TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE specializations (
    id INT PRIMARY KEY,
    name VARCHAR NOT NULL,
    description VARCHAR NOT NULL,
    doctor_id UUID REFERENCES doctors(id)
);

CREATE TABLE cabinets (
    id INT PRIMARY KEY,
    name VARCHAR NOT NULL,
    city VARCHAR NOT NULL,
    address VARCHAR NOT NULL,
    post_code VARCHAR NOT NULL,
    mobile_phone VARCHAR NOT NULL,
    doctor_id UUID REFERENCES doctors(id),
    date_created TIMESTAMP NOT NULL DEFAULT (now() at time zone 'utc'),
    date_modified TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE schedules (
    id UUID PRIMARY KEY,
    cabinet_id INT REFERENCES cabinets(id),
    date_created TIMESTAMP NOT NULL DEFAULT (now() at time zone 'utc'),
    date_modified TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE calendar_events(
    id INT PRIMARY KEY,
    start_date DATE,
    end_date DATE,
    schedule_id UUID REFERENCES schedules(id),
    date_created TIMESTAMP NOT NULL DEFAULT (now() at time zone 'utc'),
    date_modified TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE appointments (
    id UUID PRIMARY KEY,
    start_date DATE,
    end_date DATE,
    title VARCHAR NOT NULL,
    appointment_cause_id INT REFERENCES appointment_causes(id),
    schedule_id UUID REFERENCES schedules(id),
    parent_id UUID REFERENCES parents(id),
    patient_id UUID REFERENCES patients(id),
    calendar_event_id INT REFERENCES calendar_events(id),
    date_created TIMESTAMP NOT NULL DEFAULT (now() at time zone 'utc'),
    date_modified TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE
);