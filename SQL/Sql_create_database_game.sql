/*
 Navicat Premium Data Transfer

 Source Server         : Game DB
 Source Server Type    : PostgreSQL
 Source Server Version : 120022
 Source Host           : localhost:5432
 Source Catalog        : snake_tetris_game
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 120022
 File Encoding         : 65001

 Date: 11/04/2025 22:18:41
*/


-- ----------------------------
-- Sequence structure for players_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."players_id_seq";
CREATE SEQUENCE "public"."players_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for snake_game_sessions_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."snake_game_sessions_id_seq";
CREATE SEQUENCE "public"."snake_game_sessions_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Table structure for players
-- ----------------------------
DROP TABLE IF EXISTS "public"."players";
CREATE TABLE "public"."players" (
  "id" int4 NOT NULL DEFAULT nextval('players_id_seq'::regclass),
  "username" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "created_at" timestamp(6) DEFAULT now(),
  "created_by" varchar(50) COLLATE "pg_catalog"."default",
  "password" varchar(50) COLLATE "pg_catalog"."default",
  "email" varchar(200) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for snake_game_sessions
-- ----------------------------
DROP TABLE IF EXISTS "public"."snake_game_sessions";
CREATE TABLE "public"."snake_game_sessions" (
  "id" int4 NOT NULL DEFAULT nextval('snake_game_sessions_id_seq'::regclass),
  "player_id" int4 NOT NULL,
  "score" int4,
  "duration" int4,
  "played_at" timestamp(6) DEFAULT now(),
  "level" int4,
  "status" varchar(50) COLLATE "pg_catalog"."default",
  "created_by" varchar(100) COLLATE "pg_catalog"."default",
  "update_by" varchar(100) COLLATE "pg_catalog"."default",
  "update_at" timestamp(6)
)
;

-- ----------------------------
-- Table structure for snake_leaderboard
-- ----------------------------
DROP TABLE IF EXISTS "public"."snake_leaderboard";
CREATE TABLE "public"."snake_leaderboard" (
  "player_id" int4 NOT NULL,
  "highest_score" int4 NOT NULL DEFAULT 0,
  "created_at" timestamp(6),
  "created_by" varchar(50) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Function structure for update_snake_leaderboard
-- ----------------------------
DROP FUNCTION IF EXISTS "public"."update_snake_leaderboard"();
CREATE OR REPLACE FUNCTION "public"."update_snake_leaderboard"()
  RETURNS "pg_catalog"."trigger" AS $BODY$
BEGIN
    INSERT INTO snake_leaderboard (player_id, highest_score)
    VALUES (NEW.player_id, NEW.score)
    ON CONFLICT (player_id) 
    DO UPDATE SET highest_score = GREATEST(snake_leaderboard.highest_score, NEW.score);
    RETURN NEW;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."players_id_seq"
OWNED BY "public"."players"."id";
SELECT setval('"public"."players_id_seq"', 2, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."snake_game_sessions_id_seq"', 11, true);

-- ----------------------------
-- Uniques structure for table players
-- ----------------------------
ALTER TABLE "public"."players" ADD CONSTRAINT "players_username_key" UNIQUE ("username");

-- ----------------------------
-- Primary Key structure for table players
-- ----------------------------
ALTER TABLE "public"."players" ADD CONSTRAINT "players_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Triggers structure for table snake_game_sessions
-- ----------------------------
CREATE TRIGGER "update_snake_leaderboard_trigger" AFTER INSERT ON "public"."snake_game_sessions"
FOR EACH ROW
EXECUTE PROCEDURE "public"."update_snake_leaderboard"();

-- ----------------------------
-- Primary Key structure for table snake_game_sessions
-- ----------------------------
ALTER TABLE "public"."snake_game_sessions" ADD CONSTRAINT "snake_game_sessions_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table snake_leaderboard
-- ----------------------------
ALTER TABLE "public"."snake_leaderboard" ADD CONSTRAINT "snake_leaderboard_pkey" PRIMARY KEY ("player_id");

-- ----------------------------
-- Foreign Keys structure for table snake_game_sessions
-- ----------------------------
ALTER TABLE "public"."snake_game_sessions" ADD CONSTRAINT "snake_game_sessions_player_id_fkey" FOREIGN KEY ("player_id") REFERENCES "public"."players" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table snake_leaderboard
-- ----------------------------
ALTER TABLE "public"."snake_leaderboard" ADD CONSTRAINT "snake_leaderboard_player_id_fkey" FOREIGN KEY ("player_id") REFERENCES "public"."players" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
