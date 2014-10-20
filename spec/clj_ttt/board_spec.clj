(ns clj-ttt.board-spec
  (:require [speclj.core :refer :all]
            [clj-ttt.core :refer :all]))

(defn create-board [size]
  (apply str (replicate (* size size) "-")))

(defn play-move [board square mark]
  (apply str (assoc (vec board) square mark)))

(describe "creating an empty board"
          (it "creates an empty board string"
              (should= "---------" (create-board 3))
          )
          (it "creates a 4x4 board string"
              (should= "----------------" (create-board 4))
          )
)

(describe "playing a move"
          (it "plays an X"
              (should= "--X------" (play-move "---------" 2 "X")))
          )

