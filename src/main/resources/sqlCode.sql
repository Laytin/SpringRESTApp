create Table Customer
(
    id       int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    username varchar(30)  NOT NULL UNIQUE,
    fullname varchar(30)  NOT NULL,
    email    varchar(255),
    phone    varchar(255) NOT NULL,
    password varchar(255) NOT NULL
)

create Table Car
(
    id          int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name        varchar(255) NOT NULL,
    number      varchar(255) NOT NULL,
    customer_id int REFERENCES Customer (id) ON DELETE CASCADE
)
create Table Trip
(
    id          int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    car_id      int REFERENCES Car (id),
    customer_id int REFERENCES Customer (id),
    free_sits   int          NOT NULL,
    place_from  varchar(255) NOT NULL,
    place_to    varchar(255) NOT NULL,
    time_out    TIMESTAMP
)
create Table triporder
(
    id          int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    trip_id     int REFERENCES Trip (id),
    customer_id int REFERENCES Customer (id),
    status      varchar(50) NOT NULL,
    sits        int CHECK ( sits > 0 )
)
