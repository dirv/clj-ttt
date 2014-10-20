(ns clj-ttt.cli-game-spec
  (:require [speclj.core :refer :all]
            [clj-ttt.cli-game :refer :all]))


(describe "outputs a board string"
          (it "outputs an empty game board"
              (should= "1 2 3\n" (board-string "---" 3)))
          (it "splits game board by newline"
              (should= "1 2 3\n4 5 6\n" (board-string "------" 3))))
