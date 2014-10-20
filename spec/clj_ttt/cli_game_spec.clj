(ns clj-ttt.cli-game-spec
  (:require [speclj.core :refer :all]
            [clj-ttt.cli-game :refer :all]))

(describe "outputs a board string"
          (it "outputs an empty game board"
              (should= "1 2 3\n" (board-string "---" 3)))
          (it "splits game board by newline"
              (should= "1 2 3\n4 5 6\n" (board-string "------" 3)))
          (it "shows mark for played square"
              (should= "X\n" (board-string "X" 1)))
          (it "shows mixed game"
              (should= "X O X\n4 5 6\n" (board-string "XOX---" 3))))

(describe "output"
          (it "shows board and next go message"
              (should= "1 2 3\n4 5 6\n7 8 9\nX's go, choose a square: " (display "---------" 3))))
