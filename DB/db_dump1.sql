-- DROP TYPE public.accounttype;

CREATE TYPE public.accounttype AS ENUM (
	'STEAM',
	'EPIC',
	'UBISOFT',
	'ORIGIN',
	'GOG');
