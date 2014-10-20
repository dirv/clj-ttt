(ns clj-ttt.board-spec
  (:require [speclj.core :refer :all]
            [clj-ttt.core :refer :all]))

(defn create-board [size]
  (vec (replicate (* size size) "-")))

(describe "creating an empty board"
          (it "creates an empty board string"
              (should= "---------" (apply str (create-board 3)))
          )
          (it "creates a 4x4 board string"
              (should= "----------------" (apply str (create-board 4)))
          )
)

