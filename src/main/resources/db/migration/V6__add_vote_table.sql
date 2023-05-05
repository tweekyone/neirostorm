CREATE TABLE vote
(
    id          INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    practice_id INTEGER NOT NULL,
    ip_address  TEXT    NOT NULL,
    vote_value  INTEGER NOT NULL,
    FOREIGN KEY (practice_id) REFERENCES practices (id),
    CONSTRAINT unique_vote_per_user_per_practice UNIQUE (practice_id, ip_address)
);

CREATE INDEX vote_ip_address_idx ON vote (ip_address);
CREATE INDEX vote_ip_address_and_practice_idx ON vote (practice_id,ip_address);

ALTER TABLE practices DROP COLUMN IF EXISTS likes;
ALTER TABLE practices DROP COLUMN IF EXISTS dislikes;