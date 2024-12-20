-- Enable pgvector extension
CREATE EXTENSION IF NOT EXISTS vector;

-- Create sample table
CREATE TABLE embeddings (
    id SERIAL PRIMARY KEY,
    text  VARCHAR(255) NOT NULL,
    embedding vector(1536) -- vector data
);

CREATE TABLE embeddings (
	id SERIAL PRIMARY KEY,
	"text" varchar(255) NULL,
	embedding vector NULL
);

CREATE INDEX ON embeddings USING HNSW (embedding vector_cosine_ops);
