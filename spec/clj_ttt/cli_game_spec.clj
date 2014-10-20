(ns clj-ttt.cli-game-spec
  (:require [speclj.core :refer :all]
            [clj-ttt.cli-game :refer :all]))


(describe "outputs a board string"
          (it "outputs an empty game board"
              (should= "1 2 3" (board-string "---"))))
