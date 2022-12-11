-- Make sure to create a Database called ProjectGL

-- DROP TYPE public.accounttype;

CREATE TYPE public.accounttype AS ENUM (
	'STEAM',
	'EPIC',
	'UBISOFT',
	'ORIGIN',
	'GOG');

-- Drop table

-- DROP TABLE public.game;

CREATE TABLE public.game (
	id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
	"name" varchar NOT NULL,
	title varchar NOT NULL,
	information text NOT NULL,
	profileimg text NOT NULL,
	backgroundimg text NOT NULL,
	creationtimestamp timestamp NOT NULL,
	updatetimestamp timestamp NOT NULL,
	CONSTRAINT game_pk PRIMARY KEY (id)
);
CREATE INDEX game_creationtimestamp_idx ON public.game USING btree (creationtimestamp);
CREATE INDEX game_name_idx ON public.game USING btree (name);
CREATE INDEX game_updatetimestamp_idx ON public.game USING btree (updatetimestamp);

-- Drop table

-- DROP TABLE public."user";

CREATE TABLE public."user" (
	id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
	email varchar NOT NULL,
	username varchar NOT NULL,
	"password" varchar NOT NULL,
	"salt" varchar NOT NULL,
	registrationtimestamp timestamp NOT NULL,
	updatetimestamp timestamp NOT NULL,
	lastlogintimestamp timestamp NOT NULL,
	CONSTRAINT user_pk PRIMARY KEY (id)
);
CREATE INDEX user_email_idx ON public."user" USING btree (email);
CREATE INDEX user_lastlogintimestamp_idx ON public."user" USING btree (lastlogintimestamp);
CREATE INDEX user_registrationtimestamp_idx ON public."user" USING btree (registrationtimestamp);
CREATE INDEX user_updatetimestamp_idx ON public."user" USING btree (updatetimestamp);
CREATE INDEX user_username_idx ON public."user" USING btree (username);

-- Drop table

-- DROP TABLE public.registered_library_account;

CREATE TABLE public.registered_library_account (
	id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
	"type" varchar NOT NULL,
	userid int8 NOT NULL,
	apikey varchar NULL,
	creationtimestamp timestamp NOT NULL,
	updatetimestamp timestamp NOT NULL,
	CONSTRAINT registered_library_account_pk PRIMARY KEY (id),
	CONSTRAINT registered_library_account_fk FOREIGN KEY (userid) REFERENCES public."user"(id) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE INDEX registered_library_account_creationtimestamp_idx ON public.registered_library_account USING btree (creationtimestamp);
CREATE INDEX registered_library_account_updatetimestamp_idx ON public.registered_library_account USING btree (updatetimestamp);

-- Drop table

-- DROP TABLE public.personalgameinformation;

CREATE TABLE public.personalgameinformation (
	id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
	registeredlibraryaccountid int8 NOT NULL,
	gameid int8 NOT NULL,
	creationtimestamp timestamp NOT NULL,
	updatetimestamp timestamp NOT NULL,
	CONSTRAINT personalgameinformation_pk PRIMARY KEY (id),
	CONSTRAINT personalgameinformation_fk FOREIGN KEY (registeredlibraryaccountid) REFERENCES public.registered_library_account(id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT personalgameinformation_fk_1 FOREIGN KEY (gameid) REFERENCES public.game(id) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE INDEX personalgameinformation_creationtimestamp_idx ON public.personalgameinformation USING btree (creationtimestamp);
CREATE INDEX personalgameinformation_updatetimestamp_idx ON public.personalgameinformation USING btree (updatetimestamp);

-- Drop table

-- DROP TABLE public.session;

CREATE TABLE public."session" (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    sessionid varchar NOT NULL,
    userid bigint NOT NULL,
    creationtimestamp timestamp NOT NULL,
    updatetimestamp timestamp NOT NULL,
    expirationtimestamp timestamp NOT NULL,
    CONSTRAINT session_pk PRIMARY KEY (id)
);
CREATE INDEX session_sessionid_idx ON public."session" (sessionid);
CREATE INDEX session_creationtimestamp_idx ON public."session" (creationtimestamp);
CREATE INDEX session_updatetimestamp_idx ON public."session" (updatetimestamp);
CREATE INDEX session_expirationtimestamp_idx ON public."session" (expirationtimestamp);
