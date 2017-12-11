DROP TABLE IF EXISTS public.tag;
DROP TABLE IF EXISTS public.catalog;
DROP TABLE IF EXISTS public.tag_catalog;

/* tables */
CREATE TABLE public.catalog
(
  catalog_pk SERIAL PRIMARY KEY,
  first    VARCHAR(400),
  last     VARCHAR(400)
);

CREATE TABLE public.tag
(
  tag_pk SERIAL PRIMARY KEY,
  data   VARCHAR(400) NOT NULL
);

CREATE TABLE public.tag_catalog
(
  catalog_pk INTEGER NOT NULL ,
  tag_pk INTEGER NOT NULL
);
