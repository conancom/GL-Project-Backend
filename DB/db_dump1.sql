-- DROP TYPE public.accounttype;

CREATE TYPE public.accounttype AS ENUM (
	'STEAM',
	'EPIC',
	'UBISOFT',
	'ORIGIN',
	'GOG');

-- DROP TYPE public."_accounttype";

CREATE TYPE public."_accounttype" (
	INPUT = array_in,
	OUTPUT = array_out,
	RECEIVE = array_recv,
	SEND = array_send,
	ANALYZE = array_typanalyze,
	ALIGNMENT = 4,
	STORAGE = any,
	CATEGORY = A,
	ELEMENT = public.accounttype,
	DELIMITER = ',');
