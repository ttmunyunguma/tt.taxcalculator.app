--create tax_codes table
CREATE TABLE IF NOT EXISTS public.tax_codes
(
    id      integer      NOT NULL,
    code    varchar(100) NOT NULL,
    summary text,
    PRIMARY KEY (id)
);
