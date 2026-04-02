CREATE SCHEMA "forum";
ALTER SCHEMA "forum" OWNER TO "forum";

REVOKE ALL ON DATABASE "forum" FROM public;
REVOKE ALL ON SCHEMA "forum" FROM public;

GRANT CONNECT ON DATABASE "forum" TO "forum";
GRANT USAGE ON SCHEMA "forum" TO "forum";
