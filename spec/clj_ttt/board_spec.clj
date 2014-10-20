(ns clj-ttt.board-spec
  (:require [speclj.core :refer :all]
            [clj-ttt.core :refer :all]))

(defn create-board [size]
  (vec (replicate (* size size) "-")))

(defn play-move [board square mark]
  (assoc board square mark))

(describe "creating an empty board"
          (it "creates an empty board string"
              (should= "---------" (apply str (create-board 3)))
          )
          (it "creates a 4x4 board string"
              (should= "----------------" (apply str (create-board 4)))
          )
)

(describe "playing a move"
          (it "plays an X"
              (should= "--X------" (apply str (play-move (vec "---------") 2 "X"))))
          )

