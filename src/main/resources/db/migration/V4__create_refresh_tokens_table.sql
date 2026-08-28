CREATE TABLE refresh_tokens (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    token VARCHAR(512) NOT NULL UNIQUE,
    expiry_date TIMESTAMP WITH TIME ZONE NOT NULL,
    practicante_id UUID NOT NULL,
    CONSTRAINT fk_refresh_token_practicante
        FOREIGN KEY (practicante_id)
        REFERENCES practicantes(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_refresh_tokens_token ON refresh_tokens(token);
CREATE INDEX idx_refresh_tokens_practicante_id ON refresh_tokens(practicante_id);
